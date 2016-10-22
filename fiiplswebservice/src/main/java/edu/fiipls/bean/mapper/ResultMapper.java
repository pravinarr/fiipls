package edu.fiipls.bean.mapper;

import edu.fiipls.entities.LearningResults;
import edu.fiipls.model.LearningResultModel;

public class ResultMapper {

	public LearningResults resultMapServiceToDB(LearningResultModel model) {
		LearningResults result = new LearningResults();
		result.setClassifier1Correct(Double.parseDouble(model.getClassifier1Correct()));
		result.setClassifier2Correct(Double.parseDouble(model.getClassifier2Correct()));
		result.setConsistent(Double.parseDouble(model.getConsistent()));
		result.setJobId(model.getJobId());
		result.setNonConsistent(Double.parseDouble(model.getNonConsistent()));
		return result;
	}

	public LearningResultModel resultMapDBToService(LearningResults model) {
		LearningResultModel result = new LearningResultModel();
		result.setClassifier1Correct(""+model.getClassifier1Correct());
		result.setClassifier2Correct(""+model.getClassifier2Correct());
		result.setConsistent(""+model.getConsistent());
		result.setJobId(model.getJobId());
		result.setNonConsistent(""+model.getNonConsistent());
		return result;
	}

}
