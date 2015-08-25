
package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.CustomerDAO;
import model.EmployeeDAO;
import model.Model;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import controller.customer.CustomerViewAccountAction;
import controller.employee.SearchCustomerAction;
import databeans.Customer;
import databeans.Employee;
import formbeans.LoginForm;
import util.Util;

public class LoginAction extends Action {

	private static final String LOGIN_JSP = "login.jsp";

	public static final String NAME = "login.do";

	private FormBeanFactory<LoginForm> formBeanFactory = FormBeanFactory
			.getInstance(LoginForm.class);

	private EmployeeDAO employeeDao;
	private CustomerDAO customerDAO;

	public LoginAction(Model model) {
		employeeDao = model.getEmployeeDAO();
		customerDAO = model.getCustomerDAO();
	}

	public String getName() {
		return NAME;
	}

	public String perform(HttpServletRequest request) {
		Util.i();
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {

			LoginForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);
			Util.i(form);

			if (!form.isPresent()) {
				return LOGIN_JSP;
			}

			HttpSession session = request.getSession(true);
			if (session != null) {
				session.setAttribute("employee", null);
				session.setAttribute("customer", null);
			}

			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return LOGIN_JSP;
			}
			if (form.isEmployee()) {
				Employee employee = employeeDao.readByUserName(form
						.getUserName());
				if (employee == null) {
					errors.add("User name not found");
					return LOGIN_JSP;
				}

				if (!employee.checkPassword(form.getPassword())) {
					errors.add("Incorrect password");
					return LOGIN_JSP;
				}
				session.setAttribute("employee", employee);

				return SearchCustomerAction.NAME;
			} else if (form.isCustomer()) {
				Customer customer = customerDAO.readByUserName(form
						.getUserName());

				if (customer == null) {
					errors.add("User name not found");
					return LOGIN_JSP;
				}

				if (!customer.checkPassword(form.getPassword())) {
					errors.add("Incorrect password");
					return LOGIN_JSP;
				}

				session.setAttribute("customer", customer);

				return CustomerViewAccountAction.NAME;
			}
			return null;
		} catch (RollbackException e) {
			Util.e(e);
			errors.add(e.getMessage());
			return LOGIN_JSP;
		} catch (FormBeanException e) {
			Util.e(e);
			errors.add(e.getMessage());
			return LOGIN_JSP;
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}
	}
}
