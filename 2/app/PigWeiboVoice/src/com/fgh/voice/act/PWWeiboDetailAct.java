package com.fgh.voice.act;

import pigframe.PigBaseActivity;
import pigframe.control.PigController;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fgh.voice.R;
import com.fgh.voice.PWApplication;
import com.fgh.voice.db.bean.WeiboBean;
import com.fgh.voice.util.PartenUtil;
import com.fgh.voice.view.widget.FaceManager;

public class PWWeiboDetailAct extends PW_BaseActivity {
	
	public ImageView wbprofile = null;
	public ImageView wbicon = null;
	public TextView wbuser = null;
	public ImageView wbloc = null;
	public TextView wbtime = null;
	public TextView wbtext = null;
	public ImageView wbimage = null;
	public LinearLayout llrepost = null;
	public TextView wbtext_repost = null;
	public ImageView wbimage_repost = null;
	public TextView tweet_form = null;
	public ImageView tweet_redirect_pic = null;
	public TextView tweet_redirect = null;
	public ImageView tweet_comment_pic = null;
	public TextView tweet_comment = null;
	
	WeiboBean wb;
	
	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weibodetail);
		initPre();
		initFindViews();
		initListeners();
		initPost();
		
	}

	@Override
	public void initFindViews() {
		// TODO Auto-generated method stub
		wbprofile = (ImageView) findViewById(R.id.wbprofile);
		wbtext = (TextView) findViewById(R.id.wbtext);
		wbtime = (TextView) findViewById(R.id.wbtime);
		wbuser = (TextView) findViewById(R.id.wbuser);
		wbicon = (ImageView) findViewById(R.id.wbicon);
		wbloc = (ImageView) findViewById(R.id.wbloc);
		wbimage = (ImageView) findViewById(R.id.wbimage);
		llrepost = (LinearLayout) findViewById(R.id.llrepost);
		wbtext_repost = (TextView) findViewById(R.id.wbtext_repost);
		wbimage_repost = (ImageView) findViewById(R.id.wbimage_repost);
		tweet_form = (TextView) findViewById(R.id.tweet_form);
		tweet_redirect_pic = (ImageView) findViewById(R.id.tweet_redirect_pic);
		tweet_redirect = (TextView) findViewById(R.id.tweet_redirect);
		tweet_comment_pic = (ImageView) findViewById(R.id.tweet_comment_pic);
		tweet_comment = (TextView) findViewById(R.id.tweet_comment);
	}

	@Override
	public void initListeners() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initPost() {
		// TODO Auto-generated method stub
		if (wb != null) {
//			convertView.setTag(wb.getWeiboId());
			wbuser.setText(wb.getUser());
//			wbtime.setText(getTimeDifferenceStr(wb));
			SpannableString ss = FaceManager.getInstance().convertTextToEmotion(getBaseContext(), wb.getText());
			wbtext.setText(ss);

			/// 图片
			if (wb.getExistImage()) {
				wbicon.setImageResource(R.drawable.pic);
				wbicon.setVisibility(View.VISIBLE);
				wbimage.setVisibility(View.VISIBLE);
				if (wb.getIcon() != null) {
					
				} else if (wb.getIconUrl() != null) {
					wbimage.setTag(wb.getIconUrl());
					pigframe.util.PigImageUtil.showImage(true, wbimage, wb.getIconUrl(), R.drawable.icon);
				}
			} else {
				wbimage.setVisibility(View.GONE);
			}
			
			/// 头像
			if (wb.getProfile() != null) {
				wbprofile.setImageBitmap(wb.getProfile());
			} else if (wb.getProfileUrl() != null) {
				pigframe.util.PigImageUtil.showImage(true, wbprofile, wb
						.getProfileUrl(), R.drawable.icon);
//				Drawable cachedImage = asyncImageLoader.loadDrawable(wb
//						.getProfileUrl(), wbprofile, new ImageCallback() {
//
//					public void imageLoaded(Drawable imageDrawable,
//							ImageView imageView, String imageUrl) {
//						imageView.setImageDrawable(imageDrawable);
//					}
//
//				});
//				if (cachedImage == null) {
//					wbprofile.setImageResource(R.drawable.icon);
//				} else {
//					wbprofile.setImageDrawable(cachedImage);
//				}
			}
			
			/// 定位状态
			if (wb.getExistLocation()) {
				wbloc.setVisibility(View.VISIBLE);
				wbloc.setImageResource(R.drawable.location_icon);
			} else {
				wbloc.setVisibility(View.GONE);
			}
			
			/// 转播
			if (wb.getRetweet() != null) {
				llrepost.setVisibility(View.VISIBLE);
				SpannableString ss2 = FaceManager.getInstance()
						.convertTextToEmotion(
								getBaseContext(),
								"@" + wb.getRetweet().getUser() + ":"
										+ wb.getRetweet().getText());
				wbtext_repost.setText(ss2);

				/// 图片
				if (wb.getRetweet().getExistImage()) {
					wbimage_repost.setVisibility(View.VISIBLE);
					Bitmap icon = wb.getRetweet().getIcon();
					String url = wb.getRetweet().getIconUrl();
					if (icon != null) {
						
					} else if (url != null) {
						wbimage_repost.setTag(url);
						pigframe.util.PigImageUtil.showImage(true, wbimage_repost,url, R.drawable.icon);
//	                    Drawable cache = asyncImageLoader.loadDrawable(url, wbimage_repost, 
//	                    		new ImageCallback(){
//	                        public void imageLoaded(Drawable imageDrawable,
//	                        		ImageView imageView, String imageUrl) {
//	                            BitmapGet.showImgByWidth(imageView, imageDrawable, Constants.THUMB_IMAGE_WIDTH);
//	                        }
//	                    });
//	                    
//	                    if (cache != null) 
//	                    {
//                            BitmapGet.showImgByWidth(wbimage_repost, cache, Constants.THUMB_IMAGE_WIDTH);
//	                    }
					}
				}
			} else {
				llrepost.setVisibility(View.GONE);
				wbimage_repost.setVisibility(View.GONE);
			}
			
			/// 来自
			tweet_form.setText("来自:" + wb.getComeString());
			
			/// 转发
			if (wb.getRepostCount() > 0) {
				tweet_redirect_pic.setVisibility(View.VISIBLE);
				tweet_redirect.setText(wb.getRepostCount() + "");
				tweet_redirect.setVisibility(View.VISIBLE);
			}
			
			/// 评论
			if (wb.getCommentCount() > 0) {
				tweet_comment_pic.setVisibility(View.VISIBLE);
				tweet_comment.setText(wb.getCommentCount() + "");
				tweet_comment.setVisibility(View.VISIBLE);
			}
			
			PartenUtil.setAllParten(wbtext, true);
			PartenUtil.setAllParten(wbtext_repost, true);
		}
	}

	@Override
	public void initPre() {
		// TODO Auto-generated method stub
		Intent intent = this.getIntent();
		if(null != intent){
			wb = (WeiboBean) intent.getSerializableExtra("weibobean");
			
		}
	}

	
	public static void gotoActivity(WeiboBean wb){
		Bundle bundle=new Bundle();
		bundle.putSerializable("weibobean", wb);
		PigController.startAct(PWWeiboDetailAct.class, 
				PWApplication.getInstance().getCurrentActivity(),
				false, bundle);
	}
}
