package com.zhangbz.download2;

import java.io.File;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EditText et_path;
	private TextView tv_info;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		et_path = (EditText) findViewById(R.id.et_path);
		tv_info = (TextView) findViewById(R.id.tv_info);
	}
	
	public void download(View v) {
		String path = et_path.getText().toString().trim();
		if(TextUtils.isEmpty(path)) {
			Toast.makeText(this, "请输入下载路径", 0).show();
			return;
		} else {
			HttpUtils http = new HttpUtils();
			HttpHandler handler = http.download(path,
			    "/sdcard/xxx.jpg",
			    true, // 如果目标文件存在，接着未完成的部分继续下载。服务器不支持RANGE时将从新下载。
			    true, // 如果从请求返回信息中获取到文件名，下载完成后自动重命名。
			    new RequestCallBack<File>() {

			        @Override
			        public void onStart() {
			        	tv_info.setText("conn...");
			        }

			        @Override
			        public void onLoading(long total, long current, boolean isUploading) {
			        	tv_info.setText(current + "/" + total);
			        }

			        @Override
			        public void onSuccess(ResponseInfo<File> responseInfo) {
			        	tv_info.setText("downloaded:" + responseInfo.result.getPath());
			        }


			        @Override
			        public void onFailure(HttpException error, String msg) {
			        	tv_info.setText(msg);
			        }
			});
		}
	}
}
