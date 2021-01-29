package com.thkmon.was.setting.data;

public class Replacement {
	private String oldStr = null;
	private String newStr = null;
	
	public Replacement(String str1, String str2) {
		this.oldStr = str1;
		this.newStr = str2;
	}

	public String getOldStr() {
		return oldStr;
	}

	public String getNewStr() {
		return newStr;
	}
}