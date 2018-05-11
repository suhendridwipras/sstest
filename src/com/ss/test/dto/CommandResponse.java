package com.ss.test.dto;

import java.io.Serializable;

public class CommandResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	public boolean success;
	public int errorCode;
	public String message;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("success: ").append(success);
		sb.append(", errorCode: ").append(errorCode);
		sb.append(", message: ").append(message);
		return sb.toString();
	}
}
