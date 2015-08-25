package controller.customer;

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

public class CustomerViewAccountAction extends Action {

	private static final String CUSTOMER_VIEW_ACCOUNT_JSP = "customerviewaccount.jsp";
	public static final String NAME = "customer_viewaccount.do";
	private PositionDAO positionDAO;
	private FundDAO fundDAO;
	private CustomerDAO customerDAO;
	private TransactionDAO transactionDAO;
	private PriceDAO priceDAO;

	public CustomerViewAccountAction(Model model) {
		positionDAO = model.getPositionDAO();
		fundDAO = model.getFundDAO();
		customerDAO = model.getCustomerDAO();
		transactionDAO = model.getTransactionDAO();
		priceDAO = model.getPriceDAO();
	}

	public String getName() {
		return NAME;
	}

	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			Customer customer = (Customer) request.getSession(false)
					.getAttribute("customer");
			customer = customerDAO.read(customer.getId());

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
				return CUSTOMER_VIEW_ACCOUNT_JSP;
			}
			ShareInfo[] shareList = new ShareInfo[positionList.length];
			String lastTransactionDay = null;
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

			return CUSTOMER_VIEW_ACCOUNT_JSP;
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return Constants.CUSTOMER_RESULT_JSP;
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}
	}
}
