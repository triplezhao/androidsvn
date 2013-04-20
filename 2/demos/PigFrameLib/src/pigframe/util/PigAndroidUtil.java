package pigframe.util;



import java.io.InputStream;

import pigframe.PigApplication;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;



public class PigAndroidUtil {
	
	private static ProgressDialog mProgressDialog;
	private static Toast sToast;
	public static Context getContext() {
		return PigApplication.getInstance();
	}
	private static WakeLock wakeLock = null;
	
	/**
	 * ��Դ����, ����
	 * PowerManager.PARTIAL_WAKE_LOCK����cup�������б�֤��������
	 */
	public static void WakeAquire() {
		PowerManager pm = (PowerManager) getContext().getSystemService(Context.POWER_SERVICE);
		wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "FETION");
		wakeLock.acquire();
	}
	/**
	 * ��Դ����, �ͷ���
	 */
	public static void WakeRelease() {
		if(wakeLock != null && wakeLock.isHeld()) {
			wakeLock.release();
		}
		wakeLock = null;
	}
	
	public static LayoutInflater getLayoutInflater()
	{
		return LayoutInflater.from(PigApplication.getInstance().getCurrentActivity());
	}
    public static InputStream getFromAssets(Context cxt,String fileName){ 
    	InputStream is =null;  
    	try { 
	        	 AssetManager am = cxt.getResources().getAssets();  
	              is = am.open(fileName);  
	          
	        	} catch (Exception e) { 
	        		e.printStackTrace(); 
	        }
        return is;
    } 
    /**
	 * ע��һ������listView.setAdapter(bookAdapter)֮�� ʹ��ʱ��<ListView.../>����� <ViewStub
	 * android:id="@+id/ibx_empty" android:layout="@layout/empty_view" />
	 * 
	 * @param ctx
	 * @param inflater
	 *            ����Ϊ��
	 * @param lv
	 * @param showInfo
	 */
	public static void showSoftKeyBoard(Context ctx, View view) {
		InputMethodManager imm = (InputMethodManager) ctx
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		view.requestFocus();
		view.requestFocusFromTouch();
		imm.showSoftInput(view, InputMethodManager.HIDE_IMPLICIT_ONLY);
		// imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
		// Log.d(TAG, "showSoftKeyBoard:"+imm.isActive(view));
	}
	
	

	public static void showToast(Context context, int strId, int duration) {
		if(null == sToast){
			sToast = Toast.makeText(context.getApplicationContext(), strId, duration);
		} else {
			sToast.setText(strId);
			sToast.setDuration(duration);
		}
		sToast.show();
		
	}
	
	public static void showToast(Context context, String text, int duration) {
		if(null == sToast){
			sToast = Toast.makeText(context.getApplicationContext(), text, duration);
		} else {
			sToast.setText(text);
			sToast.setDuration(duration);
		}
		sToast.show();
		
	}
	
	public static void showProgressDialog(String message)
	{
		if(null == mProgressDialog || !mProgressDialog.isShowing())
		{
				mProgressDialog = new ProgressDialog(PigApplication.getInstance().getCurrentActivity());
				mProgressDialog.setMax(10000);
			    mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			
		}
		if(!TextUtils.isEmpty(message)){
			mProgressDialog.setMessage(message);
		}
			mProgressDialog.show();
		
	}
	
	public static void cancelProgressDialog()
	{
		if(null != mProgressDialog && mProgressDialog.isShowing())
		{
			mProgressDialog.cancel();
		}
	}
	
	
	
	public static  WindowManager.LayoutParams getMywmParams()
	{
		 WindowManager.LayoutParams wmParams=new WindowManager.LayoutParams();
		return wmParams;
	}
	public static int getW()
	{
		DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
		Log.i("view" , "width" +displayMetrics.widthPixels);
		return displayMetrics.widthPixels;
	}
	public static int getH()
	{
		DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
		Log.i("view" , "height" +displayMetrics.heightPixels); 
		return displayMetrics.heightPixels;
	}

	public static void start(Activity from, Class<?> dst,boolean finish, String action, Bundle data) {
           Intent intent = new Intent(from,dst);
	       if(!TextUtils.isEmpty(action)){
	    	   intent.setAction(action); 
	       }
	        if(null != data){
	            intent.putExtras(data);
	        }
            from.startActivity(intent);
	        if(finish){
	            from.finish();
	        }
    }
	public static void start(Context from, Class<?> dst,boolean finish, String action, Bundle data) {
		Intent intent = new Intent(from,dst);
		if(!TextUtils.isEmpty(action)){
			intent.setAction(action); 
		}
		if(null != data){
			intent.putExtras(data);
		}
		from.startActivity(intent);
	}

	 //转换dip为px 
	public static int convertDipOrPx(Context context, int dip) { 
	    float scale = context.getResources().getDisplayMetrics().density; 
	    return (int)(dip*scale + 0.5f*(dip>=0?1:-1)); 
	} 
	 
	//转换px为dip 
	public static int convertPxOrDip(Context context, int px) { 
	    float scale = context.getResources().getDisplayMetrics().density; 
	    return (int)(px/scale + 0.5f*(px>=0?1:-1)); 
	} 
}
