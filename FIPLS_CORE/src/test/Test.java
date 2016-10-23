package test;

import inconsistency.Inconsistency;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import knowledge.KnowledgeBean;
import learner.InitialLearner;
import logger.LoggerImp;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

public class Test extends LoggerImp{

	
	
	Logger logger;

	@SuppressWarnings("unchecked")
	public Test() {
		logger = getLoggerForThis(Test.class);
	}
	
	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException, InterruptedException {
		Test test = new Test();
		KnowledgeBean knowledge = new KnowledgeBean();
		//knowledge.setCandidatepath("C:/Users/Ashok/Desktop/praveen/campaign-contribution-text/Data/CandidateCleaned3.csv");
		knowledge.setCandidatepath("C:/Users/Ashok/Desktop/praveen/campaign-contribution-text/Data/programData/0.csv");
		
		knowledge.setCommitteepath("C:/Users/Ashok/Desktop/praveen/campaign-contribution-text/Data/CommitteCleaned.csv");
		knowledge.setDirectoryPath("C:/Users/Ashok/Desktop/praveen/campaign-contribution-text/Data/programData/");
		//knowledge.setNextCombination("21743");
		
		//knowledge.setNextCombination("14335");
		knowledge.setNextCombination("16382");
		knowledge.setToBePredictAttribute("ICO");
		
	
		
		
		/*
		DataLoadFromDB loader = new DataLoadFromDB();
		
		loader.loadDataFromCSV(knowledge,2);
		
		DataLoader lod = new DataLoader();
		
		for(int i=0;i<20;i++){
			knowledge.setNextCombination(lod.getNextCombination(knowledge.getNextCombination()));
			System.out.println("***************"+knowledge.getNextCombination()); 
		}
		
		
		
		DataLoader loader = new DataLoader();
		loader.loadDataFromCSV(knowledge, 1);
		*/
		test.logger.info("Welcome to the system");
		test.logger.info("Starting Initial knowledge learner");
		InitialLearner initialLearner = new InitialLearner();
		initialLearner.learnInitially(knowledge);
		test.logger.info("Initial knowledge learnt");
		knowledge.setInconsisPer(knowledge.printKnowledgeSummary(knowledge));
		
		Inconsistency inconsistency = new Inconsistency();
		int h=0;
		for(int i=2; i< knowledge.getCurrentData().numInstances();i++){
			
			if(inconsistency.isPredictedDataIsInconsistent(knowledge, i,true)){
				
				/*double j4 = knowledge.getJ48evaluation().pctCorrect();
				double nb = knowledge.getNbcevaluation().pctCorrect();
				*/
				System.out.println("knowledge learning");
				test.logger.info("Starting the experimenter");
				inconsistency.knowledgeRelearnerWithReshuffle(knowledge, i);
				
				if(i%20==0){
					System.gc();
					System.out.println("********------ GC Called************");
				}
				
				System.out.println("knowledge learnt");
				test.logger.info("Experimenter Stopped");

				break;
				/*if((nb<knowledge.getNbcevaluation().pctCorrect())&&(j4<knowledge.getJ48evaluation().pctCorrect()))
				{
				*/
				
				//}
			}
			
			
		}
		//System.out.println("\nH"+h);
/*		InitialLearner initialLearner = new InitialLearner();
		initialLearner.learnInitially(knowledge);
		try {
			knowledge.printKnowledgeSummary();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/		
		
		
		
	}

}
