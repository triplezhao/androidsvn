package com.fgh.reader.joke.control;

import android.content.Context;

import com.fgh.reader.joke.activity.DemoActivityPageTurner;
import com.fgh.reader.joke.database.BookStoryBean;

public class ControlCenter {
	public static void gotoPageContentAct(Context from, boolean finish,
			BookStoryBean bsb){
		DemoActivityPageTurner.gotoDemoActivityPageTurner(from, finish, bsb);
	}
}
