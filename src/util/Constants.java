package util;

public class Constants {
	public static final String CUSTOMER_RESULT_JSP = "customer-result.jsp";

	// buy fund
	public static final String YOUR_TRANSACTION_IS_COMMITED = "Thank You! Your request is accepted.";
	public static final String THERE_IS_NOT_ENOUGH_MONEY = "There is not enough money in your account.";
	public static final String INVALID_PARAMETER_FUND_ID = "Invalid parameter: fundId";
	public static final String PARAMETER_IS_REQUIRED_FUND_ID = "Parameter is required: fundId";
	public static final String PASSWORD_CHANGED_SUCCESSFULLY = "Password changed successfully!";
	public static final String NOT_ENOUGH_SHARES = "There is not enough shares in your account.";

	// customer view account
	public static final String EMPLOYEE_RESULT_JSP = "employee-result.jsp";
	public static final String EMPLOYEE_SUCCESS_JSP = "employee_success.jsp";
	public static final String CUSTOMER_SUCCESS_JSP = "customer_success.jsp";

	public static final String PARAMETER_IS_REQUIRED_USER_NAME = "Parameter  is required: userName";
	public static final String INVALID_PARAMETER_USER_NAME = "Invalid parameter: userName";
	public static final String PASSWORD_CHANGED_FOR = "Password changed successfully for ";
	public static final String CREATE_EMPLOYEE_ACCOUNT_SUCCESSFULLY = "Create employee account successfully!";
	public static final String CREATE_FUND_SUCCESSFULLY = "Create Fund Successfully!";
	public static final String INVALID_PARAMETER_DATE = "Invalid parameter: date";

	public static final String AMOUNT_FORMAT = "###,###,###,###,###,###.00";
	public static final String PRICE_FORMAT = "###,###,###,###,###,###.00";
	public static final String SHARE_FORMAT = "###,###,###,###,###,###.000";

	public static final long ONE_DAY_IN_MILLISECOND = 24 * 60 * 60 * 1000;

	public static final int MAX_LENGTH_OF_AMOUNT_INPUT = 12;
	public static final double MAX_VALUE_OF_AMOUNT_INPUT = 100 * 1000 * 1000;
	public static final String MAX_VALUE_OF_AMOUNT_INPUT_STRING = Util
			.formatNumber(MAX_VALUE_OF_AMOUNT_INPUT, AMOUNT_FORMAT);
	public static final double MIN_VALUE_OF_AMOUNT_INPUT = 0.01;

	public static final int MAX_LENGTH_OF_PRICE_INPUT = 12;
	public static final double MAX_VALUE_OF_PRICE_INPUT = 10 * 1000;
	public static final String MAX_VALUE_OF_PRICE_INPUT_STRING = Util
			.formatNumber(MAX_VALUE_OF_PRICE_INPUT, PRICE_FORMAT);
	public static final double MIN_VALUE_OF_PRICE_INPUT = 0.01;

	public static final int MAX_LENGTH_OF_SHARE_INPUT = 12;
	public static final double MAX_VALUE_OF_SHARE_INPUT = 100 * 1000 * 1000;
	public static final String MAX_VALUE_OF_SHARE_INPUT_STRING = Util
			.formatNumber(MAX_VALUE_OF_SHARE_INPUT, SHARE_FORMAT);
	public static final double MIN_VALUE_OF_SHARE_INPUT = 0.001;

}
