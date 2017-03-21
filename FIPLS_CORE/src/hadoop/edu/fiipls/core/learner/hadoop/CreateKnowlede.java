package hadoop.edu.fiipls.core.learner.hadoop;

import weka.distributed.hadoop.WekaClassifierHadoopJob;

public class CreateKnowlede extends Thread{
	
	String option;
	
	String name;
	
	public CreateKnowlede(String option,String name){
		this.option = option;
		this.name = name;
	}
	
	public void run() {

		WekaClassifierHadoopJob job = new WekaClassifierHadoopJob();
		try {
			job.setOptions(weka.core.Utils.splitOptions(option));
			job.setJobName(name);
			job.runJob();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		

	}

}
