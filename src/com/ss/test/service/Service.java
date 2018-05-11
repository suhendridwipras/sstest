package com.ss.test.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.slim3.util.CopyOptions;

import com.ss.test.dao.DAO;
import com.ss.test.dto.DTO;
import com.ss.test.model.ModelEntity;
import com.ss.test.util.HibernateUtil;

public abstract class Service<M extends ModelEntity, D extends DTO> {

	protected DAO<M> dao;
	protected CopyOptions copyOptions;

	public Service(DAO<M> dao) {
		this.dao = dao;
	}

	public M store(M model) {
		M m = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			m = dao.save(session, model);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			throw e;
		} finally {
			session.close();
		}
		return (m != null) ? m : null;
	}

	public M getById(Long id) throws IllegalArgumentException {
		if (id == null) {
			throw new IllegalArgumentException("Null ID");
		}

		M bean = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			bean = dao.getById(session, id);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return bean;
	}

	public List<D> getDataTransferObjectList(List<M> list) {
		List<D> dtoList = new ArrayList<D>();
		for (M bean : list) {
			dtoList.add(getDataTransferObject(bean));
		}
		return dtoList;
	}

	public abstract Class<D> getDtoClass();

	public D getDataTransferObject(M bean) {
		D dto = null;
		try {
			dto = getDtoClass().newInstance();
			copyBeanFieldsToDTO(bean, dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	public M getServerBean(D dto) {
		M bean = null;
		try {
			bean = dao.getClazz().newInstance();
			copyDTOFieldsToBean(dto, bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}

	protected void copyBeanFieldsToDTO(M bean, D dto) {
		dto.setId(bean.getId());
		dto.setCreatedTime(bean.getCreatedTime());
		dto.setModifiedTime(bean.getModifiedTime());
	}

	protected void copyDTOFieldsToBean(D dto, M bean) {
		bean.setId(dto.getId());

		if (dto.getCreatedTime() != null) {
			bean.setCreatedTime(dto.getCreatedTime());
		}

		if (dto.getModifiedTime() != null) {
			bean.setModifiedTime(dto.getModifiedTime());
		}
	}

	public List<M> list() {
		List<M> list = new ArrayList<M>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			list = dao.list(session);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return list;
	}

	public List<M> listByCriterions(Criterion... criterions) {
		List<M> list = new ArrayList<M>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			list = dao.listByCriterions(session, criterions);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return list;
	}
}