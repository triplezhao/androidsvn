package pigframe.views;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

public class TagCloudyView extends ViewGroup {
	
	double radius = 150.0;
	double dtr = Math.PI/180;
	double d=300.0;

	boolean active = false;
	double lasta = 1.0;
	double lastb = 1.0;
	
	boolean distr = false;
	double tspeed=3.0;
	double size=360.0;

	double mouseX=0.0;
	double mouseY=0.0;

	double howElliptical=1.0;

	double sa ;
	double ca ;
	double sb ;
	double cb ;
	double sc ;
	double cc ;
	
	 // The minimal distance of a touch slop
	  private int touchSlop;
	// Tha last known values of X and Y
	  private float lastMotionX;
	  private float lastMotionY;
	
	ArrayList<CloudItem > cloudyList=new ArrayList<CloudItem>();
	Context mContext;
	
	
	 private final static int TOUCH_STATE_REST = 0;
	  // The current touch state
	  private int touchState = TOUCH_STATE_REST;
	  private final static int TOUCH_STATE_SCROLLING = 1;

	public TagCloudyView(Context context)
	{
		super(context);
		mContext=context;
	}
    public TagCloudyView(Context context,int w,int h)
	{
		super(context);
		mContext=context;
	}
    
    public TagCloudyView(Context context, AttributeSet attrs) {
    	super (context, attrs, 0);
    	mContext=context;
      }
    
    public TagCloudyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext=context;
      }


    public void initCloud(){
    	touchSlop = ViewConfiguration.getTouchSlop();
    	setChildrenDrawingOrderEnabled(true);
    	sineCosine( 0,0,0 );
    	initPosition();
	}
	

	void  update()
	{
		sortCloudy();
		conputerPotison();
		doPositionLayout();
		measureChildren(0, 0);
	}
	void  sortCloudy(){
		Collections.sort(cloudyList,  new CloudItemComparator());
	}
	
	void conputerPotison(){
		double a;
		double b;
		
		if(active)
		{
			a = (-Math.min( Math.max( -mouseY, -size ), size ) / radius ) * tspeed;
			b = (Math.min( Math.max( -mouseX, -size ), size ) / radius ) * tspeed;
//			active=!active;
		}
		else
		{
			a = lasta * 0.98;
			b = lastb * 0.98;
		}
		
		lasta=a;
		lastb=b;
		
		if(Math.abs(a)<=0.01 && Math.abs(b)<=0.01)
		{
			return;
		}
		
		double c=0;
		sineCosine(a,b,c);
		for(int j=0;j<cloudyList.size();j++)
		{
			double rx1=cloudyList.get(j).cx;
			double ry1=cloudyList.get(j).cy*ca+cloudyList.get(j).cz*(-sa);
			double rz1=cloudyList.get(j).cy*sa+cloudyList.get(j).cz*ca;
			
			double rx2=rx1*cb+rz1*sb;
			double ry2=ry1;
			double rz2=rx1*(-sb)+rz1*cb;
			
			double rx3=rx2*cc+ry2*(-sc);
			double ry3=rx2*sc+ry2*cc;
			double rz3=rz2;
			
			cloudyList.get(j).cx=rx3;
			cloudyList.get(j).cy=ry3;
			cloudyList.get(j).cz=rz3;
			
			double per=d/(d+rz3);
////			
			cloudyList.get(j).x=(howElliptical*rx3*per)-(howElliptical*2);
			cloudyList.get(j).y=ry3*per;
			cloudyList.get(j).scale=per;
//			cloudyList.get(j).alpha=per;
//			cloudyList.get(j).alpha=(cloudyList.get(j).alpha-0.6)*(10/6);
		}
	}

	void initPosition()
	{
		double phi=0;
		double theta=0;
		int max=cloudyList.size();
		
		for( int i=1; i<max+1; i++){
			if( true )
			{
				phi = Math.acos(-1+(2*i-1)/(double)max)+0.0;
				theta = Math.sqrt(max*Math.PI)*phi+0.0;
			}
			//目前总是走这里
			else
			{
				phi = Math.random()*(Math.PI);
				theta = Math.random()*(2*Math.PI);
			}
			Log.i("phi", phi+"");
			Log.i("theta", theta+"");
			CloudItem cloudy=cloudyList.get(i-1);
			////坐标变换
			cloudy.cx = radius * Math.cos(theta)*Math.sin(phi);
			cloudy.cy = radius * Math.sin(theta)*Math.sin(phi);
			cloudy.cz = radius * Math.cos(phi);
			cloudy.order=i-1;
		}
	}

	void doPositionLayout()
	{
		for(int i=0;i<cloudyList.size();i++)
		{
				int z=(int) (Math.ceil(80*cloudyList.get(i).scale/2)+8);
				CloudItem cloudy=cloudyList.get(i);
				int l=getWidth()/2+(int)cloudy.x;
				int t=getHeight()/2+(int)cloudy.y;
				
				int r=l+z;
				int b=t+z;
				cloudy.child.layout(l-50,t-50,r,b);
				cloudy.child.getLayoutParams().height=z;
				cloudy.child.getLayoutParams().width=z+z;
		}
		
	}

	void sineCosine( double a, double b, double c)
	{
		sa = Math.sin(a * dtr);
		ca = Math.cos(a * dtr);
		sb = Math.sin(b * dtr);
		cb = Math.cos(b * dtr);
		sc = Math.sin(c * dtr);
		cc = Math.cos(c * dtr);
	}
	
	
	public void addCloudy(View cloud){
		
		CloudItem item=new CloudItem();
		cloudyList.add(item);
		item.child=cloud;
		
//		//设置相关的控件在layoutmain中的摆放参数 
        RelativeLayout.LayoutParams leftParam = new RelativeLayout.LayoutParams( 
                RelativeLayout.LayoutParams.WRAP_CONTENT, 
                RelativeLayout.LayoutParams.WRAP_CONTENT); 
 
        this.addView(cloud, leftParam); 
//        this.addView(tv_item); 
		
	}
	
	class CloudItem{
		double cx;
		double cy;
		double cz;
		double x;
		double y;
		double scale;
		View child;
		int order;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
	
	//	mouseX=event.getX()-(oDiv.offsetLeft+oDiv.offsetWidth/2);
	//	mouseY=oEvent.clientY-(oDiv.offsetTop+oDiv.offsetHeight/2);
		
		mouseX=(int) (event.getRawX()-(getLeft()+getWidth()/2));
		mouseY=(int) (event.getRawY()-(getTop()+getHeight()/2));
	//	mouseX=(int) (event.getX());
	//	mouseY=(int) (event.getY());
		
	//	mouseX/=5;
	//	mouseY/=5;
	
	 switch (event.getAction() & MotionEvent.ACTION_MASK) {
        case MotionEvent.ACTION_DOWN:
        	 Log.i("onTouchEvent", "ACTION_DOWN");
                break;
        case MotionEvent.ACTION_POINTER_DOWN:
                break;
        case MotionEvent.ACTION_UP:
        	active=false;
        	 Log.i("onTouchEvent", "ACTION_UP");
        		break;
        case MotionEvent.ACTION_POINTER_UP:
        	
                break;
        case MotionEvent.ACTION_MOVE:
        	active=true;
        	 Log.i("onTouchEvent", "ACTION_MOVE");
        	 invalidate();
                break;
        }
        return true;	
		
	}


	@Override
	protected void dispatchDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		
		super.dispatchDraw(canvas);
