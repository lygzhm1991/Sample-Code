
package databeans;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.genericdao.PrimaryKey;

import com.google.gson.Gson;

@PrimaryKey("id")
public class TransactionBean {
	private int id;
	private int customerId;
	private int fundId;
	private long date = 0;
	private long price;
	private long share;
	private long amount;
	private int type;

	public static final int BUY_FUND = 0;
	public static final int SELL_FUND = 1;
	public static final int REQUEST_CHECK = 2;
	public static final int DEPOSIT_CHECK = 3;

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

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

	public long getShare() {
		return share;
	}

	public void setShare(long share) {
		this.share = share;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isBuyFund() {
		return type == BUY_FUND;
	}

	public boolean isSellFund() {
		return type == SELL_FUND;
	}

	public boolean isRequestCheck() {
		return type == REQUEST_CHECK;
	}

	public boolean isDepositCheck() {
		return type == DEPOSIT_CHECK;
	}

	public String getTypeDescription() {
		switch (type) {
		case BUY_FUND:
			return "buy fund";
		case SELL_FUND:
			return "sell fund";
		case REQUEST_CHECK:
			return "request check";
		case DEPOSIT_CHECK:
			return "deposit check";
		default:
			return null;
		}
	}

	public String getDateString() {
		if (this.date == 0) {
			return "-";
		}
		Date date = new Date(this.date);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(date);
	}

	public double getShareValue() {
		return (double) share / 1000;
	}

	public double getAmountValue() {
		return (double) amount / 100;
	}

	public long getPrice() {
		return price;
	}

	public double getPriceValue() {
		return (double) price / 100;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public void setPrice(long price) {
		this.price = price;
	}
}
