package com.zhangbz.lifecycle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {

	//������ʱ����õķ���
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		System.out.println("onCreate");
	}

	public void click(View v) {
		Intent intent = new Intent(this, SecondActivity.class);
		startActivity(intent);
	}
	//�����ٵ�ʱ����õķ���
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.out.println("onDestory");
	}
	
	//��activity�����û��ɼ���ʱ����õķ���
	@Override
	protected void onStart() {
		System.out.println("onStart");
		super.onStart();
	}
	
	//��activity�����û����ɼ���ʱ����õķ���
	@Override
	protected void onStop() {
		System.out.println("onStop");
		super.onStop();
	}
	
	//���濪ʼ��ȡ�������Ӧ�ķ��������水ť���Ա�������ı�������������ݣ�
	@Override
	protected void onResume() {
		System.out.println("onResume");
		super.onResume();
	}
	
	//����ʧȥ�����Ӧ�ķ�������ͣ�� ����ť���ɱ�������ı��򲻿��������ݣ����ǽ����û���Ȼ�ܿ�����
	@Override
	protected void onPause() {
		System.out.println("onPause");
		super.onPause();
	}
}
