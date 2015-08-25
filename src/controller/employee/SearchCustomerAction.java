
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

import controller.Action;
import util.Constants;
import util.Util;
import databeans.Customer;
import formbeans.SearchCustomerForm;

public class SearchCustomerAction extends Action {

	private static final String SEARCHCUSTOMER_JSP = "searchcustomer.jsp";
	public static final String NAME = "employee_search_customer.do";

	private FormBeanFactory<SearchCustomerForm> formBeanFactory = FormBeanFactory
			.getInstance(SearchCustomerForm.class);

	private CustomerDAO customerDAO;

	public SearchCustomerAction(Model model) {
		customerDAO = model.getCustomerDAO();
	}

	public String getName() {
		return NAME;
	}

	public String perform(HttpServletRequest request) {
		Util.i();
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		SearchCustomerForm form;
		try {
			form = formBeanFactory.create(request);
			request.setAttribute("form", form);
			if (!form.isPresent()) {
				return SEARCHCUSTOMER_JSP;
			}

			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return SEARCHCUSTOMER_JSP;
			}

			Customer[] customers = customerDAO.find(form.getKeyword());
			request.setAttribute("customers", customers);
			Util.i(customers.length);
			return SEARCHCUSTOMER_JSP;
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			Util.i(e);
			return Constants.EMPLOYEE_RESULT_JSP;
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			Util.i(e);
			return Constants.EMPLOYEE_RESULT_JSP;
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}

	}
}
