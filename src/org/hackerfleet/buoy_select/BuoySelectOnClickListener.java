package org.hackerfleet.buoy_select;

import org.hackerfleet.MeasureStartActivity;
import org.hackerfleet.etc.AppDefs;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class BuoySelectOnClickListener implements OnClickListener {
	
	@Override
	public void onClick(View v) {
		if (v.getTag() instanceof String) {
			Log.w(AppDefs.TAG," Tag no string - dunno what Buoy");
			return;
		}
		Intent start_intent=new Intent(v.getContext(),MeasureStartActivity.class);
		start_intent.putExtra("buoy", (String)v.getTag());
		v.getContext().startActivity(start_intent);
	}

}
