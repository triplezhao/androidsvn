package pigframe.control;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public  class PigController {
	
	public static void  startAct(Class<?> toCls, Activity from, boolean finish) {
		Intent intent = new Intent(from, toCls);
		from.startActivity(intent);
		if(finish){
			from.finish();
		}
	}
	
	public static void  startAct(Class<?> toCls, Activity from, boolean finish, Bundle data) {
		Intent intent = new Intent(from, toCls);
		if(null != data){
		    intent.putExtras(data);
		}
		
		from.startActivity(intent);
		if(finish){
			from.finish();
		}
	}
	
	public static void  goForResult(Class<?> toCls, Activity from, boolean finish, Bundle data, int requestCode) {
		Intent intent = new Intent(from, toCls);
		if(null != data){
		    intent.putExtras(data);
		}
		
//		from.startActivity(intent);
		from.startActivityForResult(intent, requestCode);
		if(finish){
			from.finish();
		}
	}
	
	public static void  startAct(Class<?> toCls, Activity from, boolean finish, Bundle data, int flags) {
		Intent intent = new Intent(from, toCls);
		if(null != data){
		    intent.putExtras(data);
		}
		intent.setFlags(flags);
		from.startActivity(intent);
		if(finish){
			from.finish();
		}
	}
	public static void  startAct(Class<?> toCls, Activity from, boolean finish, Bundle data, int flags,String action) {
		Intent intent = new Intent(from, toCls);
		
		
		if(null != data){
			intent.putExtras(data);
		}
		if(null != action){
			intent.setAction(action);
		}
		intent.setFlags(flags);
		from.startActivity(intent);
		if(finish){
			from.finish();
		}
	}
	

}
