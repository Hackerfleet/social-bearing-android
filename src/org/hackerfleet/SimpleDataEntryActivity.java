package org.hackerfleet;

import java.util.Date;
import java.util.Locale;

import com.actionbarsherlock.app.SherlockActivity;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

/**
 * @author flashmop
 * @date 24.11.12 09:49
 */
public class SimpleDataEntryActivity extends SherlockActivity {
  private final static String TAG = SimpleDataEntryActivity.class.getSimpleName();

  private ApplicationController ac;
  LocationManager locationManager;
  EditText        lat;
  EditText        lon;
  EditText        acc;
  EditText timestamp;
  EditText uuid;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    ac = (ApplicationController) this.getApplicationContext();

    setContentView(R.layout.simple_data_entry);
    getSupportActionBar().setTitle("Corellian Engineering Corporation");

    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

    lat = (EditText) findViewById(R.id.gps_lat);
    lon = (EditText) findViewById(R.id.gps_lon);
    acc = (EditText) findViewById(R.id.gps_acc);
    timestamp = (EditText) findViewById(R.id.timestamp);
    uuid = (EditText) findViewById(R.id.uuid);


  }

  @Override
  public void onResume() {
    super.onResume();

    Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    Log.d(TAG, "lastLocation: " + lastLocation);

    if (lastLocation != null) {

      lat.setText("" + String.format(Locale.getDefault(), "%.4f", lastLocation.getLatitude()));
      lon.setText("" + String.format(Locale.getDefault(), "%.4f", lastLocation.getLongitude()));
      acc.setText(String.format(Locale.getDefault(), "%.1f", lastLocation.getAccuracy()) + "m");
      Date locationDate = new Date(lastLocation.getTime());
      timestamp.setText(locationDate.getHours() + ":" + locationDate.getMinutes() + ":" + locationDate.getSeconds());
      uuid.setText(ac.getUuid().toString());
    }

  }
}