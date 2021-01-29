package com.thkmon.was.main;

import java.io.File;

import com.thkmon.was.common.CConst;
import com.thkmon.was.datetime.DatetimeUtil;
import com.thkmon.was.file.FileCopyUtil;
import com.thkmon.was.file.JarExtractUtil;
import com.thkmon.was.setting.NginxSettingHelper;
import com.thkmon.was.setting.SettingData;
import com.thkmon.was.setting.SettingHelper;
import com.thkmon.was.setting.TomcatSettingHelper;
import com.thkmon.was.string.StringUtil;

public class BBWasSettingHelper {
	
	
	public static void main(String[] args) {
		try {
			System.out.println("BEGIN");
			
			SettingData settingData = new SettingData();
			settingData.setJavaHome(CConst.JAVA_HOME);
			settingData.setJreHome(CConst.JRE_HOME);
			settingData.setProjectPath(CConst.PROJECT_PATH);
			settingData.setVmArgumentList(StringUtil.splitToList(CConst.VM_ARGUMENTS, " "));
			
			BBWasSettingHelper instance = new BBWasSettingHelper();
			instance.setServerForSingleWAS(settingData);
			
			System.out.println("END");
			
//			SettingForm form = new SettingForm();
//			form;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void setServerForSingleWAS(SettingData settingData) throws Exception {
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
		String targetFolderPath = parentFolderPath + File.separator + "test_" + DatetimeUtil.getTodayDateTime();
		File targetFolderObj = new File(targetFolderPath);
		if (targetFolderObj.exists()) {
			throw new Exception("The Folder already exists. (" + targetFolderObj.getAbsolutePath() + ")");
		} else {
			targetFolderObj.mkdirs();
		}
		
		if (!targetFolderObj.isDirectory()) {
			throw new Exception("Must be a folder, not a file. (" + targetFolderObj.getAbsolutePath() + ")");
		}
		
		
		// copy tomcat folders
		{
			String tomcatZipFileName = "apache-tomcat-7.0.107-windows-x64.zip";
			String tomcatOriginFolderName = "apache-tomcat-7.0.107";
			int folderCount = 1;
			copyAndExtract(targetFolderPath, tomcatZipFileName, tomcatOriginFolderName, settingData, folderCount, "tomcat7");
		}
		
		System.out.println("targetFolderPath : " + targetFolderObj.getAbsolutePath());
	}
	
	
	public void setServerForSessionClustering(SettingData settingData) throws Exception {
		if (settingData == null) {
			throw new Exception("settingData is null");
		}
		
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
		
		if (!targetFolderObj.isDirectory()) {
			throw new Exception("Must be a folder, not a file. (" + targetFolderObj.getAbsolutePath() + ")");
		}
		
		
		// copy tomcat folders
		{
			String tomcatZipFileName = "apache-tomcat-8.5.60-windows-x64.zip";
			String tomcatOriginFolderName = "apache-tomcat-8.5.60";
			int folderCount = 3;
			copyAndExtract(targetFolderPath, tomcatZipFileName, tomcatOriginFolderName, settingData, folderCount, "tomcat8");
		}
		
		// copy nginx folder
		{
			String tomcatZipFileName = "nginx-1.18.0.zip";
			String tomcatOriginFolderName = "nginx-1.18.0";
			int folderCount = 1;
			copyAndExtract(targetFolderPath, tomcatZipFileName, tomcatOriginFolderName, settingData, folderCount, "nginx");
		}
	}
	
	
	private void copyAndExtract(String targetFolderPath, String zipFileName, String tomcatOriginFolderName, SettingData settingData, int folderCount, String prefix) throws Exception {
		if (settingData == null) {
			throw new Exception("settingData is null");
		}
		
		if (folderCount < 1) {
			return;
		}
		
		String tomcatPath = targetFolderPath + File.separator + prefix;
		
		// copy
		String targetFilePath = targetFolderPath + File.separator + zipFileName;
		File setupZipFileObj = new File("setup" + File.separator + zipFileName);
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
			
			if (targetFileObj.exists()) {
				boolean d = targetFileObj.delete();
				System.out.println(d);
			}
			
			
			File originFolderObj = new File(targetFolderPath + File.separator + tomcatOriginFolderName);
			String destFolderPath = targetFolderPath + File.separator + prefix;
			if (folderCount > 1) {
				destFolderPath += "_" + port;
			}
			
			File destFolderObj = new File(destFolderPath);
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
				settingHelper.doSetting(tomcatPath, settingData);
			}
		}
	}
}