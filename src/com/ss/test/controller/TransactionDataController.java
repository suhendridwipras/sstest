package com.ss.test.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.hibernate.criterion.Restrictions;
import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import com.ss.test.dto.CommandResponse;
import com.ss.test.dto.SumTransDTO;
import com.ss.test.dto.TransactionDataDTO;
import com.ss.test.model.TransactionData;
import com.ss.test.service.TransactionDataService;
import com.ss.test.util.Util;

public class TransactionDataController extends Controller {
	public static final int SC_INTERNAL_SERVER_ERROR = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
	public static final String MIMETYPE_JSON = "application/json";

	@Override
	protected Navigation run() throws Exception {

		try {
			if (isPut()) {
				doPut();
			}

			if (isGet()) {
				doGet();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private void doGet() throws IOException {
		Long id = asLong("id");
		String type = asString("type");
		String sum = asString("sum");
		if (sum != null && id != null) {
			TransactionData td = TransactionDataService.get().getById(id);
			List<TransactionData> list = TransactionDataService.get().listByCriterions(Restrictions
					.or(Restrictions.eq(TransactionData.PARENT, td), Restrictions.eq(TransactionData.ID, id)));
			SumTransDTO sumDTO = new SumTransDTO();
			double sumD = 0;
			for (TransactionData t : list) {
				sumD += t.getAmount();
			}
			sumDTO.setSum(sumD);
			String strResponse = Util.getGson().toJson(sumDTO);
			response.setContentType(MIMETYPE_JSON);
			response.getWriter().write(strResponse);
		} else if (type != null) {
			List<TransactionData> list = TransactionDataService.get()
					.listByCriterions(Restrictions.eq(TransactionData.TYPE, type));
			List<TransactionDataDTO> listDTO = TransactionDataService.get().getDataTransferObjectList(list);
			String strResponse = Util.getGson().toJson(listDTO);
			response.setContentType(MIMETYPE_JSON);
			response.getWriter().write(strResponse);
		} else if (id != null) {
			TransactionData td = TransactionDataService.get().getById(id);
			TransactionDataDTO tdDTO = TransactionDataService.get().getDataTransferObject(td);
			String strResponse = Util.getGson().toJson(tdDTO);
			response.setContentType(MIMETYPE_JSON);
			response.getWriter().write(strResponse);
		} else {
			List<TransactionData> list = TransactionDataService.get().list();
			List<TransactionDataDTO> listDTO = TransactionDataService.get().getDataTransferObjectList(list);
			String strResponse = Util.getGson().toJson(listDTO);
			response.setContentType(MIMETYPE_JSON);
			response.getWriter().write(strResponse);
		}
	}

	private void doPut() throws IOException {
		String json = IOUtils.toString(request.getInputStream(), Util.getCharEncoding());
		CommandResponse cr = new CommandResponse();
		try {
			Long id = asLong("id");

			if (id == null) {
				throw new Exception("Invalid ID");
			}

			if (json == null) {
				throw new Exception("Input data tidak valid");
			}

			TransactionDataDTO transactionDataDTO = Util.getGson().fromJson(json, TransactionDataDTO.class);

			if (transactionDataDTO == null) {
				throw new Exception("Input data tidak valid");
			}

			TransactionData td = TransactionDataService.get().getById(id);
			td.setAmount(transactionDataDTO.getAmount());
			td.setType(transactionDataDTO.getType());
			TransactionDataService.get().store(td);

			cr.success = true;
			cr.message = "Data berhasil dihapus";
		} catch (Exception e) {
			e.printStackTrace();
			cr.errorCode = SC_INTERNAL_SERVER_ERROR;
			cr.success = false;
			cr.message = e.getMessage();
		}

		String strResponse = Util.getGson().toJson(cr);
		response.setContentType(MIMETYPE_JSON);
		response.getWriter().write(strResponse);
	}
}