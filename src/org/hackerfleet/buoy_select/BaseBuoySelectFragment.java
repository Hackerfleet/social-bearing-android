package org.hackerfleet.buoy_select;

import org.holoeverywhere.app.Fragment;

import android.view.View;
import android.widget.ImageButton;
/**
 * a Base Fragment for Fragments that select Buoy
 * 
 * @author ligi
 *
 */
public class BaseBuoySelectFragment extends Fragment{

	private BuoySelectOnClickListener mOnBtnClickListener;
		
	public void btn2buoy(View container,int[] buoy_resids) {
		if (mOnBtnClickListener==null)
			mOnBtnClickListener=new BuoySelectOnClickListener();
		
		for (int resid: buoy_resids) {
			ImageButton button=(ImageButton)container.findViewById(resid);
			button.setTag(resid);
			button.setOnClickListener(mOnBtnClickListener);
		}
	}
}
