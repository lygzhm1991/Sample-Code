
package controller.customer;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.CustomerDAO;
import model.FundDAO;
import model.Model;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import util.Constants;
import util.Util;
import controller.Action;
import databeans.Customer;
import databeans.Fund;
import databeans.TransactionBean;
import formbeans.BuyFundForm;

public class CustomerBuyFundAction extends Action {

	private static final String FUND_TRANSACTION_JSP = "customerbuyfund.jsp";

	public static final String NAME = "customer-buy-fund.do";

	private FormBeanFactory<BuyFundForm> formBeanFactory = FormBeanFactory
			.getInstance(BuyFundForm.class);

	private FundDAO fundDAO;
	private CustomerDAO customerDAO;
	private Model model;

	public CustomerBuyFundAction(Model model) {
		fundDAO = model.getFundDAO();
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

			String fundIdString = request.getParameter("fundId");
			if (fundIdString == null || fundIdString.length() == 0) {
				errors.add(Constants.PARAMETER_IS_REQUIRED_FUND_ID);
				return Constants.CUSTOMER_RESULT_JSP;
			}

			int fundId = Integer.valueOf(fundIdString);
			Fund fund = fundDAO.read(fundId);
			if (fund == null) {
				errors.add(Constants.INVALID_PARAMETER_FUND_ID);
				return Constants.CUSTOMER_RESULT_JSP;
			}
			request.setAttribute("fund", fund);

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

			BuyFundForm form = formBeanFactory.create(request);
			Util.i("form", form);
			if (!form.isPresent()) {
				return FUND_TRANSACTION_JSP;
			}

			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return FUND_TRANSACTION_JSP;
			}

			TransactionBean transactionBean = new TransactionBean();
			transactionBean.setFundId(fundId);
			transactionBean.setCustomerId(customer.getId());
			transactionBean.setAmount((long) (form.getAmountValue() * 100));
			transactionBean.setType(TransactionBean.BUY_FUND);
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
			return Constants.CUSTOMER_RESULT_JSP;
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}

	}
}
