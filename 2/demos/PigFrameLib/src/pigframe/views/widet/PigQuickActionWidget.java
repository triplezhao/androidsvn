/*
 * Copyright (C) 2010 Cyril Mottier (http://www.cyrilmottier.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pigframe.views.widet;

import pigframe.views.widet.QuickActionWidget.OnQuickActionClickListener;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.fgh.pigframe.R;




/**
 * Abstraction of a {@link QuickAction} wrapper. A QuickActionWidget is
 * displayed on top of the user interface (it overlaps all UI elements but the
 * notification bar). Clients may listen to user actions using a
 * {@link OnQuickActionClickListener} .
 * 
 * @author Benjamin Fellous
 * @author Cyril Mottier
 */
public  class PigQuickActionWidget extends PopupWindow {

	 private Animation mRackAnimation;
	 private Animation mMissAnimation;
	 private Animation from0to1845Animation;
	 private Animation from1845to0Animation;
    private Context mContext;
    private int mScreenHeight;
    private int mScreenWidth;
    View contentView;
    PigComposeWidget compose; 
    private boolean candismiss=false;
    private boolean isShowing=false;
    private final int[] mLocation = new int[2];


    /**
     * Creates a new QuickActionWidget for the given context.
     * 
     * @param context The context in which the QuickActionWidget is running in
     */
    public PigQuickActionWidget(Context context) {
        super(context);

        mContext = context;

        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(true);
        setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

        final WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        mScreenWidth = windowManager.getDefaultDisplay().getWidth();
        mScreenHeight = windowManager.getDefaultDisplay().getHeight();
        initAnimation();
        setContentView(R.layout.pig_quick_action_bar);
        contentView=getContentView();
        initOnClickLisenner();
        
    }
    
    
    
    
    
    
    /**
     * Equivalent to {@link PopupWindow#setContentView(View)} but with a layout
     * identifier.
     * 
     * @param layoutId The layout identifier of the view to use.
     */
    public void setContentView(int layoutId) {
        setContentView(LayoutInflater.from(mContext).inflate(layoutId, null));
    }




    /**
     * Returns the width of the screen.
     * 
     * @return The width of the screen
     */
    protected int getScreenWidth() {
        return mScreenWidth;
    }

    /**
     * Returns the height of the screen.
     * 
     * @return The height of the screen
     */
    protected int getScreenHeight() {
        return mScreenHeight;
    }





    /**
     * Call that method to display the {@link PigQuickActionWidget} anchored to the
     * given view.
     * 
     * @param anchor The view the {@link PigQuickActionWidget} will be anchored to.
     */
    public void show(View anchor) {


        
        
        if (contentView == null) {
            throw new IllegalStateException("You need to set the content view using the setContentView method");
        }

        // Replaces the background of the popup with a cleared background
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        
        
        final int[] loc = mLocation;
        anchor.getLocationOnScreen(loc);
        showAtLocation(anchor, Gravity.BOTTOM|Gravity.LEFT, 
        		0, mScreenHeight-loc[1]-anchor.getMeasuredHeight());
        
       
        contentView.startAnimation(mRackAnimation);
        
        isShowing=true;
    }


    protected Context getContext() {
        return mContext;
    }

    public void initAnimation(){
    	 mRackAnimation = AnimationUtils.loadAnimation(mContext, R.anim.pig_4_to_2);

         mRackAnimation.setInterpolator(new Interpolator() {
             public float getInterpolation(float t) {
                 final float inner = (t * 1.55f) - 1.1f;
                 return 1.2f - inner * inner;
             }
         });
         mMissAnimation = AnimationUtils.loadAnimation(mContext, R.anim.pig_4_to_2_dismiss);
         
         from0to1845Animation=AnimationUtils.loadAnimation(mContext, R.anim.pig_0_to_1845);
         from1845to0Animation=AnimationUtils.loadAnimation(mContext, R.anim.pig_1845_to_0);
    }
    public void initOnClickLisenner(){
    	final View contentView = getContentView();

        contentView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});
    }
    






	@Override
	public void dismiss() {
		// TODO Auto-generated method stub
		
		if(candismiss){
			super.dismiss();
			
			candismiss=false;
			isShowing=false;
		}
		 mMissAnimation.setAnimationListener(new AnimationListener() {
				
				@Override
				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationEnd(Animation animation) {
					// TODO Auto-generated method stub
					candismiss=true;
					dismiss();
				}
			});
    	contentView.startAnimation(mMissAnimation);
    	compose.compose_child.startAnimation(from1845to0Animation);
	}
	
	public void setAction(int actionid,int resid, OnClickListener listener){
		
		ImageView iv = null;
		switch (actionid) {
		case 0:
			iv=(ImageView)contentView.findViewById(R.id.iv_1);
			break;
		case 1:
			iv=(ImageView)contentView.findViewById(R.id.iv_2);
			break;
		case 2:
			iv=(ImageView)contentView.findViewById(R.id.iv_3);
			break;
		case 3:
			iv=(ImageView)contentView.findViewById(R.id.iv_4);
			break;
		case 4:
			iv=(ImageView)contentView.findViewById(R.id.iv_5);
			break;

		default:
			break;
		}
		if(iv!=null){
			iv.setImageResource(resid);
			iv.setOnClickListener(getActionOnClickLisener(listener));
		}
		
		
	}
	
	private OnClickListener getActionOnClickLisener(final OnClickListener listener){
		return new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				listener.onClick(v);
				dismiss();
			}
		};
	}
	
	public  void setComposeButton(PigComposeWidget view){
//		
		compose=view;
		compose.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!isShowing){
					show(compose);
					compose.compose_child.startAnimation(from0to1845Animation);
				}
				
//				show(compose);
				
			}
		});
	}
	
    
}
