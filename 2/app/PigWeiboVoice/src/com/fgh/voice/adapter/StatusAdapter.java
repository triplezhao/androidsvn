package com.fgh.voice.adapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import pigframe.control.PigController;
import pigframe.util.PigAndroidUtil;
import pigframe.util.PigDateUtil;

import weibo4android.Status;
import weibo4android.WeiboException;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fgh.voice.R;
import com.fgh.voice.PWApplication;
import com.fgh.voice.act.PWZoomPicAtivity;
import com.fgh.voice.db.bean.WeiboBean;
import com.fgh.voice.util.DateUtilsDef;
import com.fgh.voice.util.PartenUtil;
import com.fgh.voice.util.WeiboUtil;
import com.fgh.voice.view.widget.FaceManager;


// 微博列表Adapater
	public class StatusAdapter extends BaseAdapter {


		private List<WeiboBean> wlist;
//		private List<Status> statuslist;
		private Context context;
		LayoutInflater inflater;

		public StatusAdapter(Context context) {
			super();
//			this.wlist=convertFromStatus(list);
			this.context = context;
			inflater = LayoutInflater.from(context);
		}
		public void addNewStatus( List<Status> list) {
			if(wlist==null){
				wlist=new ArrayList<WeiboBean>();
			}
			wlist.addAll(0,convertFromStatus(list));
		}
		public void addOldStatus( List<Status> list) {
			if(wlist==null){
				wlist=new ArrayList<WeiboBean>();
			}
			wlist.addAll(convertFromStatus(list));
		}

		@Override
		public int getCount() {
			return wlist == null ? 0 : wlist.size();
		}

		@Override
		public Object getItem(int location) {
			return wlist.get(location);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			return getWeiboListView(position, convertView, parent);
		}

//		public View getMoreWeiboView(int position, View convertView, ViewGroup parent) {
//			convertView = LayoutInflater.from(context.getApplicationContext()).inflate(
//					R.layout.weibo_more, null);
//			
//			return convertView;
//		}
		
		public View getWeiboListView(int position, View convertView, ViewGroup parent) {
			WeiboHolder wh=null;
			if(convertView==null){
				convertView = inflater.inflate(R.layout.weibo, null);
				wh = new WeiboHolder();
//				convertView=LayoutInflater.from(context).inflate(R.layout.weibo, null);
				wh.wbprofile = (ImageView) convertView.findViewById(R.id.wbprofile);
				wh.wbtext = (TextView) convertView.findViewById(R.id.wbtext);
				wh.wbtime = (TextView) convertView.findViewById(R.id.wbtime);
				wh.wbuser = (TextView) convertView.findViewById(R.id.wbuser);
				wh.wbicon = (ImageView) convertView.findViewById(R.id.wbicon);
				wh.wbloc = (ImageView) convertView.findViewById(R.id.wbloc);
				wh.wbimage = (ImageView) convertView.findViewById(R.id.wbimage);
				wh.llrepost = (LinearLayout) convertView.findViewById(R.id.llrepost);
				wh.wbtext_repost = (TextView) convertView.findViewById(R.id.wbtext_repost);
				wh.wbimage_repost = (ImageView) convertView.findViewById(R.id.wbimage_repost);
				wh.tweet_form = (TextView) convertView.findViewById(R.id.tweet_form);
				wh.tweet_redirect_pic = (ImageView) convertView.findViewById(R.id.tweet_redirect_pic);
				wh.tweet_redirect = (TextView) convertView.findViewById(R.id.tweet_redirect);
				wh.tweet_comment_pic = (ImageView) convertView.findViewById(R.id.tweet_comment_pic);
				wh.tweet_comment = (TextView) convertView.findViewById(R.id.tweet_comment);
				convertView.setTag(wh);
			}else {
				wh = (WeiboHolder) convertView.getTag();
	        }
			
			final WeiboBean wb = wlist.get(position);
			if (wb != null) {
//				convertView.setTag(wb.getWeiboId());
				wh.wbuser.setText(wb.getUser());
//				wh.wbtime.setText(getTimeDifferenceStr(wb));
				wh.wbtime.setText(PigDateUtil.dateToString(wb.getTime(),"MM-dd HH:mm"));
				String text=wb.getText();
				if(text==null||text.equals("")){
					text="转发微博";
				}
				SpannableString ss = FaceManager.getInstance().convertTextToEmotion(context, text);
				if(ss==null||ss.length()==0){
					wh.wbtext.setText(text);
				}else{
					wh.wbtext.setText(ss);
				}
				

				/// 非转发的图片
				if (wb.getExistImage()) {
					wh.wbicon.setImageResource(R.drawable.pic);
					wh.wbicon.setVisibility(View.VISIBLE);
					wh.wbimage.setVisibility(View.VISIBLE);
					if (wb.getIcon() != null) {
						
					} else if (wb.getIconUrl() != null) {
						wh.wbimage.setTag(wb.getIconUrl());
						pigframe.util.PigImageUtil.showImage(true, wh.wbimage, wb.getIconUrl(), R.drawable.icon);
					}
					
					wh.wbimage.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							PWZoomPicAtivity.open(PWApplication.getInstance().getCurrentActivity(),wb.getOriginal_pic());
						}
					});
					
				} else {
					wh.wbimage.setVisibility(View.GONE);
				}
				
				/// 头像
				if (wb.getProfile() != null) {
					wh.wbprofile.setImageBitmap(wb.getProfile());
				} else if (wb.getProfileUrl() != null) {
					pigframe.util.PigImageUtil.showImage(true, wh.wbprofile, wb
							.getProfileUrl(), R.drawable.icon);
				}
				
				/// 定位状态
				if (wb.getExistLocation()) {
					wh.wbloc.setVisibility(View.VISIBLE);
					wh.wbloc.setImageResource(R.drawable.location_icon);
				} else {
					wh.wbloc.setVisibility(View.GONE);
				}
				
				/// 转播
				if (wb.getRetweet() != null) {
					wh.llrepost.setVisibility(View.VISIBLE);
					SpannableString ss2 = FaceManager.getInstance()
							.convertTextToEmotion(
									context,
									"@" + wb.getRetweet().getUser() + ":"
											+ wb.getRetweet().getText());
					wh.wbtext_repost.setText(ss2);

					/// 转发的图片
					if (wb.getRetweet().getExistImage()) {
						wh.wbimage_repost.setVisibility(View.VISIBLE);
						Bitmap icon = wb.getRetweet().getIcon();
						String url = wb.getRetweet().getIconUrl();
						if (icon != null) {
							
						} else if (url != null) {
							wh.wbimage_repost.setTag(url);
							pigframe.util.PigImageUtil.showImage(true, wh.wbimage_repost,url, R.drawable.icon);
						}
						
						wh.wbimage_repost.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								PWZoomPicAtivity.open(PWApplication.getInstance().getCurrentActivity(),
										wb.getRetweet().getOriginal_pic());
							}
						});
						
					}
				} else {
					wh.llrepost.setVisibility(View.GONE);
					wh.wbimage_repost.setVisibility(View.GONE);
				}
				
				/// 来自
				wh.tweet_form.setText("来自:" + wb.getComeString());
				
				/// 转发
				if (wb.getRepostCount() > 0) {
					wh.tweet_redirect_pic.setVisibility(View.VISIBLE);
					wh.tweet_redirect.setText(wb.getRepostCount() + "");
					wh.tweet_redirect.setVisibility(View.VISIBLE);
				}
				
				/// 评论
				if (wb.getCommentCount() > 0) {
					wh.tweet_comment_pic.setVisibility(View.VISIBLE);
					wh.tweet_comment.setText(wb.getCommentCount() + "");
					wh.tweet_comment.setVisibility(View.VISIBLE);
				}
				
				PartenUtil.setAllParten(wh.wbtext, true);
				PartenUtil.setAllParten(wh.wbtext_repost, true);
