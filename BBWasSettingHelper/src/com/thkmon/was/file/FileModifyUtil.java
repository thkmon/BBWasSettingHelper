package com.thkmon.was.file;

import java.io.File;

import com.thkmon.was.file.data.FileContent;
import com.thkmon.was.setting.data.Replacement;
import com.thkmon.was.setting.data.ReplacementList;

public class FileModifyUtil {

	public static boolean modifyFileContent(String targetFilePath, ReplacementList replacementList) throws Exception {
		boolean isSuccess = false;
		
		// make backup file
		File file = new File(targetFilePath);
		String fullPath = file.getAbsolutePath();
		String backupFilePath = fullPath + "_backup";

		File backupFileObj = new File(backupFilePath);
		if (!backupFileObj.exists()) {
			FileCopyUtil.copyFile(file, backupFileObj);
		}

		
		// read file
		FileContent fileContent = FileIOUtil.readFile(file);
		if (fileContent == null || fileContent.size() == 0) {
			return false;
		}
		
		
		// replace content
		if (replacementList == null || replacementList.size() == 0) {
			return false;
		}

		String oldStr = null;
		String newStr = null;

		Replacement replacement = null;
		int replacementCount = replacementList.size();
		for (int i = 0; i < replacementCount; i++) {
			replacement = replacementList.get(i);
			if (replacement == null) {
				continue;
			}

			oldStr = replacement.getOldStr();
			if (oldStr == null || oldStr.length() == 0) {
				continue;
			}

			newStr = replacement.getNewStr();
			if (newStr == null) {
				newStr = "";
			}

			fileContent.replaceOne(oldStr, newStr);
		}

		
		// write file
		isSuccess = FileIOUtil.writeFile(targetFilePath, fileContent, false);
		return isSuccess;
	}
}