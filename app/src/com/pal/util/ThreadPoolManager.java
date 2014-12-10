package com.pal.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * �̳߳ع�����
 * @author Administrator
 *
 */
public class ThreadPoolManager {
	private ExecutorService service;

	private ThreadPoolManager() {
		int num = Runtime.getRuntime().availableProcessors();//�� Java ��������ؿ��ô���������Ŀ��
		service = Executors.newFixedThreadPool(num * 2);//����һ�������ù̶��߳������̳߳�
	}
	
	private static final ThreadPoolManager manager = new ThreadPoolManager();
	
	public static ThreadPoolManager getInstence() {
		return manager;
	}
	
	public void addTask(Runnable runnable) {
		service.execute(runnable);
	}
}
