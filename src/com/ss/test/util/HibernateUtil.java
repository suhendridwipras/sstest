package com.ss.test.util;

import java.io.IOException;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static Configuration configuration;
	private static SessionFactory sessionFactory;

	public static Configuration getConfiguration() throws IOException {
		if (configuration == null) {
			configuration = new Configuration().configure();
			configuration.configure("hibernate.cfg.xml");
		}
		return configuration;
	}

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				sessionFactory = getConfiguration().buildSessionFactory();
			} catch (Throwable ex) {
				System.err.println("Initial SessionFactory creation failed." + ex);
				throw new ExceptionInInitializerError(ex);
			}
		}
		return sessionFactory;
	}
}