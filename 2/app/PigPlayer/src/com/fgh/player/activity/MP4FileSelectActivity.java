package com.fgh.player.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import pigframe.PigBaseActivity;
import pigframe.util.PigDialogBuider;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.fgh.player.R;
import com.fgh.player.adapter.FileSelectAdapter;

public class MP4FileSelectActivity extends PigBaseActivity {
	private List<String> items = null;
	private List<String> paths = null;
//	private String rootPath = Environment.getExternalStorageDirectory().getPath()+"/";
	private String rootPath = Environment.getExternalStorageDirectory().getPath()
			+"/PigFrame/";
	private String curPath = "/";
	private TextView mPath;
	private ListView lv_files;
	
	private final static String TAG = "bb";

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.demo_activity_fileselect);
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

		if (!filePath.equals(rootPath)) {
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

		lv_files.setAdapter(new FileSelectAdapter(this, items, paths));
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
					openFile(file);
				}
			}
		});
		lv_files.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				File file = new File(paths.get(arg2));
				if (file.isDirectory()) {
					curPath = paths.get(arg2);
					getFileDir(paths.get(arg2));
				} else {
					showNoticeDialog(file);
				}
				return true;
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
		initTitleBar("浏览文件", "返回","",
				R.drawable.topbar_back_selector,0,
				0, 0,
				new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						finish();
					
					}
				},null);
	}

	/**
	 * 显示软件更新对话框
	 */
	private void showNoticeDialog(final File file)
	{
		PigDialogBuider.showDialog(this, "删除", null, "确定", "取消",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				file.delete();
				getFileDir(rootPath);
			}

		}, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
//		// 构造对话框
//		AlertDialog.Builder builder = new Builder(this);
//		builder.setTitle("删除吗");
//		// 更新
//		builder.setPositiveButton("yes", new DialogInterface.OnClickListener()
//		{
//
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				// TODO Auto-generated method stub
//				file.delete();
//				getFileDir(rootPath);
//			}
//		});
//		// 稍后更新
//		builder.setNegativeButton("no", new DialogInterface.OnClickListener()
//		{
//			@Override
//			public void onClick(DialogInterface dialog, int which)
//			{
//				dialog.dismiss();
//			}
//		});
//		Dialog noticeDialog = builder.create();
//		noticeDialog.show();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getFileDir(rootPath);
	}
	
}