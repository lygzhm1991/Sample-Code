
package model;

import java.io.File;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import util.Constants;
import util.Util;
import databeans.Customer;
import databeans.Position;
import databeans.Price;
import databeans.TransactionBean;

public class Model {
	private EmployeeDAO employeeDAO;
	private CustomerDAO customerDAO;
	private TransactionDAO transactionDAO;
	private FundDAO fundDAO;
	private PositionDAO positionDAO;
	private PriceDAO priceDAO;
	private ConnectionPool pool = null;

	public Model(ServletConfig config) throws ServletException {
		try {
			String jdbcDriver = config.getInitParameter("jdbcDriverName");
			String jdbcURL = config.getInitParameter("jdbcURL");

			// FIXME
			if ("\\".equals(File.separator)) {
				pool = new ConnectionPool(jdbcDriver, jdbcURL, "root", "asdf");
			} else {
				pool = new ConnectionPool(jdbcDriver, jdbcURL);
			}

			employeeDAO = new EmployeeDAO("employee", pool);
			customerDAO = new CustomerDAO("customer", pool);
			transactionDAO = new TransactionDAO("transaction", pool);
			fundDAO = new FundDAO("fund", pool);
			positionDAO = new PositionDAO("position", pool);
			priceDAO = new PriceDAO("price", pool);
		} catch (DAOException e) {
			throw new ServletException(e);
		}
	}

	public EmployeeDAO getEmployeeDAO() {
		return employeeDAO;
	}

	public CustomerDAO getCustomerDAO() {
		return customerDAO;
	}

	public FundDAO getFundDAO() {
		return fundDAO;
	}

	public TransactionDAO getTransactionDAO() {
		return transactionDAO;
	}

	public PositionDAO getPositionDAO() {
		return positionDAO;
	}

	public PriceDAO getPriceDAO() {
		return priceDAO;
	}

	private Object amountLock = new Object();

	public double[] getAmount(int customerId) throws RollbackException {
		synchronized (amountLock) {
			Customer customer = customerDAO.read(customerId);
			double currentAmount = customer.getAmountValue();
			double pendingAmount = transactionDAO.getPendingAmount(customer
					.getId());
			double validAmount = currentAmount - pendingAmount;
			double[] values = new double[3];
			values[0] = currentAmount;
			values[1] = pendingAmount;
			values[2] = validAmount;
			return values;
		}
	}

	public double[] getShare(int customerId, int fundId)
			throws RollbackException {
		synchronized (amountLock) {
			Position position = positionDAO.read(customerId, fundId);
			double currentShare = position == null ? 0 : position
					.getShareValue();
			double pendingShare = transactionDAO.getPendingShare(customerId,
					fundId);
			double validShare = currentShare - pendingShare;
			double[] values = new double[3];
			values[0] = currentShare;
			values[1] = pendingShare;
			values[2] = validShare;
			return values;
		}
	}

	public void commitTransaction(TransactionBean transaction)
			throws RollbackException {
		synchronized (amountLock) {
			switch (transaction.getType()) {
			case TransactionBean.BUY_FUND:
			case TransactionBean.REQUEST_CHECK:
				Customer customer = customerDAO.read(transaction
						.getCustomerId());
				double currentAmount = customer.getAmountValue();
				double pendingAmount = transactionDAO.getPendingAmount(customer
						.getId());
				double validAmount = currentAmount - pendingAmount;

				if (validAmount < transaction.getAmountValue()) {
					throw new RollbackException(
							Constants.THERE_IS_NOT_ENOUGH_MONEY);
				}
				transactionDAO.create(transaction);
				break;

			case TransactionBean.SELL_FUND:
				Position position = positionDAO.read(
						transaction.getCustomerId(), transaction.getFundId());
				if (position == null) {
					throw new RollbackException("illegal fund id");
				}
				double currentShare = position.getShareValue();
				double pendingShare = transactionDAO.getPendingShare(
						transaction.getCustomerId(), transaction.getFundId());
				double validShare = currentShare - pendingShare;
				if (validShare < transaction.getShareValue()) {
					throw new RollbackException(Constants.NOT_ENOUGH_SHARES);
				}
				transactionDAO.create(transaction);
				break;

			case TransactionBean.DEPOSIT_CHECK:
				transactionDAO.create(transaction);
				break;
			}
		}
	}

