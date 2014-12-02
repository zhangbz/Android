import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class MutileThreadDownload {
	/**
	 * �̵߳�����
	 */
	private static int threadCount = 3;
	/**
	 * ÿ����������Ĵ�С
	 */
	private static long blocksize;
	/**
	 * �������е��̵߳�����
	 */
	private static int runningThreadCount;
	/**
	 * 
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		//������·��
		String path = "http://download.java.net/jdk/jdk-api-localizations/jdk-api-zh-cn/publish/1.6.0/chm/JDK_API_1_6_zh_CN.CHM";
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setReadTimeout(5000);
		int code = conn.getResponseCode();
		if(code == 200) {
			long size = conn.getContentLength();//�õ��������˷��ص��ļ��Ĵ�С
			System.out.println("�������ļ��Ĵ�С��" + size);
			blocksize = size / threadCount;
			//1.�����ڱ��ش���һ����С��������һģһ���Ŀհ��ļ�
			File file = new File("temp.CHM");
			RandomAccessFile raf = new RandomAccessFile(file, "rw");
			raf.setLength(size);
			//2.�������ɸ����̷ֱ߳�ȥ���ض�Ӧ����Դ
			runningThreadCount = threadCount;
			for(int i = 1;i <= threadCount; i++) {
				long startIndex = (i - 1) * blocksize;
				long endIndex = i * blocksize - 1;
				if(i == threadCount) {
					//���һ���߳�
					endIndex = size - 1;
				}
				System.out.println("�����̣߳�" + i + "���ص�λ�ã�" + startIndex + "~" + endIndex);
				new DownloadThread(i, startIndex, endIndex, path).start();
			}
		}
		conn.disconnect();
	}
	
	private static class DownloadThread extends Thread {
		private int threadId;
		private long startIndex;
		private long endIndex;
		private String path;
		
		public DownloadThread(int threadId, long startIndex, long endIndex,
				String path) {
			super();
			this.threadId = threadId;
			this.startIndex = startIndex;
			this.endIndex = endIndex;
			this.path = path;
		}
		
		public void run() {
			try {
				//��ǰ�߳����ص��ܴ�С
				int total = 0;
				File positionFile = new File(threadId + ".txt");
				URL url = new URL(path);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				//���Ŵ���һ�ε�λ�ü�����������
				if(positionFile.exists() && positionFile.length() > 0) {  //�ж��Ƿ��м�¼
					FileInputStream fis = new FileInputStream(positionFile);
					BufferedReader br = new BufferedReader(new InputStreamReader(fis));
					//��ȡ��ǰ�߳��ϴ����ص��ܴ�С�Ƕ���
					String lasttotalstr = br.readLine();
					int lastTotal = Integer.valueOf(lasttotalstr);
					System.out.println("�ϴ��߳�" + threadId + "���ص��ܴ�С��" + lastTotal);
					startIndex += lastTotal;  //�����ϴ����ص��ܴ�С
					fis.close();
				}
				
				conn.setRequestProperty("Range", "bytes=" + startIndex + "-" +endIndex);
				conn.setConnectTimeout(5000);
				int code = conn.getResponseCode();
				System.out.println("code=" + code);
				InputStream is = conn.getInputStream();
				File file = new File("temp.CHM");
				RandomAccessFile raf = new RandomAccessFile(file, "rw");
				raf.seek(startIndex);
				System.out.println("��" + threadId + "���̣߳�д�ļ��Ŀ�ʼλ�ã�" + String.valueOf(startIndex));
				
				int len = 0;
				byte[] buffer = new byte[1024 * 1024];
				while((len = is.read(buffer)) != -1) {
					RandomAccessFile rf = new RandomAccessFile(positionFile, "rwd");//"rwd"   ���Ա��ȡ��д�룬���� "rw"����Ҫ����ļ����ݵ�ÿ�����¶�ͬ��д�뵽�ײ�洢�豸��  
					raf.write(buffer, 0, len);
					total += len;
					rf.write(String.valueOf(total).getBytes());
					rf.close();
				}
				is.close();
				raf.close();
				//conn.disconnect();
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				//ֻ�������̶߳�������Ϻ󣬲ſ���ɾ����¼�ļ�
				synchronized (MutileThreadDownload.class) {   //�̰߳�ȫ
					System.out.println("�߳�" + threadId + "���������");
					runningThreadCount--;
					if(runningThreadCount < 1) {
						System.out.println("�����̶߳���������ˡ�ɾ����ʱ��¼���ļ�");
						for (int i = 1; i <= threadCount; i++) {
							File f = new File(i + ".txt");
							System.out.println(f.delete());
						}
					}
				}
			}
		}
	}
}
 