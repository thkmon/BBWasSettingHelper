package com.thkmon.was.setting;

import com.thkmon.was.string.data.StringList;

public class SettingData {
	private String projectPath = "";
	private String javaHome = "";
	private String jreHome = "";
	private StringList vmArgumentList = new StringList();

	public String getProjectPath() {
		return projectPath;
	}

	public SettingData setProjectPath(String projectPath) {
		this.projectPath = projectPath;
		return this;
	}

	public String getJavaHome() {
		return javaHome;
	}

	public SettingData setJavaHome(String javaHome) {
		this.javaHome = javaHome;
		return this;
	}

	public String getJreHome() {
		return jreHome;
	}

	public SettingData setJreHome(String jreHome) {
		this.jreHome = jreHome;
		return this;
	}

	public StringList getVmArgumentList() {
		return vmArgumentList;
	}

	public SettingData setVmArgumentList(StringList vmArgumentList) {
		this.vmArgumentList = vmArgumentList;
		return this;
	}
}