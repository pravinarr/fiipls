package edu.fiipls.model;

public class PredictionResult {
	
	private String classifier1Result;
	
	private String classifier2Result;
	
	private String isInconsistent;

	public String getClassifier1Result() {
		return classifier1Result;
	}

	public void setClassifier1Result(String classifier1Result) {
		this.classifier1Result = classifier1Result;
	}

	public String getClassifier2Result() {
		return classifier2Result;
	}

	public void setClassifier2Result(String classifier2Result) {
		this.classifier2Result = classifier2Result;
	}

	public String getIsInconsistent() {
		return isInconsistent;
	}

	public void setIsInconsistent(String isInconsistent) {
		this.isInconsistent = isInconsistent;
	}
	
	

}
