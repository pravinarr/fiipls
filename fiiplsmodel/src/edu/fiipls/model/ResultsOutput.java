package edu.fiipls.model;

import java.util.ArrayList;
import java.util.List;

public class ResultsOutput {

	private double totalColumns;
	
	private String columns;
	
	private ResultsForChart bestClassifier;
	
	private List<ResultsForChart> learningProgress = new ArrayList<ResultsForChart>();

	public double getTotalColumns() {
		return totalColumns;
	}

	public void setTotalColumns(double totalColumns) {
		this.totalColumns = totalColumns;
	}

	public String getColumns() {
		return columns;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}

	public ResultsForChart getBestClassifier() {
		return bestClassifier;
	}

	public void setBestClassifier(ResultsForChart bestClassifier) {
		this.bestClassifier = bestClassifier;
	}

	public List<ResultsForChart> getLearningProgress() {
		return learningProgress;
	}

	public void setLearningProgress(List<ResultsForChart> learningProgress) {
		this.learningProgress = learningProgress;
	}
	
	
	
}
