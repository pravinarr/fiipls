package learner;

import inconsistency.Inconsistency;
import knowledge.KnowledgeBean;
import logger.LoggerImp;

import org.apache.log4j.Logger;

import weka.classifiers.Evaluation;

import dataload.DataLoadFromDB;

@SuppressWarnings("rawtypes")
public class InitialLearner extends LoggerImp {

	Logger logger;

	@SuppressWarnings("unchecked")
	public InitialLearner() {
		logger = getLoggerForThis(InitialLearner.class);
	}

	public KnowledgeBean learnInitially(KnowledgeBean bean) {
	
		try {
			DataLoadFromDB loader = new DataLoadFromDB();
			loader.loadDataFromDB(bean, 1);
			bean.setOldData(bean.getCurrentData());
			bean.getCurrentj48().buildClassifier(bean.getCurrentData());
			bean.getCurrentNBC().buildClassifier(bean.getCurrentData());
			Evaluation j48evaluation = new Evaluation(bean.getCurrentData()) ;
			Evaluation nbcevaluation = new Evaluation(bean.getCurrentData()) ;
			j48evaluation.evaluateModel(bean.getCurrentj48(),bean.getCurrentData());
			nbcevaluation.evaluateModel(bean.getCurrentNBC(),bean.getCurrentData());
			Inconsistency in = new Inconsistency();
			in.saveKnowledge(bean);
			logger.info("J48 evaluation Report:");
			logger.info(j48evaluation.toSummaryString());
			logger.info("NBC evaluation Report:");
			logger.info(nbcevaluation.toSummaryString());
			logger.info("*****************************");
			/*bean.setJ48evaluation(new Evaluation(bean.getCurrentData()));
			bean.setNbcevaluation(new Evaluation(bean.getCurrentData()));
			bean.getJ48evaluation().evaluateModel(bean.getCurrentj48(), bean.getCurrentData());
			bean.getNbcevaluation().evaluateModel(bean.getCurrentNBC(), bean.getCurrentData());*/

		} catch (Exception e) {
			logger.error("***************Error in Initial learner*****************\n"
					+ e.getLocalizedMessage());
		}

		return bean;
	}
}
