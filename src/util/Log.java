package util;

public class Log {
	public static void e(String tag, String msg) {
		System.out.println(Util.getString(tag, "#", msg));
	}

	public static void d(String tag, String msg) {
		System.out.println(Util.getString(tag, "#", msg));
	}

	public static void i(String tag, String msg) {
		System.out.println(Util.getString(tag, "#", msg));
	}

}
