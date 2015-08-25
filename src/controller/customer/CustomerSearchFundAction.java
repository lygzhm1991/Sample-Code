
package controller.customer;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.FundDAO;
import model.Model;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import controller.Action;
import util.Constants;
import util.Util;
import databeans.Fund;
import formbeans.SearchFundForm;

public class CustomerSearchFundAction extends Action {

	private static final String SEARCHFUND_JSP = "researchfund.jsp";
	public static final String NAME = "customer_search_fund.do";

	private FormBeanFactory<SearchFundForm> formBeanFactory = FormBeanFactory
			.getInstance(SearchFundForm.class);

	private FundDAO fundDAO;

	public CustomerSearchFundAction(Model model) {
		fundDAO = model.getFundDAO();
	}

	public String getName() {
		return NAME;
	}

	public String perform(HttpServletRequest request) {
		Util.i();
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		SearchFundForm form;
		try {
			form = formBeanFactory.create(request);
			request.setAttribute("form", form);
			if (!form.isPresent()) {
				return SEARCHFUND_JSP;
			}

			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return SEARCHFUND_JSP;
			}

			Fund[] funds = fundDAO.find(form.getKeyword());
			request.setAttribute("fundList", funds);
			Util.i(funds.length);
			return SEARCHFUND_JSP;
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			Util.i(e);
			return Constants.CUSTOMER_RESULT_JSP;
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			Util.i(e);
			return Constants.CUSTOMER_RESULT_JSP;
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}

	}
}
