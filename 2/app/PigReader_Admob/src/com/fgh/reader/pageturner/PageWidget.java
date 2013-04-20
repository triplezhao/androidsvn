package com.fgh.reader.pageturner;


import pigframe.util.PigAndroidUtil;
import pigframe.util.PigLog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Region;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Scroller;

public class PageWidget extends RelativeLayout {

	private static final String TAG = "PageWidget";
	private boolean isshown=false;
	private boolean mPageTurning=false;
	private boolean mCanCallBack=false;
	private static int animTime = 900;
	
	  public static final int KEEP = 0;
	  public static final int NEXT = 1;
	  public static final int PRE = 2;
	  
	  public static final int VISIBILE_CURR = 0;
	  public static final int VISIBILE_NEXT = 1;
	  public static final int VISIBILE_PRE = 2;
	  public static final int INVISIBILE_ALL = 3;
	
	
	
	
	private int mWidth = 0;
	private int mHeight = 0;
	private int mCornerX = 0; // 拖拽点对应的页脚
	private int mCornerY = 0;
	private Path mPath0;
	private Path mPath1;

	PointF mTouch = new PointF(); // 拖拽点
	PointF mBezierStart1 = new PointF(); // 贝塞尔曲线起始点
	PointF mBezierControl1 = new PointF(); // 贝塞尔曲线控制点
	PointF mBeziervertex1 = new PointF(); // 贝塞尔曲线顶点
	PointF mBezierEnd1 = new PointF(); // 贝塞尔曲线结束点

	PointF mBezierStart2 = new PointF(); // 另一条贝塞尔曲线
	PointF mBezierControl2 = new PointF();
	PointF mBeziervertex2 = new PointF();
	PointF mBezierEnd2 = new PointF();

	float mMiddleX;
	float mMiddleY;
	float mDegrees;
	float mTouchToCornerDis;
	ColorMatrixColorFilter mColorMatrixFilter;
	Matrix mMatrix;
	float[] mMatrixArray = { 0, 0, 0, 0, 0, 0, 0, 0, 1.0f };

	boolean mIsRTandLB; // 是否属于右上左下
	float mMaxLength ;
	int[] mBackShadowColors;
	int[] mFrontShadowColors;
	GradientDrawable mBackShadowDrawableLR;
	GradientDrawable mBackShadowDrawableRL;
	GradientDrawable mFolderShadowDrawableLR;
	GradientDrawable mFolderShadowDrawableRL;

	GradientDrawable mFrontShadowDrawableHBT;
	GradientDrawable mFrontShadowDrawableHTB;
	GradientDrawable mFrontShadowDrawableVLR;
	GradientDrawable mFrontShadowDrawableVRL;

	Paint mPaint;

	Scroller mScroller;

	
	View current_page_view;
	View next_page_view;
	View pre_page_view;
	
	
	PageAdapterImp pageAdapter;
	
	public PageWidget(Context context,int w,int h)
	{
		super(context);
		init();
	}
    
