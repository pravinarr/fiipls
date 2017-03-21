package hadoop.edu.fiipls.core.learner.initiator;

import java.util.Properties;

import hadoop.edu.fiipls.core.learner.beans.DataSetBean;
import hadoop.edu.fiipls.core.learner.controller.LearnerController;
import hadoop.edu.fiipls.core.learner.utils.LoadProperties;

public class StartLearning {

	public static void main(String[] args) throws Exception {
		if(args.length < 1){
			System.out.println("Missing properties file path to start the learing process. Try to run with the properties file "
					+ "in the command line argument");
			System.exit(1);
		}
		
		Properties runProp = LoadProperties.loadProperties(args[0]);
		
		DataSetBean bean = new DataSetBean();
		bean.setHdfsPath(runProp.getProperty(""));
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
		bean.setOutputPath(runProp.getProperty(""));
		LearnerController controller  = new LearnerController();
		controller.startLearning(bean);

	}
	
	

}
