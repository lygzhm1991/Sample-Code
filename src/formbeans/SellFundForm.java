
package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

import util.Constants;
import util.Util;

import com.google.gson.Gson;

public class SellFundForm extends FormBean {
	private String share;
	private String action;

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		getShareErrors(errors);

		if (!isSell()) {
			errors.add("Invalid action");
		}

		if (errors.size() > 0) {
			return errors;
		}

		return errors;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public boolean isSell() {
		return "sell".equals(action);
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

	public double getShareValue() {
		if (share == null) {
			return -1;
		}
		double shareValue = -1;
		try {
			shareValue = Double.valueOf(share);
		} catch (Exception e) {
			Util.e(e);
		}

		return shareValue;
	}

	public String getShare() {
		return share;
	}

	public void setShare(String share) {
		this.share = share;
	}

	private void getShareErrors(List<String> errors) {
		if (share == null) {
			errors.add("share is required");
			return;
		}
		if (share.indexOf(".") != -1
				&& (share.length() - 1 - share.indexOf(".")) > 3) {
			errors.add("share is tracked to 3 decimal places ");
			return;
		}
		double shareValue = 0;
		try {
			shareValue = Double.valueOf(share);
		} catch (Exception e) {
			errors.add("Numbers and decimal place only. No commas, letters or symbols.");
			return;
		}
		if (shareValue < Constants.MIN_VALUE_OF_SHARE_INPUT
				|| shareValue > Constants.MAX_VALUE_OF_SHARE_INPUT) {
			errors.add("The share should go between "
					+ Constants.MIN_VALUE_OF_SHARE_INPUT + " and "
					+ Constants.MAX_VALUE_OF_SHARE_INPUT_STRING);
			return;
		}
	}
}
