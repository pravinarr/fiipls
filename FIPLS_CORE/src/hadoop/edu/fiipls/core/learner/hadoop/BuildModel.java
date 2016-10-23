package hadoop.edu.fiipls.core.learner.hadoop;

import java.io.File;
import java.io.IOException;

import distributed.hadoop.HDFSConfig;
import distributed.hadoop.MapReduceJobConfig;
import weka.core.converters.CSVLoader;
import weka.core.converters.CSVSaver;
import weka.core.converters.HDFSSaver;
import weka.distributed.DistributedWekaException;
import weka.distributed.hadoop.ArffHeaderHadoopJob;
import weka.distributed.hadoop.WekaClassifierHadoopJob;

public class BuildModel {

	public void buildModel() throws DistributedWekaException, IOException {
		
		//WekaClassifierHadoopJob wekaClassifierHadoopJob = new WekaClassifierHadoopJob();*/
		
		CSVLoader loader = new CSVLoader();
		loader.setFile(new File("/home/hduser/Desktop/test.csv"));
		CSVSaver csv = new CSVSaver();
		loader.setFieldSeparator("|");
		csv.setFieldSeparator("|");
		HDFSSaver saver = new HDFSSaver();
		HDFSConfig configuration1 = saver.getConfig();
		configuration1.setHDFSHost("HadoopMaster");
		configuration1.setHDFSPort("9000");
		configuration1.setJobTrackerHost("HadoopMaster");
		configuration1.setJobTrackerPort("8050");
		saver.setHDFSPath("/input/actual/dataset.csv");
		saver.setSaver(csv);
		saver.setInstances(loader.getDataSet());
		System.out.println("Starting to write the data into hdfs");
		saver.writeBatch();
		
		
	}
	
	public void generateNewKnowledgeAndEvaluateN() throws DistributedWekaException{
		WekaClassifierHadoopJob job = new WekaClassifierHadoopJob();
		MapReduceJobConfig configuration = job
				.getMapReduceJobConfig();
		configuration.setHDFSHost("HadoopMaster");
		configuration.setHDFSPort("9000");
		configuration.setJobTrackerHost("HadoopMaster");
		configuration.setJobTrackerPort("8050");
		configuration.setInputPaths("/output2/test.csv");
		configuration.setOutputPath("/output3/");
		//wekaClassifierHadoopJob.setMapReduceJobConfig(configuration);
		System.out.println("Starting the job");
		boolean status = job.runJob();
		System.out.println("End of the job");
	}
	
	public void generateNewKnowledgeAndEvaluate() throws DistributedWekaException{
		//WekaClassifierHadoopJob job = new WekaClassifierHadoopJob();
		ArffHeaderHadoopJob job = new ArffHeaderHadoopJob();
		MapReduceJobConfig configuration = job
				.getMapReduceJobConfig();
		configuration.setHDFSHost("HadoopMaster");
		configuration.setHDFSPort("9000");
		configuration.setJobTrackerHost("HadoopMaster");
		configuration.setJobTrackerPort("8050");
		configuration.setInputPaths("/input/test.csv");
		configuration.setOutputPath("/output2/");
		//wekaClassifierHadoopJob.setMapReduceJobConfig(configuration);
		System.out.println("Starting the job");
		//job.setAttributeNames("street\ncity\nzip\nstate\nbeds\nbaths\nsq__ft\ntype\nsale_date\nprice\nlatitude\nlongitude");
		job.setAttributeNamesFile("/home/hduser/title.names");
		job.setOutputHeaderFileName("inputData.arff");
		boolean status = job.runJob();
		System.out.println("End of the job");
	}

	public static void main(String[] args) {
		BuildModel bm = new BuildModel();
		try {
			bm.generateNewKnowledgeAndEvaluate(); 
			System.out.println("starting arrf headersss");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
			/*try {
			String opts = "-hdfs-host HadoopMaster -hdfs-port 9000 -jobtracker-host HadoopMaster -jobtracker-port " +
					"8050 -input-paths /home/hduser/Desktop/test.csv -output-path /home/hduser/Desktop/Scheduler_ARFF -A petallength,petalwidth,sepallength,sepalwidth,class";

			ArffHeaderHadoopJob arffjob = new ArffHeaderHadoopJob();
			arffjob.setOptions(weka.core.Utils.splitOptions(opts));
			arffjob.runJob();
			} catch (Exception ex) {
			ex.printStackTrace();
			}*/
	}

}
