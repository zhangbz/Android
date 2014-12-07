package com.zhangbz.bindremote;

import com.zhangbz.remoteservice.IMiddlePerson;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;

public class MainActivity extends Activity {

	private MyConn conn;
	private IMiddlePerson iMp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	public void bind(View v) {
		Intent intent = new Intent();
		intent.setAction("com.zhangbz.remoteservice");
		conn = new MyConn();
		bindService(intent, conn, BIND_AUTO_CREATE);
	}
	
	private class MyConn implements ServiceConnection {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			iMp = IMiddlePerson.Stub.asInterface(service);
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			
		}
		
	}
	
	public void call(View v) {
		try {
			iMp.callMethodInService();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onDestroy() {
		unbindService(conn);
		super.onDestroy();
	}
}
