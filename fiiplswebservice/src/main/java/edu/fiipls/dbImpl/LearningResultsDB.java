package edu.fiipls.dbImpl;

import java.util.List;

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

}
