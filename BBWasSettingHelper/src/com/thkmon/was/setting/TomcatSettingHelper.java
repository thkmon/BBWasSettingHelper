package com.thkmon.was.setting;

import java.io.File;

import com.thkmon.was.common.CConst;
import com.thkmon.was.file.FileModifyUtil;
import com.thkmon.was.setting.data.Replacement;
import com.thkmon.was.setting.data.ReplacementList;

public class TomcatSettingHelper extends SettingHelper {
	
	@Override
	public void doSetting(String tomcatPath, SettingData settingData) throws Exception {
		String projectPath = settingData.getProjectPath();
		if (projectPath == null || projectPath.length() == 0) {
			throw new Exception("projectPath is null or empty.");
		}
		
		String javaHome = settingData.getJavaHome();
		if (javaHome == null || javaHome.length() == 0) {
			throw new Exception("javaHome is null or empty.");
		}
		
		String jreHome = settingData.getJreHome();
		if (jreHome == null || jreHome.length() == 0) {
			throw new Exception("jreHome is null or empty.");
		}
		
		{
			String targetFilePath = tomcatPath + "\\bin\\setclasspath.bat";
			ReplacementList replacementList = new ReplacementList();
			
			{
				String oldStr = "rem Make sure prerequisite environment variables are set";
				
				StringBuffer newStrBuff = new StringBuffer();
				newStrBuff.append("rem Make sure prerequisite environment variables are set");
				newStrBuff.append(CConst.CARRIAGE_RETURN);
				newStrBuff.append("set JAVA_HOME=").append(javaHome);
				newStrBuff.append(CConst.CARRIAGE_RETURN);
				newStrBuff.append("set JRE_HOME=").append(jreHome);
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
					int vmArgCount = settingData.getVmArgumentList().size();
					for (int i=0; i<vmArgCount; i++) {
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
				String oldStr = "rem ----- Execute The Requested Command ---------------------------------------";
				String newStr = "set \"CLASSPATH=%CLASSPATH%;" + projectPath + File.separator + "config\"" + CConst.CARRIAGE_RETURN + "rem ----- Execute The Requested Command ---------------------------------------";
				replacementList.add(new Replacement(oldStr, newStr));
			}
			
			FileModifyUtil.modifyFileContent(targetFilePath, replacementList);
		}
		

		{
			String targetFilePath = tomcatPath + "\\conf\\server.xml";
			ReplacementList replacementList = new ReplacementList();
			
			int basicPort = 80;
			int shutdownPort = (basicPort > 999) ? (basicPort + 5) : (basicPort + 1005);
			int ajpPort = (basicPort > 999) ? (basicPort + 9) : (basicPort + 1009);
			
			{
				String oldStr = "<Server port=\"8005\" shutdown=\"SHUTDOWN\">";
				String newStr = "<Server port=\"" + shutdownPort + "\" shutdown=\"SHUTDOWN\">";
				replacementList.add(new Replacement(oldStr, newStr));
			}
			
			{
				String oldStr = "<Connector port=\"8080\" protocol=\"HTTP/1.1\"";
				String newStr = "<Connector URIEncoding=\"UTF-8\" port=\"" + basicPort + "\" protocol=\"HTTP/1.1\"";
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
				String oldStr = "      </Host>";
				
				StringBuffer newStrBuff = new StringBuffer();
				newStrBuff.append("      <Context docBase=\"" + projectPath + "\\webapp\" path=\"/\" reloadable=\"false\"/>");
				newStrBuff.append(CConst.CARRIAGE_RETURN);
				newStrBuff.append("      </Host>");
				String newStr = newStrBuff.toString();
				
				replacementList.add(new Replacement(oldStr, newStr));
			}
			
			FileModifyUtil.modifyFileContent(targetFilePath, replacementList);
		}
	}
}