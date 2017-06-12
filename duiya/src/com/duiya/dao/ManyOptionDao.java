package com.duiya.dao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.duiya.util.SetMessageUtil;

public class ManyOptionDao {
	private int count;
	private List<String> list;
	/**
	 * 多个加密
	 * @param sourceUrl
	 * @param goalUrl
	 * @return
	 */
	public ManyOptionDao(){
		list = new ArrayList<String>();
	}
	public int encrypt(String sourceUrl, String goalUrl){
		/*
		 * sourceUrl   例如C:\\Users\\asus\\Desktop\\测试文件夹
		 * goalUrl   例如 C:\\Users\\asus\\Desktop
		 */
		list = null;
		count = 0;
		list = new ArrayList<String>();
		String[] A = sourceUrl.split("\\\\");
		goalUrl += "\\" + A[A.length-1] + SetMessageUtil.getFileDefaultType();
		(new File(goalUrl)).mkdirs();
		File sFile = new File(sourceUrl);
		if(sFile.exists()){
			File[] file = sFile.listFiles();
			for(int i = 0;i<file.length;i++){
				if(file[i].isFile()){
					count++;
					list.add(file[i].getAbsolutePath());
				}
				if(file[i].isDirectory()){
					String dir1 = sourceUrl + File.separator + file[i].getName();
					String dir2 =  goalUrl + File.separator + file[i].getName();
					copyDirectiory(dir1,dir2);
				}
			}
			Object[] filePath = list.toArray();
			CountDao.initialize(count);
			EncryptThreadDao threadDao1 = new EncryptThreadDao(filePath, goalUrl, sourceUrl.length());
			EncryptThreadDao threadDao2 = new EncryptThreadDao(filePath, goalUrl, sourceUrl.length());
			EncryptThreadDao threadDao3 = new EncryptThreadDao(filePath, goalUrl, sourceUrl.length());
			threadDao1.start();
			threadDao2.start();
			threadDao3.start();
			while(CountDao.success != 3){}
			CountDao.success = 0;
			return 1;
		}else{
			return -1; //文件夹不存在
		}
	}
	/**
	 * 多个解密
	 * @param sourceUrl
	 * @param goalUrl
	 * @return
	 */
	public int decipher(String sourceUrl, String goalUrl) {
		/*
		 * sourceUrl   例如C:\\Users\\asus\\Desktop\\测试文件夹
		 * goalUrl   例如 C:\\Users\\asus\\Desktop
		 */
		count = 0;
		list = null;
		list = new ArrayList<String>();
		String[] A = sourceUrl.split("\\\\");
		goalUrl += "\\" + A[A.length-1] + "OK";
		(new File(goalUrl)).mkdirs();
		File sFile = new File(sourceUrl);
		if(sFile.exists()){
			File[] file = sFile.listFiles();
			for(int i = 0;i<file.length;i++){
				if(file[i].isFile()){
					count++;
					list.add(file[i].getAbsolutePath());
				}
				if(file[i].isDirectory()){
					String dir1 = sourceUrl + File.separator + file[i].getName();
					String dir2 =  goalUrl + File.separator + file[i].getName();
					copyDirectiory(dir1,dir2);
				}
			}
			Object[] filePath = list.toArray();
			CountDao.initialize(count);
			DecipherThreadDao threadDao1 = new DecipherThreadDao(filePath, goalUrl, sourceUrl.length());
			DecipherThreadDao threadDao2 = new DecipherThreadDao(filePath, goalUrl, sourceUrl.length());
			DecipherThreadDao threadDao3 = new DecipherThreadDao(filePath, goalUrl, sourceUrl.length());
			threadDao1.start();
			threadDao2.start();
			threadDao3.start();
			while(CountDao.success != 3){}
			CountDao.success = 0;
			return 1;
		}else{
			return -1; //文件夹不存在
		}
	}
	
	private void copyDirectiory(String sourceDir,String targetDir){
        (new File(targetDir)).mkdirs();
        File[] file=(new File(sourceDir)).listFiles();
        for (int i = 0; i < file.length; i++) {
            if(file[i].isFile()){
            	count++;
				list.add(file[i].getAbsolutePath());
            }
            if(file[i].isDirectory()){
                String dir1=sourceDir+"/"+file[i].getName();
                String dir2=targetDir+"/"+file[i].getName();
                copyDirectiory(dir1, dir2);
            }
        }
	}
}