    public PageWidget(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	public PageWidget(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}
	
	
	private void init(){
		
		mPath0 = new Path();
		mPath1 = new Path();
		createDrawable();

		mPaint = new Paint();
		mPaint.setStyle(Paint.Style.FILL);

		ColorMatrix cm = new ColorMatrix();
		float array[] = { 0.55f, 0, 0, 0, 80.0f, 0, 0.55f, 0, 0, 80.0f, 0, 0,
				0.55f, 0, 80.0f, 0, 0, 0, 0.2f, 0 };
		cm.set(array);
		mColorMatrixFilter = new ColorMatrixColorFilter(cm);
		mMatrix = new Matrix();
		mScroller = new Scroller(getContext());

		mTouch.x = 0.01f; // 不让x,y为0,否则在点计算时会有问题
		mTouch.y = 0.01f;
		
	}

	

	/**
	 * Author : hmg25 Version: 1.0 Description : 计算拖拽点对应的拖拽脚
	 */
	public void calcCornerXY(float x, float y) {
		
		if (x <= mWidth / 2)
			//左
			mCornerX = 0;
		else
			//右
			mCornerX = mWidth;
		if (y <= mHeight / 2)
			//上
			mCornerY = 0;
		else
			//下
			mCornerY = mHeight;
		if ((mCornerX == 0 && mCornerY == mHeight)
				|| (mCornerX == mWidth && mCornerY == 0))
			mIsRTandLB = true;
		else
			mIsRTandLB = false;
	}
	
	
	


	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		final int action = event.getAction(); 
	    final float x = event.getX();
	    final float y = event.getY();
	    
	    //设置中间部分的拖动touch的x、y坐标
	     if( mHeight/2>=lastMotionY&&lastMotionY>mHeight/2-mHeight/5){
	    		mTouch.y=Math.abs((mHeight/2-y)/10);
		 }else if(mHeight/2<lastMotionY&&lastMotionY<(mHeight/2+mHeight/5)){
			 mTouch.y=mHeight-Math.abs((y-mHeight/2)/10);
		 } else if(lastMotionY<mHeight/2-mHeight/5){
			 mTouch.y=((y-mHeight/10)<0)?0.01f:(y-mHeight/10);
		 }else if(lastMotionY>mHeight/2+mHeight/5){
			 mTouch.y=((y+mHeight/10)>mHeight)?mHeight-0.01f:(y+mHeight/10);
		 }else {
			 mTouch.y=y;
		 }
		    mTouch.x =x;
		
	    switch (action) {
	      case MotionEvent.ACTION_DOWN:
//	    	  	PigLog.i("ACTION_DOWN x","ACTION_DOWN x:"+ x);
	    	    lastMotionX=x;
	    	    lastMotionY=y;
	    	    if (pageAdapter.isFirstPage()) {
					if(lastMotionX<mWidth/2)
						break;
				} else if(pageAdapter.isLastPage()){
					if(lastMotionX>mWidth/2)
						break;
				}
	    	    
	    	    if(!mScroller.isFinished()){
	    	    	//在结束上次未完成动画时候，3页数据内容改变了。
		    	    //此时当up触发时候，就是新内容的动画了。
					abortAnimation();
					//可是这里重新计算出来的conerX又会影响到新3页到底是
					//pre还是next
					//在draw时候会根据conerX来加载不同的3页内容。
					//所以这时候将这个放到onfinish里去。
					//calcCornerXY(x, y);
			        PigLog.i("onInterceptTouchEvent", "ACTION_DOWN:isFinished"+mPageTurning);
	    	    }else{
	    	    	calcCornerXY(x, y);
	    	    	 PigLog.i("onInterceptTouchEvent", "mCornerX:"+mCornerX);
	    	    }
				mPageTurning=!mScroller.isFinished();
				 PigLog.i("onInterceptTouchEvent", "ACTION_DOWNX:"+lastMotionX);
				 PigLog.i("onInterceptTouchEvent", "ACTION_DOWNY:"+lastMotionY);
	    	  break;
	      case MotionEvent.ACTION_MOVE:
	    	  
	    	  if (pageAdapter.isFirstPage()) {
					if(lastMotionX<mWidth/2)
						break;
				} else if(pageAdapter.isLastPage()){
					if(lastMotionX>mWidth/2)
						break;
				}
	    	  
	    	    final int xDiff = (int) Math.abs(x - lastMotionX);
		        final int yDiff = (int) Math.abs(y - lastMotionY);
//		        PigLog.i("ACTION_MOVE", "============");
//		        PigLog.i("x","x"+ x);
//		        PigLog.i("lastMotionX", "lastMotionX"+lastMotionX);
//		        PigLog.i("xDiff", "xDiff"+xDiff);
//		        PigLog.i("ACTION_MOVE", "============");
		        boolean xMoved = xDiff > 32;
		        boolean yMoved = yDiff > 32;
		       
		        boolean ismove=xMoved || yMoved;
//		        PigLog.i("xMoved", "xMoved"+xMoved);
//		        PigLog.i("yMoved", "yMoved"+yMoved);
//		        PigLog.i("onInterceptTouchEvent", "ismove:"+ismove);
		        if (ismove) {
		        	
//		          if (xMoved && !yMoved) {
		            // Scroll if the user moved far enough along the X axis
		            mPageTurning=true;
		            
					PigLog.i("onInterceptTouchEvent", "ACTION_MOVE");
//		          }
		        }
				this.postInvalidate();
	    	  break;
	      case MotionEvent.ACTION_UP:
	    	  if (pageAdapter.isFirstPage()) {
					if(lastMotionX<mWidth/2){
						PigAndroidUtil.showToast(getContext(), "第一页", 3000);
						return true;
					}
						
				} else if(pageAdapter.isLastPage()){
					if(lastMotionX>mWidth/2){
						PigAndroidUtil.showToast(getContext(), "最后一页", 3000);
						return true;
					}
				}
	    	  if(mPageTurning==true){
//		    	   if (canDragOver()) {
						startAnimation(animTime);
//					} else {
//						mTouch.x = mCornerX - 0.09f;
//						mTouch.y = mCornerY - 0.09f;
//					}
					mPageTurning=false;
					PigLog.i("onInterceptTouchEvent", "ACTION_UP ");
					this.postInvalidate();
					  
	    	  }
				break;
	    }
		return false;
	}
	 @Override
	  public boolean onTouchEvent(MotionEvent ev) {
		 return doTouchEvent(ev);
	 }


	 private float lastMotionX;
	 private float lastMotionY;
	/**
	 * 一定要搞清一件事：onInterceptTouchEvent的ACTION_DOWN总会执行的。
	 * @param event
	 * @return
	 */
	public boolean doTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		    final int action = event.getAction(); 
		    final float x = event.getX();
		    final float y = event.getY();
		    
