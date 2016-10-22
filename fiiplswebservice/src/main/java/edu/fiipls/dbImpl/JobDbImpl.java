package edu.fiipls.dbImpl;

import java.util.List;

import edu.fiipls.entities.JobEntity;
import edu.fiipls.transaction.BaseDBTransaction;

public class JobDbImpl extends BaseDBTransaction  {

	public boolean save(JobEntity result) {
		start();
		getSession().save(result);
		close();
		return true;
	}

	public List<JobEntity> get() {
		start();
		@SuppressWarnings("unchecked")
		List<JobEntity> results = getSession().createQuery("from JobEntity").list();
		close();
		return results;
	}
	
	public List<JobEntity> get(String jobId) {
		start();
		@SuppressWarnings("unchecked")
		List<JobEntity> results = getSession().createQuery("from JobEntity where jobId=\""+jobId+"\"").list();
		close();
		return results;
	}
}
