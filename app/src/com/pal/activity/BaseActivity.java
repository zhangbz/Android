package com.pal.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;


import com.pal.app.R;
import com.pal.util.CommonUtil;
import com.pal.util.Constant;
import com.pal.util.NetUtil;
import com.pal.util.ThreadPoolManager;
import com.pal.vo.RequestVo;

public abstract class BaseActivity extends Activity implements View.OnClickListener{

	
	private static final Integer DEFAULT_INDEX = 0;
	private ThreadPoolManager threadPoolManager;
	private Context context;
	private ImageView home;
	private ImageView classify;
	private ImageView search;
	private ImageView shopCar;
	private ImageView more;
	private TextView textShopCarNum;
	protected ProgressDialog progressDialog;
	
	public BaseActivity() {
		threadPoolManager = ThreadPoolManager.getInstence();
		
	}
	/**
	 * Android生命周期回调方法-创建
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); //无标题
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN); //设置窗体全屏
		context = getApplicationContext();
		initView();
	}
	
	/**
	 * 初始化
	 */
	private void initView() {
		loadViewLayout();
		if(isLoadBottomTab()) {
			loadBottomTab();
			selectedBottomTab(DEFAULT_INDEX);
		}
		findViewById();
		setListener();
		processLogic();
	}
	
	protected abstract void loadViewLayout();
	/**
	 * 是否加载底部的tab
	 * @return
	 */
	protected Boolean isLoadBottomTab() {
		return true;
	}
	
	private void loadBottomTab() {
		ImageView localImageView1 = (ImageView) findViewById(R.id.imgHome);
		home = localImageView1;
		ImageView localImageView2 = (ImageView) findViewById(R.id.imgClassify);
		classify = localImageView2;
		ImageView localImageView3 = (ImageView) findViewById(R.id.imgSearch);
		search = localImageView3;
		ImageView localImageView4 = (ImageView) findViewById(R.id.imgShoppingCar);
		shopCar = localImageView4;
		ImageView localImageView5 = (ImageView) findViewById(R.id.imgMore);
		more = localImageView5;
		textShopCarNum = (TextView) findViewById(R.id.textShopCarNum);
		home.setOnClickListener(this);
		classify.setOnClickListener(this);
		search.setOnClickListener(this);
		shopCar.setOnClickListener(this);
		more.setOnClickListener(this);
	}
	
	/**
	 * tab条图片切换
	 * 
	 * @param paramViewId
	 */
	protected void selectedBottomTab(int paramViewId) {
		home.setBackgroundResource(R.drawable.bar_home_normal);
		classify.setBackgroundResource(R.drawable.bar_class_normal);
		search.setBackgroundResource(R.drawable.bar_search_normal);
		shopCar.setBackgroundResource(R.drawable.bar_shopping_normal);
		more.setBackgroundResource(R.drawable.bar_more_normal);
		switch (paramViewId) {
		case Constant.HOME:
			home.setBackgroundResource(R.drawable.bar_home_selected);
			Constant.defaultIndex = Constant.HOME;
			break;
		case Constant.CLASSIFY:
			classify.setBackgroundResource(R.drawable.bar_class_selected);
			Constant.defaultIndex = Constant.CLASSIFY;
			break;
		case Constant.SEARCH:
			search.setBackgroundResource(R.drawable.bar_search_selected);
			Constant.defaultIndex = Constant.SEARCH;
			break;
		case Constant.SHOPCAR:
			shopCar
					.setBackgroundResource(R.drawable.bar_shopping_selected);
			Constant.defaultIndex = Constant.SHOPCAR;
			break;
		case Constant.MORE:
			more.setBackgroundResource(R.drawable.bar_more_selected);
			Constant.defaultIndex = Constant.MORE;
			break;
		}
	}
	
	/**
	 * 
	 * @author Mathew
	 *
	 */
	@SuppressWarnings("unchecked")
	class BaseHandler extends Handler{
		private Context context;
		private DataCallback callBack;
		private RequestVo reqVo;

		public BaseHandler(Context context, DataCallback callBack,
				RequestVo reqVo) {
			this.context = context;
			this.callBack = callBack;
			this.reqVo = reqVo;
		}
		
		public void handleMessage(Message msg){ 
			closeProgressDialog();
			if(msg.what==Constant.SUCCESS){
				if(msg.obj==null){
					CommonUtil.showInfoDialog(context, getString(R.string.net_error));
				}else{
					callBack.processData(msg.obj, true);
				}
			}else if(msg.what==Constant.NET_FAILED){
				CommonUtil.showInfoDialog(context, getString(R.string.net_error));
			}
		}
	}
	
	
	class BaseTask implements Runnable{
		private Context context;
		private RequestVo reqVo;
		private Handler handler;

		public BaseTask(Context context, RequestVo reqVo, Handler handler) {
			this.context = context;
			this.reqVo = reqVo;
			this.handler = handler;
		}
		
		@Override
		public void run() {
			Object obj = null;
			Message msg = new Message();
			if(NetUtil.hasNetwork(context)){
				obj = NetUtil.post(reqVo);
				msg.what = Constant.SUCCESS;
				msg.obj = obj;
				handler.sendMessage(msg);
			}else{
				msg.what = Constant.NET_FAILED;
				msg.obj = obj;
				handler.sendMessage(msg);
			}
		}
		
	}
	
	public abstract interface DataCallback<T> {
		public abstract void processData(T paramObject,
				boolean paramBoolean);
	}
	
	
	/**
	 * 从服务器上获取数据，并回调处理
	 * @param reqVo
	 * @param callBack
	 */
	protected void getDataFromServer(RequestVo reqVo, DataCallback callBack) {
		showProgressDialog();
		BaseHandler handler = new BaseHandler(this, callBack, reqVo);
		BaseTask taskThread = new BaseTask(this, reqVo, handler);
		this.threadPoolManager.addTask(taskThread);
	}
	
	/**
	 * 显示提示框
	 */
	protected void showProgressDialog() {
		if ((!isFinishing()) && (this.progressDialog == null)) {
			this.progressDialog = new ProgressDialog(this);
		}
		this.progressDialog.setTitle(getString(R.string.loadTitle));
		this.progressDialog.setMessage(getString(R.string.LoadContent));
		this.progressDialog.show();
	}
	
	/**
	 * 关闭提示框
	 */
	protected void closeProgressDialog() {
		if (this.progressDialog != null)
			this.progressDialog.dismiss();
	}
	protected abstract void findViewById();
	
	protected abstract void setListener();
	
	protected abstract void processLogic();
	
	

}
