package pigframe.download;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import pigframe.util.PigFileUtil;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.fgh.pigframe.R;



public class DownLoadOneFile {
	
	    private NotificationManager mNotificationManager;
		private Notification mNotification;
		private Context context;
		
		public static final String MSG_DATA_SIZE="MSG_DATA_SIZE";
		public static final String MSG_DATA_LENGTH="MSG_DATA_LENGTH";
		public static final String MSG_DATA_NOTIFYID="MSG_DATA_NOTIFYID";
		public static final String MSG_DATA_FILENAME="MSG_DATA_FILENAME";
		public static final String ACTION_NOTIFY_PAUSE="ACTION_NOTIFY_PAUSE";
		
		
		//下载地址
		public String downurl;
		
		//新文件名字
		public String newfilename;
		//保存文件的位置
		public File filedir;
		//下载器
		public Downloader loader;
		//notification的id，每个下载文件对应一个此类实例
		private int mNotifyid=hashCode();
		//下载文件列表
		public static Map<String, Boolean> downloadingFileMap=new HashMap<String, Boolean>();
		public static Map<Integer,DownLoadOneFile > downloadingMap=new HashMap<Integer,DownLoadOneFile>();
		
		
		/**
		 * @param context
		 * @param downurl
		 * @param savedir
		 */
		public DownLoadOneFile(Context context,String downurl,  
				File savedir,String newfilename){
			 this.context=context;
			 this.filedir=savedir;
			 this.downurl=downurl;
			 this.newfilename=newfilename;
			 mNotificationManager = (NotificationManager) context.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
		}
		
	
	public void download(){
    	  new Thread(new Runnable() {
    		 
			 public void run() {
//				 final String newfilename = downurl.substring(downurl.lastIndexOf('/') + 1);
				 if(TextUtils.isEmpty(newfilename)){
					 newfilename=downurl.substring(downurl.lastIndexOf('/') + 1);
				 }
				 try {
					 loader = new Downloader(context,
							 downurl, filedir, 1,newfilename);
					final int length = loader.getFileSize();//»ñÈ¡ÎÄ¼þµÄ³¤¶È
					
					if(downloadingFileMap.containsKey(newfilename)){
						//第一次运行要发出通知栏显示的消息
						Message msg = new Message();
						msg.what = 3;
						msg.getData().putString(MSG_DATA_FILENAME,newfilename);							
						handler.sendMessage(msg);
						return;
					}
					
					downloadingFileMap.put(newfilename, false);
					downloadingMap.put(Integer.valueOf(mNotifyid), DownLoadOneFile.this);
					//第一次运行要发出通知栏显示的消息
					Message msg = new Message();
					msg.what = 2;
					msg.getData().putString(MSG_DATA_FILENAME,newfilename);							
					handler.sendMessage(msg);
					
					
					//这里会长连接的去下载文件
					loader.downFile(new DownloadProgressListener(){
						public void onDownloadSize(int size) {//¿ÉÒÔÊµÊ±µÃµ½ÎÄ¼þÏÂÔØµÄ³¤¶È
							Message msg = new Message();
							msg.what = 1;
							msg.getData().putInt(MSG_DATA_SIZE, size);							
							msg.getData().putInt(MSG_DATA_LENGTH, length);							
							msg.getData().putString(MSG_DATA_FILENAME,newfilename);							
							handler.sendMessage(msg);
						}});
				} catch (Exception e) {
					Message msg = new Message();
					msg.what = -1;
					msg.getData().putString("error", "error");
					msg.getData().putString(MSG_DATA_FILENAME,newfilename);
					handler.sendMessage(msg);
				}
			}
		}).start();
    	
    }
	
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			
//			int mNotifyid=msg.getData().getInt(MSG_DATA_NOTIFYID);
			int size = msg.getData().getInt(MSG_DATA_SIZE);
			int file_length = msg.getData().getInt(MSG_DATA_LENGTH);
			String newfilename=msg.getData().getString(MSG_DATA_FILENAME);
//			mNotifyid=hashCode();
			switch (msg.what) {
			case 1:
				
				
				if(size>=file_length){
					completeNotification(newfilename);
					mNotificationManager.notify(mNotifyid, mNotification);
					return ;
				}else{
					if(!loader.isFinish){
						float result = (float)size/ (float)file_length;
						int p = (int)(result*100);
						RemoteViews contentView = mNotification.contentView;
						contentView.setTextViewText(R.id.rate, p + "%");
						contentView.setProgressBar(R.id.progress, 100, p, false);
						// 最后别忘了通知一下,否则不会更新
						mNotificationManager.notify(mNotifyid, mNotification);
					}
				}
				break;

			case -1:
				failedNotification(newfilename);
				break;
			case 2:
				startNotification(newfilename); 
				break;
			case 3:
				Toast.makeText(context, newfilename+",正在下载", Toast.LENGTH_SHORT).show();
				break;
			}
		}    	
    };
    
    private void cancelNotification(String newfilename) {
    	loader.isFinish=true;
    	loader.deleteFile(loader.getSaveFile());
    	downloadingFileMap.remove(newfilename);
    	downloadingMap.remove(mNotifyid);
    	Toast.makeText(context, "取消下载", 1).show();
    	mNotification.tickerText="取消下载";
    	mNotification.flags = Notification.FLAG_AUTO_CANCEL;
    	mNotification.contentView = null;
    	Intent intent = new Intent();
    	//更新参数,注意flags要使用FLAG_UPDATE_CURRENT
    	PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    	mNotification.setLatestEventInfo(context, "已取消下载", "取消下载"+newfilename, contentIntent);
    	mNotificationManager.notify(mNotifyid, mNotification);
    }
    private void failedNotification(String newfilename) {
    	loader.isFinish=true;
    	loader.deleteFile(loader.getSaveFile());
		downloadingFileMap.remove(newfilename);
		downloadingMap.remove(mNotifyid);
		Toast.makeText(context, "下载失败", 1).show();
		
		
		mNotification.tickerText="下载失败";
		mNotification.flags = Notification.FLAG_AUTO_CANCEL;
	    mNotification.contentView = null;
		Intent intent = new Intent();
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		mNotification.setLatestEventInfo(context, "下载失败了", "下载失败"+newfilename, contentIntent);
		mNotificationManager.notify(mNotifyid, mNotification);
    }
    private void completeNotification(String newfilename) {
   
		downloadingFileMap.remove(newfilename);
		downloadingMap.remove(mNotifyid);
		
		// 下载完毕后变换通知形式
		mNotification.flags = Notification.FLAG_AUTO_CANCEL;
		mNotification.contentView = null;
		
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		
		File file=new File(filedir, newfilename);
		/* 调用getMIMEType()来取得MimeType */
		String type = PigFileUtil.getMIMEType(file);
		/* 设置intent的file与MimeType */
		intent.setDataAndType(Uri.fromFile(file), type);
		
		//更新参数,注意flags要使用FLAG_UPDATE_CURRENT
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		mNotification.setLatestEventInfo(context, "下载完成", "文件:"+newfilename+"已下载完毕", contentIntent);
    }
    private void startNotification(String newfilename) {
		int icon = R.drawable.icon;
		CharSequence tickerText = "开始下载";
		long when = System.currentTimeMillis();
		mNotification = new Notification(icon, newfilename, when);
		// 放置在"正在运行"栏目中
		mNotification.flags = Notification.FLAG_ONGOING_EVENT;
		mNotification.tickerText=tickerText;
		RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.download_notification_layout);
		contentView.setTextViewText(R.id.fileName, newfilename);
		// 指定个性化视图
		mNotification.contentView = contentView;
		
		
		
