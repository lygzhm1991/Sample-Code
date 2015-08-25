
package model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import databeans.Employee;

public class EmployeeDAO extends GenericDAO<Employee> {

	public EmployeeDAO(String tableName, ConnectionPool pool)
			throws DAOException {
		super(Employee.class, tableName, pool);
	}


	public void create(Employee user) throws RollbackException {
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
			Employee dbUser = readByUserName(email);

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

	public Employee readByUserName(String userName) throws RollbackException {
		Employee[] users = match(MatchArg.equals("userName", userName));

		if (users.length > 1) {
			throw new RollbackException("More than one account matched");
		}

		if (users.length == 0) {
			return null;
		}
		return users[0];
	}
}
