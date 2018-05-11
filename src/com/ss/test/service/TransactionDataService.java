package com.ss.test.service;

import com.ss.test.dao.DAO;
import com.ss.test.dao.TransactionDataDAO;
import com.ss.test.dto.TransactionDataDTO;
import com.ss.test.model.TransactionData;

public class TransactionDataService extends Service<TransactionData, TransactionDataDTO> {
	private static TransactionDataService instance;

	public static TransactionDataService get() {
		if (instance == null) {
			instance = new TransactionDataService();
		}
		return instance;
	}

	public TransactionDataService() {
		super(TransactionDataDAO.get());
	}

	public TransactionDataService(DAO<TransactionData> dao) {
		super(TransactionDataDAO.get());
	}

	@Override
	public Class<TransactionDataDTO> getDtoClass() {
		return TransactionDataDTO.class;
	}

	@Override
	public TransactionDataDTO getDataTransferObject(TransactionData bean) {
		TransactionDataDTO dto = super.getDataTransferObject(bean);
		dto.setAmount(bean.getAmount());
		dto.setType(bean.getType());
		if (bean.getParent() != null) {
			dto.setParentId(bean.getParent().getId());
		}
		return dto;
	}

	@Override
	public TransactionData getServerBean(TransactionDataDTO dto) {
		TransactionData bean = super.getServerBean(dto);
		bean.setAmount(dto.getAmount());
		bean.setType(dto.getType());
		return bean;
	}
}