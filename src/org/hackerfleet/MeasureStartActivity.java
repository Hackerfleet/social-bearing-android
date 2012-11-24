package org.hackerfleet;

import org.hackerfleet.etc.AppDefs;
import org.holoeverywhere.app.Activity;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.TextView;

import android.content.Intent;
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
  private Button startButton;

  public final static String EXTRAS_KEY_LOCATION = "location";
//  public final static String EXTRAS_KEY_LAT = "latitude";
//  public final static String EXTRAS_KEY_LON = "longitude";
//  public final static String EXTRAS_KEY_ACC = "accuracy";

  private Location location;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.measure_start);
    ac = (ApplicationController) this.getApplicationContext();

    startButton = (Button) findViewById(R.id.start_btn);

    startButton.setOnClickListener(this);

    buoy = getIntent().getExtras().getInt("buoy");
    sat_tv = (TextView) findViewById(R.id.sat_count);
    if (ac.getLastLocation() == null)
      sat_tv.setText("NO GPS");
    else
      sat_tv.setText("" + ac.getLastLocation().getAccuracy() + "m");

    ac.getLocationManager().requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

    ImageView buoyIcon = (ImageView) findViewById(R.id.buoy_icon);
    buoyIcon.setImageResource(AppDefs.buoyDefinitions.get(buoy).image_resId);
    super.onCreate(savedInstanceState);
  }

  @Override
  public void onLocationChanged(Location location) {
    sat_tv.setText("" + location.getAccuracy() + "m");

  }

  @Override
  public void onProviderDisabled(String provider) {
  }

  @Override
  public void onProviderEnabled(String provider) {
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
