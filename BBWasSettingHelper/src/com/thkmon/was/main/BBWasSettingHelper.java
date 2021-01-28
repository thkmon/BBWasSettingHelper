package com.thkmon.was.main;

import java.io.File;

import com.thkmon.was.file.FileCopyUtil;
import com.thkmon.was.file.JarExtractUtil;
import com.thkmon.was.setting.NginxSettingHelper;
import com.thkmon.was.setting.SettingHelper;
import com.thkmon.was.setting.TomcatSettingHelper;

public class BBWasSettingHelper {
	
	
	public static void main(String[] args) {
		try {
			BBWasSettingHelper instance = new BBWasSettingHelper();
			instance.main();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void main() throws Exception {
		// check test folder
		String parentFolderPath = "C:" + File.separator + "test";
		File parentFolderObj = new File(parentFolderPath);
		if (!parentFolderObj.exists()) {
			throw new Exception("The Folder does not exists. (" + parentFolderObj.getAbsolutePath() + ")");
		}
		
		if (!parentFolderObj.isDirectory()) {
			throw new Exception("Must be a folder, not a file. (" + parentFolderObj.getAbsolutePath() + ")");
		}
		
		
		// make target folder
		String targetFolderPath = parentFolderPath + File.separator + "test210128";
		File targetFolderObj = new File(targetFolderPath);
		if (targetFolderObj.exists()) {
			throw new Exception("The Folder already exists. (" + targetFolderObj.getAbsolutePath() + ")");
		}
		
		// copy tomcat folders
		{
			String tomcatZipFileName = "apache-tomcat-8.5.60-windows-x64.zip";
			String tomcatOriginFolderName = "apache-tomcat-8.5.60";
			int folderCount = 3;
			copyAndExtract(targetFolderPath, tomcatZipFileName, tomcatOriginFolderName, folderCount, "tomcat8_");
		}
		
		// copy nginx folder
		{
			String tomcatZipFileName = "nginx-1.18.0.zip";
			String tomcatOriginFolderName = "nginx-1.18.0";
			int folderCount = 1;
			copyAndExtract(targetFolderPath, tomcatZipFileName, tomcatOriginFolderName, folderCount, "nginx_");
		}
	}
	
	
	private void copyAndExtract(String targetFolderPath, String tomcatZipFileName, String tomcatOriginFolderName, int folderCount, String prefix) throws Exception {
		if (folderCount < 1) {
			return;
		}
		
		
		// copy
		String targetFilePath = targetFolderPath + File.separator + tomcatZipFileName;
		File setupZipFileObj = new File("setup" + File.separator + tomcatZipFileName);
		File targetFileObj = new File(targetFilePath);
		
		boolean isCopied = FileCopyUtil.copyFile(setupZipFileObj, targetFileObj);
		if (!isCopied) {
			throw new Exception("Fail to copy (" + setupZipFileObj.getAbsolutePath() + " => " + targetFileObj.getAbsolutePath());
		}
		
		int[] portArray = {1010, 2020, 3030, 4040, 5050, 6060, 7070, 8080, 9090};
		int[] admuinPortArray = {1015, 2025, 3035, 4045, 5055, 6065, 7075, 8085, 9095};
		int[] ajpPortArray = {1019, 2029, 3039, 4049, 5059, 6069, 7079, 8089, 9099};
		
		for (int i=0; i<folderCount; i++) {
			int port = 0;
			if (folderCount > 1) {
				port = portArray[i];
			} else {
				port = 80;
			}
			
			
			// extract
			JarExtractUtil.extractJar(targetFilePath, targetFolderPath);
			Thread.sleep(100);
			
			
			File originFolderObj = new File(targetFolderPath + File.separator + tomcatOriginFolderName);
			File destFolderObj = new File(targetFolderPath + File.separator + prefix + port);
			boolean isRenamed = originFolderObj.renameTo(destFolderObj);
			if (!isRenamed) {
				throw new Exception("Fail to rename (" + originFolderObj.getAbsolutePath() + " => " + destFolderObj.getAbsolutePath());
			}
			Thread.sleep(100);
			
			
			SettingHelper settingHelper = null;
			
			if (prefix.toLowerCase().indexOf("tomcat") > -1) {
				settingHelper = new TomcatSettingHelper();
			} else if (prefix.toLowerCase().indexOf("nginx") > -1) {
				settingHelper = new NginxSettingHelper();
			}
			
			if (settingHelper != null) {
//				dd.setConfigFiles();
			}
		}
	}
}