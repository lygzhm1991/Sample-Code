
package controller.employee;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.CustomerDAO;
import model.FundDAO;
import model.Model;
import model.PositionDAO;
import model.PriceDAO;
import model.TransactionDAO;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import util.Constants;
import util.Util;
import controller.Action;
import databeans.Customer;
import databeans.Fund;
import databeans.Position;
import databeans.Price;
import databeans.ShareInfo;

public class ViewCustomerAction extends Action {

	private static final String VIEW_CUSTOMER_JSP = "employee-view-account.jsp";
	public static final String NAME = "employee_view_customer.do";

	private CustomerDAO customerDAO;
	private PositionDAO positionDAO;
	private FundDAO fundDAO;
	private TransactionDAO transactionDAO;
	private PriceDAO priceDAO;

	public ViewCustomerAction(Model model) {
		customerDAO = model.getCustomerDAO();
		positionDAO = model.getPositionDAO();
		fundDAO = model.getFundDAO();
		transactionDAO = model.getTransactionDAO();
		priceDAO = model.getPriceDAO();
	}

	public String getName() {
		return NAME;
	}

	public String perform(HttpServletRequest request) {
		Util.i();
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			String username = request.getParameter("username");
			if (username == null) {
				errors.add(Constants.PARAMETER_IS_REQUIRED_USER_NAME);
				return Constants.EMPLOYEE_RESULT_JSP;
			}
			Customer customer = customerDAO.readByUserName(username);
			if (customer == null) {
				errors.add(Constants.INVALID_PARAMETER_USER_NAME);
				return Constants.EMPLOYEE_RESULT_JSP;
			}
			Util.i(customer);
			request.setAttribute("customer", customer);
			double currentAmount = customer.getAmountValue();
			double pendingAmount = transactionDAO.getPendingAmount(customer
					.getId());
			double validAmount = currentAmount - pendingAmount;
			Util.i("customerId = ", customer.getId(), ", currentAmount = ",
					currentAmount, ", pendingAmount = ", pendingAmount,
					", validAmount = ", validAmount);
			request.setAttribute("currentAmount",
					Util.formatNumber(currentAmount, Constants.AMOUNT_FORMAT));
			request.setAttribute("validAmount",
					Util.formatNumber(validAmount, Constants.AMOUNT_FORMAT));

			Position[] positionList = positionDAO.readUserPosition(customer
					.getId());
			if (positionList == null) {
				return VIEW_CUSTOMER_JSP;
			}
			String lastTransactionDay = null;
			ShareInfo[] shareList = new ShareInfo[positionList.length];
			for (int i = 0; i < positionList.length; i++) {
				Fund fund = fundDAO.read(positionList[i].getFundid());
				shareList[i] = new ShareInfo();
				shareList[i].setFundId(fund.getId());
				shareList[i].setFundName(fund.getName());
				shareList[i].setFundTicker(fund.getTicker());
				shareList[i].setShare(positionList[i].getShare());
				Price price = priceDAO.getCurrentPrice(fund.getId());
				if (price != null) {
					shareList[i].setAmount(price.getPriceValue()
							* positionList[i].getShareValue());
					if (lastTransactionDay == null) {
						lastTransactionDay = price.getDateString();
						request.setAttribute("lastTransactionDay",
								lastTransactionDay);
					}
				}
			}
			request.setAttribute("shareList", shareList);

			return VIEW_CUSTOMER_JSP;
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			Util.i(e);
			return Constants.EMPLOYEE_RESULT_JSP;
		} catch (NumberFormatException e) {
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
