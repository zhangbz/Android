package com.zhangbz.smshelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListSmsActivity extends Activity {

	private ListView lv;
	private String[] objects = {"sdfsfafafasf","sdffdfafafafasfsa" ,"sdfhjkfhlkjfhfjflfhjla", "sdfja;lkfjfkajf;afjsd;fjasdf;sadfjklsad"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sms);
		
		lv = (ListView) findViewById(R.id.lv);
		lv.setAdapter(new ArrayAdapter<String>(this, R.layout.sms_item, R.id.tv_info, objects));
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				String smsinfo = objects[position];
				Intent data = new Intent();
				data.putExtra("smsinfo", smsinfo);
				//��������
				setResult(0, data);
				//�رյ���ǰ��activity,���һش�����onActivityResult()
				finish();
			}
		});
	}
}
