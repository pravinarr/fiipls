package edu.fiipls.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LearningResults {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private String jobId;

	private String totalRows;

	private String classifier1Correct;

	private String classifier2Correct;

	private String consistent;

	private String nonConsistent;
	
	private String columns;
	
	public LearningResults() {

	}

	public LearningResults(int id, String jobId, String totalRows, String classifier1Correct,
			String classifier2Correct, String consistent, String nonConsistent,String columns) {
		this.id = id;
		this.jobId = jobId;
		this.totalRows = totalRows;
		this.classifier1Correct = classifier1Correct;
		this.classifier2Correct = classifier2Correct;
		this.consistent = consistent;
		this.nonConsistent = nonConsistent;
		this.columns = columns;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public String getColumns() {
		return columns;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}

}
