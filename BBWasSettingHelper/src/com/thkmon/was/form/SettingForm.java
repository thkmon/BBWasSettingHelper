package com.thkmon.was.form;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.thkmon.was.common.CConst;
import com.thkmon.was.datetime.DatetimeUtil;
import com.thkmon.was.error.MsgException;
import com.thkmon.was.main.BBWasSettingHelper;
import com.thkmon.was.setting.SettingData;
import com.thkmon.was.string.StringUtil;

public class SettingForm {
	public static boolean isOpened = false;
	
	public static JLabel projectFolderLabel = null;
	public static JTextField projectFolderText = null;
	public static JButton projectFolderButton = null;
	
	public static JLabel httpPortLabel = null;
	public static JTextField httpPortText = null;
	
	public static JLabel shutdownPortLabel = null;
	public static JTextField shutdownPortText = null;
	
	public static JLabel ajpPortLabel = null;
	public static JTextField ajpPortText = null;
	
	public static JLabel javaHomeLabel = null;
	public static JTextField javaHomeText = null;
	public static JButton javaHomeButton = null;
	
	public static JLabel jreHomeLabel = null;
	public static JTextField jreHomeText = null;
	public static JButton jreHomeButton = null;
	
	
	public static JLabel vmArgumentLabel = null;
	public static JTextArea vmArgumentList = null;
	public static JScrollPane vmArgumentListScrollPane = null;
	
	public static JLabel classpathLabel = null;
	public static JTextArea classpathList = null;
	public static JScrollPane classpathListScrollPane = null;
	
	public static JLabel webModuleLabel = null;
	public static JTextArea webModuleList = null;
	public static JScrollPane webModuleListScrollPane = null;
	
	
	public static JLabel destDirLabel = null;
	public static JTextField destDirText = null;
	
	public static JButton destDirButton = null;
	public static int SMALL_BUTTON_GAP = 80;
	public static final int SMALL_BUTTON_WIDTH = 70;
	public static final int SMALL_BUTTON_HEIGHT = 25;
	
	
	public static JButton makeButton = null;
	
	
	private int top = 0;
	
