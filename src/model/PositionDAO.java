
package model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import databeans.Position;

public class PositionDAO extends GenericDAO<Position> {

	public PositionDAO(String tableName, ConnectionPool pool)
			throws DAOException {
		super(Position.class, tableName, pool);
	}

	public void updatePosition(Position record) throws RollbackException {
		if (record.getId() == 0) {
			create(record);
			return;
		}
		if (record.getShare() == 0) {
			delete(record.getId());
			return;
		}
		update(record);
	}

	public Position read(int customerid, int fundid) throws RollbackException {
		Position[] records = match(MatchArg.and(
				MatchArg.equals("customerid", customerid),
				MatchArg.equals("fundid", fundid)));

		if (records.length > 1) {
			throw new RollbackException("More than one account matched");
		}

		if (records.length == 0) {
			return null;
		}
		return records[0];
	}

	public Position[] readUserPosition(int customerid) throws RollbackException {
		Position[] records = match(MatchArg.equals("customerid", customerid));

		if (records.length == 0) {
			return null;
		}
		return records;
	}
}
