
package model;

import java.util.Arrays;
import java.util.Comparator;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import databeans.Price;

public class PriceDAO extends GenericDAO<Price> {

	public PriceDAO(String tableName, ConnectionPool pool) throws DAOException {
		super(Price.class, tableName, pool);
	}

	public Price getCurrentPrice(int fundId) throws RollbackException {
		Price[] prices = match(MatchArg.and(MatchArg.equals("fundId", fundId)));
		if (prices == null || prices.length == 0) {
			return null;
		}
		Price currentPrice = prices[0];
		for (Price price : prices) {
			if (price.getDate() > currentPrice.getDate()) {
				currentPrice = price;
			}
		}
		return currentPrice;
	}

	public Price[] getPrices(int fundId) throws RollbackException {
		Price[] prices = match(MatchArg.equals("fundId", fundId));
		Arrays.sort(prices, new Comparator<Price>() {

			@Override
			public int compare(Price o1, Price o2) {
				return ((Long) o1.getDate()).compareTo(o2.getDate());
			}
		});
		return prices;
	}
}
