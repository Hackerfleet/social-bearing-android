package org.hackerfleet.buoy_select;

import org.holoeverywhere.app.Fragment;

import android.view.View;
import android.widget.ImageButton;
/**
 * a Base Fragment for Fragments that select Buoys
 * 
 * @author ligi
 *
 */
public class BaseBuoySelectFragment extends Fragment{

	private BuoySelectOnClickListener mOnBtnClickListener;
		
	public void btn2buoy(View container,int buoy_resid,String tag) {
		if (mOnBtnClickListener==null)
			mOnBtnClickListener=new BuoySelectOnClickListener();
		
		ImageButton button=(ImageButton)container.findViewById(buoy_resid);
		button.setOnClickListener(mOnBtnClickListener);
	}
}
