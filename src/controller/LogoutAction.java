
package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.Model;

public class LogoutAction extends Action {

	public static final String NAME = "logout.do";

	public LogoutAction(Model model) {
	}

	public String getName() {
		return NAME;
	}

	public String perform(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.setAttribute("employee", null);
			session.setAttribute("customer", null);
		}

		return LoginAction.NAME;
	}
}
