package org.hackerfleet.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;

public class SquareImageButton extends ImageButton {

	public SquareImageButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	    int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
	    this.setMeasuredDimension(parentWidth,parentWidth);
	}

}
