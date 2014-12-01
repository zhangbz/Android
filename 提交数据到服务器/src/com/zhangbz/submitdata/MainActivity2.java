package com.zhangbz.submitdata;

import java.net.URLEncoder;

import org.apache.http.Header;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zhangbz.submitdata.Utils.NetUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity2 extends Activity {

	private EditText etUserName;
	private EditText etPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		etUserName = (EditText) findViewById(R.id.et_username);
		etPassword = (EditText) findViewById(R.id.et_password);
	}
	
	public void doGet(View v){
		final String userName = etUserName.getText().toString();  //由于需要被内部类访问，所以要用final修饰
		final String password = etPassword.getText().toString();
		
		 AsyncHttpClient client = new AsyncHttpClient();

		 String data = "username=" + URLEncoder.encode(userName) + "&password=" + URLEncoder.encode(password);
		 
	        client.get("http://10.0.2.2:8080/serverzhangbz/servlet/LoginServlet?" + data, new MyResponseHandler());
	}
	
	public void doPost(View v) {
		final String userName = etUserName.getText().toString();  //由于需要被内部类访问，所以要用final修饰
		final String password = etPassword.getText().toString();
		
		AsyncHttpClient client = new AsyncHttpClient();
		
		RequestParams params = new RequestParams();
		params.put("username", userName);
		params.put("password", password);
		
		client.post("http://10.0.2.2:8080/serverzhangbz/servlet/LoginServlet",   //结尾没有问号
				params,
				new MyResponseHandler());
	}
	
	class MyResponseHandler extends AsyncHttpResponseHandler {


		@Override
		public void onSuccess(int statusCode, Header[] headers,
				byte[] responseBody) {
			Toast.makeText(MainActivity2.this, "成功：suatusCode:" + statusCode + ", body:" + new String(responseBody), 0).show();
		}

		@Override
		public void onFailure(int statusCode, Header[] headers,
				byte[] responseBody, Throwable error) {
			Toast.makeText(MainActivity2.this, "失败：statusCode:" + statusCode, 0).show();
		}
    }

}
