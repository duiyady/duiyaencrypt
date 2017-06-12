package com.duiya.util;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class BaseUtils {
	/**
	 * ���ô�����еķ���
	 * @param jf
	 * @author duiya
	 */
	public static void SetCenter(JFrame jf){
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		double srceenWidth = d.getWidth();
		double srceenHeigth = d.getHeight();
		int frameWidth = jf.getWidth();
		int frameHeight = jf.getHeight();
		int width = (int) (srceenWidth - frameWidth) / 2;
		int height = (int) (srceenHeigth - frameHeight) / 2;
		jf.setLocation(width, height);
	}
	/**
	 * ����ͼƬ
	 * @param jf
	 */
	public static void SetImage(JFrame jf){
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image image = tk.getImage("src/resources/image.png");
		jf.setIconImage(image);
	}
	
	
	
	/**
	 * ��װͷ����Ϣ
	 * @param message
	 * @param pass
	 * @param fileName
	 * @param fileType
	 * @throws Exception
	 */
	public static void setMessage(byte[] message, byte[] pass,String fileName,String fileType) throws Exception{
		int l,sum;//sumΪ������512�����ж��ٸ�
		//�Ƚ����е���Ϊ0
		for(l=0;l<768;l++){
			message[l] = 0;
			if(l<512){
				pass[l] = 0;
			}
		}
		//��ȡ�����
		for(l = 0;l<512;l++){
			byte mess = (byte) (127-(int)(Math.random()*1000)%256);
			pass[l] = mess;
		}
		//���ļ���תΪ�ֽ���
		byte[] nameByte = fileName.getBytes("utf8");
		int length = nameByte.length;
		message[9] = (byte) length;
		for(l=0;l<length;l++){
			message[l+10] = (byte) (nameByte[l]+pass[l]);
		}
		sum = l+10;
		//���ļ���ת��Ϊ�ֽ���
		byte[] typeByte = fileType.getBytes("utf8");
		length = typeByte.length;
		message[sum] = (byte) length;
		sum++;
		for(l=0;l<length;l++){
			message[l+sum] = (byte) (typeByte[l]+pass[l]);
		}
		sum += l;
		for(l=0;l<512;l++){
			message[sum+l] = pass[l];
		}
		sum -= 128; 
		message[8] = (byte) sum;
	}
}
