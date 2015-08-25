
package controller.customer;

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
import formbeans.RequestCheckForm;

public class CustomerRequestCheckAction extends Action {

	private static final String FUND_TRANSACTION_JSP = "customer-request-check.jsp";

	public static final String NAME = "customer-request-check.do";

	private FormBeanFactory<RequestCheckForm> formBeanFactory = FormBeanFactory
			.getInstance(RequestCheckForm.class);

	private CustomerDAO customerDAO;
	private Model model;

	public CustomerRequestCheckAction(Model model) {
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

			RequestCheckForm form = formBeanFactory.create(request);
			Util.i("form", form);

			Customer customer = (Customer) request.getSession().getAttribute(
					"customer");
			customer = customerDAO.read(customer.getId());
			request.setAttribute("customer", customer);

			double[] values = model.getAmount(customer.getId());
			double currentAmount = values[0];
			double validAmount = values[2];
			Util.i("customerId = ", customer.getId(), ", currentAmount = ",
					currentAmount, ", validAmount = ", validAmount);
			request.setAttribute("currentAmount",
					Util.formatNumber(currentAmount, Constants.AMOUNT_FORMAT));
			request.setAttribute("validAmount",
					Util.formatNumber(validAmount, Constants.AMOUNT_FORMAT));

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
			transactionBean.setType(TransactionBean.REQUEST_CHECK);
			model.commitTransaction(transactionBean);

			request.setAttribute("message",
					Constants.YOUR_TRANSACTION_IS_COMMITED);
			return Constants.CUSTOMER_SUCCESS_JSP;
		} catch (FormBeanException e) {
			errors.add(e.toString());
			Util.e(e);
			return FUND_TRANSACTION_JSP;
		} catch (RollbackException e) {
			Util.e(e);
			errors.add(e.getMessage());
			return FUND_TRANSACTION_JSP;
		} catch (NumberFormatException e) {
			Util.e(e);
			errors.add(e.toString());
			return FUND_TRANSACTION_JSP;
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}
	}
}
