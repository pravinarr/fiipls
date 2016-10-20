package edu.fiipls.bean.mapper;

import edu.fiipls.entities.LearningResults;
import edu.fiipls.model.LearningResultModel;

public class ResultMapper {

	public LearningResults resultMapServiceToDB(LearningResultModel model) {
		LearningResults result = new LearningResults();
		result.setClassifier1Correct(model.getClassifier1Correct());
		result.setClassifier2Correct(model.getClassifier2Correct());
		result.setConsistent(model.getConsistent());
		result.setJobId(model.getJobId());
		result.setNonConsistent(model.getNonConsistent());
		result.setTotalRows(model.getTotalRows());
		return result;
	}

	public LearningResultModel resultMapDBToService(LearningResults model) {
		LearningResultModel result = new LearningResultModel();
		result.setClassifier1Correct(model.getClassifier1Correct());
		result.setClassifier2Correct(model.getClassifier2Correct());
		result.setConsistent(model.getConsistent());
		result.setJobId(model.getJobId());
		result.setNonConsistent(model.getNonConsistent());
		result.setTotalRows(model.getTotalRows());
		return result;
	}

}
