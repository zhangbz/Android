package com.zhangbz.remoteservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class RemoteService extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		return new MiddlePerson();
	}

	@Override
	public void onCreate() {
		System.out.println("oncreate");
		super.onCreate();
	}
	
	@Override
	public void onDestroy() {
		System.out.println("ondestroy");
		super.onDestroy();
	}
	
	private void methodInService() {
		System.out.println("我是远程服务的方法，我被调用了。。。");
	}
	
	//1.创建一个中间人  远程服务继承的是ipc的一个实现类
	private class MiddlePerson extends IMiddlePerson.Stub{

		@Override
		public void callMethodInService() {
			methodInService();
		}
		
	}
}
