package com.thkmon.was.setting;

import com.thkmon.was.common.CConst;
import com.thkmon.was.error.MsgException;
import com.thkmon.was.file.FileModifyUtil;
import com.thkmon.was.file.FolderUtil;
import com.thkmon.was.setting.data.Replacement;
import com.thkmon.was.setting.data.ReplacementList;

public class TomcatSettingHelper extends SettingHelper {
	
	@Override
	public void doSetting(String tomcatPath, SettingData settingData) throws Exception {
		String projectPath = settingData.getProjectPath();
		if (projectPath == null || projectPath.trim().length() == 0) {
			throw new MsgException("projectPath is null or empty.");
		} else {
			projectPath = projectPath.trim();
			FolderUtil.checkValidFolder(projectPath);
		}
		
		String javaHomePath = settingData.getJavaHomePath();
		if (javaHomePath == null || javaHomePath.trim().length() == 0) {
			throw new MsgException("javaHomePath is null or empty.");
		} else {
			javaHomePath = javaHomePath.trim();
			FolderUtil.checkValidFolder(javaHomePath);
		}
		
		String jreHomePath = settingData.getJreHomePath();
		if (jreHomePath == null || jreHomePath.trim().length() == 0) {
			throw new MsgException("jreHomePath is null or empty.");
		} else {
			jreHomePath = jreHomePath.trim();
			FolderUtil.checkValidFolder(jreHomePath);
		}
		
		{
			String targetFilePath = tomcatPath + "\\bin\\setclasspath.bat";
			ReplacementList replacementList = new ReplacementList();
			
			{
				String oldStr = "rem Make sure prerequisite environment variables are set";
				
				StringBuffer newStrBuff = new StringBuffer();
				newStrBuff.append("rem Make sure prerequisite environment variables are set");
				newStrBuff.append(CConst.CARRIAGE_RETURN);
				newStrBuff.append("set JAVA_HOME=").append(javaHomePath);
				newStrBuff.append(CConst.CARRIAGE_RETURN);
				newStrBuff.append("set JRE_HOME=").append(jreHomePath);
				newStrBuff.append(CConst.CARRIAGE_RETURN);
				newStrBuff.append("set CATALINA_HOME=").append(tomcatPath);
				newStrBuff.append(CConst.CARRIAGE_RETURN);
				String newStr = newStrBuff.toString();
								
				replacementList.add(new Replacement(oldStr, newStr));
			}
			
			FileModifyUtil.modifyFileContent(targetFilePath, replacementList);
		}
		
		{
			String targetFilePath = tomcatPath + "\\bin\\catalina.bat";
			ReplacementList replacementList = new ReplacementList();
			
			{
				// vm arguments
				if (settingData != null && settingData.getVmArgumentList() != null && settingData.getVmArgumentList().size() > 0) {
					String oldStr = "set CLASSPATH=";
					
					StringBuffer newStrBuff = new StringBuffer();
					newStrBuff.append("set CLASSPATH=");
					newStrBuff.append(CConst.CARRIAGE_RETURN);
					
					newStrBuff.append("set CATALINA_OPTS=");
					
					boolean isFirstArg = true;
					String oneArg = "";
					int listCount = settingData.getVmArgumentList().size();
					for (int i=0; i<listCount; i++) {
						oneArg = settingData.getVmArgumentList().get(i);
						if (oneArg == null || oneArg.trim().length() == 0) {
							continue;
						} else {
							oneArg = oneArg.trim();
						}
						
						if (isFirstArg) {
							newStrBuff.append(oneArg);
							isFirstArg = false;
						} else {
							newStrBuff.append(" ");
							newStrBuff.append(oneArg);
						}
					}
					
					String newStr = newStrBuff.toString();
					
					replacementList.add(new Replacement(oldStr, newStr));
				}
			}
			
			{
				// classpath
				if (settingData != null && settingData.getClasspathList() != null && settingData.getClasspathList().size() > 0) {
					String oldStr = "rem ----- Execute The Requested Command ---------------------------------------";
					StringBuffer newStrBuff = new StringBuffer();
					newStrBuff.append("set \"CLASSPATH=%CLASSPATH%;");
					
					boolean isFirstArg = true;
					String oneArg = "";
					int listCount = settingData.getClasspathList().size();
					for (int i=0; i<listCount; i++) {
						oneArg = settingData.getClasspathList().get(i);
						if (oneArg == null || oneArg.trim().length() == 0) {
							continue;
						} else {
							oneArg = oneArg.trim();
						}
						
						if (isFirstArg) {
							newStrBuff.append(oneArg);
							isFirstArg = false;
						} else {
							newStrBuff.append(";");
							newStrBuff.append(oneArg);
						}
					}
					
					newStrBuff.append(CConst.CARRIAGE_RETURN);
					newStrBuff.append("rem ----- Execute The Requested Command ---------------------------------------");
					
					String newStr = newStrBuff.toString();
					
					replacementList.add(new Replacement(oldStr, newStr));
				}
			}
			
			FileModifyUtil.modifyFileContent(targetFilePath, replacementList);
		}
		

		{
			String targetFilePath = tomcatPath + "\\conf\\server.xml";
			ReplacementList replacementList = new ReplacementList();
			
			int httpPort = settingData.getHttpPort();
			int shutdownPort = settingData.getShutdownPort();
			int ajpPort = settingData.getAjpPort();
			
			{
				String oldStr = "<Server port=\"8005\" shutdown=\"SHUTDOWN\">";
				String newStr = "<Server port=\"" + shutdownPort + "\" shutdown=\"SHUTDOWN\">";
				replacementList.add(new Replacement(oldStr, newStr));
			}
			
			{
				String oldStr = "<Connector port=\"8080\" protocol=\"HTTP/1.1\"";
				String newStr = "<Connector URIEncoding=\"UTF-8\" port=\"" + httpPort + "\" protocol=\"HTTP/1.1\"";
				replacementList.add(new Replacement(oldStr, newStr));
			}
			
			{
				String oldStr = "<Connector protocol=\"AJP/1.3\"";
				String newStr = "<Connector URIEncoding=\"UTF-8\" protocol=\"AJP/1.3\"";
				replacementList.add(new Replacement(oldStr, newStr));
			}
			
			{
				String oldStr = "port=\"8009\"";
				String newStr = "port=\"" + ajpPort + "\"";
				replacementList.add(new Replacement(oldStr, newStr));
			}
			
			{
				if (settingData != null && settingData.getWebModuleList() != null && settingData.getWebModuleList().size() > 0) {
					String oldStr = "      </Host>";
					
					StringBuffer newStrBuff = new StringBuffer();
					
					String oneArg = "";
					int listCount = settingData.getWebModuleList().size();
					for (int i=0; i<listCount; i++) {
						oneArg = settingData.getWebModuleList().get(i);
						if (oneArg == null || oneArg.trim().length() == 0) {
							continue;
						} else {
							oneArg = oneArg.trim();
						}
						
						int dotIndex = oneArg.indexOf(",");
						if (dotIndex < 0) {
							continue;
						}
						
						String onePath = oneArg.substring(0, dotIndex).trim();
						String oneDocBase = oneArg.substring(dotIndex + 1).trim();
						
						if (onePath.length() < 1 || oneDocBase.length() < 1) {
							continue;
						}
						
						newStrBuff.append("      <Context docBase=\"" + oneDocBase + "\" path=\"" + onePath + "\" reloadable=\"false\"/>");
					}
					
					newStrBuff.append(CConst.CARRIAGE_RETURN);
					newStrBuff.append("      </Host>");
					String newStr = newStrBuff.toString();
					
					replacementList.add(new Replacement(oldStr, newStr));
				}
			}
			
			FileModifyUtil.modifyFileContent(targetFilePath, replacementList);
		}
	}
}