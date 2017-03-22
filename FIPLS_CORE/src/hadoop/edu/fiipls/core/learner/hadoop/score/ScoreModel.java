package hadoop.edu.fiipls.core.learner.hadoop.score;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import hadoop.edu.fiipls.core.learner.beans.DataSetBean;
import hadoop.edu.fiipls.core.learner.beans.ResultBean;

public class ScoreModel extends Configured implements Tool {

	public ResultBean bean;

	DataSetBean jobBean;

	public ScoreModel() {

	}

	public ScoreModel(DataSetBean jobBean) {
		this.jobBean = jobBean;
	}

	public ResultBean execute(DataSetBean input) throws Exception {
		String[] args1 = { "" };
		jobBean = input;
		int res = ToolRunner.run(new Configuration(), new ScoreModel(jobBean), args1);
		return jobBean.getCurrentResult();
	}
	
	public String getClassIndex(){
		int i= 0;
		for (String s: jobBean.getColumnInfo().split(jobBean.getInputSplit())){
			if(s.equalsIgnoreCase(jobBean.getAttributeToFind())){
				return ""+i;
			}
			i++;
		}
		return null;
	}
	

	@SuppressWarnings("deprecation")
	public int run(String[] args) throws Exception {
		Configuration conf = new Configuration();
		conf.set("valueSplitter", jobBean.getInputSplit());
		// conf.set("classifier1Path", jobBean.getClassifier1Path());
		// conf.set("classifier2Path", jobBean.getClassifier2Path());
		/*conf.set("classifier1Path",
				"hdfs://" + jobBean.getHdfsPath() + ":" + jobBean.getHdfsPort() + "/classifier1/model/classifier1.model");
		conf.set("classifier2Path",
				"hdfs://" + jobBean.getHdfsPath() + ":" + jobBean.getHdfsPort() + "/classifier2/model/classifier2.model");
		 */
		//FileCopyToHdfs.doCopyClassifiersToDistributedCache("/classifier1/model",  "Classifier1.model");
		//FileCopyToHdfs.doCopyClassifiersToDistributedCache("/classifier2/model",  "Classifier2.model");
		DistributedCache.addCacheFile(new Path("hdfs://" + jobBean.getHdfsPath() + ":" + jobBean.getHdfsPort() +"/classifier1/model/Classifier1.model").toUri(), conf);
		DistributedCache.addCacheFile(new Path("hdfs://" + jobBean.getHdfsPath() + ":" + jobBean.getHdfsPort() +"/classifier2/model/Classifier2.model").toUri(), conf);
		DistributedCache.addCacheFile(new Path("hdfs://" + jobBean.getHdfsPath() + ":" + jobBean.getHdfsPort() +"/classifier2/model/Classifier2_arffHeader.arff").toUri(), conf);
		conf.set("classifier1Path","Classifier1.model");
		conf.set("classifier2Path","Classifier2.model");
		
		conf.set("headerFile", jobBean.getHeaderFile());
		conf.set("attributeToFind", ""+jobBean.getClassIndex());

		Job job = Job.getInstance(conf);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		job.setMapperClass(ScoreModelMapper.class);
		job.setNumReduceTasks(0);
		// job.setReducerClass(CreateNewDataSetReducer.class);

		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);

		conf.set("yarn.resourcemanager.address", jobBean.getHdfsPath());
		conf.set("mapreduce.framework.name", "yarn");

		conf.set("fs.default.name", "hdfs://" + jobBean.getHdfsPath());
		FileInputFormat.setInputPaths(job, new Path("hdfs://" + jobBean.getHdfsPath() + ":" + jobBean.getHdfsPort() + ""
				+ jobBean.getOutputPath() + "inputTrain.csv"));
		FileOutputFormat.setOutputPath(job,
				new Path("hdfs://" + jobBean.getHdfsPath() + ":" + jobBean.getHdfsPort() + "" + "/scoreResults/"));

		job.setJarByClass(ScoreModel.class);
		long start = System.currentTimeMillis();
		boolean flag = job.waitForCompletion(true);

		bean = new ResultBean();
		bean.setTimeTakenToComplete((System.currentTimeMillis() - start) / 1000);
		bean.setTotalCase((int) job.getCounters().findCounter("totalCases", "totalCases").getValue());
		bean.setClassifier1Correct(
				(int) job.getCounters().findCounter("classifier1Correct", "classifier1Correct").getValue());
		bean.setClassifier2Correct(
				(int) job.getCounters().findCounter("classifier2Correct", "classifier2Correct").getValue());
		bean.setConsistentCases(
				(int) job.getCounters().findCounter("consistentCounter", "consistentCounter").getValue());
		bean.setNonConsistentCases(
				(int) job.getCounters().findCounter("notConsistentCounter", "notConsistentCounter").getValue());

		bean.setResult(flag);
		
		jobBean.setCurrentResult(bean);

		return (flag ? 0 : 1);
	}
}