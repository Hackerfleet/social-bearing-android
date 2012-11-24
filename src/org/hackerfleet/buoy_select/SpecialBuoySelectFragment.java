package org.hackerfleet.buoy_select;

import org.hackerfleet.R;
import org.holoeverywhere.LayoutInflater;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

public class SpecialBuoySelectFragment extends BaseBuoySelectFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.special_select);
		btn2buoy(v,new int[] {R.id.specialpp_conical,R.id.specialpp_fateral,R.id.specialpp_forbidden,R.id.specialpp_lateral});
		return v;
	}
}
