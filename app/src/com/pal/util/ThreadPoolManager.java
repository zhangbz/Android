package com.pal.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池管理者
 * @author Administrator
 *
 */
public class ThreadPoolManager {
	private ExecutorService service;

	private ThreadPoolManager() {
		int num = Runtime.getRuntime().availableProcessors();//向 Java 虚拟机返回可用处理器的数目。
		service = Executors.newFixedThreadPool(num * 2);//创建一个可重用固定线程数的线程池
	}
	
	private static final ThreadPoolManager manager = new ThreadPoolManager();
	
	public static ThreadPoolManager getInstence() {
		return manager;
	}
	
	public void addTask(Runnable runnable) {
		service.execute(runnable);
	}
}
