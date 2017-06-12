package com.duiya.dao;


public class DecipherThreadDao extends Thread{
	private ImportantDao importantDao;
	private Object[] filePathArray;
	private String goalRootPath;
	private int sourcePathLength;
	private String sourceUrl;
	private String goalUrl;
	private String swapUrl;
	private String[] swapUrls;
	private boolean flag;
	
	private int index;
	public DecipherThreadDao(Object[] filePathArray,String goalRootPath,int sourcePathLength){
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
				 swapUrls = swapUrl.split("\\\\");
				 goalUrl = new String();
				 for(int i = 0;i<swapUrls.length-1;i++){
					 goalUrl += swapUrls[i] + "\\";
				 }
				 goalUrl = goalUrl.substring(0, goalUrl.length()-1);
				 importantDao.decipher(sourceUrl, goalUrl);
			 }else{
				 flag = false;
				 CountDao.success++;
				 this.stop();
			 }
		 }
	 }
}