		    if (pageAdapter.isFirstPage()) {
				if(lastMotionX<mWidth/2){
					PigAndroidUtil.showToast(getContext(), "第一页", 3000);
					return true;
				}
					
			} else if(pageAdapter.isLastPage()){
				if(lastMotionX>mWidth/2){
					PigAndroidUtil.showToast(getContext(), "最后一页", 3000);
					return true;
				}
			}
//		    mTouch.x =x;
//			mTouch.y =y;
		  //设置中间部分的拖动touch的x、y坐标
		     if( mHeight/2>=lastMotionY&&lastMotionY>mHeight/2-mHeight/5){
		    	//说明在上半边
		    		mTouch.y=Math.abs((mHeight/2-y)/5);
//		    	 if(mHeight/2>=y){
//		    		 mTouch.y=(mHeight/2-y)/10;
//		    		 PigLog.i(TAG, "//说明在上半边:"+mTouch.y);
//		    	 }else{
////		    		 lastMotionY=mHeight-lastMotionY;
//		    		 lastMotionY=mHeight/2+0.01f;
////		    		 mTouch.y=0.01f;
//		    		 calcCornerXY(lastMotionX, mHeight/2+0.01f);
//		    		 mTouch.y=0.01f;
//		    	 }
		    		
			 }else if(mHeight/2<lastMotionY&&lastMotionY<(mHeight/2+mHeight/5)){
				 mTouch.y=mHeight-Math.abs((y-mHeight/2)/5);
//				 if(mHeight/2<y){
//					 mTouch.y= mHeight-(y-mHeight/2)/10;
//		    	 }else{
////		    		 lastMotionY=mHeight-lastMotionY;
//		    		 lastMotionY=mHeight/2-0.01f;
////		    		 mTouch.y=mHeight-0.01f;
//		    		 calcCornerXY(lastMotionX, mHeight/2-0.01f);
//		    		 mTouch.y=mHeight-0.01f;
//		    	 }	
			 }else if(lastMotionY<mHeight/2-mHeight/5){
				 mTouch.y=((y-mHeight/10)<0)?0.01f:(y-mHeight/10);
			 }else if(lastMotionY>mHeight/2+mHeight/5){
				 mTouch.y=((y+mHeight/10)>mHeight)?mHeight-0.01f:(y+mHeight/10);
			 }else {
				 mTouch.y=y;
			 }
			    mTouch.x =x;
			
