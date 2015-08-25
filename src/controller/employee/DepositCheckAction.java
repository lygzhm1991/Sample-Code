
package controller.employee;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.CustomerDAO;
import model.Model;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import util.Constants;
import util.Util;
import controller.Action;
import databeans.Customer;
import databeans.TransactionBean;
import formbeans.DepositCheckForm;

public class DepositCheckAction extends Action {

	private static final String FUND_TRANSACTION_JSP = "employee-deposit-check.jsp";

	public static final String NAME = "employee-deposit-check.do";

	private FormBeanFactory<DepositCheckForm> formBeanFactory = FormBeanFactory
			.getInstance(DepositCheckForm.class);

	private CustomerDAO customerDAO;
	private Model model;

	public DepositCheckAction(Model model) {
		customerDAO = model.getCustomerDAO();
		this.model = model;
	}

	public String getName() {
		return NAME;
	}

	public String perform(HttpServletRequest request) {
		Util.i();
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			setMaxInputValues(request);

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
			DepositCheckForm form = formBeanFactory.create(request);
			Util.i(form);
			if (!form.isPresent()) {
				return FUND_TRANSACTION_JSP;
			}

			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return FUND_TRANSACTION_JSP;
			}

			TransactionBean transactionBean = new TransactionBean();
			transactionBean.setCustomerId(customer.getId());
			transactionBean.setAmount((long) (form.getAmountValue() * 100));
			transactionBean.setType(TransactionBean.DEPOSIT_CHECK);
			model.commitTransaction(transactionBean);

			request.setAttribute("message",
					Constants.YOUR_TRANSACTION_IS_COMMITED);
			return Constants.EMPLOYEE_SUCCESS_JSP;
		} catch (FormBeanException e) {
			errors.add(e.toString());
			Util.e(e);
			return FUND_TRANSACTION_JSP;
		} catch (RollbackException e) {
			Util.e(e);
			errors.add(e.toString());
			return FUND_TRANSACTION_JSP;
		} catch (NumberFormatException e) {
			Util.e(e);
			errors.add(Constants.INVALID_PARAMETER_FUND_ID);
			return FUND_TRANSACTION_JSP;
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}
	}
}
