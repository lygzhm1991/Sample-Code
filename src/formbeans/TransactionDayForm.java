
package formbeans;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.mybeans.form.FormBean;

import util.Constants;
import util.Util;

import com.google.gson.Gson;

public class TransactionDayForm extends FormBean {
	private String[] ids;
	private String[] prices;
	private String date;

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		getIdErrors(errors);
		getPricesErrors(errors);
		getDateErrors(errors);

		if (errors.size() > 0) {
			return errors;
		}

		return errors;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String dateStr) {
		this.date = dateStr;
	}

	private void getDateErrors(List<String> errors) {
		if (getDateValue(date) == 0) {
			errors.add("illegal date format");
		}
	}

	private long getDateValue(String dateString) {
		dateString = dateString + " 00:00:00";
		Date date = null;
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date = sdf.parse(dateString);
			return date.getTime();
		} catch (Exception e) {
			Util.e(e);
		}
		return 0;
	}

	public long getDateValue() {
		return getDateValue(date);
	}

	public String[] getPrices() {
		return prices;
	}

	public void setPrices(String[] prices) {
		this.prices = prices;
	}

	public void getPricesErrors(List<String> errors) {
		if (prices == null) {
			errors.add("price is require");
			return;
		}
		if (ids != null && prices != null && ids.length != prices.length) {
			errors.add("Illegal request");
			return;
		}

		double[] priceValue = new double[prices.length];
		for (int i = 0; i < prices.length; i++) {
			Util.i("price[", i, "] = ", prices[i]);
			if (prices[i] == null) {
				errors.add("price field is required");
				return;
			}
			if (prices[i].indexOf(".") != -1
					&& (prices[i].length() - 1 - prices[i].indexOf(".")) > 2) {
				errors.add("Prices are tracked to two decimal places ");
				return;
			}
			try {
				priceValue[i] = Double.valueOf(prices[i]);
			} catch (Exception e) {
				errors.add("Numbers and decimal place only. No commas, letters or symbols.");
				return;
			}
			if (priceValue[i] < Constants.MIN_VALUE_OF_PRICE_INPUT
					|| priceValue[i] > Constants.MAX_VALUE_OF_PRICE_INPUT) {
				errors.add("The price should go between "
						+ Constants.MIN_VALUE_OF_PRICE_INPUT + " and "
						+ Constants.MAX_VALUE_OF_PRICE_INPUT_STRING);
				return;
			}
		}
	}

	public void getIdErrors(List<String> errors) {
		if (ids == null) {
			errors.add("fund id is missing");
			return;
		}
		for (int i = 0; i < ids.length; i++) {
			Util.i("ids[", i, "] = ", ids[i]);
			if (ids[i] == null) {
				errors.add("id field is required");
				return;
			}
			try {
				Integer.valueOf(ids[i]);
			} catch (Exception e) {
				errors.add("The ids should be numbers.");
				return;
			}
		}
	}

	public double[] getPriceValues() {
		double[] values = new double[prices.length];
		for (int i = 0; i < values.length; ++i) {
			values[i] = Double.valueOf(prices[i]);
		}
		return values;
	}

	public int[] getIdValues() {
		int[] values = new int[ids.length];
		for (int i = 0; i < values.length; ++i) {
			values[i] = Integer.valueOf(ids[i]);
		}
		return values;
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
