package dataload;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import knowledge.KnowledgeBean;
import logger.LoggerImp;

import org.apache.log4j.Logger;

import weka.core.Instances;
import weka.core.converters.CSVLoader;

public class DataReShuffler extends LoggerImp {
	Logger logger;

	@SuppressWarnings("unchecked")
	public DataReShuffler() {
		logger = getLoggerForThis(DataReShuffler.class);
	}

	public KnowledgeBean loadDataFromCSV(KnowledgeBean bean, int option) {

		CSVLoader loader = new CSVLoader();
		try {

			if (option == 1) {
				loader.setSource(new File(bean.getCandidatepath()));
				bean.setCurrentData(loader.getDataSet());
					
			} else if (option == 2) {
				bean.setNextCombination(getNextCombination(bean
						.getNextCombination()));
			//	logger.info("########### combination setted :"+bean.getNextCombination());
				bean = loadwithNewCombination(bean);
				//loader.setSource(new File( bean.getDirectoryPath()+bean.getNextCombination()+".csv"));
//				logger.info("Data combination that is loaded for new knowledge is *****"+bean.getNextCombination());
			}
			//bean.setOldData(bean.getOldData());
			setClassAttribute(bean);
			return bean;
		} catch (IOException e) {
			logger.info("Exception in DataLoader\n" + e.getMessage());
		}
		return bean;

	}
	
	public KnowledgeBean loadwithNewCombination(KnowledgeBean bean){
		
		
		//logger.info("-----old data -No of Attributes:"+bean.getOldData().numAttributes());
		//logger.info("-----Current data -No of Attributes:"+bean.getCurrentData().numAttributes());
		
		Instances ins = new Instances(bean.getOldData());
		
		List<Integer> mapList = new ArrayList<Integer>();
		
		for(int i=0;i<14;i++){
			mapList.add(i);
		}
		
		for( int i = 0;i<14;i++){
			
			if(!isAddable(Integer.parseInt(bean.getNextCombination()), i)){
			//	logger.info("************* Instances -No of Attributes:"+ins.numAttributes()+" and i value:"+i);
				//logger.info("########"+mapList.indexOf(i));
				ins.deleteAttributeAt(mapList.indexOf(i));
				mapList.remove((Integer)i);
			}
		}
		
		bean.setCurrentData(ins);
		//logger.info("#####old data -No of Attributes:"+bean.getOldData().numAttributes());
		//logger.info("#####Current data -No of Attributes:"+bean.getCurrentData().numAttributes());
		
		return bean;
	}

	/*
	 * Method to get the next possible combination to the re-learner
	 */
	public String getNextCombination(String value) {
		String check;
		for (int i = Integer.parseInt(value)-1; i >= 0; i--) {
			check = String.format("%14s", Integer.toBinaryString(i)).replace(
					" ", "0");
			logger.info("######"+check);
			if ((check.charAt(4) == '1') && (calculateNoOfAttributes(Integer.parseInt(value)) >1 ) ) {
				value = "" + i;
				break;
			}
		}
		return value;
	}

	public boolean isAddable(int combination, int position) {
		boolean flag = false;
		String binaryString = String.format("%14s",
				Integer.toBinaryString(combination)).replace(" ", "0");
		//logger.info("---------"+binaryString);
		if (binaryString.charAt(position) == '1') {
			flag = true;
		}
		return flag;
	}

	public int calculateNoOfAttributes(int combination) {
		int size = 0;
		String binaryString = String.format("%14s",
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
	
}
