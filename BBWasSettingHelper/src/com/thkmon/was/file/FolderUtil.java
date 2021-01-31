package com.thkmon.was.file;

import java.io.File;

import com.thkmon.was.error.MsgException;

public class FolderUtil {

	public static boolean checkValidFolder(String path) throws MsgException, Exception {
		if (path == null || path.trim().length() == 0) {
			throw new MsgException("The path is null or empty.");
		} else {
			path = path.trim();
		}
		
		File dirObj = new File(path);
		if (!dirObj.exists()) {
			throw new MsgException("The Folder does not exists. (" + dirObj.getAbsolutePath() + ")");
		}
		
		if (!dirObj.isDirectory()) {
			throw new MsgException("Must be a folder, not a file. (" + dirObj.getAbsolutePath() + ")");
		}
		
		return true;
	}
}