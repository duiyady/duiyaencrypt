package com.duiya.dao;

import com.duiya.util.SetMessageUtil;

public class EncryptThreadDao extends Thread{
	private ImportantDao importantDao;
	private Object[] filePathArray;
	private String goalRootPath;
	private int sourcePathLength;
	private String sourceUrl;
	private String goalUrl;
	private String swapUrl;
	private boolean flag;
	
	private int index;
	public EncryptThreadDao(Object[] filePathArray,String goalRootPath,int sourcePathLength){
		this.filePathArray = filePathArray;
		this.goalRootPath = goalRootPath;
		this.sourcePathLength = sourcePathLength;
		importantDao = new ImportantDao();
		flag = true;
	}
	 @SuppressWarnings("deprecation")
	public synchronized  void run(){
		 while(flag == true){
			 index = CountDao.getCount();
			 if(index != -1){
				 sourceUrl = filePathArray[index].toString();
				 swapUrl = goalRootPath + sourceUrl.substring(sourcePathLength, sourceUrl.length());
				 String[] swapU = swapUrl.split("\\.");
				 int len = swapU[swapU.length-1].length();
				 goalUrl = swapUrl.substring(0, swapUrl.length()-len-1) + "." + SetMessageUtil.getFileDefaultType();
				 importantDao.encrypt(sourceUrl, goalUrl);
			 }else{
				 flag = false;
				 CountDao.success++;
				 this.stop();
			 }
		 }
	 }
}
