
package controller.employee;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.FundDAO;
import model.Model;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import util.Constants;
import util.Util;
import controller.Action;
import databeans.Fund;
import formbeans.CreateFundForm;

public class CreateFundAction extends Action {

	private static final String CREATE_FUND_JSP = "createfund.jsp";

	public static final String NAME = "employee_create_fund.do";

	private FormBeanFactory<CreateFundForm> formBeanFactory = FormBeanFactory
			.getInstance(CreateFundForm.class);

	private FundDAO fundDAO;

	public CreateFundAction(Model model) {
		fundDAO = model.getFundDAO();
	}

	public String getName() {
		return NAME;
	}

	public String perform(HttpServletRequest request) {
		Util.i();
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			CreateFundForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);
			if (!form.isPresent()) {
				return CREATE_FUND_JSP;
			}

			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return CREATE_FUND_JSP;
			}

			Fund fund = new Fund();
			fund.setName(form.getName());
			fund.setTicker(form.getTicker());
			fundDAO.create(fund);

			fund.setId(fundDAO.read(form.getTicker()).getId());
			request.setAttribute("message", Constants.CREATE_FUND_SUCCESSFULLY);
			return Constants.EMPLOYEE_SUCCESS_JSP;
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			Util.i(e);
			return CREATE_FUND_JSP;
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			Util.i(e);
			return CREATE_FUND_JSP;
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}
	}
}
