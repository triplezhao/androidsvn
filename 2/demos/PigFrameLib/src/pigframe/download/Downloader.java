package pigframe.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.util.EncodingUtils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
/**
 * �ļ�������
 * @author lihuoming@sohu.com
 */
public class Downloader {
	private static final String TAG = "FileDownloader";
	private Context context;
	private FileService fileService;	
	/* 下载进度 */
	private int downloadSize = 0;
	/* 文件长度 */
	private int fileSize = 0;
	/* 线程数 */
	private DownloadThread[] threads;
	/*保存的文件 */
	private File saveFile;
	/* 内存中的数据*/
	private Map<Integer, Integer> data = new ConcurrentHashMap<Integer, Integer>();
	/* 数据长度*/
	private int block;
	/* 下载url */
	private String downloadUrl;
	protected boolean isFinish=false;
	/**
	 *线程数
	 */
	public int getThreadSize() {
		return threads.length;
	}
	/**
	 * 获取文件长度
	 * @return
	 */
	public int getFileSize() {
		return fileSize;
	}
	/**
	 * 
	 * @param size
	 */
	protected synchronized void append(int size) {
		downloadSize += size;
	}
	/**
	 * 
	 * @param threadId �߳�id
	 * @param pos ������ص�λ��
	 */
	protected void update(int threadId, int pos) {
		this.data.put(threadId, pos);
	}
	/**
	 * �����¼�ļ�
	 */
	protected synchronized void saveLogFile() {
		this.fileService.update(this.downloadUrl, this.data);
	}
	/**
	 * �����ļ�������
	 * @param downloadUrl ����·��
	 * @param fileSaveDir �ļ�����Ŀ¼
	 * @param threadNum �����߳���
	 */
	public Downloader(Context context, String downloadUrl, File dir, int threadNum,String newfilename) {
		try {
			//得到当前外部存储设备的目录( /SDCARD )  
	        SDPATH = Environment.getExternalStorageDirectory() + "/";  
			print("downloadUrl="+downloadUrl);
			print("dir="+dir);
			this.context = context;
			this.downloadUrl = downloadUrl;
			fileService = new FileService(context);
			URL url =getConnFromURL(downloadUrl);  
			if(!dir.exists()) dir.mkdirs();
			this.threads = new DownloadThread[threadNum];					
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//			conn.setConnectTimeout(5*1000);
//			conn.setRequestMethod("GET");
//			conn.setRequestProperty("Accept", "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
//			conn.setRequestProperty("Accept-Language", "zh-CN");
//			conn.setRequestProperty("Referer", downloadUrl); 
//			conn.setRequestProperty("Charset", "UTF-8");
//			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
//			conn.setRequestProperty("Connection", "Keep-Alive");
//			conn.connect();
//			printResponseHeader(conn);
//			if (conn.getResponseCode()==200) {
				this.fileSize = conn.getContentLength();//�����Ӧ��ȡ�ļ���С
				print("fileSize="+fileSize);
//			}
//			else{
//				throw new RuntimeException("server no response ");
//			}
//			conn.disconnect();
			print("fileSize="+fileSize);
			if (this.fileSize <= 0) throw new RuntimeException("Unkown file size ");
			
//			String filename = getFileName(conn);
//			String filename = this.downloadUrl.substring(this.downloadUrl.lastIndexOf('/') + 1);
//			print("filename="+filename);
//			this.saveFile = new File(dir, filename);/* �����ļ� */
			this.saveFile = new File(dir, newfilename);/* �����ļ� */
			Map<Integer, Integer> logdata = fileService.getData(downloadUrl);//��ȡÿ���߳��Ѿ����ص��ļ�����
			print("logdata.size="+logdata.size());
			if(logdata==null||logdata.size()==0){
				for(int i=0;i<threadNum;i++){
					data.put(i+1, 0);
				}
			}else{
				for(Map.Entry<Integer, Integer> entry : logdata.entrySet())
					data.put(entry.getKey(), entry.getValue());
			}
			this.block = (this.fileSize % this.threads.length)==0? this.fileSize / this.threads.length : this.fileSize / this.threads.length + 1;
			print("block="+block);
			if(this.data.size()==this.threads.length){
				for (int i = 0; i < this.threads.length; i++) {
					this.downloadSize += this.data.get(i+1);
				}
				print("�Ѿ����صĳ���"+ this.downloadSize);
			}	
			
		} catch (Exception e) {
			print(e.toString());
			throw new RuntimeException("don't connection this url");
		}
	}
	/**
	 * ��ȡ�ļ���
	 */
	private String getFileName(HttpURLConnection conn) {
		String filename = this.downloadUrl.substring(this.downloadUrl.lastIndexOf('/') + 1);
		if(filename==null || "".equals(filename.trim())){//����ȡ�����ļ����
			for (int i = 0;; i++) {
				String mine = conn.getHeaderField(i);
				if (mine == null) break;
				if("content-disposition".equals(conn.getHeaderFieldKey(i).toLowerCase())){
					Matcher m = Pattern.compile(".*filename=(.*)").matcher(mine.toLowerCase());
					if(m.find()) return m.group(1);
				}
			}
			filename = UUID.randomUUID()+ ".tmp";//Ĭ��ȡһ���ļ���
		}
		return filename;
	}
	
