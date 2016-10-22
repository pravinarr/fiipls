package edu.fiipls.bean.mapper;

import edu.fiipls.entities.JobEntity;
import edu.fiipls.model.JobBean;

public class JobMapper {
	public JobEntity resultMapServiceToDB(JobBean model) {
		JobEntity result = new JobEntity();
		result.setColumns(model.getColumns());
		result.setClassifier1Model(model.getClassifier1Model());
		result.setClassifier2Model(model.getClassifier2Model());
		result.setJobId(model.getJobId());
		result.setTotalRows(Double.parseDouble(model.getTotalRows()));
		return result;
	}

	public JobBean resultMapDBToService(JobEntity model) {
		JobBean result = new JobBean();
		result.setColumns(model.getColumns());
		result.setClassifier1Model(model.getClassifier1Model());
		result.setClassifier2Model(model.getClassifier2Model());
		result.setJobId(model.getJobId());
		result.setTotalRows(""+(model.getTotalRows()));
		return result;
	}
}
