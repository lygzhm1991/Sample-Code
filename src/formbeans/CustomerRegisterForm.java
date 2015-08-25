
package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class CustomerRegisterForm extends FormBean {
	private String userName;
	private String firstName;
	private String lastName;
	private String password;
	private String confirm;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String zip;
	private String action;

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

		if (getUserName() == null || getUserName().length() == 0)
			errors.add("User name is required");
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
		if (addressLine1 == null || addressLine1.length() == 0) {
			errors.add("AddressLine1 is required");
		}
		if (city == null || city.length() == 0) {
			errors.add("City is required");
		}
		if (state == null || state.length() == 0) {
			errors.add("State is required");
		}
		if (zip == null || zip.length() == 0) {
			errors.add("Zip code is required");
		}
		if (action == null)
			errors.add("Action is required");

		if (errors.size() > 0)
			return errors;

		if (!action.equals("create"))
			errors.add("Invalid button");

		if (getUserName().matches(".*[<>\"].*"))
			errors.add("User name may not contain angle brackets or quotes");
		
		if (firstName.matches(".*[<>\"].*"))
			errors.add("First name may not contain angle brackets or quotes");
		
		if (lastName.matches(".*[<>\"].*"))
			errors.add("Last name may not contain angle brackets or quotes");
		
		if (addressLine1.matches(".*[<>\"].*"))
			errors.add("AddressLine1 may not contain angle brackets or quotes");
		
		if (addressLine2 != null && addressLine2.matches(".*[<>\"].*"))
			errors.add("AddressLine2 may not contain angle brackets or quotes");
		
		if (city.matches(".*[<>\"].*"))
			errors.add("City may not contain angle brackets or quotes");
		
		if (state.matches(".*[<>\"].*"))
			errors.add("State may not contain angle brackets or quotes");
		
		if (zip.matches(".*[<>\"].*"))
			errors.add("Zip may not contain angle brackets or quotes");

		return errors;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = trimAndConvert(userName, "<>>\"]");
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
}
