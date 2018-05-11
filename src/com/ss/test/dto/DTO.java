package com.ss.test.dto;

import java.io.Serializable;
import java.util.Date;

public abstract class DTO implements Serializable {
	static final long serialVersionUID = 1L;

	private Long id;
	private Date createdTime;
	private Date modifiedTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
}