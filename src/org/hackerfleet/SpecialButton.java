package org.hackerfleet;

import org.holoeverywhere.widget.Button;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class SpecialButton extends Button {

	public SpecialButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	    super.onMeasure(widthMeasureSpec, heightMeasureSpec);

	    int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
	    int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
	    int size = Math.min(parentWidth, parentHeight);
	    this.setMeasuredDimension(size,size);
	}

/*
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		this.setBackgroundResource(R.drawable.start_btn_press);
		return super.onTouchEvent(event);
	}
	
*/	

}
