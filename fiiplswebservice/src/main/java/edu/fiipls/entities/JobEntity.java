package edu.fiipls.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class JobEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String jobId;

	private String columns;

	private String classifier1Model;

	private String classifier2Model;
	
	private double totalRows;

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


	public String getColumns() {
		return columns;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}

	public double getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(double totalRows) {
		this.totalRows = totalRows;
	}

}
