package com.thkmon.was.string;

import com.thkmon.was.string.data.StringList;

public class StringUtil {

	public static String parseStirng(Object obj) {
		if (obj == null) {
			return "";
		}
		return obj.toString();
	}

	public static String parseStirng(Object obj, String defaultStr) {
		if (obj == null) {
			return defaultStr;
		}

		String resultStr = obj.toString();
		if (resultStr.length() == 0) {
			return defaultStr;
		}

		return resultStr;
	}

	public static String replaceOne(String fullStr, String oldStr, String newStr) {
		StringBuffer res = new StringBuffer();
		try {
			if (fullStr == null || fullStr.length() == 0) {
				return fullStr;
			}

			if (oldStr == null || oldStr.length() == 0) {
				return fullStr;
			}

			if (newStr == null || newStr.length() == 0) {
				return fullStr;
			}

			int posOldStr = fullStr.indexOf(oldStr);

			if (posOldStr >= 0) {
				res.append(fullStr.substring(0, posOldStr));
				res.append(newStr);
				res.append(fullStr.substring(posOldStr + oldStr.length()));
			} else {
				return fullStr;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return fullStr;
		}

		if (res == null || res.length() == 0) {
			return fullStr;
		}

		return res.toString();
	}
	
	public static StringList splitToList(String str, String regex) {
		if (str == null || str.length() == 0) {
			return null;
		}
		
		StringList result = null;
		
		String[] arr = str.split(regex);
		if (arr != null && arr.length > 0) {
			result = new StringList();
			
			int arrCount = arr.length;
			for (int i=0; i<arrCount; i++) {
				result.add(arr[i]);
			}
		}
		
		return result;
	}
}