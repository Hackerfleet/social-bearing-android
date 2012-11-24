package org.hackerfleet.buoy_select;

import org.hackerfleet.R;
import org.holoeverywhere.LayoutInflater;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

public class HeadingBuoySelectFragment extends BaseBuoySelectFragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.heading_select);
		
		btn2buoy(v,R.id.north_btn,"north");
		btn2buoy(v,R.id.south_btn,"south");
		btn2buoy(v,R.id.east_btn,"east");
		btn2buoy(v,R.id.west_btn,"west");
		btn2buoy(v,R.id.center_btn,"center");
		return v;
	}
	
}
