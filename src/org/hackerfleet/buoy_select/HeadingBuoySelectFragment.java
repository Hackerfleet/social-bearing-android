package org.hackerfleet.buoy_select;

import org.hackerfleet.R;
import org.holoeverywhere.LayoutInflater;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
/**
 * Fragment to give users the option to select Heading Buoy
 *
 * @author ligi
 *
 */
public class HeadingBuoySelectFragment extends BaseBuoySelectFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.heading_select);
		btn2buoy(v,new int[] {R.id.north_btn,R.id.south_btn,R.id.east_btn,R.id.west_btn,R.id.center_btn});
		return v;
	}

}