//		Intent intent = new Intent();
//		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//		mNotification.contentIntent = contentIntent;
		
		
		//暂停按钮的intent事件
		Intent intentpause = new Intent(NotifyBroadcastReceiver.ACTION_NOTIFY_PAUSE);
		intentpause.putExtra(MSG_DATA_NOTIFYID, mNotifyid);
		intentpause.putExtra(MSG_DATA_FILENAME, newfilename);
		PendingIntent contentIntentpause = PendingIntent.getBroadcast(context, 0, intentpause, PendingIntent.FLAG_UPDATE_CURRENT);
//		mNotification.contentView.setOnClickPendingIntent(R.id.pause,contentIntentpause);
		mNotification.contentIntent = contentIntentpause;
		
		mNotificationManager.notify(mNotifyid, mNotification);
	}
    
    public static class NotifyBroadcastReceiver extends BroadcastReceiver{

    	//暂停下载的广播接受者，主要负责cancel notify。
    	public static final String ACTION_NOTIFY_PAUSE="ACTION_NOTIFY_PAUSE";
    	@Override
    	public void onReceive(Context context, Intent intent) {
    		// TODO Auto-generated method stub
    		int notifyid=intent.getExtras().getInt(DownLoadOneFile.MSG_DATA_NOTIFYID);
    		String newfilename=intent.getExtras().getString(DownLoadOneFile.MSG_DATA_FILENAME);
    		DownLoadOneFile downing=downloadingMap.get(notifyid);
    		downing.cancelNotification(newfilename);
    	}
    	
    }
	
	
}
