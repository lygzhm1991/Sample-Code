
package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

import util.Constants;
import util.Util;

import com.google.gson.Gson;

public class RequestCheckForm extends FormBean {
	private String amount;
	private String action;

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		getAmountErrors(errors);

		if (!isRequest()) {
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

	public boolean isRequest() {
		return "request".equals(action);
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public double getAmountValue() {
		if (amount == null) {
			return 0;
		}
		double value = 0;
		try {
			value = Double.valueOf(amount);
		} catch (Exception e) {
			Util.e(e);
		}

		return value;
	}

	private void getAmountErrors(List<String> errors) {
		if (amount == null) {
			errors.add("amount is required");
			return;
		}
		if (amount.indexOf(".") != -1
				&& (amount.length() - 1 - amount.indexOf(".")) > 2) {
			errors.add("amount is tracked to 2 decimal places ");
			return;
		}
		double amountValue = 0;
		try {
			amountValue = Double.valueOf(amount);
		} catch (Exception e) {
			errors.add("Numbers and decimal place only. No commas, letters or symbols.");
			return;
		}
		if (amountValue < Constants.MIN_VALUE_OF_AMOUNT_INPUT
				|| amountValue > Constants.MAX_VALUE_OF_AMOUNT_INPUT) {
			errors.add("The amount should go between "
					+ Constants.MIN_VALUE_OF_AMOUNT_INPUT + " and "
					+ Constants.MAX_VALUE_OF_AMOUNT_INPUT_STRING);
			return;
		}
	}
}
