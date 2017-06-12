package com.duiya.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import com.duiya.dao.ImportantDao;
import com.duiya.dao.ManyOptionDao;
import com.duiya.util.SetMessageUtil;

/**
 * �ļ�����
 * @author asus
 *
 */
public class OneOptionFrame extends BaseFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ImportantDao importantDao; //���ܽ��ܷ���
	private ManyOptionDao manyOptionDao; //�������
	private int isSuccess;
	public OneOptionFrame(){
		super();
		importantDao = new ImportantDao();
		manyOptionDao = new ManyOptionDao();
		isSuccess = 0;
			
		//�����¼�
		encryptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filePath = filePathTextField.getText();
				goalPath = goalPathTextField.getText();
				if(filePath.length()>0&&filePath!=null&&goalPath.length()>0&&goalPath!=null){
					//��������
					if(oneOrMany == 0){
						if(!goalPath.endsWith("\\")){
							goalPath += "\\";
						}
						goalPath +=  fileName + "." + SetMessageUtil.getFileDefaultType();
						isSuccess = importantDao.encrypt(filePath, goalPath);
						if(isSuccess == 1){
							setNull();
							showMessage("���ܳɹ�");
						}else if(isSuccess == -1){
							setNull();
							showMessage("�ļ�������");
						}else if(isSuccess == -2){
							setNull();
							showMessage("δ֪����");
						}
						isSuccess = 0;
					}else if(oneOrMany == 1){
						if(goalPath.endsWith("\\")){
							goalPath = goalPath.substring(0, goalPath.length()-1);
						}
						if(filePath.endsWith("\\")){
							filePath = filePath.substring(0, filePath.length()-1);
						}
						encryptButton.setEnabled(false);
						decipherButton.setEnabled(false);
						isSuccess = manyOptionDao.encrypt(filePath, goalPath);
						if(isSuccess == 1){
							setNull();
							showMessage("���ܳɹ�");
						}else if(isSuccess == -1){
							setNull();
							showMessage("�ļ��в�����");
						}
						encryptButton.setEnabled(true);
						decipherButton.setEnabled(true);
						isSuccess = 0;
					}
				}
			}
		});
		//�����¼�
		decipherButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filePath = filePathTextField.getText();
				goalPath = goalPathTextField.getText();
				if(filePath.length()>0&&filePath!=null&&goalPath.length()>0&&goalPath!=null){
					//��������
					if(oneOrMany == 0){
						if(goalPath.endsWith("\\")){
							goalPath = goalPath.substring(0, goalPath.length()-1);
						}
						isSuccess = importantDao.decipher(filePath, goalPath);
						if(isSuccess == 1){
							setNull();
							showMessage("���ܳɹ�");
						}else if(isSuccess == -1){
							setNull();
							showMessage("�ļ�������");
						}else if(isSuccess == -2){
							setNull();
							showMessage("δ֪����");
						}else if(isSuccess == -3){
							setNull();
							showMessage("���Ҽ��ܵ��ļ�");
						}
					}else if(oneOrMany == 1){
						if(goalPath.endsWith("\\")){
							goalPath = goalPath.substring(0, goalPath.length()-1);
						}
						if(filePath.endsWith("\\")){
							filePath = filePath.substring(0, filePath.length()-1);
						}
						encryptButton.setEnabled(false);
						decipherButton.setEnabled(false);
						isSuccess = manyOptionDao.decipher(filePath, goalPath);
						if(isSuccess == 1){
							setNull();
							showMessage("���ܳɹ�");
						}else if(isSuccess == -1){
							setNull();
							showMessage("�ļ��в�����");
						}
						encryptButton.setEnabled(true);
						decipherButton.setEnabled(true);
						isSuccess = 0;
					}
				}
			}
		});
	}
	public void showMessage(String message){
		new Thread() {
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
				}
				JOptionPane.getRootFrame().dispose();
			}
		}.start();
		JOptionPane.showMessageDialog(null, message);
	}
}
