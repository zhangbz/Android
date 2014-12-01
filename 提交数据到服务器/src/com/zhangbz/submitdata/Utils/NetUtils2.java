package com.zhangbz.submitdata.Utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.DefaultClientConnection;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

public class NetUtils2 {
	


	private static final String TAG = "NetUtils";
	/**
	 * ʹ��post�ķ�ʽ��¼
	 * @param userName
	 * @param password
	 * @return
	 */
	public static String loginOfPost(String userName, String password){
		HttpClient client = null;
		try {
			//����һ���ͻ���
			client = new DefaultHttpClient();
			
			//����post����
			HttpPost post = new HttpPost("http://10.0.2.2:8080/serverzhangbz/servlet/LoginServlet");
			
			//����post����Ĳ���
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			parameters.add(new BasicNameValuePair("username", userName));
			parameters.add(new BasicNameValuePair("password", password));
			
			//��post����Ĳ�����װһ��
			//��д�������ƣ��������������ǻ������롣��Ҫָ���ַ���Ϊutf-8
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, "utf-8");
			//���ò���
			post.setEntity(entity);
			
			//��������ͷ��Ϣ
			//post.addHeader("Content-Length", "20");
			
			//ʹ�ÿͻ���ִ��
			HttpResponse response = client.execute(post); //��ʼ����post���󣬻᷵�ظ�����һ��HttpResponse����
			
			//ʹ����Ӧ���󣬻��״̬�룬��������
			int statusCode = response.getStatusLine().getStatusCode(); //���״̬��
			if (statusCode == 200) {
				//ʹ����Ӧ������ʵ�壬���������
				InputStream is = response.getEntity().getContent();
				String text = getStringFromInputStream(is);
				return text;
			} else {
				Log.i(TAG, "����ʧ��:" + statusCode);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(client != null) {   //�Է����ֿ�ָ���쳣
				client.getConnectionManager().shutdown();  //�ر����ӣ��ͷ���Դ
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
	public static String loginOfGet(String userName, String password) {
		
		HttpClient client = null;
		try {
			//����һ���ͻ���
			client = new DefaultHttpClient();
			
			//����һ��get���󷽷�
			String data = "username=" + userName + "&password=" + password;
			HttpGet get = new HttpGet("http://10.0.2.2:8080/serverzhangbz/servlet/LoginServlet?" + data);
			
			//response ��������Ӧ�������а�����״̬��Ϣ�ͷ��������ص����ݡ�
			HttpResponse response = client.execute(get);//��ʼִ��get�������������硣
			
			//�����Ӧ��
			int statusCode = response.getStatusLine().getStatusCode();
			
			if(statusCode == 200) {
				InputStream is = response.getEntity().getContent();
				String text = getStringFromInputStream(is);
				return text;
			} else {
				Log.i(TAG, "����ʧ��:" + statusCode);
			}
			
		    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(client != null) {
				client.getConnectionManager().shutdown();  //�ر����ӣ��ͷ���Դ
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
	private static String getStringFromInputStream(InputStream is) throws IOException{
		
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
