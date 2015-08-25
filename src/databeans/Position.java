
package databeans;

import org.genericdao.PrimaryKey;

import util.Constants;
import util.Util;

@PrimaryKey("id")
public class Position {
	private int id;
	private int customerid;
	private int fundid;
	private long share;

	public int getCustomerid() {
		return customerid;
	}

	public void setCustomerid(int id) {
		this.customerid = id;
	}

	public int getFundid() {
		return fundid;
	}

	public void setFundid(int id) {
		this.fundid = id;
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

	public void setShareValue(double share) {
		this.share = (long) (Util.truncate(share, 1000) * 1000);
	}

	public double getShareValue() {
		return (double) share / 1000;
	}

	public String getShareDescription() {
		return Util.formatNumber(getShareValue(), Constants.SHARE_FORMAT);
	}

}
