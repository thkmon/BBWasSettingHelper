package com.thkmon.was.form;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.thkmon.was.common.CConst;

public class BasicForm extends JFrame {
	
	
	private Container container = null;
	private Font font = null;
	
	
	public BasicForm(int width, int height, String title) {
		container = getContentPane();
		container.setLayout(null);
		setSize(width, height);
		setBounds(200, 200, width, height);
		
		setBackground(CConst.formBackgroundColor);
		container.setBackground(CConst.formBackgroundColor);
		
		setTitle(title);		
		font = new Font("굴림", 13, 13);
		
//		this.addWindowListener(new WindowAdapter() {
//			public void windowClosing(WindowEvent e) {
//				System.out.println("사용자 명령으로 종료합니다.");
//				System.exit(0);
//			}
//		});
		
//		this.addMouseMotionListener(new MouseMotionListener() {
//			@Override
//			public void mouseMoved(MouseEvent e) {
//				setCursor(Cursor.DEFAULT_CURSOR);
//			}
//			
//			@Override
//			public void mouseDragged(MouseEvent e) {
//			}
//		});
	}
	
	
	// 191205 창이 최소화 되어있거나 포커스를 잃어버린 경우에도 창을 보여준다.
	public void open() {
		// 최소화되어 있을 경우 윈도우 복원
		if (this.getState() == Frame.ICONIFIED) {
			this.setState(Frame.NORMAL);
		}
		
		// 윈도우 표시
		this.setVisible(true);
		
		// 윈도우 포커싱
		if (this.getFocusableWindowState()) {
			this.requestFocus();
		}
	}
	
	
	public void close() {
		this.setVisible(false);
	}
	
	
	public JScrollPane addScrollPane(JTextArea obj, int left, int top, int width, int height, boolean isAutoLinefeed) {
		JScrollPane scrollPane = null;
		
		if (isAutoLinefeed) {
			scrollPane = new JScrollPane(obj, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		} else {
			scrollPane = new JScrollPane(obj);
		}
		
		scrollPane.setBackground(Color.white);
		scrollPane.setBounds(left, top, width, height);
		
		addComponentObj(scrollPane);
		return scrollPane;
	}
	
	
	public JTextArea addTextArea(int left, int top, int width, int height, boolean isAutoLinefeed) {

		JTextArea obj = new JTextArea();
		obj.setBackground(Color.white);
		obj.setBounds(left, top, width, height);
		obj.setFont(font);
		
		if (isAutoLinefeed) {
			obj.setLineWrap(true);
		}
		
		return obj;
	}
	
	
	public JTextField addTextInput(int left, int top, int width, int height) {
		JTextField obj = new JTextField();
		obj.setBackground(Color.white);
		obj.setBounds(left, top, width, height);
		obj.setFont(font);
		
		addComponentObj(obj);
		return obj;
	}
	
	
	public JLabel addLabel(int left, int top, int width, int height, String value) {
		JLabel obj = new JLabel();
		obj.setBackground(Color.white);
		obj.setBounds(left, top, width, height);
		obj.setText(value);
		obj.setFont(font);
		
//		obj.addMouseMotionListener(new MouseMotionListener() {
//			@Override
//			public void mouseMoved(MouseEvent e) {
//				setCursor(Cursor.DEFAULT_CURSOR);
//			}
//			
//			@Override
//			public void mouseDragged(MouseEvent e) {
//			}
//		});
		
		addComponentObj(obj);
		return obj;
	}
	
	
	public JButton addButton(int left, int top, int width, int height, String value) {
		JButton obj = new JButton();
		obj.setBackground(CConst.buttonColor);
		obj.setBounds(left, top, width, height);
		obj.setText(value);
		obj.setFont(font);
		obj.setForeground(CConst.buttonTextColor);
		
//		obj.addMouseMotionListener(new MouseMotionListener() {
//			@Override
//			public void mouseMoved(MouseEvent e) {
//				setCursor(Cursor.HAND_CURSOR);
//			}
//			
//			@Override
//			public void mouseDragged(MouseEvent e) {
//			}
//		});
		
		addComponentObj(obj);
		return obj;
	}
	
	
	public JCheckBox addCheckBox(int left, int top, int width, int height, String value) {
		JCheckBox checkBox = new JCheckBox();
		checkBox.setBackground(CConst.formBackgroundColor);
		checkBox.setBounds(left, top, width, height);
		checkBox.setText(value);
		checkBox.setFont(font);
		
//		checkBox.addMouseMotionListener(new MouseMotionListener() {
//			@Override
//			public void mouseMoved(MouseEvent e) {
//				setCursor(Cursor.HAND_CURSOR);
//			}
//			
//			@Override
//			public void mouseDragged(MouseEvent e) {
//			}
//		});
		
		addComponentObj(checkBox);
		return checkBox;
	}
	
	
	private void addComponentObj(Component comp) {
		container.add(comp);
	}
}
