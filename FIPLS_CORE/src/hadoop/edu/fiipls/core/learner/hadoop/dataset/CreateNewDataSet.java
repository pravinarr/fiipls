package hadoop.edu.fiipls.core.learner.hadoop.dataset;

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

public class CreateNewDataSet extends Configured implements Tool {

	DataSetBean bean ;
	public void execute(DataSetBean bean) throws Exception {
		this.bean = bean;
		String[] args1 = { "" };
		int res = ToolRunner.run(new Configuration(), new CreateNewDataSet(), args1);
		System.exit(res);
	}

	public int run(String[] args) throws Exception {
		
		
		Configuration conf = new Configuration();
		conf.set("fieldSeperator", bean.getInputSplit());
		conf.set("nextCombination", bean.getNextCombination());
		Job job = Job.getInstance(conf);
		job.setJobName("Dataset creator");
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		job.setMapperClass(CreateNewDataSetMapper.class);
		job.setNumReduceTasks(0);
		//job.setReducerClass(CreateNewDataSetReducer.class);

		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);

		conf.set("yarn.resourcemanager.address", bean.getHdfsPath());
		conf.set("mapreduce.framework.name", "yarn");

		conf.set("fs.default.name", "hdfs://" + bean.getHdfsPath());

		FileInputFormat.setInputPaths(job, new Path(bean.getInputPath()));
		FileOutputFormat.setOutputPath(job, new Path(bean.getOutputPath()));

//		FileInputFormat.setInputPaths(job, new Path("hdfs://HadoopMaster:9000/input/actual/dataset.csv"));
	//	FileOutputFormat.setOutputPath(job, new Path("hdfs://HadoopMaster:9000/output_ext4/dataset"));

		//FileInputFormat.setInputPaths(job, new Path("/input/dataset.csv"));
		//FileOutputFormat.setOutputPath(job, new Path("/output_ext1/dataset.csv"));

		job.setJarByClass(CreateNewDataSet.class);
		System.out.println("job started");
		long start = System.currentTimeMillis();
		boolean flag = job.waitForCompletion(true);
		System.out.println("job completed" + flag);
		System.out.println("Time taken :" +((System.currentTimeMillis()-start)/1000)+" ms");
		return (flag?0:1);
	}
}