package com.fgh.pets.anim;

public class MovieConstant {
	
	public static final int gif_step_time=120;
	
	public  enum AnimType {
		xiaoji_right,
		xiaoji_left
		
	}
	
	public static int getCountByAnimType(AnimType animType){
		
		int count=0;
		if(AnimType.xiaoji_right.equals(animType)){
			count=10;
		}else if(AnimType.xiaoji_left.equals(animType)){
			count=10;
		}
		return count;
		
	}
}
