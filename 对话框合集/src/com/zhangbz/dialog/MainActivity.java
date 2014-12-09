package com.zhangbz.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void click1(View view) {
		//�Ի���Ĵ�����
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("���ǶԻ���");
		builder.setMessage("�Ի�����ʾ������");
		builder.setPositiveButton("ȷ��", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				Toast.makeText(getApplicationContext(), "ȷ���������", 0).show();
			}
		});
		builder.setNegativeButton("ȡ��", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				//ʲô����д��Ĭ��ʵ�־��ǹرյ��Ի���
			}
		});
		builder.setCancelable(false); //���ò��ܱ�ȡ��
		//builder.create().show();
		builder.show();
	}
	/**
	 * ��ѡ�Ի���
	 * @param view
	 */
	public void click2(View view) {
		//�Ի��򴴽���
		Builder builder = new Builder(this);
		builder.setTitle("��ѡ�������Ա�");
		final String[] items = {"��","Ů"};
		builder.setSingleChoiceItems(items, 1, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(getApplicationContext(), "�����Ա��ǣ�" + items[which], 0).show();
				dialog.dismiss();
			}
		});
		builder.show();
	}
	/**
	 * ��ѡ�Ի���
	 * @param view
	 */
	public void click3(View view) {
		//�Ի��򴴽���
		Builder builder = new Builder(this);
		builder.setTitle("��ѡ������Ե�ˮ��");
		final String[] items = {"ƻ��","��","����","�㽶"};
		final  boolean[] result = new boolean[]{true,false,true,false};
		builder.setMultiChoiceItems(items, result, new OnMultiChoiceClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which, boolean ischecked) {
				Toast.makeText(getApplicationContext(), items[which] + ischecked, 0).show();
				result[which] = ischecked;
			}
		});
		builder.setPositiveButton("�ύ", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < result.length; i++) {
					if(result[i]) {
						sb.append(items[i] + ",");
					}
				}
				Toast.makeText(getApplicationContext(), "��ѡ����" + sb.toString(), 0).show();
			}
		});
		builder.show();		
	}
	//�������Ի���
	public void click4(View view) {
		ProgressDialog pd = new ProgressDialog(this);
		pd.setTitle("����");
		pd.setMessage("���ڼ�������...���Եȡ�");
		pd.show();
	}
	//���������ĶԻ���
	public void click5(View view) {
		final ProgressDialog pd = new ProgressDialog(this);
		pd.setTitle("����");
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setMax(100);
		pd.setMessage("���ڼ�������...���Ե�");
		pd.show();
		new Thread() {
			public void run() {
				for (int i = 0; i < 100; i++) {
					try {
						Thread.sleep(40);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					pd.setProgress(i);
				}
				pd.dismiss();
			};
		}.start();
	}
}
