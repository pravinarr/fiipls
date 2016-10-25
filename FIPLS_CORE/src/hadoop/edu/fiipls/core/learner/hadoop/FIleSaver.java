package hadoop.edu.fiipls.core.learner.hadoop;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import hadoop.edu.fiipls.core.learner.beans.BaseJobBean;

public class FIleSaver extends Configured implements Tool {

	BaseJobBean bean;
	// public static final String FS_PARAM_NAME = "fs.defaultFS";

	public int run(String[] args) throws Exception {
		String localInputPath = bean.getInputPath();
		Path outputPath = new Path(bean.getOutputPath());

		Configuration conf = new Configuration();
		conf.set("yarn.resourcemanager.address", bean.getHdfsPath());
		conf.set("mapreduce.framework.name", "yarn");

		conf.set("fs.default.name", "hdfs://" + bean.getHdfsPath()+":"+bean.getHdfsPort());
		FileSystem fs = FileSystem.get(conf);
		if (fs.exists(outputPath)) {
			fs.delete(outputPath);
			System.err.println("output path exists");
			return 1;
		}
		OutputStream os = fs.create(outputPath);
		InputStream is = new BufferedInputStream(new FileInputStream(localInputPath));
		IOUtils.copyBytes(is, os, conf);
		return 0;
	}

	public void execute(BaseJobBean bean) throws Exception {
		this.bean = bean;
		String[] args = {};
		int returnCode = ToolRunner.run(new FIleSaver(), args);
		System.exit(returnCode);
	}

}
