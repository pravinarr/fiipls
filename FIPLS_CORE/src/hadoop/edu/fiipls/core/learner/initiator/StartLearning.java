package hadoop.edu.fiipls.core.learner.initiator;

import hadoop.edu.fiipls.core.learner.beans.DataSetBean;
import hadoop.edu.fiipls.core.learner.controller.LearnerController;
import hadoop.edu.fiipls.core.learner.hadoop.FIleSaver;
import weka.distributed.hadoop.WekaScoringHadoopJob;
import weka.gui.SysErrLog;

public class StartLearning {

	public static void main(String[] args) throws Exception {   
		/*if(args.length < 1){
			System.out.println("Missing properties file path to start the learing process. Try to run with the properties file "
					+ "in the command line argument");
			System.exit(1);
		}*/
		
		//Properties runProp = LoadProperties.loadProperties(args[0]);
		
		DataSetBean bean = new DataSetBean();
	/*	bean.setHdfsPath(runProp.getProperty(""));
		bean.setHdfsPort(runProp.getProperty(""));
		bean.setYarnPath(runProp.getProperty(""));
		bean.setYarnPort(runProp.getProperty(""));
		bean.setAttributeToFind(runProp.getProperty(""));
		bean.setInputPath(runProp.getProperty(""));
		bean.setInputSplit(runProp.getProperty(""));
		bean.setClassifier1Config(runProp.getProperty(""));
		bean.setClassifier2Config(runProp.getProperty(""));
		bean.setOutputPath(runProp.getProperty(""));
		bean.setAllowedInconsistency(Double.parseDouble(runProp.getProperty("")));
		bean.setClassifier1Path(runProp.getProperty(""));
		bean.setClassifier2Path(runProp.getProperty(""));
		bean.setColumnInfo(runProp.getProperty(""));
		bean.setNextCombination("");
		bean.setNoOfRows(200000);
		bean.setTestpath(runProp.getProperty(""));
		bean.setOutputPath(runProp.getProperty(""));*/
		
		
		bean.setHdfsPath("HadoopMaster");
		bean.setHdfsPort("9000");
		bean.setYarnPath("HadoopMaster");
		bean.setYarnPort("8050");
		//bean.setAttributeToFind("CAND_ICI");
		bean.setAttributeToFind("Status");
		bean.setInputPath("/input/inputData.csv");
		//bean.setInputSplit("\\|");
		bean.setNoOfColumns(15);
		bean.setInputSplit(",");
		bean.setClassifier1Config("weka.classifiers.trees.J48 -fold-number -1 -total-folds 10 -seed 1 -- -C 0.25 -M 2");
		bean.setClassifier2Config("weka.classifiers.bayes.NaiveBayes ");
		bean.setOutputPath("/output1/");
		bean.setAllowedInconsistency(20);
		bean.setClassifier1Path("/classifier1/");
		bean.setClassifier2Path("/classifier2/");
		//bean.setColumnInfo("CAND_ID|CAND_NAME|CAND_PTY_AFFILIATION|CAND_ELECTION_YR|CAND_OFFICE_ST|CAND_OFFICE|CAND_OFFICE_DISTRICT|CAND_ICI|CAND_STATUS|CAND_PCC|CAND_ST1|CAND_ST2|CAND_CITY|CAND_ST|CAND_ZIP|CMTE_ID|CMTE_NM|TRES_NM|CMTE_ST1|CMTE_ST2|CMTE_CITY|CMTE_ST|CMTE_ZIP|CMTE_DSGN|CMTE_TP|CMTE_PTY_AFFILIATION|CMTE_FILING_FREQ|ORG_TP|CONNECTED_ORG_NM|CC_AMNDT_IND|CC_RPT_TP|CC_TRANSACTION_PGI|CC_IMAGE_NUM|CC_TRANSACTION_TP|CC_ENTITY_TP|CC_NAME|CC_CITY|CC_STATE|CC_ZIP_CODE|CC_EMPLOYER|CC_OCCUPATION|CC_TRANSACTION_DT|CC_TRANSACTION_AMT|CC_OTHER_ID|CC_TRAN_ID|CC_FILE_NUM|CC_MEMO_CD|CC_MEMO_TEXT|CC_SUB_ID|CI_AMNDT_IND|CI_RPT_TP|CI_TRANSACTION_PGI|CI_IMAGE_NUM|CI_TRANSACTION_TP|CI_ENTITY_TP|CI_NAME|CI_CITY|CI_STATE|CI_ZIP_CODE|CI_EMPLOYER|CI_OCCUPATION|CI_TRANSACTION_DT|CI_TRANSACTION_AMT|CI_OTHER_ID|CI_TRAN_ID|CI_FILE_NUM|CI_MEMO_CD|CI_MEMO_TEXT|CI_SUB_ID");
		bean.setColumnInfo("Age,Gender,AppointmentRegistration,ApointmentData,DayOfTheWeek,Status,Diabetes,Alcoholism,HiperTension,Handcap,Smokes,Scholarship,Tuberculosis,Sms_Reminder,AwaitingTime");
		bean.setNextCombination("111111111111111");
		//bean.setNoOfRows(1098953);
		bean.setLocalInputFile("/home/hduser/Desktop/appointment1.csv");
		bean.setNoOfRows(300000);
		bean.setTestpath("/input/inputData.csv");
		bean.setHeaderFile("/classifier2/model/Classifier2_arffHeader.arff");
		FIleSaver fs = new FIleSaver(bean);
		fs.execute(bean);
		
		WekaScoringHadoopJob jj;
		
		LearnerController controller  = new LearnerController();
		//controller.initialLearner(bean);
		//controller.scoreTheLearntModel(bean);
		System.out.println("Helo");
		controller.startLearning(bean);
		//controller.createNamesFile(bean, bean.getColumnInfo());

	}
	
	

}
