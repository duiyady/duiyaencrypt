package com.duiya.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.duiya.util.BaseUtils;
import com.duiya.util.SetMessageUtil;

public class SetPostfixView extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Container container; // ����
	protected JPanel belowPanel; // �²�����
	protected JLabel postfixLabel; // Ĭ�Ϻ�׺��ǩ
	protected JTextField postfixTextField; // Ĭ�Ϻ�׺��
	protected JButton savaButton; // ȷ�ϰ�ť
	protected JButton cancelButton; // ȡ����ť
	protected String postfix;
	public SetPostfixView(){
		super();
		setTitle("Ĭ�Ϻ�׺");
		setLayout(null);
		setSize(220, 150);
		BaseUtils.SetCenter(this);
		BaseUtils.SetImage(this);
		container = getContentPane();
		belowPanel = new JPanel();
		belowPanel.setLayout(null);
		belowPanel.setBounds(0, 0, 220, 150);
		belowPanel.setBackground(Color.lightGray);
		
		postfixLabel = new JLabel("Ĭ�Ϻ�׺:");
		Font font = new Font("����", 0, 12);
		postfixLabel.setFont(font);
		postfixLabel.setBounds(20, 20, 60, 30);
		
		postfixTextField = new JTextField();
		postfixTextField.setBounds(80, 20, 100, 30);
		//postfixTextField
		setPostfix();
		
		savaButton = new JButton("����");
		Font font2 = new Font("����", 1, 12);
		savaButton.setFont(font2);
		savaButton.setBounds(40, 70, 60, 30);
		cancelButton = new JButton("ȡ��");
		cancelButton.setFont(font2);
		cancelButton.setBounds(120, 70, 60, 30);
		
		belowPanel.add(postfixLabel);
		belowPanel.add(postfixTextField);
		belowPanel.add(savaButton);
		belowPanel.add(cancelButton);
		container.add(belowPanel);
		
		savaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				postfix = postfixTextField.getText().trim();
				if(postfix.matches("[a-zA-Z]{1,10}")){
					SetMessageUtil.setFileDefaultType(postfix);
					setVisible(false);
				}else{
					postfixTextField.setText("");
					showMessage("ֻ������ĸ�Ҳ�Ϊ��");
				}
			}
		});
		
		postfixTextField.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent arg0) {}
			public void keyReleased(KeyEvent e) {}
			public void keyTyped(KeyEvent e) {
				String mess = postfixTextField.getText();
				if(mess.length()>9){
					postfixTextField.setText(mess.substring(0, 9));
				}
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				postfixTextField.setText("");;
				setVisible(false);
			}
		});
		
		setVisible(false);
		setResizable(false);
	}
	public void setPostfix(){
		postfixTextField.setText(SetMessageUtil.getFileDefaultType());
	}
	public void showMessage(String message){
		new Thread() {
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
				JOptionPane.getRootFrame().dispose();
			}
		}.start();
		JOptionPane.showMessageDialog(null, message);
	}

}
