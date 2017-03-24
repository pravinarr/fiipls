package hadoop.edu.fiipls.core.learner.hadoop;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;

import distributed.hadoop.HDFSUtils;
import hadoop.edu.fiipls.core.learner.beans.DataSetBean;

public class FileCopyToHdfs {

	public void writetoHdfs(DataSetBean bean) throws IOException, URISyntaxException {
		Configuration configuration = new Configuration();

		System.out.println("configured filesystem = " + configuration.get("fs.defaultFS"));
		InputStream inputStream = new BufferedInputStream(new FileInputStream(bean.getLocalInputFile()));
		FileSystem hdfs = FileSystem.get(new URI("hdfs://" + bean.getHdfsPath() + ":" + bean.getHdfsPort()),
				configuration);
		OutputStream outputStream = hdfs.create(
				new Path("hdfs://" + bean.getHdfsPath() + ":" + bean.getHdfsPort() + "" + bean.getInputPath()),
				new Progressable() {
					public void progress() { 
						System.out.println("Please wait .. uploading the file");
					}
				});
		try {
			IOUtils.copyBytes(inputStream, outputStream, 4096, false);
		} finally {
			IOUtils.closeStream(inputStream);
			IOUtils.closeStream(outputStream);
		}
		
		
		
		 inputStream = new BufferedInputStream(new FileInputStream(bean.getLocalTestFilePath()));
		 hdfs = FileSystem.get(new URI("hdfs://" + bean.getHdfsPath() + ":" + bean.getHdfsPort()),
				configuration);
		 outputStream = hdfs.create(
				new Path("hdfs://" + bean.getHdfsPath() + ":" + bean.getHdfsPort() + "" + bean.getTestpath()),
				new Progressable() {
					public void progress() { 
						System.out.println("Please wait .. uploading the file");
					}
				});
		try {
			IOUtils.copyBytes(inputStream, outputStream, 4096, false);
		} finally {
			IOUtils.closeStream(inputStream);
			IOUtils.closeStream(outputStream);
		}
	}
	
	public void writeTesttoHdfs(DataSetBean bean) throws IOException, URISyntaxException {
		Configuration configuration = new Configuration();

		BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(bean.getLocalTestFilePath()));
		FileSystem hdfs = FileSystem.get(new URI("hdfs://" + bean.getHdfsPath() + ":" + bean.getHdfsPort()),
				configuration);
		OutputStream outputStream = hdfs.create(
				new Path("hdfs://" + bean.getHdfsPath() + ":" + bean.getHdfsPort() + "" + bean.getTestpath()),
				new Progressable() {
					public void progress() { 
						System.out.println("Please wait .. uploading the file");
					}
				});
		try {
			IOUtils.copyBytes(inputStream, outputStream, 4096, false);
		} finally {
			IOUtils.closeStream(inputStream);
			IOUtils.closeStream(outputStream);
		}
	}
	
	public void writeCreatedtoTesHdfs(DataSetBean bean,String testInput) throws IOException, URISyntaxException {
		Configuration configuration = new Configuration();
		InputStream inputStream = new BufferedInputStream(new FileInputStream(testInput));
		FileSystem hdfs = FileSystem.get(new URI("hdfs://" + bean.getHdfsPath() + ":" + bean.getHdfsPort()),
				configuration);
		OutputStream outputStream = hdfs.create(
				new Path("hdfs://" + bean.getHdfsPath() + ":" + bean.getHdfsPort() + "" + bean.getTestDirectory()+"inputTrain.csv"),
				new Progressable() {
					public void progress() {
						System.out.println("Please wait .. uploading the file");
					}
				});
		try {
			IOUtils.copyBytes(inputStream, outputStream, 4096, false);
		} finally {
			IOUtils.closeStream(inputStream);
			IOUtils.closeStream(outputStream);
		}
	}
	
	public void writetoHdfs(DataSetBean bean,String trainingInput,String testInput) throws IOException, URISyntaxException {
		Configuration configuration = new Configuration();

		System.out.println("configured filesystem = " + configuration.get("fs.defaultFS"));
		InputStream inputStream = new BufferedInputStream(new FileInputStream(trainingInput));
		FileSystem hdfs = FileSystem.get(new URI("hdfs://" + bean.getHdfsPath() + ":" + bean.getHdfsPort()),
				configuration);
		OutputStream outputStream = hdfs.create(
				new Path("hdfs://" + bean.getHdfsPath() + ":" + bean.getHdfsPort() + "" + bean.getOutputPath()+"inputTrain.csv"),
				new Progressable() {
					public void progress() {
						System.out.println("Please wait .. uploading the file");
					}
				});
		try {
			IOUtils.copyBytes(inputStream, outputStream, 4096, false);
		} finally {
			IOUtils.closeStream(inputStream);
			IOUtils.closeStream(outputStream);
		}
		
		inputStream = new BufferedInputStream(new FileInputStream(testInput));
		hdfs = FileSystem.get(new URI("hdfs://" + bean.getHdfsPath() + ":" + bean.getHdfsPort()),
				configuration);
		outputStream = hdfs.create(
				new Path("hdfs://" + bean.getHdfsPath() + ":" + bean.getHdfsPort() + "" + bean.getTestDirectory()+"inputTrain.csv"),
				new Progressable() {
					public void progress() {
						System.out.println("Please wait .. uploading the file");
					}
				});
		try {
			IOUtils.copyBytes(inputStream, outputStream, 4096, false);
		} finally {
			IOUtils.closeStream(inputStream);
			IOUtils.closeStream(outputStream);
		}
	}
	
	public static boolean doCopyClassifiersToDistributedCache(String srcPath,String fileName) throws IllegalArgumentException, IOException, InterruptedException {
		Runtime rt = Runtime.getRuntime();
       Process proc = rt.exec("/usr/local/hadoop/bin/hadoop fs -cp "+srcPath+"/"+fileName+" "+HDFSUtils.WEKA_TEMP_DISTRIBUTED_CACHE_FILES +fileName);
       proc.waitFor();
		return true;
	}
}
