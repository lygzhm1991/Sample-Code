
package controller.employee;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.Model;
import model.EmployeeDAO;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import util.Constants;
import controller.Action;
import databeans.Employee;
import formbeans.ChangePwdForm;

public class EmployeeChangePwdAction extends Action {
	private static final String CHANGE_PWD_JSP = "empchangepwd.jsp";

	private static final String NAME = "employee-change-pwd.do";

	private FormBeanFactory<ChangePwdForm> formBeanFactory = FormBeanFactory
			.getInstance(ChangePwdForm.class);

	private EmployeeDAO EmployeeDAO;

	public EmployeeChangePwdAction(Model model) {
		EmployeeDAO = model.getEmployeeDAO();
	}

	public String getName() {
		return NAME;
	}

	public String perform(HttpServletRequest request) {
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

			Employee user = (Employee) request.getSession().getAttribute(
					"employee");

			// Change the password
			EmployeeDAO.setPassword(user.getUserName(), form.getNewPassword());

			request.setAttribute("message",
					Constants.PASSWORD_CHANGED_SUCCESSFULLY);
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
