package org.hackerfleet;

import org.hackerfleet.etc.AppDefs;
import org.holoeverywhere.app.Activity;
import org.holoeverywhere.widget.TextView;

import android.os.Bundle;
import android.widget.ImageView;

public class MeasureStartActivity extends Activity {

	private Integer buoy;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView (R.layout.measure_start);
		
		buoy=getIntent().getExtras().getInt("buoy");
		TextView sat_tv=(TextView)findViewById(R.id.sat_count);
		sat_tv.setText(buoy);
		
		ImageView bouy_icon=(ImageView)findViewById(R.id.buoy_icon);
		bouy_icon.setImageResource(AppDefs.foo.get(buoy).image_resId);
		super.onCreate(savedInstanceState);
	}

}
