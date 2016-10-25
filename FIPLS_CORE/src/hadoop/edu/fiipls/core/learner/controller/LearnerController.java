package hadoop.edu.fiipls.core.learner.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;

import edu.fiipls.model.JobBean;
import edu.fiipls.model.LearningResultModel;
import edu.fiipls.rest.client.PostResults;
import edu.fiipls.rest.client.bean.PostResultsBean;
import hadoop.edu.fiipls.core.learner.beans.BaseJobBean;
import hadoop.edu.fiipls.core.learner.beans.DataSetBean;
import hadoop.edu.fiipls.core.learner.beans.ResultBean;
import hadoop.edu.fiipls.core.learner.hadoop.CreateKnowlede;
import hadoop.edu.fiipls.core.learner.hadoop.FIleSaver;
import hadoop.edu.fiipls.core.learner.hadoop.dataset.CreateNewDataSet;
import hadoop.edu.fiipls.core.learner.hadoop.learner.ArrfCreator;
import hadoop.edu.fiipls.core.learner.hadoop.score.ScoreModel;
import hadoop.edu.fiipls.core.learner.inconsistency.InconsistencyChecker;

public class LearnerController {
	

	public String createNamesFile(BaseJobBean bean, String cols) throws IOException {
		File f = new File("/home/hduser/execution/header.names");
		if (f.exists()) {
			f.delete();
		}
		BufferedWriter writer = new BufferedWriter(new FileWriter("/home/hduser/execution/header.names"));
		
		int i =0;
		for (String column : cols.split(bean.getInputSplit())) {
			if(bean.getColumnInfo().charAt(i)=='1'){
				writer.write(column + "\n");	
			}
			i++;
		}
		writer.flush();
		writer.close();
		return "/home/hduser/execution/header.names";
	}
	
	public void startLearning(DataSetBean bean) throws Exception{

		System.out.println("Starting the learning");
		initialLearner(bean);
		System.out.println("Scoring the initial learning.");
		ResultBean b = scoreTheLearntModel(bean);
		InconsistencyChecker checker = new InconsistencyChecker();
		while(!checker.isInconsistent(bean, b)){
			System.out.println("Relearning....");
			relearner(bean);
			System.out.println("Scoring....");
			b = scoreTheLearntModel(bean);
			System.out.println("Checking for Inconsistency....");
		}
	}
	

	public String getLearningOptions(BaseJobBean bean, String modelName, String options, String classifierPath) {
		String option = "-hdfs-host " + bean.getHdfsPath() + "-hdfs-port " + bean.getHdfsPort() + ""
				+ "-jobtracker-host " + bean.getYarnPath() + " -jobtracker-port " + bean.getYarnPort() + " "
				+ "-existing-header "+bean.getHeaderFile()+" " + "-class " + bean.getAttributeToFind()
				+ " -input-paths " + bean.getInputPath() + " " + "-output-path " + classifierPath + " "
				+ "-model-file-name " + modelName + " " + "-preserve-order -num-chunks 10 -num-folds 10"
				+ "-max-split-size 100000 -M ? -E ' -F , " + "-W " + options;
		return option;
	}

	public void createDataSet(DataSetBean bean) throws Exception {
		CreateNewDataSet dataset = new CreateNewDataSet();
		dataset.execute(bean);
	}

	public ResultBean scoreTheLearntModel(DataSetBean bean) throws Exception {
		ScoreModel model = new ScoreModel();
		return model.execute(bean);
	}

	public void relearner(DataSetBean bean) throws Exception {
		createDataSet(bean);
		ArrfCreator arff = new ArrfCreator();
		String arffFile = createNamesFile(bean, bean.getColumnInfo());
		arff.generateArffFile(bean, arffFile);
		CreateKnowlede classfier1 = new CreateKnowlede("Classifier1",
				getLearningOptions(bean, "Classifier1", bean.getClassifier1Config(), bean.getClassifier1Path()));
		CreateKnowlede classfier2 = new CreateKnowlede("Classifier2",
				getLearningOptions(bean, "Classifier2", bean.getClassifier2Config(), bean.getClassifier2Path()));
		classfier1.start();
		classfier2.start();
		classfier1.join();
		classfier2.join();
		setNextCombination(bean);
	}

	public void initialLearner(DataSetBean bean) throws Exception {
		createDataSet(bean);
		FIleSaver fs = new FIleSaver();
		fs.execute(bean);
		ArrfCreator arff = new ArrfCreator();
		String arffFile = createNamesFile(bean, bean.getColumnInfo());
		arff.generateArffFile(bean, arffFile);
		CreateKnowlede classfier1 = new CreateKnowlede("Classifier1", bean.getClassifier1Config());
		CreateKnowlede classfier2 = new CreateKnowlede("Classifier1", bean.getClassifier2Config());
		classfier1.start();
		classfier2.start();
		classfier1.join();
		classfier2.join();
		setNextCombination(bean);
	}

	public void setNextCombination(DataSetBean bean) {

		bean.setNextCombination(
				StringUtils.leftPad(Integer.toBinaryString(Integer.parseInt(bean.getNextCombination()) - 2), 16, '0'));

	} 

	public void postResults(ResultBean bean, BaseJobBean job) {
		PostResults results = new PostResults();
		PostResultsBean post = new PostResultsBean();
		JobBean jbean = new JobBean();
		jbean.setClassifier1Model("J48");
		jbean.setClassifier2Model("NaiveBayes");
		jbean.setColumns(job.getColumnInfo());
		jbean.setJobId("first");
		jbean.setTotalRows(""+job.getNoOfRows());
		post.setJob(jbean);
		results.postNewResult(post);
	}
	
	public void postJobDetails(ResultBean bean, BaseJobBean job) {
		PostResults results = new PostResults();
		PostResultsBean post = new PostResultsBean();
		LearningResultModel model = new LearningResultModel();
		model.setClassifier1Correct("" + bean.getClassifier1Correct());
		model.setClassifier2Correct("" + bean.getClassifier2Correct());
		model.setConsistent("" + bean.getConsistentCases());
		model.setJobId("first");
		model.setNonConsistent("" + bean.getNonConsistentCases());
		post.setResult(model);
		results.postNewResult(post);
	}

	public int getNoOfColumns(BaseJobBean bean) {
		return bean.getColumnInfo().split(bean.getInputSplit()).length;
	}

}
