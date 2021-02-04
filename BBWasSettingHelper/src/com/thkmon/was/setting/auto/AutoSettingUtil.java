package com.thkmon.was.setting.auto;

import com.thkmon.was.form.SettingForm;
import com.thkmon.was.path.PathUtil;

public class AutoSettingUtil {
	
	/**
	 * 프로젝트 폴더 경로에 따라 인풋박스 자동세팅
	 */
	public static void doAutoSetting() {
		String projectFolderPath = SettingForm.projectFolderText.getText();
		if (projectFolderPath == null || projectFolderPath.length() == 0) {
			return;
		} else {
			projectFolderPath = PathUtil.revisePath(projectFolderPath);
		}
		
		String lowerProjectFolderPath = projectFolderPath.trim().toLowerCase();
		
		if (lowerProjectFolderPath.toLowerCase().indexOf("\\nanum\\") < 0) {
			return;
		}
		
		if (lowerProjectFolderPath.toLowerCase().indexOf("\\nanum\\portal\\workspaces") > -1) {
			SettingForm.httpPortText.setText("8080");
			SettingForm.shutdownPortText.setText("8085");
			SettingForm.ajpPortText.setText("8089");
			
			SettingForm.javaHomeText.setText("C:\\NANUM\\Java\\jdk1.7.0_80(x64)");
			
			SettingForm.jreHomeText.setText("C:\\NANUM\\Java\\jre7");
			
			{
				StringBuffer buff = new StringBuffer();
				buff.append(getBasicVmArgumentText());
				SettingForm.vmArgumentList.setText(buff.toString());
			}
			
			{
				StringBuffer buff = new StringBuffer();
				buff.append("/, " + projectFolderPath + "\\NANUM\\src\\main\\webapp");
				buff.append("\n");
				buff.append("/image, " + projectFolderPath + "\\NANUM\\userdir\\image");
				buff.append("\n");
				buff.append("/imagetmp, " + projectFolderPath + "\\NANUM\\userdir\\imagetmp");
				SettingForm.webModuleList.setText(buff.toString());
			}
			
		} else if (lowerProjectFolderPath.toLowerCase().indexOf("\\nanum\\mobile\\workspaces") > -1) {
			SettingForm.httpPortText.setText("10000");
			SettingForm.shutdownPortText.setText("10005");
			SettingForm.ajpPortText.setText("10009");
			
			SettingForm.javaHomeText.setText("C:\\NANUM\\Java\\jdk1.7.0_80(x64)");
			
			SettingForm.jreHomeText.setText("C:\\NANUM\\Java\\jre7");
			
			{
				StringBuffer buff = new StringBuffer();
				buff.append(getBasicVmArgumentText());
				SettingForm.vmArgumentList.setText(buff.toString());
			}
			
			{
				StringBuffer buff = new StringBuffer();
				buff.append("/, " + projectFolderPath + "\\NANUM\\src\\main\\webapp");
				SettingForm.webModuleList.setText(buff.toString());
			}
			
		} else if (lowerProjectFolderPath.toLowerCase().indexOf("\\nanum\\workspaces") > -1) {
			SettingForm.httpPortText.setText("80");
			SettingForm.shutdownPortText.setText("1085");
			SettingForm.ajpPortText.setText("1089");
			
			SettingForm.javaHomeText.setText("C:\\NANUM\\Java\\jdk1.6.0_45(x64)");
			
			SettingForm.jreHomeText.setText("C:\\NANUM\\Java\\jre6");
			
			{
				StringBuffer buff = new StringBuffer();
				buff.append(getBasicVmArgumentText());
				buff.append("\n");
				buff.append("-DXF_HOME=").append(projectFolderPath);
				buff.append("\n");
				buff.append("-DNANUM_HOME=").append(projectFolderPath);
				buff.append("\n");
				buff.append("-DNBRM_HOME=").append(projectFolderPath);
				SettingForm.vmArgumentList.setText(buff.toString());
			}
			
			{
				StringBuffer buff = new StringBuffer();
				buff.append(projectFolderPath + "\\config");
				SettingForm.classpathList.setText(buff.toString());
			}
			
			{
				StringBuffer buff = new StringBuffer();
				buff.append("/, " + projectFolderPath + "\\webapp");
				SettingForm.webModuleList.setText(buff.toString());
			}
		}
	}
	
	
	private static String getBasicVmArgumentText() {
		StringBuffer buff = new StringBuffer();
		
		buff.append("-Xms1024m -Xmx1024m -XX:PermSize=256m -XX:MaxPermSize=512m");
		buff.append("\n");
		buff.append("-Dfile.encoding=\"UTF-8\"");
		
		return buff.toString();
	}
}