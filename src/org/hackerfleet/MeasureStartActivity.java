package org.hackerfleet;

import org.hackerfleet.etc.AppDefs;
import org.holoeverywhere.app.Activity;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.TextView;

import android.content.Intent;
import android.location.GpsSatellite;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MeasureStartActivity extends Activity implements LocationListener, View.OnClickListener {

  private Integer buoy;
  private TextView sat_tv;
  private ApplicationController ac;
  private Button                startButton;
  private TextView				accuracy_tv;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.measure_start);
    ac = (ApplicationController) this.getApplicationContext();

    startButton = (Button) findViewById(R.id.start_btn);

    startButton.setOnClickListener(this);

    buoy = getIntent().getExtras().getInt("buoy");
    sat_tv = (TextView) findViewById(R.id.sat_count);
    accuracy_tv = (TextView) findViewById(R.id.accuracy_count);
    if (ac.getLastLocation() == null)
    	sat_tv.setText("NO GPS");
    else
    	onLocationChanged(ac.getLastLocation());
      
	ac.getLocationManager().requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		
	ImageView bouy_icon=(ImageView)findViewById(R.id.buoy_icon);
	bouy_icon.setImageResource(AppDefs.foo.get(buoy).image_resId);
	super.onCreate(savedInstanceState);
	}

	@Override
	public void onLocationChanged(Location location) {
		int sat_cnt=0;
		
		if (location == null) {
		    accuracy_tv.setText("NO GPS FIX");
		} else {
			accuracy_tv.setText("Accuracy:"+ac.getLastLocation().getAccuracy()+"m");	
		}
		
		Iterable<GpsSatellite> sats=ac.getLocationManager().getGpsStatus(null).getSatellites();
		
		for (GpsSatellite sat:sats) {
			if (sat.usedInFix())
				sat_cnt++;
		}
		
		sat_tv.setText("Sats:"+sat_cnt);
		
	}

  @Override
  public void onLocationChanged(Location location) {
    sat_tv.setText("" + location.getAccuracy() + "m");

  }

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		sat_tv.setText(""+ac.getLastLocation().getAccuracy()+"m");
		accuracy_tv.setText(""+ac.getLastLocation().getAccuracy()+"m");
	}

  @Override
  public void onStatusChanged(String provider, int status, Bundle extras) {
    sat_tv.setText("" + ac.getLastLocation().getAccuracy() + "m");
  }

  @Override
  public void onClick(View view) {
    Intent startSimpleForm = new Intent(MeasureStartActivity.this, SimpleDataEntryActivity.class);
    startActivity(startSimpleForm);
  }

}
