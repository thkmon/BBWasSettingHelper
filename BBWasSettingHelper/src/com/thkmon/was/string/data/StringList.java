package com.thkmon.was.string.data;

import java.util.ArrayList;

import com.thkmon.was.string.StringUtil;

public class StringList extends ArrayList<String> {

	public void replace(String str1, String str2) {
		if (str1 == null || str1.length() == 0) {
			return;
		}
		
		if (str2 == null) {
			str2 = "";
		}
		
		String oldStr = null;
		String replaced = null;

		int count = size();
		for (int i = 0; i < count; i++) {
			oldStr = this.get(i);
			if (oldStr == null) {
				continue;
			}

			replaced = oldStr.replace(str1, str2);
			this.set(i, replaced);
		}
	}

	public void replaceOne(String str1, String str2) {
		if (str1 == null || str1.length() == 0) {
			return;
		}
		
		if (str2 == null) {
			str2 = "";
		}
		
		String oldStr = null;
		String replaced = null;

		int count = size();
		for (int i = 0; i < count; i++) {
			oldStr = this.get(i);
			if (oldStr == null) {
				continue;
			}

			replaced = StringUtil.replaceOne(oldStr, str1, str2);
			this.set(i, replaced);
			if (replaced != null && !replaced.equals(oldStr)) {
				return;
			}
		}
	}
}