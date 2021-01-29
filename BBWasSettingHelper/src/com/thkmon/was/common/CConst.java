package com.thkmon.was.common;

import java.awt.Color;

import com.thkmon.was.prop.PropFileController;
import com.thkmon.was.string.StringUtil;
import com.thkmon.was.string.data.StringMap;

public class CConst {
	public static String version = "210130";
		
		private static final StringMap optionPropFile = new PropFileController().readPropFile("option.properties");
		public static String getOption(String keyText, String defaultStr) {
			if (optionPropFile == null) {
				return defaultStr;
			}
			return StringUtil.parseStirng(optionPropFile.get(keyText), defaultStr);
		}
		
		public static final String PROJECT_PATH = getOption("PROJECT_PATH", "");
		public static final String JAVA_HOME = getOption("JAVA_HOME", "");
		public static final String JRE_HOME = getOption("JRE_HOME", "");
		public static final String VM_ARGUMENTS = getOption("VM_ARGUMENTS", "");
		
		public static String targetDir = getOption("TARGET_DIR", "");
		
		public static String classDir = getOption("CLASS_DIR", "");
		
		public static boolean bJavaToClass = getOption("JAVA_TO_CLASS", "1").equals("1");
		
		public static boolean bGetWebappDirOnly = false;
		
		public static String forbiddenFile = getOption("FORBIDDEN_FILE", "*.xml, *.conf, *.config, *.properties");
		
		public static String destDir = getOption("RESULT_DIR", "C:/0_patch");
		
		public static Color buttonColor = new Color(200, 200, 200);
		public static Color buttonTextColor = new Color(0, 0, 0);

		public static int winWidth = 600;
		public static int winHeight = 540;
		
		public static int errLogWidth = 600;
		public static int errLogHeight = 520;
		
		public static Color formBackgroundColor = new Color(230, 230, 230);
		
		public static final String CARRIAGE_RETURN = "\n";
}