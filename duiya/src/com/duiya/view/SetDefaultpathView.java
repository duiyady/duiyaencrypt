package com.duiya.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.duiya.util.BaseUtils;
import com.duiya.util.SetMessageUtil;

public class SetDefaultpathView extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Container container; // ����
	protected JPanel belowPanel; // �²�����
	protected JLabel defaultPathLabel; // Ĭ�ϵ�ַ��ǩ
	protected JTextField defaultPathTextField; // Ĭ�ϵ�ַ��
	protected JFileChooser fileChooserFrame; //��ַѡ���
	protected JButton findPathButton; //Ѱַ��ַ�İ�ť
	protected JButton savaButton; // ȷ�ϰ�ť
	protected JButton cancelButton; // ȡ����ť
	protected String defaultPath;
	public SetDefaultpathView(JTextField changeTextField){
		super();
		setTitle("Ĭ�ϴ洢λ��");
		setLayout(null);
		setSize(220, 150);
		BaseUtils.SetCenter(this);
		BaseUtils.SetImage(this);
		container = getContentPane();
		belowPanel = new JPanel();
		belowPanel.setLayout(null);
		belowPanel.setBounds(0, 0, 220, 150);
		belowPanel.setBackground(Color.lightGray);
		fileChooserFrame = new JFileChooser();
		defaultPathLabel = new JLabel("��ַ:");
		Font font = new Font("����", 0, 12);
		defaultPathLabel.setFont(font);
		defaultPathLabel.setBounds(5, 20, 30, 30);
		
		defaultPathTextField = new JTextField();
		defaultPathTextField.setBounds(35, 20, 115, 30);
		defaultPathTextField.setEditable(false);
		setDefaultPath();
		findPathButton = new JButton("ѡ��");
		findPathButton.setFont(font);
		findPathButton.setBounds(152, 20, 58, 30);
		
		savaButton = new JButton("����");
		Font font2 = new Font("����", 1, 12);
		savaButton.setFont(font2);
		savaButton.setBounds(40, 70, 60, 30);
		cancelButton = new JButton("ȡ��");
		cancelButton.setFont(font2);
		cancelButton.setBounds(120, 70, 60, 30);
		
		belowPanel.add(defaultPathLabel);
		belowPanel.add(defaultPathTextField);
		belowPanel.add(findPathButton);
		belowPanel.add(savaButton);
		belowPanel.add(cancelButton);
		container.add(belowPanel);
		
		findPathButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fileChooserFrame.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int result = fileChooserFrame.showDialog(new JLabel(), "ѡ��");
				if(result == JFileChooser.APPROVE_OPTION){
					File file = fileChooserFrame.getSelectedFile();
					if (file.isDirectory()&&file!=null) {
						String filePath = file.getAbsolutePath();
						defaultPathTextField.setText(filePath);
					}
				}
			}
		});
		
		savaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				defaultPath = defaultPathTextField.getText();
				SetMessageUtil.setFileDefaultPath(defaultPath);
				changeTextField.setText(defaultPath);
				setVisible(false);
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				defaultPathTextField.setText("");;
				setVisible(false);
			}
		});
		
		setVisible(false);
		setResizable(false);
	}
	public void setDefaultPath(){
		defaultPathTextField.setText(SetMessageUtil.getFileDefaultPath());
	}
}
