package edu.fiipls.dbImpl;

import java.util.List;

import org.hibernate.Query;

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
		String hql= "select result from JobEntity result where result.jobId=:jobName";
		Query query = getSession().createQuery(hql);
        query.setParameter("jobName", jobId);
		@SuppressWarnings("unchecked")
		List<JobEntity> results = query.list();
		close();
		return results;
	}
}
