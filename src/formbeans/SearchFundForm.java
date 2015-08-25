
package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class SearchFundForm extends FormBean {
	private String keyword;

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (keyword == null || keyword.length() == 0) {
			errors.add("Keyword is required");
		}

		if (errors.size() > 0) {
			return errors;
		}

		return errors;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword.trim();
	}
}
