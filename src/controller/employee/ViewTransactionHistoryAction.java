
package controller.employee;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.CustomerDAO;
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

public class ViewTransactionHistoryAction extends Action {

	private static final String VIEW_HISTORY_JSP = "emp-view-transaction-history.jsp";

	private static final String NAME = "employee-view-transactions.do";

	private CustomerDAO customerDAO;
	private TransactionDAO transactionDAO;
	private FundDAO fundDAO;

	public ViewTransactionHistoryAction(Model model) {
		customerDAO = model.getCustomerDAO();
		transactionDAO = model.getTransactionDAO();
		fundDAO = model.getFundDAO();
	}

	public String getName() {
		return NAME;
	}

	public String perform(HttpServletRequest request) {
		// Set up error list
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			String userName = request.getParameter("userName");
			if (userName == null) {
				errors.add(Constants.PARAMETER_IS_REQUIRED_USER_NAME);
				return Constants.EMPLOYEE_RESULT_JSP;
			}
			Customer customer = customerDAO.readByUserName(userName);
			if (customer == null) {
				errors.add(Constants.INVALID_PARAMETER_USER_NAME);
				return Constants.EMPLOYEE_RESULT_JSP;
			}
			request.setAttribute("customer", customer);

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
			return Constants.EMPLOYEE_RESULT_JSP;
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}
	}
}
