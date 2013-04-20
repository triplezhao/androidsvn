package com.iceeye.crop;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.iceeye.crop.kernelcrop.CropImage;
import com.iceeye.crop.service.TopViewService;
import com.iceeye.crop.util.BitMapTools;


public class ShareActivity extends Activity {
	
	private ImageView iv_croped;
	private Button bt_save;
	private Button bt_share;
	private Button bt_cancel;
	
	private Bitmap croped_bitmap;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.layout_share);
        
//        AdView adview = (AdView)findViewById(R.id.adView);
//        AdRequest re = new AdRequest();
//        re.setTesting(true);
//        re.setGender(AdRequest.Gender.FEMALE);
//        adview.loadAd(re);
        
        findViews();
        setViewLiseners();
        Intent intent = getIntent();
    	Bundle extras = intent.getExtras();
    	if (extras != null) {
    	    String pngPath = extras.getString("image-path");
    	    callCropActivity(pngPath);
    	}
    	
    }
    public void findViews(){
    	bt_save=(Button) findViewById(R.id.bt_save);
    	bt_share=(Button) findViewById(R.id.bt_share);
    	bt_cancel=(Button) findViewById(R.id.bt_drop);
    	iv_croped=(ImageView) findViewById(R.id.croped_img);
    	
    	
    }
    public void setViewLiseners(){
    	bt_save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String savename=String.valueOf(System.currentTimeMillis())+".png";
				//存储添加水印的图片到save
				BitMapTools.saveBitmapAsPNG(TopViewService.save_path,savename , 
						croped_bitmap);
				Toast.makeText(ShareActivity.this,
						"图片以保存到"+TopViewService.save_path, Toast.LENGTH_LONG).show();
				finish();
				
			}
		});
    	bt_share.setOnClickListener(new OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			  Intent intent=new Intent(Intent.ACTION_SEND);
    		      intent.setType("image/*");
    		      Uri uri = Uri.fromFile(new File(TopViewService.tmp_path+
    		    		  TopViewService.tmp_crop_png_name));
    		      intent.putExtra(Intent.EXTRA_STREAM, uri);
//    		      intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
//    		      intent.putExtra(Intent.EXTRA_TEXT, "快来使用拇指群");
    		      startActivity(Intent.createChooser(intent, getTitle()));
    			
    		}
    	});
    	bt_cancel.setOnClickListener(new OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			finish();
    			
    		}
    	});
    }
    
   
    /**
     * @param file_path_name   图片存储的位置+图片名字
     */
    public void callCropActivity(String file_path_name){
//        File f = new File("/sdcard/"+filename+".png");
        Intent intent = new Intent(this, CropImage.class);
	    // here you have to pass absolute path to your file
	    intent.putExtra("image-path", file_path_name);
	    intent.putExtra("return-data", true);  
	    startActivityForResult(intent, 5);
       
   }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
      if (resultCode == Activity.RESULT_OK) {
		      Bundle extras = data.getExtras();
		      Log.i("onActivityResult", "ok");
		       if(extras != null ) {
		    	   croped_bitmap= extras.getParcelable("data");
			      	if(croped_bitmap!=null){
			      		croped_bitmap=BitMapTools.generatorContactCountIcon(croped_bitmap);
			      		Drawable cropimg=new BitmapDrawable(croped_bitmap);
			          	iv_croped.setBackgroundDrawable(cropimg);
			          	
			          	//存储添加水印的图片到temp
			          	BitMapTools.saveBitmapAsPNG(TopViewService.tmp_path,
			          			TopViewService.tmp_crop_png_name, 
			          			croped_bitmap);
			      	}
		      	}
      	}else{
	      	  ShareActivity.this.finish();
        }
    }
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		  if (keyCode == KeyEvent.KEYCODE_BACK){  
			  
//			  if(croped_bitmap!=null){
//				  croped_bitmap.recycle();
////				  croped_bitmap=null;
//	    		}
              ShareActivity.this.finish();
              return true;
      }
		return super.onKeyDown(keyCode, event);
	}
    
    
}