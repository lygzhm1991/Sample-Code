
package model;

import java.util.Arrays;
import java.util.Comparator;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import util.Util;
import databeans.TransactionBean;

public class TransactionDAO extends GenericDAO<TransactionBean> {

	public TransactionDAO(String tableName, ConnectionPool pool)
			throws DAOException {
		super(TransactionBean.class, tableName, pool);
	}

	public TransactionBean[] getTransactions() throws RollbackException {
		TransactionBean[] transaction = match();
		return transaction;
	}

	public TransactionBean[] getPendingTransactions() throws RollbackException {
		TransactionBean[] transactions = match(MatchArg.equals("date", 0L));
		return transactions;
	}

	public double getPendingShare(int customerId, int fundId)
			throws RollbackException {
		TransactionBean[] transactions = match(MatchArg.and(
				MatchArg.equals("customerId", customerId),
				MatchArg.equals("fundId", fundId), MatchArg.equals("date", 0L),
				MatchArg.equals("type", TransactionBean.SELL_FUND)));
		long share = 0;
		if (transactions == null) {
			return share;
		}
		for (TransactionBean transactionBean : transactions) {
			share += transactionBean.getShare();
		}
		return (double) share / 1000;
	}

	public double getPendingAmount(int customerId) throws RollbackException {
		TransactionBean[] transactions = match(MatchArg.and(
				MatchArg.equals("customerId", customerId),
				MatchArg.equals("date", 0L)));
		long amount = 0;
		if (transactions == null) {
			return amount;
		}
		for (TransactionBean transactionBean : transactions) {
			if (transactionBean.isBuyFund() || transactionBean.isRequestCheck()) {
				amount += transactionBean.getAmount();
			}
		}
		return (double) amount / 100;
	}

	public TransactionBean[] readByCustomerId(int customerId)
			throws RollbackException {
		TransactionBean[] transactions = match(MatchArg.equals("customerId",
				customerId));
		Util.i("transactions = ", transactions.length);
		Arrays.sort(transactions, new Comparator<TransactionBean>() {

			@Override
			public int compare(TransactionBean o1, TransactionBean o2) {
				if (o1.getDate() != o2.getDate()) {
					return ((Long) o1.getDate()).compareTo(o2.getDate());
				}
				return -((Integer) o1.getId()).compareTo(o2.getId());
			}
		});
		return transactions;
	}

	public TransactionBean getLastTransaction(int customerId)
			throws RollbackException {
		TransactionBean[] transactions = match(MatchArg
				.and(MatchArg.equals("customerId", customerId),
						MatchArg.max("date")));
		if (transactions == null || transactions.length == 0) {
			return null;
		}
		return transactions[0];
	}
}
