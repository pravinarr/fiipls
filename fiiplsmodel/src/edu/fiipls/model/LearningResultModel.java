package edu.fiipls.model;

public class LearningResultModel {
	
	private String jobId;

	private String totalRows;

	private String classifier1Correct;

	private String classifier2Correct;

	private String consistent;

	private String nonConsistent;

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(String totalRows) {
		this.totalRows = totalRows;
	}

	public String getClassifier1Correct() {
		return classifier1Correct;
	}

	public void setClassifier1Correct(String classifier1Correct) {
		this.classifier1Correct = classifier1Correct;
	}

	public String getClassifier2Correct() {
		return classifier2Correct;
	}

	public void setClassifier2Correct(String classifier2Correct) {
		this.classifier2Correct = classifier2Correct;
	}

	public String getConsistent() {
		return consistent;
	}

	public void setConsistent(String consistent) {
		this.consistent = consistent;
	}

	public String getNonConsistent() {
		return nonConsistent;
	}

	public void setNonConsistent(String nonConsistent) {
		this.nonConsistent = nonConsistent;
	}
	
	
	

}
