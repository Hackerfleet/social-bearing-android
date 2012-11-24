package org.hackerfleet.buoy_select;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
/**
 * a Base Fragment for Fragments that select Buoys
 * 
 * @author ligi
 *
 */
public class BaseBuoySelectFragment extends Fragment{

	private BuoySelectOnClickListener mOnBtnClickListener;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mOnBtnClickListener=new BuoySelectOnClickListener();

		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	public void btn2buoy(View container,int buoy_resid,String tag) {
		ImageButton button=(ImageButton)container.findViewById(buoy_resid);
		button.setOnClickListener(mOnBtnClickListener);
	}
}
