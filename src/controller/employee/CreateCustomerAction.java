
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
import formbeans.CustomerRegisterForm;

public class CreateCustomerAction extends Action {

	private static final String REGISTER_JSP = "createcustomer.jsp";

	public static final String NAME = "employee_create_customer.do";

	private FormBeanFactory<CustomerRegisterForm> formBeanFactory = FormBeanFactory
			.getInstance(CustomerRegisterForm.class);

	private CustomerDAO userDAO;

	public CreateCustomerAction(Model model) {
		userDAO = model.getCustomerDAO();
	}

	public String getName() {
		return NAME;
	}

	public String perform(HttpServletRequest request) {
		Util.i();
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			CustomerRegisterForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);
			if (!form.isPresent()) {
				return REGISTER_JSP;
			}

			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return REGISTER_JSP;
			}

			Customer user = new Customer();
			user.setUserName(form.getUserName());
			user.setFirstName(form.getFirstName());
			user.setLastName(form.getLastName());
			user.setAddrLine1(form.getAddressLine1());
			user.setAddrLine2(form.getAddressLine2());
			user.setCity(form.getCity());
			user.setState(form.getState());
			user.setPassword(form.getPassword());
			user.setZip(form.getZip());
			userDAO.create(user);

			user.setId(userDAO.readByUserName(form.getUserName()).getId());

			request.setAttribute("message", "Create user successfully.");
			return Constants.EMPLOYEE_SUCCESS_JSP;
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			Util.i(e);
			return REGISTER_JSP;
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			Util.i(e);
			return REGISTER_JSP;
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}
	}
}
