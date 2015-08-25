
package databeans;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.genericdao.PrimaryKey;

import util.Constants;
import util.Util;

@PrimaryKey("id")
public class Price {
	private int id;
	private int fundId;
	private long date;
	private long price;

	public int getFundId() {
		return fundId;
	}

	public void setFundId(int fundId) {
		this.fundId = fundId;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getPriceValue() {
		return (double) price / 100;
	}

	public String getPriceString() {
		return Util.formatNumber(price, Constants.PRICE_FORMAT);
	}

	public String getDateString() {
		if (this.date == 0) {
			return "-";
		}
		Date date = new Date(this.date);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(date);
	}
	
	public String getDateMMDDYYYY() {
		if (this.date == 0) {
			return "-";
		}
		Date date = new Date(this.date);
		SimpleDateFormat df = new SimpleDateFormat("MM/dd, yyyy");
		return df.format(date);
	}
	
	
}
