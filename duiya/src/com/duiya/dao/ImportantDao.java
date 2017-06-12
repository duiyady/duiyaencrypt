package com.duiya.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import com.duiya.util.BaseUtils;

/**
 * 主要的方法类
 * 
 * @author duiya
 *
 */
public class ImportantDao {
	/**
	 * 加密方法
	 */
	public int encrypt(String sourceUrl, String goalUrl) {
		int byteRead = 0,i=0;
		try {
			File sourceFile = new File(sourceUrl);
			if (sourceFile.exists()) {
				InputStream inStream = new FileInputStream(sourceUrl);
				String[] path = sourceUrl.split("\\\\");
				String[] swap = path[path.length-1].split("\\.");
				String fileName = new String("");
				for(i=0;i<swap.length-1;i++){
					fileName += swap[i] + ".";
				}
				fileName = fileName.substring(0, fileName.length()-1);//原文件名
				String fileType = swap[swap.length-1];//原文件类型
				File filetest = new File(goalUrl);
				int counttest = 1;
				String goalUrlT = new String();
				goalUrlT = goalUrl;
				while(filetest.exists()){
					goalUrlT = goalUrl + counttest;
					counttest++;
					filetest = new File(goalUrlT);
				}
				FileOutputStream fileOutStream = new FileOutputStream(goalUrlT);
	
				byte[] messageA = new byte[768]; //中间的头信息，不是最后的；
				byte[] pass = new byte[512];
				BaseUtils.setMessage(messageA, pass, fileName, fileType);
				int headLength = (int)messageA[8] + 128 +512;
				byte[] messageB = new byte[headLength]; //头信息
				for(i=0;i<headLength;i++){
					messageB[i] = messageA[i];
				}
				fileOutStream.write(messageB, 0, headLength);
				fileOutStream.flush();
				
				byte[] buffer = new byte[1024];
				byte[] buffer2 = new byte[1024];
				int count = 0;
				while ((byteRead = inStream.read(buffer)) > 0) {
					for(i=0;i<byteRead;i++){
	                    byte b=buffer[i];
	                    b += pass[i/2];
	                    b += pass[count%512];
	                    buffer2[i]=b;
	                }
					fileOutStream.write(buffer2, 0, byteRead);
					fileOutStream.flush();
					count++;
				}
				inStream.close();
				fileOutStream.close();
			}else{
				return -1;
			}
		} catch (Exception e) {
			return -2;
		}
		return 1;
	}

	/**
	 * 解密方法
	 */
	public int decipher(String sourceUrl, String goalUrl) {
		int byteRead = 0;
		String fileName; //文件名字
		String fileType; //文件类型
		int i;
		try {
			File sourceFile = new File(sourceUrl);
			if (sourceFile.exists()) {
				InputStream inStream = new FileInputStream(sourceUrl);
				byte[] bufferA = new byte[9]; //判断是否是我加密的文件
				if((byteRead = inStream.read(bufferA)) > 0){
					//判断是否为我加密的文件
					int flag = 1;
					for(i=0;i<8;i++){
						if(bufferA[i]!=0){
							flag = 0;
						}
					}
					if(flag == 1){
						int counta = 0;
						int valueLength = (int)bufferA[8] + 128 + 512 -9; //头部剩下信息的长度
						int start = (int)bufferA[8] + 128 - 9;
						byte[] bufferB = new byte[valueLength]; 
						if((byteRead = inStream.read(bufferB)) > 0){
							byte[] pass = new byte[512];
							for(i=0;i<512;i++){
								pass[i] = bufferB[start+i];
							}
							int nameLength = bufferB[0];
							counta++;
							byte[] nameByte = new byte[nameLength];
							for(i=0;i<nameLength;i++){
								nameByte[i] = (byte) (bufferB[counta+i]-pass[i]);
							}
							fileName = new String(nameByte,"utf8");
							counta += i;
							
							int typeLength = bufferB[counta];
							counta++;
							byte[] typeByte = new byte[typeLength];
							for(i=0;i<typeLength;i++){
								typeByte[i] = (byte) (bufferB[counta+i]-pass[i]);
							}
							fileType = new String(typeByte,"utf8");
							String url = goalUrl + "\\" + fileName + "." +fileType;
							
							File filetest = new File(url);
							int counttest = 1;
							while(filetest.exists()){
								url = goalUrl + "\\" + fileName + "(" + counttest + ")" +  "." +fileType;
								counttest++;
								filetest = new File(url);
							}
							FileOutputStream fileOutStream = new FileOutputStream(url);
							byte[] buffer = new byte[1024];
							byte[] buffer2 = new byte[1024];
							int count = 0;
							while ((byteRead = inStream.read(buffer)) > 0) {
								for(i=0;i<byteRead;i++){
				                    byte b=buffer[i];
				                    b -= pass[i/2];
				                    b -= pass[count%512];
				                    buffer2[i]=b;
				                }
								fileOutStream.write(buffer2, 0, byteRead);
								fileOutStream.flush();
								count++;
							}
							inStream.close();
							fileOutStream.close();
						}else{
							inStream.close();
							return -3;
						}
					}else{
						inStream.close();
						return -3;
					}
				}
				inStream.close();
			}else{
				return -1;
			}
		} catch (Exception e) {
			return -2;
		}
		return 1;
	}	
}
