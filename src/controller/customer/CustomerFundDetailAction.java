
package controller.customer;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.FundDAO;
import model.Model;
import model.PriceDAO;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import util.Constants;
import controller.Action;
import databeans.Fund;
import databeans.Price;

public class CustomerFundDetailAction extends Action {

	private static final String CUSTOMER_VIEW_ACCOUNT_JSP = "customer-fund-detail.jsp";
	public static final String NAME = "customer_fund_detail.do";
	private FundDAO fundDAO;
	private PriceDAO priceDAO;

	public CustomerFundDetailAction(Model model) {
		fundDAO = model.getFundDAO();
		priceDAO = model.getPriceDAO();
	}

	public String getName() {
		return NAME;
	}

	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			String fundIdString = request.getParameter("fundId");
			if (fundIdString == null) {
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

			Price[] prices = priceDAO.getPrices(fundId);
			if (prices != null && prices.length != 0) {
				request.setAttribute("prices", prices);
			}

			return CUSTOMER_VIEW_ACCOUNT_JSP;
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return Constants.CUSTOMER_RESULT_JSP;
		} catch (NumberFormatException e) {
			errors.add(Constants.INVALID_PARAMETER_FUND_ID);
			return Constants.CUSTOMER_RESULT_JSP;
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}
	}
}
