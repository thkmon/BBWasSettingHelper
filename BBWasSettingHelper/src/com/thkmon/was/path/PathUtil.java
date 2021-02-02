package com.thkmon.was.path;

import java.io.File;

public class PathUtil {

	public static String revisePath(String path) {
		if (path == null || path.trim().length() == 0) {
			return "";
		} else {
			path = path.trim();
		}
		
		String oneSlash = File.separator;
		String doubleSlash = oneSlash + oneSlash;
		
		if (path.indexOf("/") > -1) {
			path = path.replace("/", oneSlash);
		}
		
		if (path.indexOf("\\") > -1) {
			path = path.replace("\\", oneSlash);
		}
		
		if (doubleSlash.length() == 2 && oneSlash.length() == 1) {
			while (path.indexOf(doubleSlash) > -1) {
				path = path.replace(doubleSlash, oneSlash);
			}
		}
		
		if (path.endsWith(oneSlash)) {
			path = path.substring(0, path.length() - 1);
		}
		
		return path;
	}
}
