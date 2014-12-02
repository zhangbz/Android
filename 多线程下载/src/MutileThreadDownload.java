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
	 * 线程的数量
	 */
	private static int threadCount = 3;
	/**
	 * 每个下载区块的大小
	 */
	private static long blocksize;
	/**
	 * 正在运行的线程的数量
	 */
	private static int runningThreadCount;
	/**
	 * 
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		//服务器路径
		String path = "http://download.java.net/jdk/jdk-api-localizations/jdk-api-zh-cn/publish/1.6.0/chm/JDK_API_1_6_zh_CN.CHM";
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setReadTimeout(5000);
		int code = conn.getResponseCode();
		if(code == 200) {
			long size = conn.getContentLength();//得到服务器端返回的文件的大小
			System.out.println("服务器文件的大小：" + size);
			blocksize = size / threadCount;
			//1.首先在本地创建一个大小跟服务器一模一样的空白文件
			File file = new File("temp.CHM");
			RandomAccessFile raf = new RandomAccessFile(file, "rw");
			raf.setLength(size);
			//2.开启若干个子线程分别去下载对应的资源
			runningThreadCount = threadCount;
			for(int i = 1;i <= threadCount; i++) {
				long startIndex = (i - 1) * blocksize;
				long endIndex = i * blocksize - 1;
				if(i == threadCount) {
					//最后一个线程
					endIndex = size - 1;
				}
				System.out.println("开启线程：" + i + "下载的位置：" + startIndex + "~" + endIndex);
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
				//当前线程下载的总大小
				int total = 0;
				File positionFile = new File(threadId + ".txt");
				URL url = new URL(path);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				//接着从上一次的位置继续下载数据
				if(positionFile.exists() && positionFile.length() > 0) {  //判断是否有记录
					FileInputStream fis = new FileInputStream(positionFile);
					BufferedReader br = new BufferedReader(new InputStreamReader(fis));
					//获取当前线程上次下载的总大小是多少
					String lasttotalstr = br.readLine();
					int lastTotal = Integer.valueOf(lasttotalstr);
					System.out.println("上次线程" + threadId + "下载的总大小：" + lastTotal);
					startIndex += lastTotal;  //加上上次下载的总大小
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
				System.out.println("第" + threadId + "个线程：写文件的开始位置：" + String.valueOf(startIndex));
				
				int len = 0;
				byte[] buffer = new byte[1024 * 1024];
				while((len = is.read(buffer)) != -1) {
					RandomAccessFile rf = new RandomAccessFile(positionFile, "rwd");//"rwd"   打开以便读取和写入，对于 "rw"，还要求对文件内容的每个更新都同步写入到底层存储设备。  
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
				//只有所有线程都下载完毕后，才可以删除记录文件
				synchronized (MutileThreadDownload.class) {   //线程安全
					System.out.println("线程" + threadId + "下载完毕了");
					runningThreadCount--;
					if(runningThreadCount < 1) {
						System.out.println("所有线程都工作完毕了。删除临时记录的文件");
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
 