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
		// 1. Get the instance of COnfiguration
		Configuration configuration = new Configuration();

		System.out.println("configured filesystem = " + configuration.get("fs.defaultFS"));
		// 2. Create an InputStream to read the data from local file
		InputStream inputStream = new BufferedInputStream(new FileInputStream(bean.getLocalInputFile()));
		// 3. Get the HDFS instance
		FileSystem hdfs = FileSystem.get(new URI("hdfs://" + bean.getHdfsPath() + ":" + bean.getHdfsPort()),
				configuration);
		// 4. Open a OutputStream to write the data, this can be obtained from
		// the FileSytem
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
	}
	
	public void writetoHdfs(DataSetBean bean,String trainingInput) throws IOException, URISyntaxException {
		// 1. Get the instance of COnfiguration
		Configuration configuration = new Configuration();

		System.out.println("configured filesystem = " + configuration.get("fs.defaultFS"));
		// 2. Create an InputStream to read the data from local file
		InputStream inputStream = new BufferedInputStream(new FileInputStream(trainingInput));
		// 3. Get the HDFS instance
		FileSystem hdfs = FileSystem.get(new URI("hdfs://" + bean.getHdfsPath() + ":" + bean.getHdfsPort()),
				configuration);
		// 4. Open a OutputStream to write the data, this can be obtained from
		// the FileSytem
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
	}
	
	public static boolean doCopyClassifiersToDistributedCache(String srcPath,String fileName) throws IllegalArgumentException, IOException, InterruptedException {
		Runtime rt = Runtime.getRuntime();
       Process proc = rt.exec("/usr/local/hadoop/bin/hadoop fs -cp "+srcPath+"/"+fileName+" "+HDFSUtils.WEKA_TEMP_DISTRIBUTED_CACHE_FILES +fileName);
       proc.waitFor();
		return true;
	}
}
