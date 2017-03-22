package hadoop.edu.fiipls.core.learner.hadoop.learner;

import distributed.hadoop.MapReduceJobConfig;
import hadoop.edu.fiipls.core.learner.beans.BaseJobBean;
import hadoop.edu.fiipls.core.learner.beans.DataSetBean;
import weka.distributed.DistributedWekaException;
import weka.distributed.hadoop.ArffHeaderHadoopJob;
import weka.gui.SysErrLog;

public class ArrfCreator {

	public boolean generateArffFile(DataSetBean bean, String namesFile) throws Exception {
		ArffHeaderHadoopJob job = new ArffHeaderHadoopJob();
		job.setCsvToArffTaskOptions("-N 1-"+(int)bean.getNoOfOnes());
		MapReduceJobConfig configuration = job.getMapReduceJobConfig();
		configuration.setHDFSHost(bean.getHdfsPath());
		configuration.setHDFSPort(bean.getHdfsPort());
		configuration.setJobTrackerHost(bean.getYarnPath());
		configuration.setJobTrackerPort(bean.getYarnPort());
		configuration.setInputPaths(bean.getOutputPath()+"inputTrain.csv");
		configuration.setOutputPath("/output/");
		
		//configuration.setOptions(weka.core.Utils.splitOptions(" -N 1-69"));
		job.setAttributeNamesFile(namesFile);
		job.setOutputHeaderFileName("inputData.arff");
		job.runJob();
		System.out.println(job.getCsvToArffTaskOptions());
		System.out.println(job.getOptions());
		System.out.println("Completed arrf file creattor.");
		return true;
	}

}
