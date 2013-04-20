package pigframe.util;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

public class PigDialogBuider {
	/**
	 *  构造一个极其普通的对话框,只有信息和确认按钮
	 */
	public static  void showMsgDialog(Context ctx,String msg)
	{
		showDialog(ctx, null, msg, null, null, null, null);
	}
	/**
	 * 
	 * 构造一个普通的对话框
	 * @param ctx
	 * @param title
	 * @param msg
	 * @param ok
	 * @param cancel
	 * @param okListener
	 * @param cancelListener
	 */
	public static void showDialog(Context ctx,String title,String msg,
								  String ok,String cancel,
								  DialogInterface.OnClickListener okListener,
								  DialogInterface.OnClickListener cancelListener){
			// 构造对话框
			AlertDialog.Builder builder = new Builder(ctx);
			
			if(!TextUtils.isEmpty(title)){
				builder.setTitle(title);
			}
			if(!TextUtils.isEmpty(msg)){
				builder.setMessage(msg);
			}
			if(!TextUtils.isEmpty(ok)){
				builder.setPositiveButton(ok,okListener );	
			}else{
			}
			
			if(!TextUtils.isEmpty(cancel)){
				builder.setNegativeButton(cancel,cancelListener) ;
			}
			
			Dialog noticeDialog = builder.create();
			noticeDialog.show();
	}
	public static void showListDialog(Context ctx,String title,String msg,
			  CharSequence[] items,DialogInterface.OnClickListener itemClickListener){
		
//			CharSequence[] items=new CharSequence[]{"修改","删除"};
			AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
			builder.setTitle(title);
			builder.setItems(items, itemClickListener);
			builder.create().show();
	}
	
}
