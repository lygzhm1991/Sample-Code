
package databeans;

import util.Constants;
import util.Util;

public class TransactionInfo {
	private int fundId = 0;
	private String fundName = "-";
	private String fundSymbol = "-";
	private String price = "-";
	private String share = "-";
	private String amount = "-";
	private String type = "-";
	private String date = "-";

	public int getFundId() {
		return fundId;
	}

	public void setFundId(int fundId) {
		this.fundId = fundId;
	}

	public String getFundName() {
		return fundName;
	}

	public void setFundName(String fundName) {
		this.fundName = fundName;
	}

	public String getFundSymbol() {
		return fundSymbol;
	}

	public void setFundSymbol(String fundSymbol) {
		this.fundSymbol = fundSymbol;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = Util.formatNumber(price, Constants.PRICE_FORMAT);
	}

	public String getShare() {
		return share;
	}

	public void setShare(double share) {
		this.share = Util.formatNumber(share, Constants.SHARE_FORMAT);
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = Util.formatNumber(amount, Constants.AMOUNT_FORMAT);
	}

}