//				convertView.setOnClickListener(new OnClickListener() {
//					
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						PWWeiboDetailAct.gotoActivity(wb);
//					}
//				});
			}

			return convertView;
		}
		
		public class WeiboHolder {
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
		}
		
		private WeiboBean convertFromStatus(Status status){
			WeiboBean wb=null;
			try {
				wb = getWeiboByStatus(status);
			} catch (WeiboException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return wb;
			
		}
		private List<WeiboBean> convertFromStatus(List<Status> statuslist){
			
			 List<WeiboBean> weibolist=new ArrayList<WeiboBean>();
			
			for (Status status : statuslist) {
				WeiboBean wb=convertFromStatus(status);
				weibolist.add(wb);
			}
			
			return weibolist;
			
		}
		
		private WeiboBean getWeiboByStatus(Status status/*, Weibo weibo*/) throws WeiboException {
			WeiboBean wb = new WeiboBean();
			
			/// 标识
			wb.setWeiboId(status.getId());
			
			/// 头像
			//wb.setProfile(BitmapGet.getHttpBitmap(status.getUser().getProfileImageURL()));
			wb.setProfileUrl(status.getUser().getProfileImageURL().toString());
			
			/// 用户名
			wb.setUser(status.getUser().getScreenName());
			
			/// 微博
			wb.setText(status.getText());
			
			/// 时间
			wb.setTime(status.getCreatedAt());
			
			/// 是否包含图片
			if (status.getThumbnail_pic() == null || status.getThumbnail_pic().equals("")){
				wb.setExistImage(false);
			} else {
				wb.setIconUrl(status.getThumbnail_pic());
				wb.setOriginal_pic(status.getOriginal_pic());
				wb.setExistImage(true);
			}
			
			/// 是否包含定位信息
			if (status.getLatitude() > 0.0 && status.getLongitude() > 0.0) {
				wb.setExistLocation(true);
				wb.setLatitude(status.getLatitude());
				wb.setLongitude(status.getLongitude());
			} else {
				wb.setExistLocation(false);
			}
			
			/// 是否为转发的
			if (status.isRetweet()) {
				WeiboBean retweet = new WeiboBean();
				Status retweetStatus = status.getRetweeted_status();
				
				retweet.setUser(retweetStatus.getUser().getScreenName());
				retweet.setText(retweetStatus.getText());
				
				/// 转发是否包含图片
				if (retweetStatus.getThumbnail_pic() == null || retweetStatus.getThumbnail_pic().equals("")){
					retweet.setExistImage(false);
				} else {
					retweet.setIconUrl(retweetStatus.getThumbnail_pic());
					retweet.setOriginal_pic(retweetStatus.getOriginal_pic());
					retweet.setExistImage(true);
					
				}
				
//				/// 获取转发原微博的转发数和评论数
//				List<Count> counts = weibo.getCounts(new Long(retweetStatus.getId()).toString());

//				if (counts != null && counts.size() > 0) {
//					Count count = counts.get(0);
//					
////					Log.i(TAG, "count: " + count.toString());
//					
//					retweet.setRepostCount((int) count.getRt());
//					retweet.setCommentCount((int) count.getComments());
//				} else {
//					retweet.setRepostCount(0);
//					retweet.setCommentCount(0);
//				}
				
				wb.setRetweet(retweet);
				
			}

//			/// 获取转发原微博的转发数和评论数
//			List<Count> counts = weibo.getCounts(new Long(status.getId()).toString());
//			
//			if (counts != null && counts.size() > 0) {
//				Count count = counts.get(0);
//				
////				Log.i(TAG, "count: " + count.toString());
//				
//				wb.setRepostCount((int) count.getRt());
//				wb.setCommentCount((int) count.getComments());
//			} else {
//				wb.setRepostCount(0);
//				wb.setCommentCount(0);d
//			}
			
//			/// 获取来源
			String source = status.getSource();
			if (source.length() > 0) {
				wb.setComeString(WeiboUtil.getSourceByXml(source));
			}
			
			return wb;
		}
		
		private String getTimeDifferenceStr(WeiboBean wb) {

			Date startDate = wb.getTime();
			Date nowDate = Calendar.getInstance().getTime();
			String timeDifferenceStr = new DateUtilsDef(context).twoDateDistance(
					startDate, nowDate);
			wb.setTimeDifference(timeDifferenceStr + context.getString(R.string.STR_AGO));
			
			return wb.getTimeDifference();
		}
	}