	public int download(DownloadProgressListener listener) throws Exception{
		try {
//			RandomAccessFile randOut = new RandomAccessFile(this.saveFile, "rw");
//			if(this.fileSize>0) randOut.setLength(this.fileSize);
//			randOut.close();
			URL url = new URL(this.downloadUrl);
			if(this.data.size() != this.threads.length){
				this.data.clear();
				for (int i = 0; i < this.threads.length; i++) {
					this.data.put(i+1, 0);
				}
			}
			for (int i = 0; i < this.threads.length; i++) {
				int downLength = this.data.get(i+1);
				if(downLength < this.block && this.downloadSize<this.fileSize){ //���߳�δ�������ʱ,��������						
					this.threads[i] = new DownloadThread(this, url, this.saveFile, this.block, this.data.get(i+1), i+1);
					this.threads[i].setPriority(7);
					this.threads[i].start();
				}else{
					this.threads[i] = null;
				}
			}
			this.fileService.save(this.downloadUrl, this.data);
			boolean isFinish = true;//����δ���
			while (isFinish) {// ѭ���ж��Ƿ��������
				Thread.sleep(900);
				isFinish = false;//�ٶ��������
				for (int i = 0; i < this.threads.length; i++){
					if (this.threads[i] != null && !this.threads[i].isFinish()) {
						isFinish = true;//����û�����
						if(this.threads[i].getDownLength() == -1){//�������ʧ��,����������
							this.threads[i] = new DownloadThread(this, url, this.saveFile, this.block, this.data.get(i+1), i+1);
							this.threads[i].setPriority(7);
							this.threads[i].start();
						}
					}
				}				
				if(listener!=null) listener.onDownloadSize(this.downloadSize);
			}
			fileService.delete(this.downloadUrl);
		} catch (Exception e) {
			print(e.toString());
			throw new Exception("file download fail");
		}
		return this.downloadSize;
	}
	public File getSaveFile() {
		return saveFile;
	}
	public void setSaveFile(File saveFile) {
		this.saveFile = saveFile;
	}
	/**
	 * ��ȡHttp��Ӧͷ�ֶ�
	 * @param http
	 * @return
	 */
	public static Map<String, String> getHttpResponseHeader(HttpURLConnection http) {
		Map<String, String> header = new LinkedHashMap<String, String>();
		for (int i = 0;; i++) {
			String mine = http.getHeaderField(i);
			if (mine == null) break;
			header.put(http.getHeaderFieldKey(i), mine);
		}
		return header;
	}
	/**
	 * ��ӡHttpͷ�ֶ�
	 * @param http
	 */
	public static void printResponseHeader(HttpURLConnection http){
		Map<String, String> header = getHttpResponseHeader(http);
		for(Map.Entry<String, String> entry : header.entrySet()){
			String key = entry.getKey()!=null ? entry.getKey()+ ":" : "";
			print(key+ entry.getValue());
		}
	}

	private static void print(String msg){
		Log.i(TAG, msg);
	}
	public int getThreadsNum(){
		return threads.length;
	}
	
