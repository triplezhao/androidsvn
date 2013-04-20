package com.fgh.player.control;

import com.fgh.player.activity.PlayMovieHttpActivity;

import pigframe.control.PigController;
import android.app.Activity;
import android.os.Bundle;

public class LogicControl extends PigController{
	
	public static final String key_video_id="key_video_id";
	
	public static  void gotoPlayMovie(Activity from,String videoId){
		Bundle data=new Bundle();
		data.putString(key_video_id, videoId);
		startAct(PlayMovieHttpActivity.class, from, false, data);
	};
	
}
