package edu.fiipls.model;

public class JobBean {
	
	private String jobId;
	
	private String columns;
	
	private String classifier1Model;
	
	private String classifier2Model;
	
	private String totalRows;
	
	private String allowedInconsistency;

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getColumns() {
		return columns;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}

	public String getClassifier1Model() {
		return classifier1Model;
	}

	public void setClassifier1Model(String classifier1Model) {
		this.classifier1Model = classifier1Model;
	}

	public String getClassifier2Model() {
		return classifier2Model;
	}

	public void setClassifier2Model(String classifier2Model) {
		this.classifier2Model = classifier2Model;
	}

	public String getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(String totalRows) {
		this.totalRows = totalRows;
	}

	public String getAllowedInconsistency() {
		return allowedInconsistency;
	}

	public void setAllowedInconsistency(String allowedInconsistency) {
		this.allowedInconsistency = allowedInconsistency;
	}

	
}
