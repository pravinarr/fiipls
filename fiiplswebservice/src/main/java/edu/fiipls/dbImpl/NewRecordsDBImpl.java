package edu.fiipls.dbImpl;

import java.util.List;

import org.hibernate.Query;

import edu.fiipls.entities.NewRecords;
import edu.fiipls.transaction.BaseDBTransaction;

public class NewRecordsDBImpl extends BaseDBTransaction {
	public boolean save(NewRecords result) {
		start();
		getSession().save(result);
		close();
		return true;
	}

	public List<NewRecords> get() {
		start();
		@SuppressWarnings("unchecked")
		List<NewRecords> results = getSession().createQuery("from NewRecords").list();
		close();
		return results;
	}

	public void deleteAll() {
		start();
		String stringQuery = "DELETE FROM NewRecords";
		Query query = getSession().createQuery(stringQuery);
		query.executeUpdate();
		close();
	}

}
