package com.ss.test.dao;

import com.ss.test.model.TransactionData;

public class TransactionDataDAO extends DAO<TransactionData> {
	protected TransactionDataDAO() {
		super(TransactionData.class);
	}

	private static TransactionDataDAO instance;

	public static TransactionDataDAO get() {
		if (instance == null) {
			instance = new TransactionDataDAO();
		}
		return instance;
	}
}