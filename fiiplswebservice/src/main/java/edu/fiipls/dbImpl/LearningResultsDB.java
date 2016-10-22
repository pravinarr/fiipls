package edu.fiipls.dbImpl;

import java.util.List;

import org.hibernate.Query;

import edu.fiipls.entities.LearningResults;
import edu.fiipls.transaction.BaseDBTransaction;

public class LearningResultsDB extends BaseDBTransaction {

	public boolean save(LearningResults result) {
		start();
		getSession().save(result);
		close();
		return true;
	}

	public List<LearningResults> get() {
		start();
		@SuppressWarnings("unchecked")
		List<LearningResults> results = getSession().createQuery("from LearningResults").list();
		close();
		return results;
	}
	
	public List<LearningResults> get(String jobId) {
		start();
		String hql = "select result from LearningResults result where result.jobId=:jobName ";
		Query query = getSession().createQuery(hql);
        query.setParameter("jobName", jobId);
		@SuppressWarnings("unchecked")
		List<LearningResults> results =  query.list();
		close();
		return results;
	}
	
	public List<LearningResults> getBeat(String jobId) {
		start();
		String hql = "select result from LearningResults result where result.jobId=:jobName "
				+ "and result.consistent = (select max(consistent) from  LearningResults)";
		Query query = getSession().createQuery(hql);
        query.setParameter("jobName", jobId);
		@SuppressWarnings("unchecked")
		List<LearningResults> results =  query.list();
		close();
		return results;
	}

}
