package com.fgh.reader.control;

import android.content.Context;

import com.fgh.reader.activity.DemoActivityPageTurner;
import com.fgh.reader.database.BookStoryBean;

public class ControlCenter {
	public static void gotoPageContentAct(Context from, boolean finish,
			BookStoryBean bsb){
		DemoActivityPageTurner.gotoDemoActivityPageTurner(from, finish, bsb);
	}
}
