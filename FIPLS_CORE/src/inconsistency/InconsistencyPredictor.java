package inconsistency;

import logger.LoggerImp;

import org.apache.log4j.Logger;

@SuppressWarnings("rawtypes")
public class InconsistencyPredictor extends LoggerImp {
	
	
	 Logger logger ; 

	
	@SuppressWarnings("unchecked")
	public InconsistencyPredictor(){
		logger = getLoggerForThis(InconsistencyPredictor.class);
	}
	
	/*public static void main(String[] args){
		InconsistencyPredictor i = new InconsistencyPredictor();
		i.logger.debug("Hello test");
	}*/
}