	public static void main(String[] args) {
	/*	FileDownloader loader = new FileDownloader(context, "http://browse.babasport.com/ejb3/ActivePort.exe",
				new File("D:\\androidsoft\\test"), 2);
		loader.getFileSize();//�õ��ļ��ܴ�С
		try {
			loader.download(new DownloadProgressListener(){
				public void onDownloadSize(int size) {
					print("�Ѿ����أ�"+ size);
				}			
			});
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

	
	   /**  
     *   
     * @param urlStr  
     * @param path  
     * @param fileName  
     * @return   
     *      -1:文件下载出错  
     *       0:文件下载成功  
     *       1:文件已经存在  
     */  
	public File getCnpkmDir(){
		File  dir=new File( Environment.getExternalStorageDirectory().getPath()+"/"+"cnpkm");;//
		return dir;
	}
	
	
	
	
	static Lock lock = new ReentrantLock();// 锁 
    public int downFile(final DownloadProgressListener listener){  
    	isFinish=false;
        InputStream inputStream = null;  
        OutputStream output = null;  
        try {  
              
            if(isFileExist( this.saveFile.getPath())){  
                return 1;  
            } else {  
                    inputStream = getInputStreamFromURL(downloadUrl);  
                    output = new FileOutputStream(saveFile);  
                    byte[] buffer = new byte[FILESIZE];  
                    
                    new Thread(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							do{
								try {
//									lock.lock();
									if(listener!=null&&downloadSize<fileSize) 
										listener.onDownloadSize(downloadSize);
									
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
//								lock.unlock();
							}while((!isFinish));
							
						}
					}).start();
                    int offset = 0;
                    while ((offset = inputStream.read(buffer, 0, FILESIZE)) != -1) {
                    	
                    	output.write(buffer, 0, offset);
                    	append(offset);
                    	if(isFinish){
                    		return 1;
                    	}
                    }  
                    isFinish=true;
                    if(listener!=null) listener.onDownloadSize(fileSize);
                    output.flush();  
                }   
        }   
        catch (Exception e) {  
            e.printStackTrace();  
            return -1;  
        }  
        finally{  
            try {  
                inputStream.close();  
                output.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return 0;  
    }  
      
    /**  
     * 根据URL得到输入流  
     * @param urlStr  
     * @return  
     */  
    public InputStream getInputStreamFromURL(String urlStr) {  
        HttpURLConnection urlConn = null;  
        InputStream inputStream = null;  
        try {  
//        	URL url = new URL(URLEncoder.encode(urlStr,"UTF-8")); 
        	URL url =getConnFromURL(urlStr);  
        	urlConn = (HttpURLConnection)url.openConnection();  
            inputStream = urlConn.getInputStream();  
              
        } catch (MalformedURLException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
          
        return inputStream;  
    } 
    private String SDPATH;  
    
    private int FILESIZE =  1024;   
      
    public String getSDPATH(){  
        return SDPATH;  
    }  
      
        
      
    /**  
     * 在SD卡上创建文件  
     * @param fileName  
     * @return  
     * @throws IOException  
     */  
    public File createSDFile(String fileName) throws IOException{  
        File file = new File(SDPATH + fileName);  
        file.createNewFile();  
        return file;  
    }  
      
    /**  
     * 在SD卡上创建目录  
     * @param dirName  
     * @return  
     */  
    public File createSDDir(String dirName){  
        File dir = new File(SDPATH + dirName);  
        dir.mkdir();  
        return dir;  
    }  
      
    /**  
     * 判断SD卡上的文件夹是否存在  
     * @param fileName  
     * @return  
     */  
    public boolean isFileExist(String fileName){  
        File file = new File(SDPATH + fileName);  
        return file.exists();  
    }  
      
    /**  
     * 将一个InputStream里面的数据写入到SD卡中  
     * @param path  
     * @param fileName  
     * @param input  
     * @return  
     */  
    public File write2SDFromInput(String path,String fileName,InputStream input){  
        File file = null;  
        OutputStream output = null;  
        try {  
            createSDDir(path);  
            file = createSDFile(path + fileName);  
            output = new FileOutputStream(file);  
            byte[] buffer = new byte[FILESIZE];  
            while((input.read(buffer)) != -1){  
                output.write(buffer);  
            }  
            output.flush();  
        }   
        catch (Exception e) {  
            e.printStackTrace();  
        }  
        finally{  
            try {  
                output.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return file;  
    }  
    public URL getConnFromURL(String urlStr) {  
//    	URL url = new URL(urlStr);  
		String filename = urlStr.substring(urlStr.lastIndexOf('/') + 1);
		String preurl = urlStr.substring( 0,urlStr.lastIndexOf('/')+1);
		URL url=null;
		try {
			url = new URL(preurl+URLEncoder.encode(filename,"UTF-8"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
//		urlConn = (HttpURLConnection)url.openConnection();  
//		inputStream = urlConn.getInputStream();  
	
	return url;  
} 
    
    public void deleteFile(File file){
    	if(file.exists()){
    		file.delete();
    	}
    	
    }
}
