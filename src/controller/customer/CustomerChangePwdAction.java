
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
import formbeans.ChangePwdForm;

public class CustomerChangePwdAction extends Action {

	private static final String CHANGE_PWD_JSP = "customerchangepwd.jsp";

	private static final String NAME = "customer-change-pwd.do";

	private FormBeanFactory<ChangePwdForm> formBeanFactory = FormBeanFactory
			.getInstance(ChangePwdForm.class);

	private CustomerDAO customerDAO;

	public CustomerChangePwdAction(Model model) {
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

			ChangePwdForm form = formBeanFactory.create(request);
			if (!form.isPresent()) {
				return CHANGE_PWD_JSP;
			}

			// Check for any validation errors
			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return CHANGE_PWD_JSP;
			}

			Customer user = (Customer) request.getSession().getAttribute(
					"customer");
			user = customerDAO.read(user.getId());
			// Change the password
			customerDAO.setPassword(user.getUserName(), form.getNewPassword());

			request.setAttribute("message",
					Constants.PASSWORD_CHANGED_SUCCESSFULLY);
			return Constants.CUSTOMER_SUCCESS_JSP;
		} catch (RollbackException e) {
			Util.e(e);
			errors.add(e.toString());
			return CHANGE_PWD_JSP;
		} catch (FormBeanException e) {
			Util.e(e);
			errors.add(e.toString());
			return CHANGE_PWD_JSP;
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}
	}
}
