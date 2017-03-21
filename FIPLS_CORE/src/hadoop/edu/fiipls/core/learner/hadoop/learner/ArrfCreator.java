package hadoop.edu.fiipls.core.learner.hadoop.learner;

import distributed.hadoop.MapReduceJobConfig;
import hadoop.edu.fiipls.core.learner.beans.BaseJobBean;
import weka.distributed.DistributedWekaException;
import weka.distributed.hadoop.ArffHeaderHadoopJob;

public class ArrfCreator {

	public boolean generateArffFile(BaseJobBean bean, String namesFile) throws DistributedWekaException {
		ArffHeaderHadoopJob job = new ArffHeaderHadoopJob();
		MapReduceJobConfig configuration = job.getMapReduceJobConfig();
		configuration.setHDFSHost(bean.getHdfsPath());
		configuration.setHDFSPort(bean.getHdfsPort());
		configuration.setJobTrackerHost(bean.getYarnPath());
		configuration.setJobTrackerPort(bean.getYarnPort());
		configuration.setInputPaths(bean.getInputPath());
		configuration.setOutputPath("/output/");
		job.setAttributeNamesFile(namesFile);
		job.setOutputHeaderFileName("inputData.arff");
		return job.runJob();
	}

}
