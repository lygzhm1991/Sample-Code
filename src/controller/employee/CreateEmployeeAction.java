
package controller.employee;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.EmployeeDAO;
import model.Model;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import util.Constants;
import util.Util;
import controller.Action;
import databeans.Employee;
import formbeans.EmployeeRegisterForm;

public class CreateEmployeeAction extends Action {

	private static final String REGISTER_JSP = "createemployee.jsp";

	public static final String EMPLOYEE_REGISTER_DO = "employee_register.do";

	private FormBeanFactory<EmployeeRegisterForm> formBeanFactory = FormBeanFactory
			.getInstance(EmployeeRegisterForm.class);

	private EmployeeDAO userDAO;

	public CreateEmployeeAction(Model model) {
		userDAO = model.getEmployeeDAO();
	}

	public String getName() {
		return EMPLOYEE_REGISTER_DO;
	}

	public String perform(HttpServletRequest request) {
		Util.i();
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			EmployeeRegisterForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);
			if (!form.isPresent()) {
				return REGISTER_JSP;
			}

			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return REGISTER_JSP;
			}

			Employee employee = new Employee();
			employee.setUserName(form.getUserName());
			employee.setFirstName(form.getFirstName());
			employee.setLastName(form.getLastName());
			employee.setPassword(form.getPassword());
			userDAO.create(employee);

			employee.setUserid(userDAO.readByUserName(form.getUserName())
					.getUserid());
			request.setAttribute("message",
					Constants.CREATE_EMPLOYEE_ACCOUNT_SUCCESSFULLY);
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
