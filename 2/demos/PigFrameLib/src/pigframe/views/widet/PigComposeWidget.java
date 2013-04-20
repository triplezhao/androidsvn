package pigframe.views.widet;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fgh.pigframe.R;


public class PigComposeWidget extends LinearLayout
{
   
    private int screenW;
    private int screenH;
    private Context context;
    private PigQuickActionWidget pigbar;
    public ImageView compose_child;
	
    public PigComposeWidget(Context context,int w,int h)
	{
		super(context);
		screenW = w;
		screenH = h;
	}
    
    public PigComposeWidget(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.init(context);
	}

	public PigComposeWidget(Context context) {
		super(context);
		this.init(context);
	}
    private void init(Context context) {
		this.context=context;
		this.setBackgroundResource(R.drawable.composer_button);
		initChild(context);
		initPigQuickAction(context);
	}
    private void initChild(Context context) {
    	compose_child=new ImageView(context);
    	compose_child.setImageResource(R.drawable.composer_icn_plus);
    	this.addView(compose_child);
    	
    }
    private void initPigQuickAction(Context context) {
    	pigbar=new PigQuickActionWidget(context) ;
		pigbar.setComposeButton(this);
    }
    
    public void setAction(int actionid,int resid, OnClickListener listener){
    	pigbar.setAction(actionid, resid, listener);
    }
    
    	
    
    
    
    
    

}
