
package controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import util.Constants;

public abstract class Action {
	// Returns the name of the action, used to match the request in the hash
	// table
	public abstract String getName();

	// Returns the name of the jsp used to render the output.
	public abstract String perform(HttpServletRequest request);

	//
	// Class methods to manage dispatching to Actions
	//
	private static Map<String, Action> hash = new HashMap<String, Action>();

	public static void add(Action a) {
		synchronized (hash) {
			hash.put(a.getName(), a);
		}
	}

	public static String perform(String name, HttpServletRequest request) {
		Action a;
		synchronized (hash) {
			a = hash.get(name);
		}
		if (a == null)
			return null;
		return a.perform(request);
	}
	
	public void setMaxInputValues(HttpServletRequest request) {
		request.setAttribute("MAX_VALUE_OF_AMOUNT_INPUT_STRING",
				Constants.MAX_VALUE_OF_AMOUNT_INPUT_STRING);
		request.setAttribute("MAX_VALUE_OF_SHARE_INPUT_STRING",
				Constants.MAX_VALUE_OF_SHARE_INPUT_STRING);
		request.setAttribute("MAX_VALUE_OF_PRICE_INPUT_STRING",
				Constants.MAX_VALUE_OF_PRICE_INPUT_STRING);
	}
}
