
package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

import com.google.gson.Gson;

public class LoginForm extends FormBean {
	private String userName;
	private String password;
	private String action;

	public String getPassword() {
		return password;
	}

	public String getAction() {
		return action;
	}

	public void setPassword(String s) {
		password = s.trim();
	}

	public void setAction(String s) {
		action = s;
	}

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (userName == null || userName.length() == 0)
			errors.add("User name is required");
		if (password == null || password.length() == 0)
			errors.add("Password is required");
		if (action == null)
			errors.add("Action is required");

		if (errors.size() > 0)
			return errors;

		if (!isEmployee() && !isCustomer())
			errors.add("Invalid login action");
		if (userName.matches(".*[<>\"].*"))
			errors.add("User name may not contain angle brackets or quotes");

		return errors;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName.trim();
	}

	public boolean isEmployee() {
		return "employee_login".equals(action);
	}

	public boolean isCustomer() {
		return "customer_login".equals(action);
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
