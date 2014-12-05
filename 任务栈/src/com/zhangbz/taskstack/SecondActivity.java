package com.zhangbz.taskstack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class SecondActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		System.out.println("02Activity开启了。任务栈id：" + getTaskId());
	}

	public void open01(View v) {
		Intent intent = new Intent(this,MainActivity.class);
		startActivity(intent);
	}

	public void open02(View v) {
		Intent intent = new Intent(this,SecondActivity.class);
		startActivity(intent);
	}
	//屏蔽掉返回键，桌面的实现
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed(); //finish();
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		System.out.println("02ActivityIntent。任务栈id：" + getTaskId());
		super.onNewIntent(intent);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.second, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
