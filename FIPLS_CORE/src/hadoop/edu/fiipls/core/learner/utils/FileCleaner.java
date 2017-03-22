package hadoop.edu.fiipls.core.learner.utils;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class FileCleaner {

	private static List<String> fileUrl = new ArrayList<String>();

	public static void fileCleaner() {
		fileUrl.add("/output/");
		fileUrl.add("/output1/");
		fileUrl.add("/classifier1/");
		fileUrl.add("/classifier2/");
		fileUrl.add("/scoreResults/");
	}

	public static void clean() throws IOException {
		fileCleaner();
		Configuration conf = new Configuration();
		conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
		conf.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());
		FileSystem hdfs = FileSystem.get(URI.create("hdfs://HadoopMaster:9000"), conf);
		//hdfs.delete(new Path("/scoreResults30/"), true);
		for (String s : getFileUrl()) {
			hdfs.delete(new Path(s), true);
		}
	}

	public static List<String> getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(List<String> fileUrl) {
		this.fileUrl = fileUrl;
	}
	
}
