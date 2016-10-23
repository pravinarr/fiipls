package hadoop.edu.fiipls.core.learner.hadoop.classify;

import weka.distributed.hadoop.WekaClassifierHadoopJob;

public class ClassificationModels {
	
	private String options;
	
	public void classify(String classificationAttribute) throws Exception{
		WekaClassifierHadoopJob job = new WekaClassifierHadoopJob();
		job.setOptions(weka.core.Utils.splitOptions(options));
		job.setClassAttribute(classificationAttribute);
		job.runJob();
	}

}
