package hadoop.edu.fiipls.core.learner.hadoop.score;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
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
import hadoop.edu.fiipls.core.learner.beans.ScoreBean;

public class ScoreModel extends Configured implements Tool {

	public ResultBean bean;
	
	DataSetBean jobBean;

	public ResultBean execute(DataSetBean input) throws Exception {
		String[] args1 = {""};
		jobBean = input;
		int res = ToolRunner.run(new Configuration(), new ScoreModel(), args1);
		return bean;
	}

	public int run(String[] args) throws Exception {
		Configuration conf = new Configuration();
		conf.set("valueSplitter", jobBean.getInputSplit());
		conf.set("classifier1Path", jobBean.getClassifier1Path());
		conf.set("classifier2Path", jobBean.getClassifier2Path());
		conf.set("headerFile", jobBean.getHeaderFile());
		conf.set("attributeToFind", jobBean.getAttributeToFind());

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

		conf.set("fs.default.name", "hdfs://"+jobBean.getHdfsPath());
		FileInputFormat.setInputPaths(job, new Path(jobBean.getInputPath()));
		FileOutputFormat.setOutputPath(job, new Path(jobBean.getOutputPath()));

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

		return (flag ? 0 : 1);
	}
}