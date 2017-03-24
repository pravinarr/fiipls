package edu.fiipls.model;

public class ResultsForChart {
	
	private String id;
	
	private double classifier1Accuracy;

	private double classifier2Accuracy;
	
	private double inconsistency;
	
	private double bestInConsistency;
	
	private double noOfRows;
	
	private double allowedInconsistency;
	
	private String columns;
	
	
	public String getColumns() {
		return columns;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}

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

	public double getInconsistency() {
		return inconsistency;
	}

	public void setInconsistency(double inconsistency) {
		this.inconsistency = inconsistency;
	}

	public double getBestInConsistency() {
		return bestInConsistency;
	}

	public void setBestInConsistency(double bestInConsistency) {
		this.bestInConsistency = bestInConsistency;
	}

}
