package pigframe.download;


import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;


import android.util.Log;

public class DownloadThread extends Thread {
	private static final String TAG = "DownloadThread";
	private static final int BUFFER_SIZE=1024; 
	private File saveFile;
	private URL downUrl;
	private int block;
	/* ���ؿ�ʼλ��  */
	private int threadId = -1;	
	private int downLength;
	private boolean finish = false;
	private Downloader downloader;

	public DownloadThread(Downloader downloader, URL downUrl, File saveFile, int block, int downLength, int threadId) {
		this.downloader = downloader;
		this.downUrl = downUrl;
		this.saveFile = saveFile;
		this.block = block;
		this.downLength = downLength;
		this.threadId = threadId;
	}
	
	@Override
	public void run() {
		if(downLength < block){//δ�������
			try {
				HttpURLConnection http = (HttpURLConnection) downUrl.openConnection();
				http.setConnectTimeout(5 * 1000);
				http.setRequestMethod("GET");
				http.setRequestProperty("Accept", "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
				http.setRequestProperty("Accept-Language", "zh-CN");
				http.setRequestProperty("Referer", downUrl.toString()); 
				http.setRequestProperty("Charset", "UTF-8");
				int startPos = block * (threadId - 1) + downLength;//��ʼλ��
				
				int endPos = block * threadId -1;//����λ��
				//by ngj
				if(threadId==downloader.getThreadsNum()){//���һ���̲߳�Ҫ-1
					endPos+=1;
				}
				http.setRequestProperty("Range", "bytes=" + startPos + "-"+ endPos);//���û�ȡʵ����ݵķ�Χ
				http.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
				http.setRequestProperty("Connection", "Keep-Alive");
				
				InputStream is = http.getInputStream();
				byte[] buffer = new byte[BUFFER_SIZE];
				int offset = 0;
				print("Thread " + this.threadId + " start download from position "+ startPos+" end at "+endPos);
				RandomAccessFile raf = new RandomAccessFile(this.saveFile, "rwd");
				raf.seek(startPos);
				while ((offset = is.read(buffer, 0, BUFFER_SIZE)) != -1) {
					raf.write(buffer, 0, offset);
					downLength += offset;
					downloader.update(this.threadId, downLength);
					downloader.saveLogFile();
					downloader.append(offset);
				}
				raf.close();
				is.close();
				http.disconnect();
				print("Thread " + this.threadId + " download finish");
				this.finish = true;
			} catch (Exception e) {
				this.downLength = -1;
				print("Thread "+ this.threadId+ ":"+ e);
			}
		}
	}
	private static void print(String msg){
		Log.i(TAG, msg);
	}
	/**
	 * �����Ƿ����
	 * @return
	 */
	public boolean isFinish() {
		return finish;
	}
	/**
	 * �Ѿ����ص����ݴ�С
	 * @return ����ֵΪ-1,�������ʧ��
	 */
	public long getDownLength() {
		return downLength;
	}
}
