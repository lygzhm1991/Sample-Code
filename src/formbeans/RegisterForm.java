
package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class RegisterForm extends FormBean {
	private String email;
	private String firstName;
	private String lastName;
	private String password;
	private String confirm;
	private String action;

	public String getEmail() {
		return email;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPassword() {
		return password;
	}

	public String getConfirm() {
		return confirm;
	}

	public String getAction() {
		return action;
	}

	public void setEmail(String s) {
		email = trimAndConvert(s, "<>>\"]");
	}

	public void setFirstName(String s) {
		firstName = trimAndConvert(s, "<>>\"]");
	}

	public void setLastName(String s) {
		lastName = trimAndConvert(s, "<>>\"]");
	}

	public void setPassword(String s) {
		password = s.trim();
	}

	public void setConfirm(String s) {
		confirm = s.trim();
	}

	public void setAction(String s) {
		action = s;
	}

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (email == null || email.length() == 0)
			errors.add("Email is required");
		if (firstName == null || firstName.length() == 0)
			errors.add("First name is required");
		if (lastName == null || lastName.length() == 0)
			errors.add("Last name is required");
		if (password == null || password.length() == 0) {
			errors.add("Password is required");
		} else if (confirm == null || confirm.length() == 0) {
			errors.add("Please confirm your password");
		} else if (!password.equals(confirm)) {
			errors.add("Two input password is not the same");
		}
		if (action == null)
			errors.add("Action is required");

		if (errors.size() > 0)
			return errors;

		if (!action.equals("Register"))
			errors.add("Invalid button");

		if (email.matches(".*[<>\"].*"))
			errors.add("User email may not contain angle brackets or quotes");

		return errors;
	}
}
