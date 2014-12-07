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
		System.out.println("����Զ�̷���ķ������ұ������ˡ�����");
	}
	
	//1.����һ���м���  Զ�̷���̳е���ipc��һ��ʵ����
	private class MiddlePerson extends IMiddlePerson.Stub{

		@Override
		public void callMethodInService() {
			methodInService();
		}
		
	}
}
