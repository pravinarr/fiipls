package dataload;

import inconsistency.InconsistencyPredictor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;

import knowledge.KnowledgeBean;
import logger.LoggerImp;

import org.apache.log4j.Logger;

import weka.core.Instances;
import weka.core.converters.CSVLoader;

public class DataLoader extends LoggerImp {

	Logger logger;

	@SuppressWarnings("unchecked")
	public DataLoader() {
		logger = getLoggerForThis(InconsistencyPredictor.class);
	}

	public KnowledgeBean loadDataFromCSV(KnowledgeBean bean, int option) {

		CSVLoader loader = new CSVLoader();
		try {

			if (option == 1) {
				loader.setSource(new File(bean.getCandidatepath()));
				
			} else if (option == 2) {
				bean.setNextCombination(getNextCombination(bean
						.getNextCombination()));
				logger.info("########### combination setted :"+bean.getNextCombination());
				/*loader.setSource(new File(createNewDataFile(
						bean.getCandidatepath(), bean.getCommitteepath(),
						bean.getNextCombination(), bean.getDirectoryPath())));*/
				loader.setSource(new File( bean.getDirectoryPath()+bean.getNextCombination()+".csv"));
				logger.info("Data combination that is loaded for new knowledge is *****"+bean.getNextCombination());
			}
			//bean.setOldData(bean.getOldData());
			bean.setCurrentData(loader.getDataSet());
			setClassAttribute(bean);
			return bean;
		} catch (IOException e) {
			logger.info("Exception in DataLoader\n" + e.getMessage());
		}
		return bean;

	}

	/*
	 * Method to get the next possible combination to the re-learner
	 */
	public String getNextCombination(String value) {
		String check;
		for (int i = Integer.parseInt(value)+1; i <= 32766; i++) {
			check = String.format("%16s", Integer.toBinaryString(i)).replace(
					" ", "0");
			logger.info("######"+check);
			if ((check.charAt(0) == '0') && (check.charAt(15) == '0')) {
				value = "" + i;
				break;
			}
		}
		return value;
	}

	public boolean isAddable(int combination, int position) {
		boolean flag = false;
		String binaryString = String.format("%16s",
				Integer.toBinaryString(combination)).replace(" ", "0");
		if (binaryString.charAt(position) == '1') {
			flag = true;
		}
		return flag;
	}

	public int calculateNoOfAttributes(int combination) {
		int size = 0;
		String binaryString = String.format("%16s",
				Integer.toBinaryString(combination)).replace(" ", "0");
		for (int i = 0; i < binaryString.length(); i++) {
			if (binaryString.charAt(i) == '1') {
				size++;
			}
		}
		return size;
	}

	public void setClassAttribute(KnowledgeBean bean){
		
		for(int i =0;i<bean.getCurrentData().numInstances();i++){
			if(bean.getCurrentData().attribute(i).name().equalsIgnoreCase(bean.getToBePredictAttribute())){
				bean.getCurrentData().setClassIndex(i);
				break;
			}
		}
		
	}
	
	public String createNewDataFile(String candidatePath, String committepath,
			String combination, String directoryPath) throws IOException {
		int ui = Integer.parseInt(combination);
		BufferedReader candidateReader, committeeReader = null;
		BufferedWriter newFileCreator;
		String filePath = directoryPath + "/" + combination + ".csv";
		StringBuffer stringCreator = new StringBuffer();
		String readCandidate, readCommittee;
		String[] splitCandidate, splitCommittee;

		candidateReader = new BufferedReader(new FileReader(new File(
				candidatePath)));
		boolean firstRowFlag = true;
		boolean checkIndicator = false;
		newFileCreator = new BufferedWriter(new FileWriter(new File(filePath)));
		while ((readCandidate = candidateReader.readLine()) != null) {
			committeeReader = new BufferedReader(new FileReader(new File(
					committepath)));
			stringCreator = new StringBuffer();
			stringCreator.append(readCandidate);
			splitCandidate = readCandidate.split(",");
			while (((readCommittee = committeeReader.readLine()) != null)) {
				
				splitCommittee = readCommittee.split(",");
				
					if (splitCandidate[11].equalsIgnoreCase(splitCommittee[1])||(firstRowFlag)) {
						do{
						firstRowFlag = false;
						int i = 0;
						for (String comite : splitCommittee) {
							if (isAddable(Integer.parseInt(combination), i)) {
								stringCreator.append(",");
								stringCreator.append(comite);
							}
							i++;
						}
						int size =stringCreator.toString().split(",").length;
					
						int start = stringCreator.length()-1;
						
						while(stringCreator.charAt(start)==','){
							size++;
							start--;
						}
						
					//	logger.info(stringCreator.toString()+"Size is =="+size);
						if((splitCandidate.length + calculateNoOfAttributes(ui))!= size){
								stringCreator = new StringBuffer(readCandidate);
								checkIndicator = true;
								
					    	}
						else{
							checkIndicator = false;
				    	}
						}while(checkIndicator);	
					newFileCreator.append(stringCreator.toString() + "\n");
					newFileCreator.flush();
					break;
				}
				
			}
		}
		candidateReader.close();
		return filePath;
	}
	
	public String[][] getAttributes() throws IOException, ClassNotFoundException{
		KnowledgeBean bean =  loadKnowledgeBean();
		String[][] attibutes = new String[14][2];
		Instances in = bean.getCurrentData();
		int j = 0;
		for(int i =0 ; i<in.numAttributes();i++){
			String attibuteName = in.attribute(i).name();
			if(!"ICO".equalsIgnoreCase(attibuteName.trim())){
				attibutes[j][0] = attibuteName;
				attibutes[j][1] = "";
				j++;
			}
		}
		return attibutes;
	}
	
	public KnowledgeBean loadKnowledgeBean() throws IOException, ClassNotFoundException{
		KnowledgeBean bean;
		FileInputStream fout = new FileInputStream("object.txt");
		ObjectInputStream oos = new ObjectInputStream(fout);   
		bean =(KnowledgeBean) oos.readObject();
		oos.close();
		return bean;
	}
}
