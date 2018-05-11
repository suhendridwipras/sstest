package com.ss.test.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;

import com.ss.test.model.ModelEntity;

@SuppressWarnings({ "unchecked", "deprecation" })
public class DAO<T extends ModelEntity> {

	protected Class<T> clazz;

	protected DAO(Class<T> clazz) {
		this.clazz = clazz;
	}

	public Class<T> getClazz() {
		return clazz;
	}

	public T save(Session session, T t) {
		t.setModifiedTime(new Date());
		session.saveOrUpdate(t);
		return t;
	}

	public T getById(Session session, Serializable id) {
		T bean = (T) session.get(clazz, id);
		return bean;
	}

	public List<T> list(Session session) {
		List<T> list = session.createCriteria(clazz.getName()).list();
		return list;
	}

	public List<T> listByCriterions(Session session, Criterion... criterions) {
		Criteria criteria = session.createCriteria(clazz.getName());
		for (Criterion criterion : criterions) {
			criteria.add(criterion);
		}
		return criteria.list();
	}
}
