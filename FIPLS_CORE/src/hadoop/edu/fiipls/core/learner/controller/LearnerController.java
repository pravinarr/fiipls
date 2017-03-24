package hadoop.edu.fiipls.core.learner.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;

import edu.fiipls.model.JobBean;
import edu.fiipls.model.LearningResultModel;
import edu.fiipls.rest.client.FileUploaderClient;
import edu.fiipls.rest.client.PostResults;
import edu.fiipls.rest.client.bean.PostResultsBean;
import hadoop.edu.fiipls.core.learner.beans.BaseJobBean;
import hadoop.edu.fiipls.core.learner.beans.DataSetBean;
import hadoop.edu.fiipls.core.learner.beans.ResultBean;
import hadoop.edu.fiipls.core.learner.hadoop.CreateKnowlede;
import hadoop.edu.fiipls.core.learner.hadoop.FileCopyToHdfs;
import hadoop.edu.fiipls.core.learner.hadoop.dataset.CreateNewDataSet;
import hadoop.edu.fiipls.core.learner.hadoop.dataset.CreateTestDataSet;
import hadoop.edu.fiipls.core.learner.hadoop.learner.ArrfCreator;
import hadoop.edu.fiipls.core.learner.hadoop.score.ScoreModel;
import hadoop.edu.fiipls.core.learner.inconsistency.InconsistencyChecker;
import hadoop.edu.fiipls.core.learner.relearner.RelearningTrigger;
import hadoop.edu.fiipls.core.learner.utils.FileCleaner;
import hadoop.edu.fiipls.core.learner.utils.UtilFunctions;

public class LearnerController {

