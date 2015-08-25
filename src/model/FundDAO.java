
package model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import databeans.Fund;

public class FundDAO extends GenericDAO<Fund> {

	public FundDAO(String tableName, ConnectionPool pool) throws DAOException {
		super(Fund.class, tableName, pool);
	}

	public void create(Fund fund) throws RollbackException {
		try {
			Transaction.begin();
			if (read(fund.getTicker()) != null) {
				throw new RollbackException("Ticker already exists");
			}
			super.create(fund);
			Transaction.commit();
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
	}

	public Fund[] readAll() throws RollbackException {
		return match();
	}

	public Fund read(String ticker) throws RollbackException {
		Fund[] funds = match(MatchArg.equals("ticker", ticker));

		if (funds.length > 1) {
			throw new RollbackException("More than one account matched");
		}

		if (funds.length == 0) {
			return null;
		}
		return funds[0];
	}

	public Fund[] find(String keyword) throws RollbackException {
		Fund[] funds = match(MatchArg.or(
				MatchArg.containsIgnoreCase("name", keyword),
				MatchArg.containsIgnoreCase("ticker", keyword)));
		return funds;
	}
}