		    switch (action) {
		    //无论如何Ointercepte的ACTION_DOWN都执行，所以这里的就不做操作了。
//		      case MotionEvent.ACTION_DOWN:
//		    	  break;
		      case MotionEvent.ACTION_MOVE:
		    	    
		    	    final int xDiff = (int) Math.abs(x - lastMotionX);
			        final int yDiff = (int) Math.abs(y - lastMotionY);
			        boolean xMoved = xDiff > 32;
			        boolean yMoved = yDiff > 32;
			       
			        boolean ismove=xMoved || yMoved;
			        if (ismove) {
			            mPageTurning=true;
						PigLog.i("doTouchEvent", "ACTION_MOVE");
			        }
					this.postInvalidate();
		    	  break;
		      case MotionEvent.ACTION_UP:
		    	  
		    	  		//与intercept只有这里不同。
						startAnimation(animTime);
						mPageTurning=false;
						PigLog.i("doTouchEvent", "ACTION_UP ");
						this.postInvalidate();
					break;
		    }
		return true;
	}

	/**
	 * Author : hmg25 Version: 1.0 Description : 求解直线P1P2和直线P3P4的交点坐标
	 */
	public PointF getCross(PointF P1, PointF P2, PointF P3, PointF P4) {
		PointF CrossP = new PointF();
		// 二元函数通式： y=ax+b
		float a1 = (P2.y - P1.y) / (P2.x - P1.x);
		float b1 = ((P1.x * P2.y) - (P2.x * P1.y)) / (P1.x - P2.x);

		float a2 = (P4.y - P3.y) / (P4.x - P3.x);
		float b2 = ((P3.x * P4.y) - (P4.x * P3.y)) / (P3.x - P4.x);
		CrossP.x = (b2 - b1) / (a1 - a2);
		CrossP.y = a1 * CrossP.x + b1;
		return CrossP;
	}

	private void calcPoints() {
		mMiddleX = (mTouch.x + mCornerX) / 2;
		mMiddleY = (mTouch.y + mCornerY) / 2;
		mBezierControl1.x = mMiddleX - (mCornerY - mMiddleY)
				* (mCornerY - mMiddleY) / (mCornerX - mMiddleX);
		mBezierControl1.y = mCornerY;
		mBezierControl2.x = mCornerX;
		mBezierControl2.y = mMiddleY - (mCornerX - mMiddleX)
				* (mCornerX - mMiddleX) / (mCornerY - mMiddleY);

		// Log.i("hmg", "mTouchX  " + mTouch.x + "  mTouchY  " + mTouch.y);
		// Log.i("hmg", "mBezierControl1.x  " + mBezierControl1.x
		// + "  mBezierControl1.y  " + mBezierControl1.y);
		// Log.i("hmg", "mBezierControl2.x  " + mBezierControl2.x
		// + "  mBezierControl2.y  " + mBezierControl2.y);

		mBezierStart1.x = mBezierControl1.x - (mCornerX - mBezierControl1.x)
				/ 2;
		mBezierStart1.y = mCornerY;

		// 当mBezierStart1.x < 0或者mBezierStart1.x > 480时
		// 如果继续翻页，会出现BUG故在此限制
		if (mTouch.x > 0 && mTouch.x < mWidth) {
			if (mBezierStart1.x < 0 || mBezierStart1.x > mWidth) {
				if (mBezierStart1.x < 0)
					mBezierStart1.x = mWidth - mBezierStart1.x;

				float f1 = Math.abs(mCornerX - mTouch.x);
				float f2 = mWidth * f1 / mBezierStart1.x;
				mTouch.x = Math.abs(mCornerX - f2);

				float f3 = Math.abs(mCornerX - mTouch.x)
						* Math.abs(mCornerY - mTouch.y) / f1;
				mTouch.y = Math.abs(mCornerY - f3);

				mMiddleX = (mTouch.x + mCornerX) / 2;
				mMiddleY = (mTouch.y + mCornerY) / 2;

				mBezierControl1.x = mMiddleX - (mCornerY - mMiddleY)
						* (mCornerY - mMiddleY) / (mCornerX - mMiddleX);
				mBezierControl1.y = mCornerY;

				mBezierControl2.x = mCornerX;
				mBezierControl2.y = mMiddleY - (mCornerX - mMiddleX)
						* (mCornerX - mMiddleX) / (mCornerY - mMiddleY);
				// Log.i("hmg", "mTouchX --> " + mTouch.x + "  mTouchY-->  "
				// + mTouch.y);
				// Log.i("hmg", "mBezierControl1.x--  " + mBezierControl1.x
				// + "  mBezierControl1.y -- " + mBezierControl1.y);
				// Log.i("hmg", "mBezierControl2.x -- " + mBezierControl2.x
				// + "  mBezierControl2.y -- " + mBezierControl2.y);
				mBezierStart1.x = mBezierControl1.x
						- (mCornerX - mBezierControl1.x) / 2;
			}
		}
		mBezierStart2.x = mCornerX;
		mBezierStart2.y = mBezierControl2.y - (mCornerY - mBezierControl2.y)
				/ 2;

		mTouchToCornerDis = (float) Math.hypot((mTouch.x - mCornerX),
				(mTouch.y - mCornerY));

		mBezierEnd1 = getCross(mTouch, mBezierControl1, mBezierStart1,
				mBezierStart2);
		mBezierEnd2 = getCross(mTouch, mBezierControl2, mBezierStart1,
				mBezierStart2);

		// Log.i("hmg", "mBezierEnd1.x  " + mBezierEnd1.x + "  mBezierEnd1.y  "
		// + mBezierEnd1.y);
		// Log.i("hmg", "mBezierEnd2.x  " + mBezierEnd2.x + "  mBezierEnd2.y  "
		// + mBezierEnd2.y);

		/*
		 * mBeziervertex1.x 推导
		 * ((mBezierStart1.x+mBezierEnd1.x)/2+mBezierControl1.x)/2 化简等价于
		 * (mBezierStart1.x+ 2*mBezierControl1.x+mBezierEnd1.x) / 4
		 */
		mBeziervertex1.x = (mBezierStart1.x + 2 * mBezierControl1.x + mBezierEnd1.x) / 4;
		mBeziervertex1.y = (2 * mBezierControl1.y + mBezierStart1.y + mBezierEnd1.y) / 4;
		mBeziervertex2.x = (mBezierStart2.x + 2 * mBezierControl2.x + mBezierEnd2.x) / 4;
		mBeziervertex2.y = (2 * mBezierControl2.y + mBezierStart2.y + mBezierEnd2.y) / 4;
	}

	private void drawCurrentPageArea(Canvas canvas, View bitmap, Path path) {
		mPath0.reset();
		mPath0.moveTo(mBezierStart1.x, mBezierStart1.y);
		mPath0.quadTo(mBezierControl1.x, mBezierControl1.y, mBezierEnd1.x,
				mBezierEnd1.y);
		mPath0.lineTo(mTouch.x, mTouch.y);
		mPath0.lineTo(mBezierEnd2.x, mBezierEnd2.y);
		mPath0.quadTo(mBezierControl2.x, mBezierControl2.y, mBezierStart2.x,
				mBezierStart2.y);
		mPath0.lineTo(mCornerX, mCornerY);
		mPath0.close();
		
		canvas.save();
		canvas.clipPath(path, Region.Op.XOR);
//		canvas.drawBitmap(bitmap, 0, 0, null);
		bitmap.draw(canvas);
		canvas.restore();
	}

	private void drawNextPageAreaAndShadow(Canvas canvas, View bitmap) {
		mPath1.reset();
		mPath1.moveTo(mBezierStart1.x, mBezierStart1.y);
		mPath1.lineTo(mBeziervertex1.x, mBeziervertex1.y);
		mPath1.lineTo(mBeziervertex2.x, mBeziervertex2.y);
		mPath1.lineTo(mBezierStart2.x, mBezierStart2.y);
		mPath1.lineTo(mCornerX, mCornerY);
		mPath1.close();

		mDegrees = (float) Math.toDegrees(Math.atan2(mBezierControl1.x
				- mCornerX, mBezierControl2.y - mCornerY));
		int leftx;
		int rightx;
		GradientDrawable mBackShadowDrawable;
		if (mIsRTandLB) {
			leftx = (int) (mBezierStart1.x);
			rightx = (int) (mBezierStart1.x + mTouchToCornerDis / 4);
			mBackShadowDrawable = mBackShadowDrawableLR;
		} else {
			leftx = (int) (mBezierStart1.x - mTouchToCornerDis / 4);
			rightx = (int) mBezierStart1.x;
			mBackShadowDrawable = mBackShadowDrawableRL;
		}
		canvas.save();
		canvas.clipPath(mPath0);
		canvas.clipPath(mPath1, Region.Op.INTERSECT);
//		canvas.drawBitmap(bitmap, 0, 0, null);
		bitmap.draw(canvas);
		canvas.rotate(mDegrees, mBezierStart1.x, mBezierStart1.y);
		mBackShadowDrawable.setBounds(leftx, (int) mBezierStart1.y, rightx,
				(int) (mMaxLength + mBezierStart1.y));
		mBackShadowDrawable.draw(canvas);
		canvas.restore();
	}

