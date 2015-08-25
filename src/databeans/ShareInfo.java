
package databeans;

import util.Constants;
import util.Util;

public class ShareInfo {
	private int fundId;
	private String fundName;
	private String fundTicker;
	private String share;
	private String amount;

	public String getFundName() {
		return fundName;
	}

	public void setFundName(String fundName) {
		this.fundName = fundName;
	}

	public String getFundTicker() {
		return fundTicker;
	}

	public void setFundTicker(String fundSymbol) {
		this.fundTicker = fundSymbol;
	}

	public String getShare() {
		return share;
	}

	public void setShare(long share) {
		this.share = Util.formatNumber((double) share / 1000, Constants.SHARE_FORMAT);
	}

	public int getFundId() {
		return fundId;
	}

	public void setFundId(int fundId) {
		this.fundId = fundId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = Util.formatNumber(amount, Constants.AMOUNT_FORMAT);
	}

}
