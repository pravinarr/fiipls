package edu.fiipls.rest.client.bean;

import edu.fiipls.model.JobBean;
import edu.fiipls.model.LearningResultModel;

public class PostResultsBean {
	
	private String url;
	
	private String path;
	
	private JobBean job;
	
	private LearningResultModel result;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public JobBean getJob() {
		return job;
	}

	public void setJob(JobBean job) {
		this.job = job;
	}

	public LearningResultModel getResult() {
		return result;
	}

	public void setResult(LearningResultModel result) {
		this.result = result;
	}
	
	

}