//	public void setBitmaps(Bitmap bm1, Bitmap bm2) {
//		mCurPageBitmap = bm1;
//		mNextPageBitmap = bm2;
//	}

	public void setScreen(int w, int h) {
		mWidth = w;
		mHeight = h;
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
//		super.dispatchDraw(canvas);
//		canvas.drawColor(0xFFAAAAAA);
//		canvas.drawColor(getResources().getColor(R.color.burlywood));
		if(!isshown){
			mWidth=getWidth();
			mHeight = getHeight();
			mMaxLength = (float) Math.hypot(mWidth, mHeight);
			isshown=true;
		}
		 if (this.mPageTurning||!mScroller.isFinished()) {
			 	mCanCallBack=true;
				calcPoints();
//				drawCurrentPageArea(canvas, mCurPageBitmap, mPath0);
				drawCurrentPageArea(canvas, current_page_view, mPath0);
//				drawNextPageAreaAndShadow(canvas, mNextPageBitmap);
				if(DragToRight()){
					drawNextPageAreaAndShadow(canvas, pre_page_view);
				}else{
					drawNextPageAreaAndShadow(canvas, next_page_view);
				}
//				drawCurrentBackArea(canvas, mCurPageBitmap);
				drawCurrentPageShadow(canvas);
				drawCurrentBackArea(canvas, current_page_view);
				
		 }else{//翻页结束了，所以要调用结束回调函数
			 if(mCanCallBack){
				
				 PigLog.i("mCanCallBack", "1111mCornerX:"+mCornerX);
				 if(mCornerX>0){
					 goNextExChangeView();
				 }else{
					 goPreExChangeView();
				 }
				 mCanCallBack=false;
				 calcCornerXY(lastMotionX, lastMotionY);
				 PigLog.i("mCanCallBack", "ACTION_DOWNX:"+lastMotionX);
				 PigLog.i("mCanCallBack", "mCornerX:"+mCornerX);
			 }
			 super.dispatchDraw(canvas);
		 }
	
	}

	/**
	 * Author : hmg25 Version: 1.0 Description : 创建阴影的GradientDrawable
	 */
	private void createDrawable() {
		int[] color = { 0x333333, 0xb0333333 };
		mFolderShadowDrawableRL = new GradientDrawable(
				GradientDrawable.Orientation.RIGHT_LEFT, color);
		mFolderShadowDrawableRL
				.setGradientType(GradientDrawable.LINEAR_GRADIENT);

		mFolderShadowDrawableLR = new GradientDrawable(
				GradientDrawable.Orientation.LEFT_RIGHT, color);
		mFolderShadowDrawableLR
				.setGradientType(GradientDrawable.LINEAR_GRADIENT);

		mBackShadowColors = new int[] { 0xff111111, 0x111111 };
		mBackShadowDrawableRL = new GradientDrawable(
				GradientDrawable.Orientation.RIGHT_LEFT, mBackShadowColors);
		mBackShadowDrawableRL.setGradientType(GradientDrawable.LINEAR_GRADIENT);

		mBackShadowDrawableLR = new GradientDrawable(
				GradientDrawable.Orientation.LEFT_RIGHT, mBackShadowColors);
		mBackShadowDrawableLR.setGradientType(GradientDrawable.LINEAR_GRADIENT);

		mFrontShadowColors = new int[] { 0x80111111, 0x111111 };
		mFrontShadowDrawableVLR = new GradientDrawable(
				GradientDrawable.Orientation.LEFT_RIGHT, mFrontShadowColors);
		mFrontShadowDrawableVLR
				.setGradientType(GradientDrawable.LINEAR_GRADIENT);
		mFrontShadowDrawableVRL = new GradientDrawable(
				GradientDrawable.Orientation.RIGHT_LEFT, mFrontShadowColors);
		mFrontShadowDrawableVRL
				.setGradientType(GradientDrawable.LINEAR_GRADIENT);

		mFrontShadowDrawableHTB = new GradientDrawable(
				GradientDrawable.Orientation.TOP_BOTTOM, mFrontShadowColors);
		mFrontShadowDrawableHTB
				.setGradientType(GradientDrawable.LINEAR_GRADIENT);

		mFrontShadowDrawableHBT = new GradientDrawable(
				GradientDrawable.Orientation.BOTTOM_TOP, mFrontShadowColors);
		mFrontShadowDrawableHBT
				.setGradientType(GradientDrawable.LINEAR_GRADIENT);
	}

	/**
	 * Author : hmg25 Version: 1.0 Description : 绘制翻起页的阴影
	 */
	public void drawCurrentPageShadow(Canvas canvas) {
		double degree;
		if (mIsRTandLB) {
			degree = Math.PI
					/ 4
					- Math.atan2(mBezierControl1.y - mTouch.y, mTouch.x
							- mBezierControl1.x);
		} else {
			degree = Math.PI
					/ 4
					- Math.atan2(mTouch.y - mBezierControl1.y, mTouch.x
							- mBezierControl1.x);
		}
		// 翻起页阴影顶点与touch点的距离
		double d1 = (float) 25 * 1.414 * Math.cos(degree);
		double d2 = (float) 25 * 1.414 * Math.sin(degree);
		float x = (float) (mTouch.x + d1);
		float y;
		if (mIsRTandLB) {
			y = (float) (mTouch.y + d2);
		} else {
			y = (float) (mTouch.y - d2);
		}
		mPath1.reset();
		mPath1.moveTo(x, y);
		mPath1.lineTo(mTouch.x, mTouch.y);
		mPath1.lineTo(mBezierControl1.x, mBezierControl1.y);
		mPath1.lineTo(mBezierStart1.x, mBezierStart1.y);
		mPath1.close();
		float rotateDegrees;
		canvas.save();

		canvas.clipPath(mPath0, Region.Op.XOR);
		canvas.clipPath(mPath1, Region.Op.INTERSECT);
		int leftx;
		int rightx;
		GradientDrawable mCurrentPageShadow;
		if (mIsRTandLB) {
			leftx = (int) (mBezierControl1.x);
			rightx = (int) mBezierControl1.x + 25;
			mCurrentPageShadow = mFrontShadowDrawableVLR;
		} else {
			leftx = (int) (mBezierControl1.x - 25);
			rightx = (int) mBezierControl1.x + 1;
			mCurrentPageShadow = mFrontShadowDrawableVRL;
		}

		rotateDegrees = (float) Math.toDegrees(Math.atan2(mTouch.x
				- mBezierControl1.x, mBezierControl1.y - mTouch.y));
		canvas.rotate(rotateDegrees, mBezierControl1.x, mBezierControl1.y);
		mCurrentPageShadow.setBounds(leftx,
				(int) (mBezierControl1.y - mMaxLength), rightx,
				(int) (mBezierControl1.y));
		mCurrentPageShadow.draw(canvas);
		canvas.restore();

		mPath1.reset();
		mPath1.moveTo(x, y);
		mPath1.lineTo(mTouch.x, mTouch.y);
		mPath1.lineTo(mBezierControl2.x, mBezierControl2.y);
		mPath1.lineTo(mBezierStart2.x, mBezierStart2.y);
		mPath1.close();
		canvas.save();
		canvas.clipPath(mPath0, Region.Op.XOR);
		canvas.clipPath(mPath1, Region.Op.INTERSECT);
		if (mIsRTandLB) {
			leftx = (int) (mBezierControl2.y);
			rightx = (int) (mBezierControl2.y + 25);
			mCurrentPageShadow = mFrontShadowDrawableHTB;
		} else {
			leftx = (int) (mBezierControl2.y - 25);
			rightx = (int) (mBezierControl2.y + 1);
			mCurrentPageShadow = mFrontShadowDrawableHBT;
		}
		rotateDegrees = (float) Math.toDegrees(Math.atan2(mBezierControl2.y
				- mTouch.y, mBezierControl2.x - mTouch.x));
		canvas.rotate(rotateDegrees, mBezierControl2.x, mBezierControl2.y);
		float temp;
		if (mBezierControl2.y < 0)
			temp = mBezierControl2.y - mHeight;
		else
			temp = mBezierControl2.y;

		int hmg = (int) Math.hypot(mBezierControl2.x, temp);
		if (hmg > mMaxLength)
			mCurrentPageShadow
					.setBounds((int) (mBezierControl2.x - 25) - hmg, leftx,
							(int) (mBezierControl2.x + mMaxLength) - hmg,
							rightx);
		else
			mCurrentPageShadow.setBounds(
					(int) (mBezierControl2.x - mMaxLength), leftx,
					(int) (mBezierControl2.x), rightx);

		// Log.i("hmg", "mBezierControl2.x   " + mBezierControl2.x
		// + "  mBezierControl2.y  " + mBezierControl2.y);
		mCurrentPageShadow.draw(canvas);
		canvas.restore();
	}
	/**
	 * Author : hmg25 Version: 1.0 Description : 绘制翻起页背面
	 */
	private void drawCurrentBackArea(Canvas canvas, View bitmap) {
		
		int i = (int) (mBezierStart1.x + mBezierControl1.x) / 2;
		float f1 = Math.abs(i - mBezierControl1.x);
		int i1 = (int) (mBezierStart2.y + mBezierControl2.y) / 2;
		float f2 = Math.abs(i1 - mBezierControl2.y);
		float f3 = Math.min(f1, f2);
		mPath1.reset();
		mPath1.moveTo(mBeziervertex2.x, mBeziervertex2.y);
		mPath1.lineTo(mBeziervertex1.x, mBeziervertex1.y);
		mPath1.lineTo(mBezierEnd1.x, mBezierEnd1.y);
		mPath1.lineTo(mTouch.x, mTouch.y);
		mPath1.lineTo(mBezierEnd2.x, mBezierEnd2.y);
		mPath1.close();
		GradientDrawable mFolderShadowDrawable;
		int left;
		int right;
		if (mIsRTandLB) {
			left = (int) (mBezierStart1.x - 1);
			right = (int) (mBezierStart1.x + f3 + 1);
			mFolderShadowDrawable = mFolderShadowDrawableLR;
		} else {
			left = (int) (mBezierStart1.x - f3 - 1);
			right = (int) (mBezierStart1.x + 1);
			mFolderShadowDrawable = mFolderShadowDrawableRL;
		}
		canvas.save();
		canvas.clipPath(mPath0);
		canvas.clipPath(mPath1, Region.Op.INTERSECT);
		
		mPaint.setColorFilter(mColorMatrixFilter);
		
		float dis = (float) Math.hypot(mCornerX - mBezierControl1.x,
				mBezierControl2.y - mCornerY);
		float f8 = (mCornerX - mBezierControl1.x) / dis;
		float f9 = (mBezierControl2.y - mCornerY) / dis;
		mMatrixArray[0] = 1 - 2 * f9 * f9;
		mMatrixArray[1] = 2 * f8 * f9;
		mMatrixArray[3] = mMatrixArray[1];
		mMatrixArray[4] = 1 - 2 * f8 * f8;
		mMatrix.reset();
		mMatrix.setValues(mMatrixArray);
		mMatrix.preTranslate(-mBezierControl1.x, -mBezierControl1.y);
		mMatrix.postTranslate(mBezierControl1.x, mBezierControl1.y);
//		canvas.drawBitmap(bitmap, mMatrix, mPaint);
		Bitmap backbitmap=bitmap.getDrawingCache();
		if(backbitmap!=null){
//			canvas.drawColor(0xFFffffff);
//			canvas.drawColor(getResources().getColor(R.color.burlywood));
//			canvas.drawColor(getResources().getColor(R.color.graylight));
			int pixel = backbitmap.getPixel(backbitmap.getWidth()-1,
					backbitmap.getHeight()-1);
//			int newpixel=Color.argb(200,Color.red(pixel) 
//					, Color.green(pixel)
//					, Color.blue(pixel));
			canvas.drawColor(pixel);
			Paint bitPaint=new Paint();
//			bitPaint.setAlpha(95);
			canvas.drawBitmap(backbitmap, mMatrix, bitPaint);
		}
		 
		mPaint.setColorFilter(null);
//		canvas.drawColor(getResources().getColor(R.color.white));
		canvas.drawARGB(200, 225, 225, 225);
		canvas.rotate(mDegrees, mBezierStart1.x, mBezierStart1.y);
		mFolderShadowDrawable.setBounds(left, (int) mBezierStart1.y, right,
				(int) (mBezierStart1.y + mMaxLength));
		mFolderShadowDrawable.draw(canvas);
		
		
		canvas.restore();
	}

	public void computeScroll() {
		super.computeScroll();
		if (mScroller.computeScrollOffset()) {
			float x = mScroller.getCurrX();
			float y = mScroller.getCurrY();
			mTouch.x = x;
			mTouch.y = y;
			if(mTouch.y==0){
				mTouch.y=0.01f;
			}
			postInvalidate();
		}
	}

	private void startAnimation(int delayMillis) {
		int dx, dy;
		// dx 水平方向滑动的距离，负值会使滚动向左滚动
		// dy 垂直方向滑动的距离，负值会使滚动向上滚动
		if (mCornerX > 0) {
			dx = -(int) (mWidth + mTouch.x);
		} else {
			dx = (int) (mWidth - mTouch.x + mWidth);
		}
		if (mCornerY > 0) {
			dy = (int) (mHeight - mTouch.y);
		} else {
			dy = (int) (0 - mTouch.y); // 防止mTouch.y最终变为0
		}
		mScroller.startScroll((int) mTouch.x, (int) mTouch.y, dx, dy,
				delayMillis);
	}

	public void abortAnimation() {
		if (!mScroller.isFinished()) {
			mScroller.abortAnimation();
		}
	}

	public boolean canDragOver() {
		if (mTouchToCornerDis > mWidth / 10)
			return true;
		return false;
	}

	/**
	 * Author : hmg25 Version: 1.0 Description : 是否从左边翻向右边
	 */
	public boolean DragToRight() {
		if (mCornerX > 0)
			return false;
		return true;
	}

	
	
	public PageAdapterImp getPageAdapter() {
		return pageAdapter;
	}

	public void setPageAdapter(PageAdapterImp pageAdapter) {
		this.pageAdapter = pageAdapter;
//		loading_page_view=pageAdapter.getLoadingView(loading_page_view);
		inflate3PageView();
		add3PageView();
	}
	
	
	public void inflate3PageView(){
		
		pre_page_view=pageAdapter.getPreView(pre_page_view);
		next_page_view=	pageAdapter.getNextView(next_page_view);
		current_page_view=pageAdapter.getCurrView(current_page_view);
		
		
	}
	private void add3PageView(){
		this.addView(pre_page_view,-1,-1);
		this.addView(next_page_view,-1,-1);
		this.addView(current_page_view,-1,-1);
	}
	
	
	public void goPreExChangeView() {
		
		if(pageAdapter.isFirstPage()){
			PigAndroidUtil.showToast(getContext(), "第一页了：", 3000);
			return ;
		}
		
		//adapter获取新内容，设置好3页内容
		pageAdapter. onPageTurnFinished(PRE);
		
		//调整3页view到合适位置。
		View temp=next_page_view;
		next_page_view=current_page_view;
		current_page_view=pre_page_view;
		pre_page_view=	pageAdapter.getPreView(temp);
		inflate3PageView();
		setVisible(VISIBILE_CURR);
	}
	public void goNextExChangeView() {
		if(pageAdapter.isLastPage()){
			PigAndroidUtil.showToast(getContext(), "以是最后一页：", 3000);
			return ;
		}
		//adapter获取新内容，设置好3页内容
		pageAdapter.onPageTurnFinished(NEXT);
		
		//调整3页view到合适位置。
		View temp=pre_page_view;
		pre_page_view=current_page_view;
		current_page_view=next_page_view;
		next_page_view=	pageAdapter.getNextView(temp);
		inflate3PageView();
		setVisible(VISIBILE_CURR);
	}
	
	public void setVisible(int witch){
		switch (witch) {
		case VISIBILE_CURR:
			current_page_view.setVisibility(VISIBLE);
			next_page_view.setVisibility(INVISIBLE);
			pre_page_view.setVisibility(INVISIBLE);
//			loading_page_view.setVisibility(INVISIBLE);
		
			break;
		case VISIBILE_PRE:
			pre_page_view.setVisibility(VISIBLE);
			current_page_view.setVisibility(INVISIBLE);
			next_page_view.setVisibility(INVISIBLE);
//			loading_page_view.setVisibility(INVISIBLE);
			break;
		case VISIBILE_NEXT:
			next_page_view.setVisibility(VISIBLE);
			current_page_view.setVisibility(INVISIBLE);
			pre_page_view.setVisibility(INVISIBLE);
//			loading_page_view.setVisibility(INVISIBLE);
			break;
		case INVISIBILE_ALL:
			next_page_view.setVisibility(INVISIBLE);
			current_page_view.setVisibility(INVISIBLE);
			pre_page_view.setVisibility(INVISIBLE);
//			loading_page_view.setVisibility(INVISIBLE);
			break;

		default:
			break;
		}
	}
}
