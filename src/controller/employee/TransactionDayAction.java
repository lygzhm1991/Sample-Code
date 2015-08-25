
package controller.employee;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import model.FundDAO;
import model.Model;
import model.PriceDAO;
import model.TransactionDAO;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import util.Constants;
import util.Util;
import controller.Action;
import databeans.Fund;
import databeans.FundInfo;
import databeans.Price;
import databeans.TransactionBean;
import formbeans.TransactionDayForm;

public class TransactionDayAction extends Action {

	private static final String TRANSACTION_DAY_JSP = "transitionday.jsp";

	public static final String NAME = "employee-transition-day.do";

	private FormBeanFactory<TransactionDayForm> formBeanFactory = FormBeanFactory
			.getInstance(TransactionDayForm.class);

	private TransactionDAO transactionDAO;
	private FundDAO fundDAO;
	private PriceDAO priceDAO;
	private Model model;

	public TransactionDayAction(Model model) {
		transactionDAO = model.getTransactionDAO();
		fundDAO = model.getFundDAO();
		priceDAO = model.getPriceDAO();
		this.model = model;
	}

	public String getName() {
		return NAME;
	}

	public synchronized String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			setMaxInputValues(request);

			Fund[] funds = fundDAO.readAll();
			if (funds == null) {
				return TRANSACTION_DAY_JSP;
			}

			TransactionDayForm form = formBeanFactory.create(request);
			Util.i(form);
			if (!form.isPresent()) {
				setFundInfo(request, funds);
				return TRANSACTION_DAY_JSP;
			}

			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				setFundInfo(request, funds);
				return TRANSACTION_DAY_JSP;
			}

			long newDate = form.getDateValue();
			long minNewDate = setFundInfo(request, funds);
			if (newDate < minNewDate) {
				errors.add(Constants.INVALID_PARAMETER_DATE);
				return TRANSACTION_DAY_JSP;
			}
			int[] ids = form.getIdValues();
			double[] prices = form.getPriceValues();

			Map<Integer, Double> newPrices = new HashMap<Integer, Double>();
			for (int i = 0; i < ids.length; ++i) {
				if (newPrices.containsKey(ids[i])) {
					errors.add("Duplicate fund data!");
					return TRANSACTION_DAY_JSP;
				}
				newPrices.put(ids[i], prices[i]);
			}

			for (Fund fund : funds) {
				if (!newPrices.containsKey(fund.getId())) {
					errors.add("Some funds' prices are missing!");
					return TRANSACTION_DAY_JSP;
				}
			}

			if (newPrices.size() != funds.length) {
				errors.add("Illegal fund data");
				return TRANSACTION_DAY_JSP;
			}

			setNewPrice(form, priceDAO, newPrices);
			setFundInfo(request, funds);

			TransactionBean[] transactions = transactionDAO
					.getPendingTransactions();
			if (transactions == null || transactions.length == 0) {
				request.setAttribute("message", "0 transaction(s) proceeded");
				return TRANSACTION_DAY_JSP;
			}

			for (TransactionBean transactionBean : transactions) {
				model.processTransaction(transactionBean, newDate);
			}
			request.setAttribute("message", String.valueOf(transactions.length)
					+ " transaction(s) proceeded");
			return TRANSACTION_DAY_JSP;
		} catch (FormBeanException e) {
			Util.e(e);
			errors.add(e.toString());
		} catch (RollbackException e) {
			Util.e(e);
			errors.add(e.getMessage());
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}
		return Constants.EMPLOYEE_RESULT_JSP;
	}

	private long setNewPrice(TransactionDayForm form, PriceDAO priceDAO,
			Map<Integer, Double> newPrices) throws RollbackException {
		long date = form.getDateValue();
		Fund[] funds = fundDAO.readAll();
		for (Fund fund : funds) {
			Price price = new Price();
			price.setFundId(fund.getId());
			price.setPrice((long) (newPrices.get(fund.getId()) * 100));
			price.setDate(date);
			priceDAO.create(price);
		}
		return date;
	}

	private long setFundInfo(HttpServletRequest request, Fund[] funds)
			throws RollbackException {
		FundInfo[] fundInfos = new FundInfo[funds.length];
		String lastTransactionDay = null;
		long minNewTransactionDay = 0;
		for (int i = 0; i < funds.length; i++) {
			fundInfos[i] = new FundInfo();
			fundInfos[i].setFundId(funds[i].getId());
			fundInfos[i].setFundName(funds[i].getName());
			fundInfos[i].setFundSymbol(funds[i].getTicker());
			Price price = priceDAO.getCurrentPrice(funds[i].getId());
			fundInfos[i].setPrice(price == null ? 0 : price.getPriceValue());
			if (price != null) {
				if (lastTransactionDay == null) {
					lastTransactionDay = price.getDateString();
					request.setAttribute("lastTransactionDay",
							lastTransactionDay);
					minNewTransactionDay = price.getDate()
							+ Constants.ONE_DAY_IN_MILLISECOND;
					request.setAttribute("minNewTransactionDay",
							getDateString(minNewTransactionDay));
				}
			}
		}
		request.setAttribute("funds", fundInfos);
		return minNewTransactionDay;
	}

	private String getDateString(long timeStamp) {
		Date date = new Date(timeStamp);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(date);
	}
}
