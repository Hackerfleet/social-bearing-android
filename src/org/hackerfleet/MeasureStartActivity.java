package org.hackerfleet;

import org.hackerfleet.etc.AppDefs;
import org.holoeverywhere.app.Activity;
import org.holoeverywhere.widget.TextView;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.ImageView;

public class MeasureStartActivity extends Activity implements LocationListener {

	private Integer buoy;
	private TextView sat_tv;
	private ApplicationController ac;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView (R.layout.measure_start);
		ac=(ApplicationController)this.getApplicationContext();
		
		buoy=getIntent().getExtras().getInt("buoy");
		sat_tv=(TextView)findViewById(R.id.sat_count);
		if (ac.getLastLocation()==null)
			sat_tv.setText("NO GPS");
		else
			sat_tv.setText(""+ac.getLastLocation().getAccuracy()+"m");
		
		ac.getLocationManager().requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		
		ImageView bouy_icon=(ImageView)findViewById(R.id.buoy_icon);
		bouy_icon.setImageResource(AppDefs.foo.get(buoy).image_resId);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onLocationChanged(Location location) {
		sat_tv.setText(""+location.getAccuracy()+"m");

	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		sat_tv.setText(""+ac.getLastLocation().getAccuracy()+"m");

	}

}
