package com.duiya.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import com.duiya.dao.ImportantDao;
import com.duiya.dao.ManyOptionDao;
import com.duiya.util.SetMessageUtil;

/**
 * 文件操作
 * @author asus
 *
 */
public class OneOptionFrame extends BaseFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ImportantDao importantDao; //加密解密方法
	private ManyOptionDao manyOptionDao; //多个加密
	private int isSuccess;
	public OneOptionFrame(){
		super();
		importantDao = new ImportantDao();
		manyOptionDao = new ManyOptionDao();
		isSuccess = 0;
			
		//加密事件
		encryptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filePath = filePathTextField.getText();
				goalPath = goalPathTextField.getText();
				if(filePath.length()>0&&filePath!=null&&goalPath.length()>0&&goalPath!=null){
					//单个加密
					if(oneOrMany == 0){
						if(!goalPath.endsWith("\\")){
							goalPath += "\\";
						}
						goalPath +=  fileName + "." + SetMessageUtil.getFileDefaultType();
						isSuccess = importantDao.encrypt(filePath, goalPath);
						if(isSuccess == 1){
							setNull();
							showMessage("加密成功");
						}else if(isSuccess == -1){
							setNull();
							showMessage("文件不存在");
						}else if(isSuccess == -2){
							setNull();
							showMessage("未知错误");
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
							showMessage("加密成功");
						}else if(isSuccess == -1){
							setNull();
							showMessage("文件夹不存在");
						}
						encryptButton.setEnabled(true);
						decipherButton.setEnabled(true);
						isSuccess = 0;
					}
				}
			}
		});
		//解密事件
		decipherButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filePath = filePathTextField.getText();
				goalPath = goalPathTextField.getText();
				if(filePath.length()>0&&filePath!=null&&goalPath.length()>0&&goalPath!=null){
					//单个解密
					if(oneOrMany == 0){
						if(goalPath.endsWith("\\")){
							goalPath = goalPath.substring(0, goalPath.length()-1);
						}
						isSuccess = importantDao.decipher(filePath, goalPath);
						if(isSuccess == 1){
							setNull();
							showMessage("解密成功");
						}else if(isSuccess == -1){
							setNull();
							showMessage("文件不存在");
						}else if(isSuccess == -2){
							setNull();
							showMessage("未知错误");
						}else if(isSuccess == -3){
							setNull();
							showMessage("不我加密的文件");
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
							showMessage("解密成功");
						}else if(isSuccess == -1){
							setNull();
							showMessage("文件夹不存在");
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