	public void processTransaction(TransactionBean transactionBean, long newDate)
			throws RollbackException {
		synchronized (amountLock) {
			switch (transactionBean.getType()) {
			case TransactionBean.BUY_FUND:
				processBuy(newDate, transactionBean);
				break;
			case TransactionBean.SELL_FUND:
				processSell(newDate, transactionBean);
				break;
			case TransactionBean.REQUEST_CHECK:
				processRequest(newDate, transactionBean);
				break;
			case TransactionBean.DEPOSIT_CHECK:
				processDeposit(newDate, transactionBean);
				break;
			default:
				break;
			}
		}
	}

	private void processBuy(long date, TransactionBean transactionBean)
			throws RollbackException {
		Transaction.begin();
		Price price = priceDAO.getCurrentPrice(transactionBean.getFundId());
		double currentPrice = price == null ? Constants.MIN_VALUE_OF_PRICE_INPUT
				: price.getPriceValue();
		Customer customer = customerDAO.read(transactionBean.getCustomerId());
		Position position = positionDAO.read(transactionBean.getCustomerId(),
				transactionBean.getFundId());
		if (position == null) {
			position = new Position();
			position.setCustomerid(transactionBean.getCustomerId());
			position.setFundid(transactionBean.getFundId());
		}

		double incrementOfShare = transactionBean.getAmountValue()
				/ currentPrice;
		incrementOfShare = Util.truncate(incrementOfShare, 1000);
		double oldShare = position.getShareValue();
		double newShare = oldShare + incrementOfShare;
		Util.i("share: ", oldShare, " + ", incrementOfShare, " = ", newShare);

		long oldAmount = customer.getAmount();
		long decrementOfAmount = transactionBean.getAmount();
		long newAmount = oldAmount - decrementOfAmount;
		Util.i("amount: ", oldAmount, " - ", decrementOfAmount, " = ",
				newAmount);

		position.setShare((long) (newShare * 1000));
		customer.setAmount(newAmount);
		transactionBean.setPrice((long) (currentPrice * 100));
		transactionBean.setShare((long) (incrementOfShare * 1000));
		transactionBean.setDate(date);
		customerDAO.update(customer);
		positionDAO.updatePosition(position);
		transactionDAO.update(transactionBean);
		Transaction.commit();
	}

	private void processSell(long date, TransactionBean transactionBean)
			throws RollbackException {
		Transaction.begin();
		Price price = priceDAO.getCurrentPrice(transactionBean.getFundId());
		double currentPrice = price == null ? Constants.MIN_VALUE_OF_PRICE_INPUT
				: price.getPriceValue();
		Position position = positionDAO.read(transactionBean.getCustomerId(),
				transactionBean.getFundId());
		Customer customer = customerDAO.read(transactionBean.getCustomerId());

		double oldShare = position.getShareValue();
		double decrementOfShare = transactionBean.getShareValue();
		double newShare = oldShare - decrementOfShare;
		Util.i("share: ", oldShare, " - ", decrementOfShare, " = ", newShare);

		long oldAmount = customer.getAmount();
		long incrementOfAmount = (long) (Util.truncate(decrementOfShare
				* currentPrice, 100) * 100);
		long newAmount = oldAmount + incrementOfAmount;
		Util.i("amount: ", oldAmount, " + ", incrementOfAmount, " = ",
				newAmount);

		position.setShare((long) (newShare * 1000));
		transactionBean.setDate(date);
		transactionBean.setPrice((long) (currentPrice * 100));
		transactionBean.setAmount(incrementOfAmount);
		customer.setAmount(newAmount);
		positionDAO.updatePosition(position);
		transactionDAO.update(transactionBean);
		customerDAO.update(customer);
		Transaction.commit();
	}

	private void processRequest(long date, TransactionBean transactionBean)
			throws RollbackException {
		Transaction.begin();
		Customer customer = customerDAO.read(transactionBean.getCustomerId());

		long oldValue = customer.getAmount();
		long decrement = transactionBean.getAmount();
		long newValue = oldValue - decrement;
		Util.i(oldValue, " - ", decrement, " = ", newValue);

		customer.setAmount(newValue);
		transactionBean.setDate(date);
		customerDAO.update(customer);
		transactionDAO.update(transactionBean);
		Transaction.commit();
	}

	private void processDeposit(long date, TransactionBean transactionBean)
			throws RollbackException {
		Transaction.begin();
		Customer customer = customerDAO.read(transactionBean.getCustomerId());

		long oldValue = customer.getAmount();
		long increment = transactionBean.getAmount();
		long newValue = oldValue + increment;
		Util.i(oldValue, " + ", increment, " = ", newValue);

		customer.setAmount(newValue);
		transactionBean.setDate(date);
		customerDAO.update(customer);
		transactionDAO.update(transactionBean);
		Transaction.commit();
	}
}
