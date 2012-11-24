package org.hackerfleet;

import android.app.Activity;
import android.content.Intent;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import org.hackerfleet.etc.AppDefs;

import java.util.Iterator;

public class MeasureStartActivity extends Activity implements LocationListener, GpsStatus.Listener, View.OnClickListener {

  private Integer buoy;
  private TextView satellitesTextView, accuracyTextView;
  private ApplicationController ac;
  private Button startButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.measure_start);
    ac = (ApplicationController) this.getApplicationContext();

    startButton = (Button) findViewById(R.id.start_btn);

    startButton.setOnClickListener(this);
    ac.getLocationManager().addGpsStatusListener(this);

    buoy = getIntent().getExtras().getInt("buoy");
    satellitesTextView = (TextView) findViewById(R.id.sat_count);
    accuracyTextView = (TextView) findViewById(R.id.accuracy);
    if (ac.getLastLocation() == null)
      satellitesTextView.setText("NO GPS");
    else
      accuracyTextView.setText(ac.getLastLocation().getAccuracy() + "m");

    accuracyTextView = (TextView) findViewById(R.id.accuracy);

    ac.addLocationListener(this);

    ImageView buoyIcon = (ImageView) findViewById(R.id.buoy_icon);
    buoyIcon.setImageResource(AppDefs.buoyDefinitions.get(buoy).image_resId);
    super.onCreate(savedInstanceState);
  }

  @Override
  protected void onPause() {
    super.onPause();
    ac.disableLocationUpdates();
  }

  @Override
  public void onLocationChanged(Location location) {
    accuracyTextView.setText("" + location.getAccuracy() + "m");

  }

  @Override
  public void onProviderDisabled(String provider) {
  }

  @Override
  public void onProviderEnabled(String provider) {
  }

  @Override
  public void onStatusChanged(String provider, int status, Bundle extras) {
    accuracyTextView.setText("" + ac.getLastLocation().getAccuracy() + "m");
  }

  @Override
  public void onClick(View view) {
    Intent startSimpleForm = new Intent(MeasureStartActivity.this, SimpleDataEntryActivity.class);
    startActivity(startSimpleForm);
  }

  @Override
  public void onGpsStatusChanged(final int event) {
    switch (event) {
      case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
        GpsStatus status = ac.getLocationManager().getGpsStatus(null);
        Iterator<GpsSatellite> satelliteIterator = status.getSatellites().iterator();
        GpsSatellite satellite;
        int i = 0;
        while (satelliteIterator.hasNext() && (satellite = satelliteIterator.next()) != null) {
          if (satellite.usedInFix()) i++;
        }
        if (i > 0)
          satellitesTextView.setText(String.format("%d", i));
        break;
      default:
        break;
    }
  }
}
