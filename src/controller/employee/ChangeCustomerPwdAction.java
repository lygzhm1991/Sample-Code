
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
import formbeans.ChangePwdForm;

public class ChangeCustomerPwdAction extends Action {

	private static final String CHANGE_PWD_JSP = "empchangecustpwd.jsp";

	private static final String NAME = "employee-change-customer-pwd.do";

	private FormBeanFactory<ChangePwdForm> formBeanFactory = FormBeanFactory
			.getInstance(ChangePwdForm.class);

	private CustomerDAO customerDAO;

	public ChangeCustomerPwdAction(Model model) {
		customerDAO = model.getCustomerDAO();
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
				Util.i(Constants.INVALID_PARAMETER_USER_NAME);
				errors.add(Constants.INVALID_PARAMETER_USER_NAME);
				return Constants.EMPLOYEE_RESULT_JSP;
			}

			request.setAttribute("customer", customer);
			ChangePwdForm form = formBeanFactory.create(request);
			if (!form.isPresent()) {
				return CHANGE_PWD_JSP;
			}

			// Check for any validation errors
			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return CHANGE_PWD_JSP;
			}

			customerDAO.setPassword(customer.getUserName(),
					form.getNewPassword());

			request.setAttribute("message", Constants.PASSWORD_CHANGED_FOR
					+ customer.getUserName());
			return Constants.EMPLOYEE_SUCCESS_JSP;
		} catch (RollbackException e) {
			errors.add(e.toString());
			return CHANGE_PWD_JSP;
		} catch (FormBeanException e) {
			errors.add(e.toString());
			return CHANGE_PWD_JSP;
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}
	}
}
