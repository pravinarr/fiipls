package edu.fiipls.model;

import java.util.ArrayList;
import java.util.List;

public class ResultsOutput {

	private String jobId;

	private ResultsForChart bestClassifier;

	private List<ResultsForChart> learningProgress = new ArrayList<ResultsForChart>();

	private JobBean jobDetails;

	public ResultsOutput() {

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

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public JobBean getJobDetails() {
		return jobDetails;
	}

	public void setJobDetails(JobBean jobDetails) {
		this.jobDetails = jobDetails;
	}

}
