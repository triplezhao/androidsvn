package com.fgh.reader.joke.activity;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import pigframe.PigBaseActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.fgh.reader.joke.R;
import com.fgh.reader.joke.adapter.FileBookLocalAdapter;
import com.fgh.reader.joke.database.BookStoryBean;
import com.fgh.reader.joke.database.BookStoryDao;



public class DemoBookLocalActivity extends PigBaseActivity {
	private List<String> items = null;
	private List<String> paths = null;
	private String rootPath = Environment.getExternalStorageDirectory().getPath();
	private String curPath = "/";
	private TextView mPath;
	private ListView lv_files;
	
	private final static String TAG = "bb";

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.demo_activity_localfile);
		initPre();
		initFindViews();
		initListeners();
		initPost();
	
	}

	private void getFileDir(String filePath) {
		mPath.setText(filePath);
		items = new ArrayList<String>();
		paths = new ArrayList<String>();
		File f = new File(filePath);
		File[] files = f.listFiles();

		if (!(f.getPath()).equals(rootPath)) {
			items.add("b1");
			paths.add(rootPath);
			items.add("b2");
			paths.add(f.getParent());
		}
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			items.add(file.getName());
			paths.add(file.getPath());
		}

		lv_files.setAdapter(new FileBookLocalAdapter(this, items, paths));
		lv_files.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				File file = new File(paths.get(arg2));
				if (file.isDirectory()) {
					curPath = paths.get(arg2);
					getFileDir(paths.get(arg2));
				} else {
					openTxtReader(file);
				}
			}
		});
	}


	private void openFile(File f) {
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);

		String type = getMIMEType(f);
		intent.setDataAndType(Uri.fromFile(f), type);
		startActivity(intent);
	}
	private void openTxtReader(File f) {
		
		BookStoryDao dao=BookStoryDao.getInstance(this);
		BookStoryBean bean=dao.queryOneBookByBookPath(f.getPath());
		BookStoryBean bsb;
		if(bean!=null){
			bsb=bean;
		}else{
			bsb=new BookStoryBean();
			bsb.setBookName(f.getName());
			bsb.setBookId(f.getPath());
			bsb.setBookPath(f.getPath());
		}
		DemoActivityPageTurner.gotoDemoActivityPageTurner(this, false, bsb);
	}

	private String getMIMEType(File f) {
		String type = "";
		String fName = f.getName();
		String end = fName
				.substring(fName.lastIndexOf(".") + 1, fName.length())
				.toLowerCase();

		if (end.equals("m4a") || end.equals("mp3") || end.equals("mid")
				|| end.equals("xmf") || end.equals("ogg") || end.equals("wav")) {
			type = "audio";
		} else if (end.equals("3gp") || end.equals("mp4")) {
			type = "video";
		} else if (end.equals("jpg") || end.equals("gif") || end.equals("png")
				|| end.equals("jpeg") || end.equals("bmp")) {
			type = "image";
		} else {
			type = "*";
		}
		type += "/*";
		return type;
	}

	@Override
	public void initFindViews() {
		// TODO Auto-generated method stub
		mPath = (TextView) findViewById(R.id.mPath);
		lv_files=(ListView) findViewById(R.id.lv_files);
	}

	@Override
	public void initListeners() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initPost() {
		// TODO Auto-generated method stub
		getFileDir(rootPath);
	}

	@Override
	public void initPre() {
		// TODO Auto-generated method stub
		setTitleBarBg(R.drawable.bookshelf_header_bg);
		initTitleBar("本地书库", "","",
				0,0,
				0, 0,
				null,null);
	}

}