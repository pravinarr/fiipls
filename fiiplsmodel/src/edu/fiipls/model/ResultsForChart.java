package edu.fiipls.model;

public class ResultsForChart {
	
	private String id;
	
	private double classifier1Accuracy;

	private double classifier2Accuracy;
	
	private double consistency;
	
	private double bestConsistency;
	
	private double noOfRows;
	
	private double allowedInconsistency;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getClassifier1Accuracy() {
		return classifier1Accuracy;
	}

	public void setClassifier1Accuracy(double classifier1Accuracy) {
		this.classifier1Accuracy = classifier1Accuracy;
	}

	public double getClassifier2Accuracy() {
		return classifier2Accuracy;
	}

	public void setClassifier2Accuracy(double classifier2Accuracy) {
		this.classifier2Accuracy = classifier2Accuracy;
	}

	public double getConsistency() {
		return consistency;
	}

	public void setConsistency(double consistency) {
		this.consistency = consistency;
	}

	public double getBestConsistency() {
		return bestConsistency;
	}

	public void setBestConsistency(double bestConsistency) {
		this.bestConsistency = bestConsistency;
	}

	public double getNoOfRows() {
		return noOfRows;
	}

	public void setNoOfRows(double noOfRows) {
		this.noOfRows = noOfRows;
	}

	public double getAllowedInconsistency() {
		return allowedInconsistency;
	}

	public void setAllowedInconsistency(double allowedInconsistency) {
		this.allowedInconsistency = allowedInconsistency;
	}

}
