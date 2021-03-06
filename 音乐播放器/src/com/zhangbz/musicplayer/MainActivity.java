package com.zhangbz.musicplayer;

import java.io.File;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EditText et_path;
	private MediaPlayer mediaPlayer;
	private Button bt_play, bt_pause, bt_stop, bt_replay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		et_path = (EditText) findViewById(R.id.et_path);
		bt_play = (Button) findViewById(R.id.bt_play);
		bt_pause = (Button) findViewById(R.id.bt_pause);
	    bt_replay = (Button) findViewById(R.id.bt_replay);
	    bt_stop = (Button) findViewById(R.id.bt_stop);
	}
	/**
	 * 播放
	 * @param view
	 */
	public void play(View view) {
		String filepath = et_path.getText().toString().trim();
		if(filepath.startsWith("http://")) {
			try {
				mediaPlayer = new MediaPlayer();
				mediaPlayer.setDataSource(filepath);//设置播放的数据源
				mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
				//mediaPlayer.prepare();//准备开始播放,播放的逻辑是c代码在新的线程执行。//同步的准备方法
				mediaPlayer.prepareAsync();//异步的准备
				mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
					
					@Override
					public void onPrepared(MediaPlayer arg0) {
						mediaPlayer.start();
						bt_play.setEnabled(false);
					}
				});
				mediaPlayer.start();
				bt_play.setEnabled(false);
				mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
					 
					public void onCompletion(MediaPlayer arg0) {
						bt_play.setEnabled(true);
					}
				});
			} catch (Exception e) {
				Toast.makeText(this, "播放失败", 0).show();
				e.printStackTrace();
			}
		} else {
			Toast.makeText(this, "文件不存在，请检查文件路径", 0).show();
		}
	}
	/**
	 * 暂停
	 * @param view
	 */
	public void pause(View view) {
		if("继续".equals(bt_pause.getText().toString().trim())) {
			mediaPlayer.start();
			bt_pause.setText("暂停");
			return;
		}
		if(mediaPlayer != null && mediaPlayer.isPlaying()) {
			mediaPlayer.pause();
			bt_pause.setText("继续");
		}
	}
	/**
	 * 停止
	 * @param view
	 */
	public void stop(View view) {
		if(mediaPlayer != null && mediaPlayer.isPlaying()) {
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = null;
		}
		bt_pause.setText("暂停");
		bt_play.setEnabled(true);
	}
	/**
	 * 重播
	 * @param view
	 */
	public void replay(View view) {
		if(mediaPlayer != null && mediaPlayer.isPlaying()) {
			mediaPlayer.seekTo(0);
		} else {
			play(view);
		}
		bt_replay.setText("暂停");
	}
}
