package inconsistency;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import knowledge.KnowledgeBean;
import logger.LoggerImp;

import org.apache.log4j.Logger;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.trees.J48;
import dataload.DataLoadFromDB;
import dataload.DataLoader;
import dataload.DataReShuffler;

@SuppressWarnings("rawtypes")
public class Inconsistency extends LoggerImp {

	Logger logger;

	@SuppressWarnings("unchecked")
	public Inconsistency() {
		logger = getLoggerForThis(Inconsistency.class);
	}

	public boolean isPredictedDataIsInconsistent(KnowledgeBean bean,
			int inputRecordIndex,boolean log) {
		boolean flag = true;
		int attributeNo = 0;
		for (int i = 0; i < bean.getCurrentData().numAttributes(); i++) {
			if (bean.getCurrentData().attribute(i).name().trim()
					.equalsIgnoreCase(bean.getToBePredictAttribute())) {
				attributeNo = i;
			}
		}
		try {
			if (bean.getCurrentData()
					.instance(inputRecordIndex)
					.attribute(attributeNo)
					.value((int) bean.getCurrentj48().classifyInstance(
							bean.getCurrentData().instance(inputRecordIndex)))
					.equalsIgnoreCase(
							bean.getCurrentData()
									.instance(inputRecordIndex)
									.attribute(attributeNo)
									.value((int) bean
											.getCurrentNBC()
											.classifyInstance(
													bean.getCurrentData()
															.instance(
																	inputRecordIndex))))) {

				flag = false;

			}
			if ((flag == false)
					&& bean.getCurrentData()
							.instance(inputRecordIndex)
							.attribute(attributeNo)
							.value((int) bean.getCurrentj48().classifyInstance(
									bean.getCurrentData().instance(
											inputRecordIndex)))
							.equalsIgnoreCase(
									bean.getCurrentData()
											.instance(inputRecordIndex)
											.attribute(attributeNo)
											.value((int) bean.getCurrentData()
													.instance(inputRecordIndex)
													.classValue()))) {
				flag = false;

			}else{
				flag =true;
			}
			if(log){
			logger.info("Predicted Result--> J48:"
					+ bean.getCurrentData()
							.instance(inputRecordIndex)
							.attribute(attributeNo)
							.value((int) bean.getCurrentj48().classifyInstance(
									bean.getCurrentData().instance(
											inputRecordIndex)))
					+ " And NBC :"
					+ bean.getCurrentData()
							.instance(inputRecordIndex)
							.attribute(attributeNo)
							.value((int) bean.getCurrentNBC().classifyInstance(
									bean.getCurrentData().instance(
											inputRecordIndex)))
					+ " And Actual value is:"+bean.getCurrentData()
					.instance(inputRecordIndex)
					.attribute(attributeNo)
					.value((int) bean.getCurrentData()
							.instance(inputRecordIndex)
							.classValue()));
			logger.info("***Retunr"+flag);
			}

		} catch (Exception e) {
			logger.info("Error in checking inconsistency\n" + e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}

	public KnowledgeBean knowledgeRelearner(KnowledgeBean bean,
			int inputRecordIndex) {
		boolean continueFlag = true;
		DataLoader loader = new DataLoader();
		try {
			int i = 0;
			while (continueFlag) {
				logger.info("The combination that is being tested is:"
						+ bean.getNextCombination());
				bean = loader.loadDataFromCSV(bean, 2);
				bean = relearner(bean);
				if (!isPredictedDataIsInconsistent(bean, inputRecordIndex,true)) {
					logger.info("New Knowledge learned and updated...");
					continueFlag = false;
				}
				if (i % 20 == 0) {
					logger.info("***************------GC Called---------***********");
					System.gc();
				}
				i++;
			}
		} catch (Exception e) {
			logger.info("Exception in knowledge re-learner\n" + e.getMessage());
		}
		return bean;
	}

	public KnowledgeBean knowledgeRelearnerWithDB(KnowledgeBean bean,
			int inputRecordIndex) {
		boolean continueFlag = true;

		try {
			int i = 0;
			DataLoadFromDB loader = new DataLoadFromDB();
			while (continueFlag) {
				logger.info("The combination that is being tested is:"
						+ bean.getNextCombination());
				bean = loader.loadDataFromDB(bean, 2);
				if (i % 20 == 0) {
					logger.info("***************------GC Called---------***********");
					System.gc();
				}
				i++;
				/*
				 * bean = relearner(bean); if
				 * (!isPredictedDataIsInconsistent(bean, inputRecordIndex)) {
				 * logger.info("New Knowledge learned and updated...");
				 * continueFlag = false; }
				 */
			}
		} catch (Exception e) {
			logger.info("Exception in knowledge re-learner\n" + e.getMessage());
		}
		return bean;
	}

	public KnowledgeBean knowledgeRelearnerWithReshuffle(KnowledgeBean bean,
			int inputRecordIndex) {
		boolean continueFlag = true;
		int count = 0;
		String valu = "ss";
		try {
			int i = 0;
			DataReShuffler loader = new DataReShuffler();
			while (continueFlag) {
				logger.info("The combination that is being tested is:"
						+ bean.getNextCombination());
				bean = loader.loadDataFromCSV(bean, 2);
				if (i % 100 == 0) {
					System.gc();
				}
				i++;

				bean = relearner(bean);
				if (!isPredictedDataIsInconsistent(bean, inputRecordIndex,true)) {
					float per = bean.printKnowledgeSummary(bean);
					
					//if(per < bean.getInconsisPer() )
					{
									
					logger.info("New Knowledge learned and updated...");
					
					logger.info("*****************************");
					Evaluation j48evaluation = new Evaluation(bean.getCurrentData()) ;
					Evaluation nbcevaluation = new Evaluation(bean.getCurrentData()) ;
					j48evaluation.evaluateModel(bean.getCurrentj48(),bean.getCurrentData());
					nbcevaluation.evaluateModel(bean.getCurrentNBC(),bean.getCurrentData());
					logger.info("J48 evaluation Report:");
					logger.info(j48evaluation.toSummaryString());
					logger.info("NBC evaluation Report:");
					logger.info(nbcevaluation.toSummaryString());
					logger.info("*****************************");

					saveKnowledge(bean);
					bean.setInconsisPer(per);
					continueFlag = false;
					}
				}
				if (valu.equalsIgnoreCase(bean.getNextCombination())) {
					continueFlag = false;
					logger.info("**********New knowledge cannot be obtained for instance---------***********"
							+ inputRecordIndex);
				} else {
					valu = bean.getNextCombination();
				}
			}
		} catch (Exception e) {
			logger.info("Exception in knowledge re-learner\n" + e.getMessage());
			e.printStackTrace();

		}
		return bean;
	}

	public void saveKnowledge(KnowledgeBean bean) throws FileNotFoundException,
			IOException {
		FileOutputStream fout = new FileOutputStream("object.txt");
		ObjectOutputStream oos = new ObjectOutputStream(fout);
		oos.writeObject(bean);
		oos.close();
		System.out.println("Done");
	}

	public KnowledgeBean relearner(KnowledgeBean bean) throws Exception {

		bean.setCurrentj48(new J48());
		bean.setCurrentNBC(new NaiveBayes());
		bean.getCurrentj48().buildClassifier(bean.getCurrentData());
		bean.getCurrentNBC().buildClassifier(bean.getCurrentData());
		/*
		 * bean.getJ48evaluation().evaluateModel(bean.getCurrentj48(),
		 * bean.getCurrentData());
		 * bean.getNbcevaluation().evaluateModel(bean.getCurrentNBC(),
		 * bean.getCurrentData());
		 */
		logger.info("New knowledge is learned and it is to be tested");
		return bean;
	}

	public void evaludate(KnowledgeBean bean) {

	}

}
