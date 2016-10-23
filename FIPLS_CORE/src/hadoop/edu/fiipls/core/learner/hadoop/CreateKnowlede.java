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

		String opts = "-hdfs-host HadoopMaster -hdfs-port 9000 "
				+ "-jobtracker-host HadoopMaster -jobtracker-port 8050 "
				+ "-existing-header /output2/arff/inputData.arff "
				+ "-class type -input-paths /input/test.csv "
				+ "-output-path /output4/ " + "-model-file-name J48_dist.model "
				+ "-preserve-order -num-chunks 10 -num-folds 10" + "-max-split-size 100000 -M ? -E ' -F , "
				+ "-W weka.classifiers.trees.J48 -fold-number -1 -total-folds 10 -seed 1 -- -C 0.25 -M 2";
		
		
		/*String opts1 = "-hdfs-host HadoopMaster -hdfs-port 9000 "
				+ "-jobtracker-host HadoopMaster -jobtracker-port 8050 "
				+ "-existing-header /output2/arff/inputData.arff "
				+ "-class type -input-paths /input/test.csv "
				+ "-output-path /output5/ " + "-model-file /output4/model/J48_dist.model "
				+ " "
				+ ""; */
		 
		
		WekaClassifierHadoopJob job = new WekaClassifierHadoopJob();
		try {
			job.setOptions(weka.core.Utils.splitOptions(option));
			job.setJobName(name);
			job.runJob();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*WekaClassifierHadoopJob job = new WekaClassifierHadoopJob();
		job.setOptions(weka.core.Utils.splitOptions(opts));
		job.runJob();*/
		
		
		/*WekaScoringHadoopJob job =new WekaScoringHadoopJob();
		job.setOptions(weka.core.Utils.splitOptions(opts1));
		job.setColumnsToOutputInScoredData("type");
		job.runJob();
		System.out.println("Result");*/
		//System.out.println(job.);
		

	}

}
