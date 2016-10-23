package problemsolver;

import inconsistency.Inconsistency;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import knowledge.KnowledgeBean;
import logger.LoggerImp;

import org.apache.log4j.Logger;

import weka.core.Instance;
import dataload.DataLoader;

@SuppressWarnings("rawtypes")
public class ProblemSolver extends LoggerImp {

	Logger logger;

	@SuppressWarnings("unchecked")
	public ProblemSolver() {
		logger = getLoggerForThis(ProblemSolver.class);
	}

	public KnowledgeBean problemResolver(KnowledgeBean bean) {
		Inconsistency inconsistency = new Inconsistency();
		logger.info("Problem Resolver Started");
		for (int i = 0; i < bean.getCurrentData().numInstances(); i++) {

			logger.info("Checking for the instance:" + i);
			if (inconsistency.isPredictedDataIsInconsistent(bean, i,true)) {
				inconsistency.knowledgeRelearnerWithReshuffle(bean, i);
				logger.info("Inconsistency is Removed and knowledge is updated");
				break;

			}

		}

		return bean;
	}

	public String predictor(KnowledgeBean bean, List values) throws Exception {

		Instance ins = bean.getCurrentData().instance(0);
		int attributeNo = 0;
		for (int i = 0; i < bean.getCurrentData().numAttributes(); i++) {
			if (bean.getCurrentData().attribute(i).name().trim()
					.equalsIgnoreCase("ICO")) {
				attributeNo = i;
			}
		}

		for (int j = 0; j < values.size(); j++) {
			String[] str = ((String) values.get(j)).split(",");
			for (int i = 0; i < bean.getCurrentData().numAttributes(); i++) {
				if (bean.getCurrentData().attribute(i).name().trim()
						.equalsIgnoreCase(str[0])) {
					if (str.length > 1) {
						System.out.println("**&*"+str[1]);
						
						ins.setValue(i, str[1]);
					} else {
						ins.setMissing(i);
					}
				}
			}
		}
		String jval = ins.attribute(attributeNo).value(
				(int) bean.getCurrentj48().classifyInstance(ins));

		String nbcVal = ins.attribute(attributeNo).value(
				(int) bean.getCurrentNBC().classifyInstance(ins));

		String retVal = null;

		if (jval.equalsIgnoreCase(nbcVal)) {
			if (jval.equalsIgnoreCase("I")) {
				retVal = "INCUMBENT";
			} else if (jval.equalsIgnoreCase("C")) {
				retVal = "Challenger";
			} else if (jval.equalsIgnoreCase("")) {
				retVal = "Openseat";
			}
			return "The cadidate type pridicted for the given values is:"
					+ retVal;
		} else {
			return "Cannot predict. The output was inconsistent. The system will try to resolve ";
		}

	}
	
	public boolean isNumeric(String str)
	{
	    for (char c : str.toCharArray())
	    {
	        if (!Character.isDigit(c)) return false;
	    }
	    return true;
	}	
	public static void main(String[] args){
		ProblemSolver sol = new ProblemSolver();
		java.util.List<String> values = new ArrayList<String>();
		
		
		values.add("PARTY1,DEM");
		values.add("PARTY2,");	
		values.add("STATUS,P");
		values.add("STREET1,");
		values.add("STREET2,");
		values.add("CITY,");
		values.add("STATE,DC");
		values.add("ZIP,20510");
		values.add("COMID,C00029769");
		values.add("ELECYEAR,82");
		values.add("DISTRICT,0");
			
		
		DataLoader load = new DataLoader();
		KnowledgeBean bean = null;
		try {
			 bean = load.loadKnowledgeBean();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			System.out.println(sol.predictor(bean, values));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Instance ins = bean.getCurrentData().instance(0);
		System.out.println("**********************");
		for(int g=0;g<ins.attribute(1).numValues();g++){
			System.out.println(ins.attribute(10).value(g));
		}
		System.out.println("**********************");
		
		
		/*for (int i = 0; i < bean.getCurrentData().numAttributes(); i++) {
			System.out.println("");
			System.out.print(ins.attribute(i).name()+"***");
			System.out.print(ins.value(i));
			System.out.print("+++"+ins.attribute(i).value((int)ins.value(i)));
			if("".equalsIgnoreCase(ins.attribute(i).value((int)ins.value(i)))){
				System.out.print("---yes");
			}
		}*/
		
		
		
	}

}