//		if(active){
			update();
//		}
			 Log.i("dispatchDraw", "dispatchDraw");
	}
	
	
	@Override
	  public boolean onInterceptTouchEvent(MotionEvent ev) {

	    /*
	     * Shortcut the most recurring case: the user is in the dragging state and he is moving his finger. We want to
	     * intercept this motion.
	     */
	    final int action = ev.getAction();
	    if ((action == MotionEvent.ACTION_MOVE) && (touchState != TOUCH_STATE_REST)) {
	      return true;
	    }
	    
	    final float x = ev.getX();
	    final float y = ev.getY();

	    switch (action) {
	      case MotionEvent.ACTION_MOVE:
	    	  
	    	  final int xDiff = (int) Math.abs(x - lastMotionX);
	          final int yDiff = (int) Math.abs(y - lastMotionY);
	          boolean xMoved = xDiff > touchSlop;
	          boolean yMoved = yDiff > touchSlop;

	          Log.i("onInterceptTouchEvent", "xDiff:"+xDiff+"xDiff:"+yDiff);
	          
	          if (xMoved || yMoved) {

//	            if (xMoved && !yMoved) {
	              // Scroll if the user moved far enough along the X axis
	              touchState = TOUCH_STATE_SCROLLING;
	              Log.i("onInterceptTouchEvent", "ACTION_MOVE");
//	            }
	          }
	            
//	            invalidate();
	        break;

	      case MotionEvent.ACTION_DOWN:
	    	  
	    	  lastMotionX = x;
	          lastMotionY = y;
	    	  
	    	  touchState = TOUCH_STATE_REST;
	    	  Log.i("onInterceptTouchEvent", "ACTION_DOWN");
	    	 
	        /*
	         * If being flinged and user touches the screen, initiate drag; otherwise don't. mScroller.isFinished should be
	         * false when being flinged.
	         */
	        break;
	      case MotionEvent.ACTION_CANCEL:
	      case MotionEvent.ACTION_UP:
	        touchState = TOUCH_STATE_REST;
	        Log.i("onInterceptTouchEvent", "ACTION_UP");
	        break;
	    }
	    /*
	     * The only time we want to intercept motion events is if we are in the drag mode.
	     */
	    return touchState != TOUCH_STATE_REST;
	  }
	
	
	 static class CloudItemComparator implements Comparator<CloudItem> { 
		
			public int compare(CloudItem element1, 
								CloudItem element2) { 
						if (element1.scale > element2.scale) {  
			                return 1;  
			            } else if (element1.scale== element2.scale) {  
			                return 0;  
			            } else {  
			                return -1;  
			            }  
			}
	}

	 @Override
	 protected int getChildDrawingOrder(int childCount, int i) {
		// TODO Auto-generated method stub
		int order=i;
		CloudItem cloudy=cloudyList.get(i);
		if(cloudy!=null){
			order=cloudy.order;
		}
//		Log.i("i:::"+i, "order:::"+order);
		return order;
	}
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		
	}
	
	
}
