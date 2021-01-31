package com.thkmon.was.setting;

import com.thkmon.was.string.data.StringList;

public class SettingData {
	private String projectPath = "";
	
	private int httpPort = 8080;
	private int shutdownPort = 8085;
	private int ajpPort = 8089;
	
	private String javaHomePath = "";
	private String jreHomePath = "";
	private StringList vmArgumentList = new StringList();
	private StringList classpathList = new StringList();
	private StringList webModuleList = new StringList();
	private String destFolderPath = "";
	
	public String getProjectPath() {
		return projectPath;
	}

	public SettingData setProjectPath(String projectPath) {
		this.projectPath = projectPath;
		return this;
	}
	
	public int getHttpPort() {
		return httpPort;
	}

	public void setHttpPort(int httpPort) {
		this.httpPort = httpPort;
	}

	public int getShutdownPort() {
		return shutdownPort;
	}

	public void setShutdownPort(int shutdownPort) {
		this.shutdownPort = shutdownPort;
	}

	public int getAjpPort() {
		return ajpPort;
	}

	public void setAjpPort(int ajpPort) {
		this.ajpPort = ajpPort;
	}

	public String getJavaHomePath() {
		return javaHomePath;
	}

	public void setJavaHomePath(String javaHomePath) {
		this.javaHomePath = javaHomePath;
	}

	public String getJreHomePath() {
		return jreHomePath;
	}

	public void setJreHomePath(String jreHomePath) {
		this.jreHomePath = jreHomePath;
	}

	public StringList getVmArgumentList() {
		return vmArgumentList;
	}

	public SettingData setVmArgumentList(StringList vmArgumentList) {
		this.vmArgumentList = vmArgumentList;
		return this;
	}

	public StringList getClasspathList() {
		return classpathList;
	}

	public void setClasspathList(StringList classpathList) {
		this.classpathList = classpathList;
	}

	public StringList getWebModuleList() {
		return webModuleList;
	}

	public void setWebModuleList(StringList webModuleList) {
		this.webModuleList = webModuleList;
	}

	public String getDestFolderPath() {
		return destFolderPath;
	}

	public void setDestFolderPath(String destFolderPath) {
		this.destFolderPath = destFolderPath;
	}
}