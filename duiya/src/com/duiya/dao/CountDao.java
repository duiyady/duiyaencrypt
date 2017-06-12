package com.duiya.dao;

public class CountDao {
	public static int count = 0;
	public static int allCount;
	public static int success;
	public CountDao(int allCount){
		CountDao.allCount = allCount;
		success = 0;
	}
	public static void initialize(int a){
		CountDao.allCount = a;
		CountDao.count = 0;
	}
	public static int getCount(){
		if(count < allCount){
			return count++;
		}else{
			return -1;
		}
	}
}
