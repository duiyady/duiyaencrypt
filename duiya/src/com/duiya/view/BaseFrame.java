package com.duiya.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
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

public class BaseFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	protected int oneOrMany; // 标记单个还是多个 0表示单个加密 1表示多个加密
	protected static int flag = 0; // 退出时标记是否有未完成的项
	protected static int flagPro = 0; // 强制退出
	protected MenuBar menuBar; // 菜单条
	protected Menu optionMenu; // 选择菜单
	protected Menu setMenu; // 设置菜单
	protected MenuItem defaultTypeItem; // 设置缀名菜单项
	protected MenuItem defaultPathItem; // 设置默认保存路径
	protected MenuItem oneOptionItem; // 单个操作
	protected MenuItem manyOptionItem; // 多个操作
	protected Container container; // 容器
	protected JPanel belowPanel; // 下部区域
	protected JButton encryptButton; // 加密按钮
	protected JButton decipherButton; // 解密按钮
	protected JButton findFileButton; // 寻找源文件按钮
	protected JButton findGoalButton; // 寻找目的地址按钮
	protected JLabel filePathLabel; // 源文件标签
	protected JLabel goalPathLabel; // 目的目录标签
	protected JTextField filePathTextField; // 源文件地址框
	protected JTextField goalPathTextField; // 目的地址框
	protected JFileChooser fileChooserFrame;// 文件选择框
	protected String defaultPath; //默认保存地址
	protected String filePath; // 文件路径
	protected String goalPath; // 目的路径
	protected String fileName; // 文件名字
	protected SetDefaultpathView defaultpathView; //设置默认存储路径视图
	protected SetPostfixView postfixView; //设置默认后缀视图

	public BaseFrame() {
		super();
		setTitle("加密解密");
		setLayout(null);
		setSize(300, 200);
		BaseUtils.SetCenter(this);
		BaseUtils.SetImage(this);
		oneOrMany = 0;
		fileChooserFrame = new JFileChooser();
		container = getContentPane();
		belowPanel = new JPanel();
		belowPanel.setLayout(null);
		belowPanel.setBounds(0, 0, 300, 200);
		belowPanel.setBackground(Color.lightGray);
		menuBar = new MenuBar();
		optionMenu = new Menu("操作");
		setMenu = new Menu("设置");
		defaultTypeItem = new MenuItem("后缀名");
		defaultPathItem = new MenuItem("存储路径");
		oneOptionItem = new MenuItem("单个操作");
		manyOptionItem = new MenuItem("多个操作");
		oneOptionItem.removeNotify();
		optionMenu.add(manyOptionItem);
		setMenu.add(defaultTypeItem);
		setMenu.add(defaultPathItem);
		menuBar.add(setMenu);
		menuBar.add(optionMenu);
		this.setMenuBar(menuBar);

		filePathLabel = new JLabel("源文件:");
		Font font = new Font("宋体", 0, 12);
		filePathLabel.setFont(font);
		filePathLabel.setBounds(10, 20, 50, 30);
		filePathTextField = new JTextField();
		filePathTextField.setEditable(false);
		filePathTextField.setBounds(60, 20, 160, 30);
		findFileButton = new JButton("选择");
		findFileButton.setBounds(220, 20, 60, 30);
		findFileButton.setFont(font);
		goalPathLabel = new JLabel("目的地址:");
		goalPathLabel.setFont(font);
		goalPathLabel.setBounds(7, 50, 56, 30);
		goalPathTextField = new JTextField();
		goalPathTextField.setEditable(false);
		goalPathTextField.setBounds(60, 50, 160, 30);
		findGoalButton = new JButton("选择");
		findGoalButton.setBounds(220, 50, 60, 30);
		findGoalButton.setFont(font);

		encryptButton = new JButton("加密");
		decipherButton = new JButton("解密");
		encryptButton.setBounds(70, 90, 60, 30);
		decipherButton.setBounds(160, 90, 60, 30);

		defaultpathView = new SetDefaultpathView(goalPathTextField);
		postfixView = new SetPostfixView();
		
		belowPanel.add(filePathLabel);
		belowPanel.add(filePathTextField);
		belowPanel.add(findFileButton);
		belowPanel.add(goalPathLabel);
		belowPanel.add(goalPathTextField);
		belowPanel.add(findGoalButton);
		belowPanel.add(encryptButton);
		belowPanel.add(decipherButton);

		container.add(belowPanel);
		
		setNull();
		
		//切换到单个操作
		oneOptionItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				oneOrMany = 0;
				setNull();
				setFilePathName("源文件:");
				optionMenu.add(manyOptionItem);
				optionMenu.remove(oneOptionItem);
			}
		});
		// 切换到多个操作
		manyOptionItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				oneOrMany = 1;
				setNull();
				setFilePathName("源地址:");
				optionMenu.add(oneOptionItem);
				optionMenu.remove(manyOptionItem);

			}
		});

		// 寻找源文件按钮事件
		findFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(oneOrMany == 0){
					fileChooserFrame.setFileSelectionMode(JFileChooser.FILES_ONLY);
					int result = fileChooserFrame.showDialog(new JLabel(), "选择");
					if(result == JFileChooser.APPROVE_OPTION){
						File file = fileChooserFrame.getSelectedFile();
						if (file.isFile()&&file!=null) {
							filePath = file.getAbsolutePath();
							String str = fileChooserFrame.getSelectedFile().getName();
							fileName = str.split("\\.")[0];
							filePathTextField.setText(filePath);
						}
					}
				}else if(oneOrMany == 1){
					fileChooserFrame.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					int result = fileChooserFrame.showDialog(new JLabel(), "选择");
					if(result == JFileChooser.APPROVE_OPTION){
						File file = fileChooserFrame.getSelectedFile();
						if (file.isDirectory()&&file!=null) {
							filePath = file.getAbsolutePath();
							filePathTextField.setText(filePath);
						}
					}
				}
			}
		});
		// 寻找目的地址事件
		findGoalButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileChooserFrame.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int result = fileChooserFrame.showDialog(new JLabel(), "选择");
				if(result == JFileChooser.APPROVE_OPTION){
					File file = fileChooserFrame.getSelectedFile();
					if (file.isDirectory()&&file!=null) {
						goalPath = file.getAbsolutePath();
						goalPathTextField.setText(goalPath);
					} 
				}
			}
		});
		
		//设置后缀名
		defaultTypeItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				postfixView.setPostfix();
				postfixView.setVisible(true);
			}
		});
		//设置默认存储位置
		defaultPathItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				defaultpathView.setDefaultPath();
				defaultpathView.setVisible(true);
			}
		});

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		this.setResizable(false);
	}
	//设置源文件或源地址标签
	protected void setFilePathName(String filePathName) {
		filePathLabel.setText(filePathName);
	}
	//清空地址文本框
	protected void setNull(){
		filePathTextField.setText("");
		defaultPath = SetMessageUtil.getFileDefaultPath();
		if(!defaultPath.equals("default")){
			goalPathTextField.setText(defaultPath);
		}
	}
}

