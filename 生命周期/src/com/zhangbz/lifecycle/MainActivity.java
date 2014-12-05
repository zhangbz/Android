package com.zhangbz.lifecycle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {

	//被创建时候调用的方法
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
	//被销毁的时候调用的方法
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.out.println("onDestory");
	}
	
	//当activity界面用户可见的时候调用的方法
	@Override
	protected void onStart() {
		System.out.println("onStart");
		super.onStart();
	}
	
	//当activity界面用户不可见的时候调用的方法
	@Override
	protected void onStop() {
		System.out.println("onStop");
		super.onStop();
	}
	
	//界面开始获取到焦点对应的方法（界面按钮可以被点击，文本框可以输入内容）
	@Override
	protected void onResume() {
		System.out.println("onResume");
		super.onResume();
	}
	
	//界面失去焦点对应的方法（暂停） （按钮不可被点击，文本框不可输入内容，但是界面用户能然能看见）
	@Override
	protected void onPause() {
		System.out.println("onPause");
		super.onPause();
	}
}
