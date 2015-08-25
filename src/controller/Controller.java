
package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Model;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import util.Util;
import controller.customer.CustomerBuyFundAction;
import controller.customer.CustomerChangePwdAction;
import controller.customer.CustomerFundDetailAction;
import controller.customer.CustomerRequestCheckAction;
import controller.customer.CustomerSearchFundAction;
import controller.customer.CustomerSellFundAction;
import controller.customer.CustomerViewAccountAction;
import controller.customer.CustomerViewTransactionHistoryAction;
import controller.employee.ChangeCustomerPwdAction;
import controller.employee.CreateCustomerAction;
import controller.employee.CreateEmployeeAction;
import controller.employee.CreateFundAction;
import controller.employee.DepositCheckAction;
import controller.employee.EmployeeChangePwdAction;
import controller.employee.SearchCustomerAction;
import controller.employee.TransactionDayAction;
import controller.employee.ViewCustomerAction;
import controller.employee.ViewTransactionHistoryAction;
import databeans.Customer;
import databeans.Employee;
import databeans.Fund;

@SuppressWarnings("serial")
public class Controller extends HttpServlet {
	private Model model;

	public void init() throws ServletException {
		model = new Model(getServletConfig());

		try {
			if (model.getEmployeeDAO().getCount() == 0) {
				createDefaultAccount();
			}
		} catch (RollbackException e) {
			e.printStackTrace();
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}

		// customer
		Action.add(new CustomerViewAccountAction(model));
		Action.add(new CustomerChangePwdAction(model));
		Action.add(new CustomerSearchFundAction(model));
		Action.add(new CustomerBuyFundAction(model));
		Action.add(new CustomerSellFundAction(model));
		Action.add(new CustomerRequestCheckAction(model));
		Action.add(new CustomerViewTransactionHistoryAction(model));
		Action.add(new CustomerFundDetailAction(model));

		// employee
		Action.add(new SearchCustomerAction(model));
		Action.add(new CreateEmployeeAction(model));
		Action.add(new EmployeeChangePwdAction(model));
		Action.add(new CreateFundAction(model));
		Action.add(new CreateCustomerAction(model));
		Action.add(new TransactionDayAction(model));
		Action.add(new ViewCustomerAction(model));
		Action.add(new ChangeCustomerPwdAction(model));
		Action.add(new ViewTransactionHistoryAction(model));
		Action.add(new DepositCheckAction(model));

		// common action
		Action.add(new LoginAction(model));
		Action.add(new LogoutAction(model));
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String servletPath = request.getServletPath();
		String action = getActionName(servletPath);
		Util.i("action = ", action);
		String nextPage = null;
		if (action.startsWith("employee")) {
			nextPage = performEmployeeAction(request);
		} else if (action.startsWith("customer")) {
			nextPage = performCustomerAction(request);
		} else {
			nextPage = performCommonAction(request);
		}
		sendToNextPage(nextPage, request, response);
	}

	private String performCommonAction(HttpServletRequest request) {
		String servletPath = request.getServletPath();
		String action = getActionName(servletPath);
		return Action.perform(action, request);
	}

	private String performEmployeeAction(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		String servletPath = request.getServletPath();
		String action = getActionName(servletPath);
		Employee employee = (Employee) session.getAttribute("employee");
		Util.i("employee = ", employee);

		if (employee == null) {
			return LoginAction.NAME;
		}

		return Action.perform(action, request);
	}

	private String performCustomerAction(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		String servletPath = request.getServletPath();
		String action = getActionName(servletPath);
		Customer user = (Customer) session.getAttribute("customer");

		if (user == null) {
			return LoginAction.NAME;
		}

		return Action.perform(action, request);
	}

	private void sendToNextPage(String nextPage, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		if (nextPage == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND,
					request.getServletPath());
			return;
		}

		Util.i("nextPage = ", nextPage);
		if (nextPage.endsWith(".do")) {
			response.sendRedirect(nextPage);
			return;
		}

		if (nextPage.endsWith(".jsp")) {
			RequestDispatcher d = request.getRequestDispatcher("WEB-INF/"
					+ nextPage);
			d.forward(request, response);
			return;
		}

		if (nextPage.equals("redirect")) {
			response.sendRedirect((String) request.getAttribute("url"));
			return;
		}

		throw new ServletException(Controller.class.getName()
				+ ".sendToNextPage(\"" + nextPage + "\"): invalid extension.");
	}

	/*
	 * Returns the path component after the last slash removing any "extension"
	 * if present.
	 */
	private String getActionName(String path) {
		// We're guaranteed that the path will start with a slash
		int slash = path.lastIndexOf('/');
		return path.substring(slash + 1);
	}

	private void createDefaultAccount() throws RollbackException {
		Employee admin = new Employee();
		admin.setUserName("admin");
		admin.setFirstName("Hello");
		admin.setLastName("World");
		admin.setPassword("admin");
		model.getEmployeeDAO().create(admin);

		Employee employee = new Employee();
		employee.setUserName("Jason");
		employee.setFirstName("Hello");
		employee.setLastName("World");
		employee.setPassword("1");
		model.getEmployeeDAO().create(employee);

		Customer customer = new Customer();
		customer.setUserName("Jeff");
		customer.setPassword("1");
		customer.setFirstName("Jeff");
		customer.setLastName("Eppinger");
		customer.setAddrLine1("5000 Forbes Avenue");
		customer.setCity("Pittsburgh");
		customer.setState("PA");
		customer.setZip("15213");
		model.getCustomerDAO().create(customer);

		Fund google = new Fund();
		google.setName("GOOGLE");
		google.setTicker("GOOG");
		model.getFundDAO().create(google);

		Fund apple = new Fund();
		apple.setName("APPLE");
		apple.setTicker("APP");
		model.getFundDAO().create(apple);
	}
}
