package controller;

import knowledge.KnowledgeBean;
import inconsistency.InconsistencyPredictor;
import learner.InitialLearner;
import logger.LoggerImp;

import org.apache.log4j.Logger;

import problemsolver.ProblemSolver;

@SuppressWarnings({ "rawtypes", "unused" })
public class Controller extends LoggerImp{
	
	private Logger logger ; 

	public  Logger getLogger() {
		return logger;
	}

	public  void setLogger(Logger logger) {
		this.logger = logger;
	}

	@SuppressWarnings("unchecked")
	Controller(){
		logger = getLoggerForThis(Controller.class);
	}
	
	public static void main(String[] args) throws InterruptedException{
		Controller control = new Controller();
		
		control.getLogger().info("Welcome .. Lets start the program");
		control.getLogger().info("Initialising the context of the system");
		KnowledgeBean knowledge = new KnowledgeBean();
		knowledge.setCandidatepath("C:/Users/Ashok/Desktop/praveen/campaign-contribution-text/Data/programData/0.csv");
		knowledge.setCommitteepath("C:/Users/Ashok/Desktop/praveen/campaign-contribution-text/Data/committees.csv");
		knowledge.setDirectoryPath("C:/Users/Ashok/Desktop/praveen/campaign-contribution-text/Data/programData");
		knowledge.setNextCombination("0");
		knowledge.setToBePredictAttribute("ICO");
		control.getLogger().info("Initialised the context of the system");
		
		
		control.getLogger().info("Starting the initial learner");
		
		InitialLearner initialLearner = new InitialLearner();
		initialLearner.learnInitially(knowledge);
		knowledge.printKnowledgeSummary(knowledge);
		control.getLogger().info("Initial nowledge is learned");
		
		control.getLogger().info("Starting the pertual learner");
		
		ProblemSolver problemSolver = new ProblemSolver();
		problemSolver.problemResolver(knowledge);
		
		
		control.getLogger().info("The pertual learner stopped");
		
		
	}
	
}