	public String createNamesFile(DataSetBean bean, String cols) throws IOException {
		File f = new File("/home/hduser/execution/header.names");
		if (f.exists()) {
			f.delete();
		}
		String columns = "";
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File("/home/hduser/execution/header.names")));

		int i = 0;
		for (String column : cols.split(bean.getInputSplit())) {
			if (bean.getNextCombination().charAt(i) == '1') {
				writer.write(column + "\n");
				columns = columns + column +",";
			}
			i++;
		}
		bean.setCurrentColumns(columns.substring(0, (columns.length()-1)));
		writer.flush();
		writer.close();
		return "/home/hduser/execution/header.names";
	}

	public void createTestData(DataSetBean bean) throws Exception {

		CreateTestDataSet data = new CreateTestDataSet(bean);
		data.execute(bean);
	}
	
	public void checkForCurrentBest(DataSetBean bean) throws Exception{
		double best  = 100*(bean.getBestResults().getNonConsistentCases()/bean.getBestResults().getTotalCase());
		double curent = 100*(bean.getCurrentResult().getNonConsistentCases()/bean.getCurrentResult().getTotalCase());
		if( best > curent ){
			bean.setBestResults(bean.getCurrentResult());
			copyClassifiersAndUpload(bean);
		}
	}
	
	
	public void copyfromhdfs(String srcPath,String destPath) throws Exception{
		Runtime rt = Runtime.getRuntime();
		Process proc = rt.exec("/usr/local/hadoop/bin/hadoop fs -get " + srcPath
				+ " "+ destPath);
		proc.waitFor();
	}
	
	public void copyClassifiersAndUpload(DataSetBean bean) throws Exception{
		copyfromhdfs("/classifier1/model/Classifier1.model", "/home/hduser/classifiers/Classifier1.model");
		copyfromhdfs("/classifier2/model/Classifier2.model", "/home/hduser/classifiers/Classifier2.model");
		copyfromhdfs("/classifier2/model/Classifier2_arffHeader.arff", "/home/hduser/classifiers/sample.arff");
		FileUploaderClient client= new FileUploaderClient();
		client.upload("/home/hduser/classifiers/Classifier1.model");
		client.upload("/home/hduser/classifiers/Classifier2.model");
		client.upload("/home/hduser/classifiers/sample.arff");
		
	}

	public void startLearning(DataSetBean bean) throws Exception {

		System.out.println("Starting the learning");
		initialLearner(bean);
		System.out.println("Scoring the initial learning.");
		ResultBean b = scoreTheLearntModel(bean);
		bean.setBestResults(bean.getCurrentResult());
		copyClassifiersAndUpload(bean);
		postJobDetails(b, bean);
		postResults(b, bean);
		InconsistencyChecker checker = new InconsistencyChecker();
		while (!checker.isInconsistent(bean, b)) {
			checkForCurrentBest(bean);
			System.out.println("Relearning....");
			relearner(bean);
			System.out.println("Scoring....");
			b = scoreTheLearntModel(bean);
			postResults(b, bean);
			System.out.println("Checking for Inconsistency....");
		}
		RelearningTrigger relearn =  new RelearningTrigger();
		bean.setNextCombination(bean.getNextCombination().replaceAll("0", "1"));
		relearn.triggerLearning(bean);
	}

	public String getLearningOptions(BaseJobBean bean, String modelName, String options, String classifierPath) {
		String option = "-hdfs-host " + bean.getHdfsPath() + " -hdfs-port " + bean.getHdfsPort() + " "
				+ " -jobtracker-host " + bean.getYarnPath() + " -jobtracker-port " + bean.getYarnPort() + " "
				+ " -existing-header " + bean.getHeaderFile() + " " + " -class " + bean.getAttributeToFind()
				+ " -input-paths " + bean.getOutputPath() + "inputTrain.csv" + " " + " -output-path " + classifierPath
				+ " " + " -model-file-name " + modelName + " " + " -preserve-order -num-chunks 10 -num-folds 10"
				+ " -max-split-size 100000 -M ? -E ' -F , " + "-W " + options;
		return option;
	}

	public void createDataSet(DataSetBean bean) throws Exception {
		CreateNewDataSet dataset = new CreateNewDataSet(bean);
		dataset.execute(bean);
	}

	public ResultBean scoreTheLearntModel(DataSetBean bean) throws Exception {
		// String hdfsPath = HDFSUtils.WEKA_TEMP_DISTRIBUTED_CACHE_FILES +
		// modelNameOnly;

		ScoreModel model = new ScoreModel();
		return model.execute(bean);
	}

	public void relearner(DataSetBean bean) throws Exception {
		FileCleaner.clean();
		bean.setNoOfOnes(bean.getNextCombination().length() - bean.getNextCombination().replaceAll("1", "").length());
		FileCopyToHdfs sav = new FileCopyToHdfs();
		sav.writetoHdfs(bean);
		createDataSet(bean);
		createTestData(bean);
		System.out.println("Merging the dataset..");
		getMergeInHdfs(bean);
		System.out.println("Saving to hdfs the new dataset..");
		sav.writetoHdfs(bean, "/usr/local/hadoop/inputToTrain.csv","/usr/local/hadoop/inputToTest.csv");

		ArrfCreator arff = new ArrfCreator();
		String arffFile = createNamesFile(bean, bean.getColumnInfo());
		arff.generateArffFile(bean, arffFile);
		CreateKnowlede classfier1 = new CreateKnowlede(
				getLearningOptions(bean, "Classifier1.model", bean.getClassifier1Config(), bean.getClassifier1Path()),
				"Classifier1");

		CreateKnowlede classfier2 = new CreateKnowlede(
				getLearningOptions(bean, "Classifier2.model", bean.getClassifier2Config(), bean.getClassifier2Path()),
				"Classifier2");
		classfier1.start();
		classfier1.join();
		classfier2.start();
		classfier2.join();
		setNextCombination(bean);
	}
	
	public void prelimStepsForRelearning(DataSetBean bean) throws Exception{
		FileCopyToHdfs sav = new FileCopyToHdfs();
		sav.writeTesttoHdfs(bean);
		createTestData(bean);
		getTestDataFromHdfs(bean);
		sav.writeCreatedtoTesHdfs(bean, "/usr/local/hadoop/inputToTest.csv");
	}
	
	public boolean getTestDataFromHdfs(DataSetBean bean) throws IllegalArgumentException, IOException, InterruptedException {
		Runtime rt = Runtime.getRuntime();
		Process proc = rt.exec("/usr/local/hadoop/bin/hadoop fs -getmerge " + bean.getTestDirectory()
				+ "/part-m-* /usr/local/hadoop/inputToTest.csv");
		proc.waitFor();
		return true;
	}

	public boolean getMergeInHdfs(DataSetBean bean) throws IllegalArgumentException, IOException, InterruptedException {
		Runtime rt = Runtime.getRuntime();
		Process proc = rt.exec("/usr/local/hadoop/bin/hadoop fs -getmerge " + bean.getOutputPath()
				+ "/part-m-* /usr/local/hadoop/inputToTrain.csv");
		proc.waitFor();
		getTestDataFromHdfs(bean);
		return true;
	}

	public void initialLearner(DataSetBean bean) throws Exception {
		bean.setNoOfOnes(bean.getNextCombination().length() - bean.getNextCombination().replaceAll("1", "").length());
		bean.setHeaderFile("/output/arff/inputData.arff");
		FileCopyToHdfs sav = new FileCopyToHdfs();
		sav.writetoHdfs(bean);
		createDataSet(bean);
		createTestData(bean);
		System.out.println("Merging the dataset..");
		getMergeInHdfs(bean);
		System.out.println("Saving to hdfs the new dataset..");
		sav.writetoHdfs(bean, "/usr/local/hadoop/inputToTrain.csv","/usr/local/hadoop/inputToTest.csv");

		ArrfCreator arff = new ArrfCreator();
		String arffFile = createNamesFile(bean, bean.getColumnInfo());
		arff.generateArffFile(bean, arffFile);
		CreateKnowlede classfier1 = new CreateKnowlede(
				getLearningOptions(bean, "Classifier1.model", bean.getClassifier1Config(), bean.getClassifier1Path()),
				"Classifier1");

		CreateKnowlede classfier2 = new CreateKnowlede(
				getLearningOptions(bean, "Classifier2.model", bean.getClassifier2Config(), bean.getClassifier2Path()),
				"Classifier2");
		classfier1.start();
		classfier1.join();
		classfier2.start();
		classfier2.join();
		setNextCombination(bean);

	}

	public void setNextCombination(DataSetBean bean) {

		bean.setNextCombination(StringUtils.leftPad(
				UtilFunctions.binaryFromNumber(UtilFunctions.integerfrmbinary(bean.getNextCombination()) - 1),
				Integer.parseInt("" + getNoOfColumns(bean)), '0'));
		String[] cols = bean.getColumnInfo().split(bean.getInputSplit());
		int i = 0;
		int j = 0;
		for (String str : cols) {
			if (bean.getNextCombination().charAt(i) == '1') {
				j++;
			}
			if (str.equalsIgnoreCase(bean.getAttributeToFind())) {

				if (bean.getNextCombination().charAt(i) == '0') {
					char[] ch = bean.getNextCombination().toCharArray();
					ch[i] = '1';
					bean.setNextCombination(ch.toString());
					break;
				}
				bean.setClassIndex(j - 1);
				break;
			}
			i++;
		}

	}

	public static void main(String[] args) {
		LearnerController l = new LearnerController();
		DataSetBean b = new DataSetBean();
		b.setNextCombination("101011111111111");
		b.setColumnInfo(
				"Age,Gender,AppointmentRegistration,ApointmentData,DayOfTheWeek,Status,Diabetes,Alcoholism,HiperTension,Handcap,Smokes,Scholarship,Tuberculosis,Sms_Reminder,AwaitingTime");
		b.setAttributeToFind("status");
		b.setInputSplit(",");
		l.setNextCombination(b);
		System.out.println(b.getNextCombination());
		System.out.println(b.getClassIndex());
		System.out.println(b.getNextCombination().length() - b.getNextCombination().replaceAll("1", "").length());

	}

	public void postResults(ResultBean bean, DataSetBean job) {
		PostResults results = new PostResults();
		PostResultsBean post = new PostResultsBean();
		LearningResultModel model = new LearningResultModel();
		model.setClassifier1Correct("" + bean.getClassifier1Correct());
		model.setClassifier2Correct("" + bean.getClassifier2Correct());
		model.setConsistent("" + bean.getConsistentCases());
		model.setJobId("first");
		model.setColumns(job.getCurrentColumns());
		model.setNonConsistent("" + bean.getNonConsistentCases());
		post.setResult(model);
		results.postNewResult(post);
	}

	public void postJobDetails(ResultBean bean, DataSetBean job) {

		PostResults results = new PostResults();
		PostResultsBean post = new PostResultsBean();
		JobBean jbean = new JobBean();
		jbean.setClassifier1Model("J48");
		jbean.setClassifier2Model("NaiveBayes");
		jbean.setColumns(job.getColumnInfo());
		jbean.setJobId("first");
		jbean.setTotalRows("" + bean.getTotalCase());
		post.setJob(jbean);
		results.postNewJob(post);
	}

	public int getNoOfColumns(BaseJobBean bean) {
		return bean.getColumnInfo().split(bean.getInputSplit()).length;
	}

}
