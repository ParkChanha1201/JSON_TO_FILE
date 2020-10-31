package com.chcraft.util;

public class StringExtension {
	public static String toFirstCharUpperCase(String str) {
		return changeFirstCharCase(str, true);
	}

	public static String toFirstCharLowerCase(String str) {
		return changeFirstCharCase(str, false);
	}

	private static String changeFirstCharCase(String str, boolean toUpper) {
		if (isNullOrEmpty(str)) {
			return "";
		}

		char ch = str.charAt(0);

		if (str.length() < 2) {
			return toUpper ? Character.toUpperCase(ch) + "" : Character.toLowerCase(ch) + "";
		}

		return toUpper ? Character.toUpperCase(ch) + str.substring(1) : Character.toLowerCase(ch) + str.substring(1);
	}

	public static boolean isNullOrEmpty(String str) {
		if (str == null) {
			return true;
		}

		if (str.isEmpty()) {
			return true;
		}

		return false;
	}
}