	private void plusTop(int num) {
		top = top + (25 * num);
	}
	private void plusTopByPixel(int pixel) {
		top = top + pixel;
	}
	
	
	public SettingForm() {
		
		String title = "BBWasSettingHelper";
		if (CConst.version != null && CConst.version.length() > 0) {
			title = title + "_" + CConst.version;
		}
		
		int formWidth = CConst.winWidth;
		int formHeight = CConst.winHeight;
		
		final BasicForm bForm = new BasicForm(formWidth, formHeight, title);
		
		bForm.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.out.println("사용자 명령으로 종료합니다.");
				System.exit(0);
			}
		});
		
		int width = 560;
		int left = 10;
		
		projectFolderLabel = bForm.addLabel(left, top, width, 30, "Project Folder Path");
		plusTop(1);
		projectFolderText = bForm.addTextInput(left, top, width - SMALL_BUTTON_GAP, 25);
		projectFolderText.setText(CConst.PROJECT_PATH);
		
		projectFolderButton = bForm.addButton(left + (width - SMALL_BUTTON_GAP) + 10, top, SMALL_BUTTON_WIDTH, SMALL_BUTTON_HEIGHT, "Set");
		projectFolderButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedPath = new DialogWrapper().openFolderDialog(projectFolderText.getText());
				if (selectedPath != null && selectedPath.length() > 0) {
					projectFolderText.setText(selectedPath);
				}
			}
		});
		
		plusTop(1);
		
		
		int oneWidth = getOneOverThreeWidth(formWidth);
		
		httpPortLabel = bForm.addLabel(left, top, oneWidth, 30, "Port");
		shutdownPortLabel = bForm.addLabel(left + (oneWidth + 10) * 1, top, oneWidth, 30, "Shutdown Port");
		ajpPortLabel = bForm.addLabel(left + (oneWidth + 10) * 2, top, oneWidth, 30, "AJP Port");
		plusTop(1);
		
		httpPortText = bForm.addTextInput(left, top, oneWidth, 25);
		httpPortText.setText("8080");
		httpPortText.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				onKeyPressed();
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				onKeyPressed();
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				onKeyPressed();
			}
			
			private void onKeyPressed() {
				int httpPort = StringUtil.parseInt(httpPortText.getText(), 0);
				if (httpPort > 0) {
					int shutdownPort = (httpPort > 999) ? (httpPort + 5) : (httpPort + 1005);
					int ajpPort = (httpPort > 999) ? (httpPort + 9) : (httpPort + 1009);
					
					shutdownPortText.setText(String.valueOf(shutdownPort));
					ajpPortText.setText(String.valueOf(ajpPort));
				}
			}
		});
		
		shutdownPortText = bForm.addTextInput(left + (oneWidth + 10) * 1, top, oneWidth, 25);
		shutdownPortText.setText("8085");
		
		ajpPortText = bForm.addTextInput(left + (oneWidth + 10) * 2, top, oneWidth, 25);
		ajpPortText.setText("8089");
		
		plusTop(1);
		
		
		javaHomeLabel = bForm.addLabel(left, top, width, 30, "Java Home");
		plusTop(1);
		javaHomeText = bForm.addTextInput(left, top, width - SMALL_BUTTON_GAP, 25);
		javaHomeText.setText(CConst.JAVA_HOME);
		
		javaHomeButton = bForm.addButton(left + (width - SMALL_BUTTON_GAP) + 10, top, SMALL_BUTTON_WIDTH, SMALL_BUTTON_HEIGHT, "Set");
		javaHomeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedPath = new DialogWrapper().openFolderDialog(javaHomeText.getText());
				if (selectedPath != null && selectedPath.length() > 0) {
					javaHomeText.setText(selectedPath);
				}
			}
		});
		
		plusTop(1);
		
		
		jreHomeLabel = bForm.addLabel(left, top, width, 30, "Jre Home");
		plusTop(1);
		jreHomeText = bForm.addTextInput(left, top, width - SMALL_BUTTON_GAP, 25);
		jreHomeText.setText(CConst.JRE_HOME);
		
		jreHomeButton = bForm.addButton(left + (width - SMALL_BUTTON_GAP) + 10, top, SMALL_BUTTON_WIDTH, SMALL_BUTTON_HEIGHT, "Set");
		jreHomeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedPath = new DialogWrapper().openFolderDialog(jreHomeText.getText());
				if (selectedPath != null && selectedPath.length() > 0) {
					jreHomeText.setText(selectedPath);
				}
			}
		});
		
		plusTop(1);
		
		
		vmArgumentLabel = bForm.addLabel(left, top, width, 30, "VM Arguments");
		plusTop(1);
		
		int oneTextareaHeight = getOneTextareaHeight(formHeight);
		
		vmArgumentList = bForm.addTextArea(left, top, width, oneTextareaHeight, false);
		vmArgumentListScrollPane = bForm.addScrollPane(vmArgumentList, left, top, width, oneTextareaHeight, false);
		vmArgumentList.setText(CConst.VM_ARGUMENTS.replace(" -D", "\n-D"));
		
		plusTopByPixel(oneTextareaHeight);
		
		
		classpathLabel = bForm.addLabel(left, top, width, 30, "Classpath");
		plusTop(1);
		
		classpathList = bForm.addTextArea(left, top, width, oneTextareaHeight, false);
		classpathListScrollPane = bForm.addScrollPane(classpathList, left, top, width, oneTextareaHeight, false);
		
		plusTopByPixel(oneTextareaHeight);
		
		
		webModuleLabel = bForm.addLabel(left, top, width, 30, "Web Modules (Path, Document Base)");
		plusTop(1);
		
		webModuleList = bForm.addTextArea(left, top, width, oneTextareaHeight, false);
		webModuleListScrollPane = bForm.addScrollPane(webModuleList, left, top, width, oneTextareaHeight, false);
		
		plusTopByPixel(oneTextareaHeight);
		
		
		destDirLabel = bForm.addLabel(left, top, width, 30, "Destination Folder Path");
		plusTop(1);
		
		destDirText = bForm.addTextInput(left, top, width - SMALL_BUTTON_GAP, 25);
		destDirText.setText(getNotExistingDestDirPath());
		
		destDirButton = bForm.addButton(left + (width - SMALL_BUTTON_GAP) + 10, top, SMALL_BUTTON_WIDTH, SMALL_BUTTON_HEIGHT, "Open");
		destDirButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				boolean bDirExisting = false;
				File destDirObj = new File(destDirText.getText());
				if (destDirObj.exists()) {
					bDirExisting = true;
				} else {
					bDirExisting = destDirObj.mkdirs();
				}
				
				if (bDirExisting) {
					try {
						Runtime rt = Runtime.getRuntime();
						Process p1 = rt.exec("explorer.exe " + destDirObj.getAbsolutePath());
						p1.waitFor();
					} catch (Exception e) {
						System.err.println("destDirButton 오류 : " + e.getMessage());
						e.printStackTrace();
					}
				} else {
					System.err.println("destDirButton 알림 : 폴더생성 실패");
				}
			}
		});
		
		plusTop(1);
		plusTop(1);
		
		
		makeButton = bForm.addButton(left, top, width, 30, "MAKE");
		makeButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				try {
					System.out.println("BEGIN");
					
					SettingData settingData = new SettingData();
					settingData.setHttpPort(StringUtil.parseInt(httpPortText.getText(), 8080));
					settingData.setShutdownPort(StringUtil.parseInt(shutdownPortText.getText(), 8085));
					settingData.setAjpPort(StringUtil.parseInt(ajpPortText.getText(), 8089));
					
					settingData.setJavaHomePath(javaHomeText.getText());
					settingData.setJreHomePath(jreHomeText.getText());
					settingData.setProjectPath(projectFolderText.getText());
					settingData.setVmArgumentList(StringUtil.splitToList(vmArgumentList.getText(), "[\\r\\n\\t\\s]"));
					settingData.setClasspathList(StringUtil.splitToList(classpathList.getText(), "[\\r\\n]"));
					settingData.setWebModuleList(StringUtil.splitToList(webModuleList.getText(), "[\\r\\n]"));
					settingData.setDestFolderPath(destDirText.getText());
					
					BBWasSettingHelper instance = new BBWasSettingHelper();
					instance.setServerForSingleWAS(settingData);
					
					System.out.println("END");
					
				} catch (MsgException e) {
					System.err.println(e.getMessage());
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		bForm.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent evt) {
				resizeForm(bForm.getWidth(), bForm.getHeight());
	        }
		});
		
		bForm.open();
		
		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
					isOpened = true;
				} catch (InterruptedException e) {
				} catch (Exception e) {}
			}
			
		};
		thread.start();
	}
	
	
	private static void resizeForm(int oldFormWidth, int oldFormHeight) {
		if (!isOpened) {
			return;
		}
		
		int topToAdd = 0;
		int newWidth = oldFormWidth - 40;
		int newHeight = oldFormHeight - 40;
		
		{
			projectFolderLabel.setBounds(projectFolderLabel.getX(), topToAdd, newWidth, projectFolderLabel.getHeight());
		
			topToAdd += 25;
		
			int widthWithoutButton = newWidth - SMALL_BUTTON_GAP;
			projectFolderText.setBounds(projectFolderText.getX(), topToAdd, widthWithoutButton, projectFolderText.getHeight());
			projectFolderButton.setBounds(projectFolderText.getX() + widthWithoutButton + 10, topToAdd, SMALL_BUTTON_WIDTH, projectFolderButton.getHeight());
		}
		
		topToAdd += 25;
		
		{
			int oneWidth = getOneOverThreeWidth(oldFormWidth);
			
			int left = httpPortLabel.getX();
			httpPortLabel.setBounds(left, topToAdd, oneWidth, httpPortLabel.getHeight());
			shutdownPortLabel.setBounds(left + (oneWidth + 10) * 1, topToAdd, oneWidth, shutdownPortLabel.getHeight());
			ajpPortLabel.setBounds(left + (oneWidth + 10) * 2, topToAdd, oneWidth, ajpPortLabel.getHeight());
			
			topToAdd += 25;
			
			httpPortText.setBounds(left, topToAdd, oneWidth, httpPortText.getHeight());
			shutdownPortText.setBounds(left + (oneWidth + 10) * 1, topToAdd, oneWidth, shutdownPortText.getHeight());
			ajpPortText.setBounds(left + (oneWidth + 10) * 2, topToAdd, oneWidth, ajpPortText.getHeight());
		}
		
		topToAdd += 25;
		
		{
			javaHomeLabel.setBounds(javaHomeLabel.getX(), topToAdd, newWidth, javaHomeLabel.getHeight());
		
			topToAdd += 25;
		
			int widthWithoutButton = newWidth - SMALL_BUTTON_GAP;
			javaHomeText.setBounds(javaHomeText.getX(), topToAdd, widthWithoutButton, javaHomeText.getHeight());
			javaHomeButton.setBounds(javaHomeText.getX() + widthWithoutButton + 10, topToAdd, SMALL_BUTTON_WIDTH, javaHomeButton.getHeight());
		}
		
		topToAdd += 25;
		
		{
			jreHomeLabel.setBounds(jreHomeLabel.getX(), topToAdd, newWidth, jreHomeLabel.getHeight());
		
			topToAdd += 25;
		
			int widthWithoutButton = newWidth - SMALL_BUTTON_GAP;
			jreHomeText.setBounds(jreHomeText.getX(), topToAdd, widthWithoutButton, jreHomeText.getHeight());
			jreHomeButton.setBounds(jreHomeText.getX() + widthWithoutButton + 10, topToAdd, SMALL_BUTTON_WIDTH, jreHomeButton.getHeight());
		}
		
		topToAdd += 25;
		
		int oneTextareaHeight = getOneTextareaHeight(oldFormHeight);
		
		{
			vmArgumentLabel.setBounds(vmArgumentLabel.getX(), topToAdd, newWidth, vmArgumentLabel.getHeight());
		
			topToAdd += 25;
			vmArgumentList.setBounds(vmArgumentList.getX(), topToAdd, newWidth, oneTextareaHeight);
			vmArgumentListScrollPane.setBounds(vmArgumentListScrollPane.getX(), topToAdd, newWidth, oneTextareaHeight);
		}
		
		topToAdd += vmArgumentListScrollPane.getHeight();
		
		{
			classpathLabel.setBounds(classpathLabel.getX(), topToAdd, newWidth, classpathLabel.getHeight());
		
			topToAdd += 25;
			classpathList.setBounds(classpathList.getX(), topToAdd, newWidth, oneTextareaHeight);
			classpathListScrollPane.setBounds(classpathListScrollPane.getX(), topToAdd, newWidth, oneTextareaHeight);
		}
		
		topToAdd += classpathListScrollPane.getHeight();
		
		{
			webModuleLabel.setBounds(webModuleLabel.getX(), topToAdd, newWidth, webModuleLabel.getHeight());
			
			topToAdd += 25;
			webModuleList.setBounds(webModuleList.getX(), topToAdd, newWidth, oneTextareaHeight);
			webModuleListScrollPane.setBounds(webModuleListScrollPane.getX(), topToAdd, newWidth, oneTextareaHeight);
		}
		
		topToAdd += webModuleListScrollPane.getHeight();
		
		{
			destDirLabel.setBounds(destDirLabel.getX(), topToAdd, newWidth, destDirLabel.getHeight());
		
			topToAdd += 25;
		
			int widthWithoutButton = newWidth - SMALL_BUTTON_GAP;
			destDirText.setBounds(destDirText.getX(), topToAdd, widthWithoutButton, destDirText.getHeight());
			destDirButton.setBounds(destDirText.getX() + widthWithoutButton + 10, topToAdd, SMALL_BUTTON_WIDTH, destDirButton.getHeight());
		}
		
		topToAdd += 25;
		topToAdd += 25;
		
		{
			makeButton.setBounds(makeButton.getX(), topToAdd, newWidth, makeButton.getHeight());
		}
	}
	
	
	/**
	 * 아직 디스크에 존재하지 않는 목적폴더의 경로를 생성한다.
	 * @return
	 */
	private String getNotExistingDestDirPath() {
		
		String prefix = CConst.destDir + "/" + DatetimeUtil.getTodayDate().substring(2);
		prefix = StringUtil.revisePath(prefix);
		
		int num = 0;
		String numStr = "";
		
		File dir = null;
		boolean inLoop = true;
		
		while(inLoop) {
			if (num == 0) {
				numStr = "";
			} else if (num < 10) {
				numStr = "_0" + num;
			} else {
				numStr = "_" + num;
			}
			
			dir = new File(prefix + numStr);
			if (dir.exists()) {
				num++;
				continue;
			}
			
			// 존재하지 않을 경우
			inLoop = false;
			break;
		}
	
		return StringUtil.revisePath(dir.getAbsolutePath());
	}
	
	
	/**
	 * 1개의 textarea 세로길이 가져오기
	 * 
	 * @param formHeight
	 * @return
	 */
	private static int getOneTextareaHeight(int formHeight) {
		int border = 40;
		
		int heightOfTextareas = formHeight - border - 420;
		int remain = heightOfTextareas % 3;
		int oneTextareaHeight = (heightOfTextareas - remain) / 3;
		return oneTextareaHeight;
	}
	
	
	/**
	 * 1/3 크기의 가로길이 가져오기
	 * 
	 * @param formWidth
	 * @return
	 */
	private static int getOneOverThreeWidth(int formWidth) {
		int border = 40;
		int betweenInputs = 10 * 2;
		
		int widthWithoutGap = formWidth - border - betweenInputs;
		int remain = widthWithoutGap % 3;
		int oneWidth = (widthWithoutGap - remain) / 3;
		return oneWidth;
	}
}