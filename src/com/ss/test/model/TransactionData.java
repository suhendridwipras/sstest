package com.ss.test.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "transaction_data")
public class TransactionData extends ModelEntity {
	private static final long serialVersionUID = 1L;

	public static final String TYPE = "type";
	public static final String PARENT = "parent";

	@ManyToOne
	@JoinColumn(name = "parent_id")
	private TransactionData parent;

	@Column(name = "amount")
	private double amount;

	@Column(name = "type")
	private String type;

	public TransactionData getParent() {
		return parent;
	}

	public void setParent(TransactionData parent) {
		this.parent = parent;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
