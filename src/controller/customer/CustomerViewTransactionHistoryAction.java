package controller.customer;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.FundDAO;
import model.Model;
import model.TransactionDAO;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import util.Constants;
import controller.Action;
import databeans.Customer;
import databeans.Fund;
import databeans.TransactionBean;
import databeans.TransactionInfo;

public class CustomerViewTransactionHistoryAction extends Action {

	private static final String VIEW_HISTORY_JSP = "customer-view-transaction-history.jsp";

	private static final String NAME = "customer-view-transactions.do";

	private TransactionDAO transactionDAO;
	private FundDAO fundDAO;

	public CustomerViewTransactionHistoryAction(Model model) {
		transactionDAO = model.getTransactionDAO();
		fundDAO = model.getFundDAO();
	}

	public String getName() {
		return NAME;
	}

	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			Customer customer = (Customer) request.getSession().getAttribute(
					"customer");
			TransactionBean[] transactionBeans = transactionDAO
					.readByCustomerId(customer.getId());
			if (transactionBeans.length == 0) {
				return VIEW_HISTORY_JSP;
			}

			TransactionInfo[] transactions = new TransactionInfo[transactionBeans.length];
			for (int i = 0; i < transactionBeans.length; i++) {
				transactions[i] = new TransactionInfo();
				Fund fund = fundDAO.read(transactionBeans[i].getFundId());
				if (fund != null) {
					transactions[i].setFundName(fund.getName());
				}
				transactions[i].setDate(transactionBeans[i].getDateString());
				if (transactionBeans[i].getShare() != 0) {
					transactions[i].setShare(transactionBeans[i]
							.getShareValue());
				}
				if (transactionBeans[i].getPrice() != 0) {
					transactions[i].setPrice(transactionBeans[i]
							.getPriceValue());
				}
				if (transactionBeans[i].getAmount() != 0) {
					transactions[i].setAmount(transactionBeans[i]
							.getAmountValue());
				}
				transactions[i].setType(transactionBeans[i]
						.getTypeDescription());
			}

			request.setAttribute("transactions", transactions);

			return VIEW_HISTORY_JSP;
		} catch (RollbackException e) {
			errors.add(e.toString());
			return Constants.CUSTOMER_RESULT_JSP;
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}
	}
}
