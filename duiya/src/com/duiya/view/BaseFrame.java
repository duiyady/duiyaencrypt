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
	protected int oneOrMany; // ��ǵ������Ƕ�� 0��ʾ�������� 1��ʾ�������
	protected static int flag = 0; // �˳�ʱ����Ƿ���δ��ɵ���
	protected static int flagPro = 0; // ǿ���˳�
	protected MenuBar menuBar; // �˵���
	protected Menu optionMenu; // ѡ��˵�
	protected Menu setMenu; // ���ò˵�
	protected MenuItem defaultTypeItem; // ����׺���˵���
	protected MenuItem defaultPathItem; // ����Ĭ�ϱ���·��
	protected MenuItem oneOptionItem; // ��������
	protected MenuItem manyOptionItem; // �������
	protected Container container; // ����
	protected JPanel belowPanel; // �²�����
	protected JButton encryptButton; // ���ܰ�ť
	protected JButton decipherButton; // ���ܰ�ť
	protected JButton findFileButton; // Ѱ��Դ�ļ���ť
	protected JButton findGoalButton; // Ѱ��Ŀ�ĵ�ַ��ť
	protected JLabel filePathLabel; // Դ�ļ���ǩ
	protected JLabel goalPathLabel; // Ŀ��Ŀ¼��ǩ
	protected JTextField filePathTextField; // Դ�ļ���ַ��
	protected JTextField goalPathTextField; // Ŀ�ĵ�ַ��
	protected JFileChooser fileChooserFrame;// �ļ�ѡ���
	protected String defaultPath; //Ĭ�ϱ����ַ
	protected String filePath; // �ļ�·��
	protected String goalPath; // Ŀ��·��
	protected String fileName; // �ļ�����
	protected SetDefaultpathView defaultpathView; //����Ĭ�ϴ洢·����ͼ
	protected SetPostfixView postfixView; //����Ĭ�Ϻ�׺��ͼ

	public BaseFrame() {
		super();
		setTitle("���ܽ���");
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
		optionMenu = new Menu("����");
		setMenu = new Menu("����");
		defaultTypeItem = new MenuItem("��׺��");
		defaultPathItem = new MenuItem("�洢·��");
		oneOptionItem = new MenuItem("��������");
		manyOptionItem = new MenuItem("�������");
		oneOptionItem.removeNotify();
		optionMenu.add(manyOptionItem);
		setMenu.add(defaultTypeItem);
		setMenu.add(defaultPathItem);
		menuBar.add(setMenu);
		menuBar.add(optionMenu);
		this.setMenuBar(menuBar);

		filePathLabel = new JLabel("Դ�ļ�:");
		Font font = new Font("����", 0, 12);
		filePathLabel.setFont(font);
		filePathLabel.setBounds(10, 20, 50, 30);
		filePathTextField = new JTextField();
		filePathTextField.setEditable(false);
		filePathTextField.setBounds(60, 20, 160, 30);
		findFileButton = new JButton("ѡ��");
		findFileButton.setBounds(220, 20, 60, 30);
		findFileButton.setFont(font);
		goalPathLabel = new JLabel("Ŀ�ĵ�ַ:");
		goalPathLabel.setFont(font);
		goalPathLabel.setBounds(7, 50, 56, 30);
		goalPathTextField = new JTextField();
		goalPathTextField.setEditable(false);
		goalPathTextField.setBounds(60, 50, 160, 30);
		findGoalButton = new JButton("ѡ��");
		findGoalButton.setBounds(220, 50, 60, 30);
		findGoalButton.setFont(font);

		encryptButton = new JButton("����");
		decipherButton = new JButton("����");
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
		
		//�л�����������
		oneOptionItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				oneOrMany = 0;
				setNull();
				setFilePathName("Դ�ļ�:");
				optionMenu.add(manyOptionItem);
				optionMenu.remove(oneOptionItem);
			}
		});
		// �л����������
		manyOptionItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				oneOrMany = 1;
				setNull();
				setFilePathName("Դ��ַ:");
				optionMenu.add(oneOptionItem);
				optionMenu.remove(manyOptionItem);

			}
		});

		// Ѱ��Դ�ļ���ť�¼�
		findFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(oneOrMany == 0){
					fileChooserFrame.setFileSelectionMode(JFileChooser.FILES_ONLY);
					int result = fileChooserFrame.showDialog(new JLabel(), "ѡ��");
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
					int result = fileChooserFrame.showDialog(new JLabel(), "ѡ��");
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
		// Ѱ��Ŀ�ĵ�ַ�¼�
		findGoalButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileChooserFrame.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int result = fileChooserFrame.showDialog(new JLabel(), "ѡ��");
				if(result == JFileChooser.APPROVE_OPTION){
					File file = fileChooserFrame.getSelectedFile();
					if (file.isDirectory()&&file!=null) {
						goalPath = file.getAbsolutePath();
						goalPathTextField.setText(goalPath);
					} 
				}
			}
		});
		
		//���ú�׺��
		defaultTypeItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				postfixView.setPostfix();
				postfixView.setVisible(true);
			}
		});
		//����Ĭ�ϴ洢λ��
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
	//����Դ�ļ���Դ��ַ��ǩ
	protected void setFilePathName(String filePathName) {
		filePathLabel.setText(filePathName);
	}
	//��յ�ַ�ı���
	protected void setNull(){
		filePathTextField.setText("");
		defaultPath = SetMessageUtil.getFileDefaultPath();
		if(!defaultPath.equals("default")){
			goalPathTextField.setText(defaultPath);
		}
	}
}

