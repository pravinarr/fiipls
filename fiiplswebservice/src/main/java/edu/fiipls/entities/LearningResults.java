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

	private double classifier1Correct;

	private double classifier2Correct;

	private double consistent;

	private double nonConsistent;
	
	private String columns;
	
	public LearningResults() {

	}

	public LearningResults(int id, String jobId, double totalRows, double classifier1Correct,
			double classifier2Correct, double consistent, double nonConsistent,String columns) {
		this.id = id;
		this.jobId = jobId;
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

	public double getClassifier1Correct() {
		return classifier1Correct;
	}

	public void setClassifier1Correct(double classifier1Correct) {
		this.classifier1Correct = classifier1Correct;
	}

	public double getClassifier2Correct() {
		return classifier2Correct;
	}

	public void setClassifier2Correct(double classifier2Correct) {
		this.classifier2Correct = classifier2Correct;
	}

	public double getConsistent() {
		return consistent;
	}

	public void setConsistent(double consistent) {
		this.consistent = consistent;
	}

	public double getNonConsistent() {
		return nonConsistent;
	}

	public void setNonConsistent(double nonConsistent) {
		this.nonConsistent = nonConsistent;
	}

	public String getColumns() {
		return columns;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}

}
