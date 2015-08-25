
package model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import databeans.Customer;

public class CustomerDAO extends GenericDAO<Customer> {

	public CustomerDAO(String tableName, ConnectionPool pool)
			throws DAOException {
		super(Customer.class, tableName, pool);
	}

	public void create(Customer user) throws RollbackException {
		try {
			Transaction.begin();
			if (readByUserName(user.getUserName()) != null) {
				throw new RollbackException("User name already exists");
			}
			super.create(user);
			Transaction.commit();
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
	}

	public void setPassword(String email, String password)
			throws RollbackException {
		try {
			Transaction.begin();
			Customer dbUser = readByUserName(email);

			if (dbUser == null) {
				throw new RollbackException("User " + email
						+ " no longer exists");
			}
			dbUser.setPassword(password);

			update(dbUser);
			Transaction.commit();
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
	}

	public Customer readByUserName(String userName) throws RollbackException {
		Customer[] users = match(MatchArg.equals("userName", userName));

		if (users.length > 1) {
			throw new RollbackException("More than one account matched");
		}

		if (users.length == 0) {
			return null;
		}
		return users[0];
	}

	public Customer[] find(String keyword) throws RollbackException {
		Customer[] users = match(MatchArg.or(
				MatchArg.containsIgnoreCase("userName", keyword),
				MatchArg.containsIgnoreCase("firstName", keyword),
				MatchArg.containsIgnoreCase("lastName", keyword)));
		return users;
	}

	public long getCurrentAmount(int customerId) throws RollbackException {
		Customer[] users = match(MatchArg.equals("id", customerId));
		if (users.length != 1) {
			throw new RollbackException("Invalid user id");
		}

		return users[0].getAmount();
	}

}
