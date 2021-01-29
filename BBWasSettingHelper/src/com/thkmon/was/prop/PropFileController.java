package com.thkmon.was.prop;

import java.io.File;

import com.thkmon.was.file.FileIOUtil;
import com.thkmon.was.string.data.StringList;
import com.thkmon.was.string.data.StringMap;

public class PropFileController {
	
	public StringMap readPropFile(String propFilePath) {
		
		StringMap resultMap = new StringMap();
		
		try {
			File propFileObj = new File(propFilePath);
			StringList initContentList = FileIOUtil.readFile(propFileObj);
			if (initContentList == null) {
				return null;
			}
			
			String lineText = null;
			int lineCount = initContentList.size();
			for (int i=0; i<lineCount; i++) {
				lineText = initContentList.get(i);
				
				if (lineText == null || lineText.length() == 0) {
					continue;
				}
				
				if (lineText.startsWith("#")) {
					continue;
				}
				
				// 샵(#)이 발견될 경우 샵 포함 후반부를 제거한다.
				int sharpMarkIdx = lineText.indexOf("#");
				if (sharpMarkIdx > -1) {
					lineText = lineText.substring(0, sharpMarkIdx);
				}
				
				int equalMarkIdx = lineText.indexOf("=");
				if (equalMarkIdx < 0) {
					continue;
				}
				
				String keyText = lineText.substring(0, equalMarkIdx);
				if (keyText.trim().length() == 0) {
					continue;
				} else {
					keyText = keyText.trim();
				}
				
				String valueText = lineText.substring(equalMarkIdx + 1);
				valueText = valueText.trim();
				
				resultMap.put(keyText, valueText);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return resultMap;
	}
}