package pigframe.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
/**
 * �̳�ImageView ʵ���˶�㴥�����϶�������
 * @author Administrator
 *
 */
public class TouchView extends ImageView
{
    static final int NONE = 0;
    static final int DRAG = 1;	   //�϶���
    static final int ZOOM = 2;     //������
    static final int BIGGER = 3;   //�Ŵ�ing
    static final int SMALLER = 4;  //��Сing
    private int mode = NONE;	   //��ǰ���¼� 

    private float beforeLenght;   //���������
    private float afterLenght;    //���������
    private float scale = 0.04f;  //���ŵı��� X Y���������ֵ Խ�����ŵ�Խ��
   
    private int screenW;
    private int screenH;
    
    /*�����϶� ���� */
    private int start_x;
    private int start_y;
	private int stop_x ;
	private int stop_y ;
	
    private TranslateAnimation trans; //���?���߽�Ķ���
	private boolean start=true;
	
    public TouchView(Context context,int w,int h)
	{
		super(context);
		this.setPadding(0, 0, 0, 0);
		screenW = w;
		screenH = h;
	}
    
    public TouchView(Context context, AttributeSet attrs) {
    	super (context, attrs, 0);
    	screenW=this.getWidth();
    	screenH=this.getHeight();
      }
    
    public TouchView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
      }
	
	/**
	 * ���������ľ���
	 */
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return FloatMath.sqrt(x * x + y * y);
    }
    
    /**
     * ���?��..
     */
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{	
		if(start){
//			screenH=getHeight();
//			screenW=getWidth();
//			if(getHeight()<=getBottom()){
				screenH=getHeight()+2*getTop();
//			}else{
//				screenH=getHeight();
//			}
			
//			if(getWidth()<=getRight()){
				screenW=getWidth()+2*getLeft();
//			}else{
//				screenW=getWidth();
//			}
			
			start=false;
		}
		
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
        case MotionEvent.ACTION_DOWN:
        		mode = DRAG;
    	    	stop_x = (int) event.getRawX();
    	    	stop_y = (int) event.getRawY();
        		start_x = (int) event.getX();
            	start_y = stop_y - this.getTop();
            	if(event.getPointerCount()==2)
            		beforeLenght = spacing(event);
                break;
        case MotionEvent.ACTION_POINTER_DOWN:
                if (spacing(event) > 10f) {
                        mode = ZOOM;
                		beforeLenght = spacing(event);
                }
                break;
        case MotionEvent.ACTION_UP:
        	/*�ж��Ƿ񳬳���Χ     ������*/
        		int disX = 0;
        		int disY = 0;
        		int disX_smale = 0;
        		int disY_smale= 0;
//	        	if(getHeight()<=screenH && this.getTop()<=0)
//	        	{
//		        	if(this.getTop()<0 )
//		        	{
//		        		int dis = getTop();
//	                	this.layout(this.getLeft(), 0, this.getRight(), 0 + this.getHeight());
//	            		disY = dis - getTop();
//		        	}
//		        	else if(this.getBottom()>screenH)
//		        	{
//		        		disY = getHeight()- screenH+getTop();
//	                	this.layout(this.getLeft(), screenH-getHeight(), this.getRight(), screenH);
//		        	}
//	        	}
	        	if(getHeight()>=screenH )
	        	{
		        	if(this.getTop()>0 )
		        	{
		        		int dis = getTop();
	                	this.layout(this.getLeft(), 0, this.getRight(), 0 + this.getHeight());
	            		disY = dis - getTop();
		        	}
		        	else if(this.getBottom()<screenH)
		        	{
		        		disY = getHeight()- screenH+getTop();
	                	this.layout(this.getLeft(), screenH-getHeight(), this.getRight(), screenH);
		        	}
	        	}else{
	        		this.layout((screenW-getWidth())/2, (screenH-getHeight())/2,
	        				(screenW+getWidth())/2,  (screenH+getHeight())/2);
//	        		if(this.getTop()<0 )
//			        	{
////	        				disY_smale = getTop()-(screenH-getHeight())/2;
//	        				this.layout(this.getLeft(), (screenH-getHeight())/2,
//	                			        this.getRight(), 0 + (screenH+getHeight())/2);
//		                	
//			        	}
//			        	else if(this.getBottom()>screenH)
//			        	{
////			        		disY_smale = getTop()-(screenH-getHeight())/2;
//			        		this.layout((screenW-getWidth())/2, (screenH-getHeight())/2,
//			        				(screenW+getWidth())/2,  (screenH+getHeight())/2);
//			        	}
	        	}
	        	
	        	
	        	if(getWidth()>=screenW)
	        	{
		        	if(this.getLeft()>0)
		        	{
		        		disX = getLeft();
	                	this.layout(0, this.getTop(), 0+getWidth(), this.getBottom());
		        	}
		        	else if(this.getRight()<screenW)
		        	{
		        		disX = getWidth()-screenW+getLeft();
	                	this.layout(screenW-getWidth(), this.getTop(), screenW, this.getBottom());
		        	}
	        	}else{
	        		this.layout((screenW-getWidth())/2, (screenH-getHeight())/2,
	        				(screenW+getWidth())/2,  (screenH+getHeight())/2);
//	        		if(this.getLeft()<0 )
//			        	{
////	        				disX_smale = getTop()-(screenW-getWidth())/2;
//	        				this.layout((screenW-getWidth())/2, this.getTop(),
//	        						(screenW+getWidth())/2, this.getBottom());
//		                	
//			        	}
////			        	else if(this.getBottom()>screenH)
////			        	{
////			        		disX_smale = getTop()-(screenH-getHeight())/2;
////			        		this.layout(this.getLeft(), (screenH-getHeight())/2,
////                			        this.getRight(), 0 + (screenH+getHeight())/2);
////			        	}
	        	
	        	}
	        	if(disX!=0 || disY!=0)
	        	{
            		trans = new TranslateAnimation(disX, 0, disY, 0);
            		trans.setDuration(500);
            		this.startAnimation(trans);
	        	}
//	        	else if(disX_smale!=0 || disY_smale!=0){
//	        		trans = new TranslateAnimation(disX, 0,disY_smale ,0);
//            		trans.setDuration(500);
//            		this.startAnimation(trans);
//            		
//	        	}
	        	mode = NONE;
        		break;
        case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                break;
        case MotionEvent.ACTION_MOVE:
        		/*�����϶�*/
                if (mode == DRAG) {
                	if(Math.abs(stop_x-start_x-getLeft())<88 && Math.abs(stop_y - start_y-getTop())<85)
                	{
                    	this.setPosition(stop_x - start_x, stop_y - start_y, stop_x + this.getWidth() - start_x, stop_y - start_y + this.getHeight());           	
                    	stop_x = (int) event.getRawX();
                    	stop_y = (int) event.getRawY();
                	}
                } 
                /*��������*/
                else if (mode == ZOOM) {
                	if(spacing(event)>10f)
                	{
                        afterLenght = spacing(event);
                        float gapLenght = afterLenght - beforeLenght;                     
                        if(gapLenght == 0) {  
                           break;
                        }
                        else if(Math.abs(gapLenght)>5f)
                        {
                            if(gapLenght>0) { 
                                this.setScale(scale,BIGGER);   
                            }else {  
                                this.setScale(scale,SMALLER);   
                            }                             
                            beforeLenght = afterLenght; 
                        }
                	}
                }
                break;
        }
        return true;	
	}
	
	/**
	 * ʵ�ִ�������
	 */
    private void setScale(float temp,int flag) {   
        
        if(flag==BIGGER) {   
            this.setFrame(this.getLeft()-(int)(temp*this.getWidth()),    
                          this.getTop()-(int)(temp*this.getHeight()),    
                          this.getRight()+(int)(temp*this.getWidth()),    
                          this.getBottom()+(int)(temp*this.getHeight()));      
        }else if(flag==SMALLER){  
            this.setFrame(this.getLeft()+(int)(temp*this.getWidth()),    
                          this.getTop()+(int)(temp*this.getHeight()),    
                          this.getRight()-(int)(temp*this.getWidth()),    
                          this.getBottom()-(int)(temp*this.getHeight()));   
         	
        	
        }   
    }
    
    public void setBigger(){
    	if(start){
				screenH=getHeight()+2*getTop();
				screenW=getWidth()+2*getLeft();
				start=false;
		}
    	 this.setFrame(this.getLeft()-(int)(2*scale*this.getWidth()),    
                 this.getTop()-(int)(2*scale*this.getHeight()),    
                 this.getRight()+(int)(2*scale*this.getWidth()),    
                 this.getBottom()+(int)(2*scale*this.getHeight()));
    	
    }
    public void setSmailler(){
    	if(start){
			screenH=getHeight()+2*getTop();
			screenW=getWidth()+2*getLeft();
			start=false;
    	}
    	this.setFrame(this.getLeft()+(int)(2*scale*this.getWidth()),    
                this.getTop()+(int)(2*scale*this.getHeight()),    
                this.getRight()-(int)(2*scale*this.getWidth()),    
                this.getBottom()-(int)(2*scale*this.getHeight())); 
    	if(this.getLeft()>screenW||getRight()<0 )
            this.layout((screenW-getWidth())/2, (screenH-getHeight())/2,
   				(screenW+getWidth())/2,  (screenH+getHeight())/2);
    	 if(this.getTop()>screenH||getBottom()<0 )
             this.layout((screenW-getWidth())/2, (screenH-getHeight())/2,
    				(screenW+getWidth())/2,  (screenH+getHeight())/2);
    }
    
	/**
	 * ʵ�ִ����϶�
	 */
    private void setPosition(int left,int top,int right,int bottom) {  
    	this.layout(left,top,right,bottom);           	
    }

}
