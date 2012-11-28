package org.hackerfleet.socialbearing.widgets;

import org.holoeverywhere.widget.Button;

import android.content.Context;
import android.util.AttributeSet;

public class SquareButton extends Button {

	public SquareButton(Context context, AttributeSet attrs) {
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

}
