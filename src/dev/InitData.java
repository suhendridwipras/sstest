package dev;

import java.io.IOException;

import org.hibernate.HibernateException;

import com.ss.test.model.TransactionData;
import com.ss.test.service.TransactionDataService;
import com.ss.test.util.HibernateUtil;

public class InitData {
	public static void main(String[] args) throws IOException {
		HibernateUtil.getConfiguration().setProperty("hibernate.hbm2ddl.auto", "create-drop");
		try {
			setData();
		} catch (HibernateException e) {
			e.printStackTrace();
		}

		System.out.println("-- Done running " + InitData.class.getSimpleName());
		System.exit(0);
	}

	private static void setData() {
		TransactionData transactionData1 = new TransactionData();
		transactionData1.setAmount(1000000);
		transactionData1.setType("cars");
		transactionData1 = TransactionDataService.get().store(transactionData1);

		TransactionData transactionData2 = new TransactionData();
		transactionData2.setAmount(900000);
		transactionData2.setType("cars");
		transactionData2.setParent(transactionData1);
		TransactionDataService.get().store(transactionData2);

		transactionData1 = new TransactionData();
		transactionData1.setAmount(300000);
		transactionData1.setType("cars");
		transactionData1 = TransactionDataService.get().store(transactionData1);

		transactionData1 = new TransactionData();
		transactionData1.setAmount(15000);
		transactionData1.setType("motorcycle");
		transactionData1 = TransactionDataService.get().store(transactionData1);
	}
}