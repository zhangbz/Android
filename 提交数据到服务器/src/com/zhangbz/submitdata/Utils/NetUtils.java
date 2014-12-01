package com.zhangbz.submitdata.Utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import android.util.Log;

public class NetUtils {
	


	private static final String TAG = "NetUtils";
	/**
	 * ʹ��post�ķ�ʽ��¼
	 * @param userName
	 * @param password
	 * @return
	 */
	public static String loginOfPost(String userName, String password){
		HttpURLConnection conn = null;
		try {
			URL url = new URL("http://10.0.2.2:8080/serverzhangbz/servlet/LoginServlet?");
			
			conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("POST");
			conn.setReadTimeout(10000);  //���ӵĳ�ʱʱ��
			conn.setReadTimeout(5000);   //�����ݵĳ�ʱʱ��
			conn.setDoOutput(true);//�������ô˷������������
			//conn.setRequestProperty("content-Length", 234);  //��������ͷ��Ϣ���������ö��
			
			//post����Ĳ���
			String data = "username=" + userName + "&password=" + password;
			
			//���һ��������������������д���ݣ�Ĭ������£�ϵͳ��������������������
			OutputStream out = conn.getOutputStream();
			out.write(data.getBytes());
			out.flush();
			out.close();
			
			int responseCode = conn.getResponseCode();
			if(responseCode == 200) {
				InputStream is = conn.getInputStream();
				String state = getSringFromInputStream(is);
				Log.i(TAG, state);
				return state;
			} else {
				Log.i(TAG, "����ʧ�ܣ�" + responseCode);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(conn != null) {
				conn.disconnect(); //�ر�����
			}
		}
		
		return null;
	}

	/**
	 * ʹ��get�ķ�ʽ��¼
	 * @param userName
	 * @param password
	 * @return
	 */
	public static String logOfPost(String userName, String password) {
		HttpURLConnection conn = null; //�ֲ�������ʹ��ʱ������г�ʼ��
		try {
			String data = "username=" + URLEncoder.encode(userName) + "&password=" + URLEncoder.encode(password);
			URL url = new URL("http://10.0.2.2:8080/serverzhangbz/servlet/LoginServlet?" + data);
			conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("GET");//get����post�����ȫ��д
			conn.setReadTimeout(10000);//���ӵĳ�ʱʱ��
			conn.setReadTimeout(5000);//�����ݵĳ�ʱ����
			
			int responseCode = conn.getResponseCode();
			if(responseCode == 200) {
				InputStream is = conn.getInputStream();
				String state = getSringFromInputStream(is);
				Log.i(TAG, state);
				return state;
			} else {
				Log.i(TAG, "����ʧ�ܣ�" + responseCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(conn != null) {
				conn.disconnect(); //�ر�����
			}
		}
		
		return null;
		
	}

	/**
	 * ����������һ���ַ�����Ϣ
	 * @param is
	 * @return
	 * @throws IOException
	 */
	private static String getSringFromInputStream(InputStream is) throws IOException{
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		
		while((len = is.read(buffer)) != -1){
			baos.write(buffer, 0, len);
		}
		is.close();
		String html = baos.toString(); //�����е�����ת�����ַ��������õı����ǣ�utf-8
		
		//String html = new String(baos.toByteArray(), "GBK");
		
		baos.close();
		return html;
	}
}
