package edu.fiipls.transaction;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class BaseDBTransaction {
	
	Session session = null;
	Transaction tx = null;
	
	public void start(){
		session = DBConfiguration.getInstance().openSession();
		tx = session.beginTransaction();
	}
	
	public void close(){
		tx.commit();
		session.close();
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public Transaction getTx() {
		return tx;
	}

	public void setTx(Transaction tx) {
		this.tx = tx;
	}
	
	

}
