package com.fgh.reader.pageturner;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import pigframe.util.PigFileUtil;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.Layout.Alignment;

public class PageFileFactory {

	private File book_file = null;
	private MappedByteBuffer m_mbBuf = null;
	private int m_mbBufLen = 0;
	private String m_strCharsetName = "GBK";

	
	private Paint mPaint;
	public  int m_fontSize = 24;
	public int marginWidth = 0; // 左右与边缘的距离
	public int marginHeight = 0; // 上下与边缘的距离
	private float mVisibleHeight; // 绘制内容的宽
	private float mVisibleWidth; // 绘制内容的宽
	private int mWidth;
	private int mHeight;

	

	/**
	 *  在构造的时候就确定显示内容的大小。把margin等都算出去
	 * @param w   页面宽高  
	 * @param h
	 * @param textsize  字体大小
	 * @param filepath  txt文件路径
	 */
	public PageFileFactory(int w, int h,int textsize,String filepath) {
		// TODO Auto-generated constructor stub
		mWidth = w;
		mHeight = h;
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setTextAlign(Align.LEFT);
		mPaint.setTextSize(m_fontSize);
		mVisibleWidth = mWidth - marginWidth * 2;
		mVisibleHeight = mHeight - marginHeight * 2;
		m_fontSize=textsize;
		try {
			openbook(filepath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void openbook(String strFilePath) throws IOException {
		book_file = new File(strFilePath);
		m_strCharsetName=PigFileUtil.encodeType(book_file);
		long lLen = book_file.length();
		m_mbBufLen = (int) lLen;
		m_mbBuf = new RandomAccessFile(book_file, "r").getChannel().map(
				FileChannel.MapMode.READ_ONLY, 0, lLen);
	}

	// 读取下一页的缓冲字符串
	protected String readParagraphForward2048(int nFromPos) {
		int buffsize=2048;
		//根据一屏幕最多显示字数，确定要读取的内容大小
		buffsize=3*(int) ((mVisibleHeight/mPaint.getFontSpacing())*
				 (mVisibleWidth/mPaint.getFontSpacing()));
		//预读2048多个字节
		String strParagraph="";
		
		int nStart = nFromPos;
		int i = nStart;
		byte b0, b1;
		// 根据编码格式判断换行
		if (m_strCharsetName.equals("UTF-16LE")) {
			while (i < m_mbBufLen - 1) {
				b0 = m_mbBuf.get(i++);
				b1 = m_mbBuf.get(i++);
				if (i>=(nStart+buffsize)&&b0 == 0x0a && b1 == 0x00) {
					break;
				}
			}
		} else if (m_strCharsetName.equals("UTF-16BE")) {
			while (i < m_mbBufLen - 1) {
				b0 = m_mbBuf.get(i++);
				b1 = m_mbBuf.get(i++);
				if (i>=(nStart+buffsize)&&b0 == 0x00 && b1 == 0x0a) {
					break;
				}
			}
		} else {
			while (i < m_mbBufLen) {
				b0 = m_mbBuf.get(i++);
				if (i>=(nStart+buffsize)&&b0 == 0x0a) {
					break;
				}
			}
		}
		int nParaSize = i - nStart;
		byte[] buf = new byte[nParaSize];
		for (i = 0; i < nParaSize; i++) {
			buf[i] = m_mbBuf.get(nFromPos + i);
		}
		
		try {
			strParagraph = new String(buf, m_strCharsetName);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return strParagraph;
	}
	// 读取前一页内容的缓冲字符串
	protected String readParagraphBack2048(int nFromPos) {
		int buffsize=2048;
		//根据一屏幕最多显示字数，确定要读取的内容大小
		buffsize=3*(int) ((mVisibleHeight/mPaint.getFontSpacing())*
				(mVisibleWidth/mPaint.getFontSpacing()));
		int realFromPos=nFromPos;
		//或者是第一行开始，或者是从前2048的地方开始，向后开始读取
		nFromPos=realFromPos<buffsize?0:(realFromPos-buffsize);
		//预读2048多个字节
		String strParagraph="";
		
		int nStart = nFromPos;
		int i = nStart;
		byte b0, b1;
		// 根据编码格式判断换行
		if (m_strCharsetName.equals("UTF-16LE")) {
			while (i < m_mbBufLen - 1) {
				b0 = m_mbBuf.get(i++);
				b1 = m_mbBuf.get(i++);
				if (i>=realFromPos||i>=(nStart+buffsize)&&b0 == 0x0a && b1 == 0x00) {
					break;
				}
			}
		} else if (m_strCharsetName.equals("UTF-16BE")) {
			while (i < m_mbBufLen - 1) {
				b0 = m_mbBuf.get(i++);
				b1 = m_mbBuf.get(i++);
				if (i>=realFromPos||i>=(nStart+buffsize)&&b0 == 0x00 && b1 == 0x0a) {
					break;
				}
			}
		} else {
			while (i < m_mbBufLen) {
				b0 = m_mbBuf.get(i++);
				if (i>=realFromPos||i>=(nStart+buffsize)&&b0 == 0x0a) {
					break;
				}
			}
		}
		int nParaSize = i - nStart;
		byte[] buf = new byte[nParaSize];
		for (i = 0; i < nParaSize; i++) {
			buf[i] = m_mbBuf.get(nFromPos + i);
		}
		
		try {
			strParagraph = new String(buf, m_strCharsetName);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return strParagraph;
	}
	
	/**
	 * @param startoffset   从哪里开始读取
	 * @param forward  向前或者向后
	 * @return  返回适合此屏幕配置的一页内容
	 */
	protected PageContent getOnePageContent(int startoffset,boolean forward) {
		
		int endOffset=startoffset;
		String strParagraph = "";
		String onePageStr = "";
		
		if ( startoffset < m_mbBufLen) {
			if(forward){
				// 向后读取一个段落,m_mbBufEnd做起点。
				strParagraph= readParagraphForward2048(startoffset); 
			}else{
				// 向前读取一个段落,m_mbBufEnd做起点。
//				strParagraph= readParagraphBack2048(startoffset);
				strParagraph= readParagraphBack2048(startoffset); 
			}
			
			//如果读取到内容了。
			if (strParagraph.length() > 0) {
				//重置起点
				try {
					if(forward){
						//获取一页的字符串
						onePageStr = getOnePageWrapString(strParagraph, mPaint, 
								(int)mVisibleWidth, (int)mVisibleHeight,true);
//						//处理下换行符问题。
//						String strReturn = "";
//						if (strParagraph.indexOf("\r\n") != -1) {
//							strReturn = "\r\n";
//							strParagraph = strParagraph.replaceAll("\r\n", "");
//						} else if (strParagraph.indexOf("\n") != -1) {
//							strReturn = "\n";
//							strParagraph = strParagraph.replaceAll("\n", "");
//						}
						endOffset += (onePageStr).getBytes(m_strCharsetName).length;
					}else{
						//获取一页的字符串,后n行的字符串
						onePageStr = getOnePageWrapString(strParagraph, mPaint, 
								(int)mVisibleWidth, (int)mVisibleHeight,false);
						endOffset -= (onePageStr).getBytes(m_strCharsetName).length;
					}
					
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
		PageContent content=new PageContent();
		content.mPageContent= onePageStr;
		if(forward){
			content.mOffsetHead=startoffset;
			content.mOffsetTail=endOffset;
		}else{
			content.mOffsetHead=endOffset;
			content.mOffsetTail=startoffset;
		}
		
		if(content.mOffsetTail>=m_mbBufLen){
			content.mOffsetTail=m_mbBufLen;
			content.isLastPage=true;
		}
		if(content.mOffsetHead<=0){
			content.mOffsetHead=0;
			content.isFirstPage=true;
		}
		return content;
	}
	
	public PageContent getPrePageContent(PageContent currContent) {
		
		PageContent content=getOnePageContent(currContent.mOffsetHead,false);
		
		
		return content;
	}
	public PageContent getCurrPageContent(int thisPageOffset) {
		
		PageContent content=getOnePageContent(thisPageOffset,true);
		
		return content;
	}
	public PageContent getNextPageContent(PageContent currContent) {
		
		PageContent content=getOnePageContent(currContent.mOffsetTail,true);;
		
		return content;
	}
	
	
	/**
	 * @param strParagraph  准备分页的字符串
	 * @param p  字体等信息
	 * @param width  每行要多宽
	 * @param maxline 最大多少行
	 * @return  带分页的字符串
	 */
	protected String getOnePageWrapString(String strParagraph,Paint p,
			int width,int height,boolean forward){
		
		
		String pagestr="";
		
		int maxline=(int) ((height)/(p.getFontSpacing()+1));
		TextPaint tvpaint=new TextPaint(p);
		StaticLayout layout = new StaticLayout(strParagraph,
				tvpaint, width, Alignment.ALIGN_NORMAL, 
				(float) 1.0,(float) 0.0, true);
		int allline=layout.getLineCount();
		//获取一页的字符串
		if(forward){//读取后n行内容
			 int endoffset=layout.getLineEnd(maxline<allline?maxline:allline-1);
			 pagestr=strParagraph.substring(0,endoffset);
				
			 
		}else{//读取前n行内容，也就是截取缓冲字符串的后n行内容。
			int startLine=maxline<allline?(allline-maxline):0;
			if(startLine>0){
				startLine=startLine-1;
			}
			 int startoffset=layout.getLineStart(startLine);
			 
			 if(startLine>0){
				 startLine=startLine-1;
			 }
			 pagestr=strParagraph.substring(startoffset);
			
			    //如果最后有一个字符是回车。则重新截取字符串，在前边多读取一样。
				int start=pagestr.lastIndexOf("\r\n");
				if(start!=-1&&start==(pagestr.length()-2)){
					 int startoffset2=layout.getLineStart(startLine);
					 pagestr=strParagraph.substring(startoffset2);
				}
				start=pagestr.lastIndexOf("\n");
				if(start!=-1&&start==(pagestr.length()-2)){
					 int startoffset2=layout.getLineStart(startLine);
					 pagestr=strParagraph.substring(startoffset2);
				}
		}
		
		return  pagestr;
	}
	
	public File getFile(){
		return book_file;
	}
	public int getFileSize(){
		return m_mbBufLen;
	}
	
	public void setTextSize(int textsize){
		if(textsize>48){
			textsize=48;
		}else if(textsize<16){
			textsize=16;
		}
		mPaint.setTextSize(textsize);
		m_fontSize=textsize;
	}
	
	
}
