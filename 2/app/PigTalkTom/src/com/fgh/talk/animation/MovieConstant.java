package com.fgh.talk.animation;

public class MovieConstant {
	
	public static final int gif_step_time=80;
	
	public  enum AnimType {
		panda_cry,
		panda_hello,
		panda_jump,
		panda_zhini,
		panda_xiagui;
		
	}
	
	public static int getCountByAnimType(AnimType animType){
		
		int count=0;
		if(AnimType.panda_cry.equals(animType)){
			count=53;
		}else if(AnimType.panda_hello.equals(animType)){
			count=36;
		}else if(AnimType.panda_jump.equals(animType)){
			count=42;
		}else if(AnimType.panda_zhini.equals(animType)){
			count=34;
		}else if(AnimType.panda_xiagui.equals(animType)){
			count=90;
		}
		return count;
		
	}
}
