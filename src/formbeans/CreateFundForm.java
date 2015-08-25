
package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class CreateFundForm extends FormBean {
	private String name;
	private String ticker;

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (getName() == null || getName().length() == 0) {
			errors.add("fund name is required");
		}

		if (getName() != null && getName().length() > 30) {
			errors.add("fund name should be less than 30 characters");
		}
		if (getTicker() == null || getTicker().length() == 0) {
			errors.add("Ticker is required");
		}
		if (getTicker() != null && getTicker().length() > 30) {
			errors.add("fund ticker should be less than 30 characters");
		}

		if (errors.size() > 0) {
			return errors;
		}

		if (getName().matches(".*[<>\"].*"))
			errors.add("Fund Name may not contain angle brackets or quotes");

		if (getTicker().matches(".*[<>\"].*"))
			errors.add("Ticker may not contain angle brackets or quotes");

		return errors;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
}
