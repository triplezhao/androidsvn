package com.fgh.alice;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bitoflife.chatterbean.AliceBot;

/**
 * @author ztw
 *	web聊天机器人大脑
 */
public class WebAliceBot {
	
	// initing alice bot
	private AliceBotMother xmother = new AliceBotMother();
	private AliceBot xbot;
	
	public WebAliceBot() {

		//configing the alice bot
		try {
			xbot = xmother.newInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		xbot.respond("welcome");
		xmother.setUp(); 
		
	};
	
	public String getRespond(String input){
			String output=normalizationChinese(input);
			output=xbot.respond(output);
			return output;
	};
	
	
	public String normalizationChinese(String input){
		Pattern pattern  = Pattern.compile("[\\u4e00-\\u9fa5]");
		Matcher mather = pattern.matcher(input);
//		mather.find();
		StringBuffer target = new StringBuffer();
//		for(int i = 0; i < input.length(); i++){
//			if(mather.find()){
//				target.append(input.charAt(i));
//				target.append(" ");
//			}else{
//				target.append(input.charAt(i));
//			}
//		}
		while(!mather.hitEnd() && mather.find()){
			mather.appendReplacement(target, " "+mather.group()+" ");
		}
		mather.appendTail(target);
		String result = target.toString();
		System.out.println(result);
		return result;
	}
	
	
}
