
package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

import util.Constants;
import util.Util;

import com.google.gson.Gson;

public class DepositCheckForm extends FormBean {
	private String amount;

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		getAmountErrors(errors);

		if (errors.size() > 0) {
			return errors;
		}
		return errors;
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

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

	public double getAmountValue() {
		if (amount == null) {
			return -1;
		}
		if (amount.length() > Constants.MAX_LENGTH_OF_AMOUNT_INPUT) {
			return -1;
		}
		double amountValue = -1;
		try {
			amountValue = Double.valueOf(amount);
		} catch (Exception e) {
			Util.e(e);
		}

		if (amountValue > Constants.MAX_VALUE_OF_AMOUNT_INPUT) {
			return -1;
		}
		return amountValue;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
}
