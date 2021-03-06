package org.hackerfleet.socialbearing;

import java.util.ArrayList;
import java.util.Iterator;

import org.hackerfleet.socialbearing.etc.AppDefs;
import org.hackerfleet.socialbearing.model.Bearing;

import com.actionbarsherlock.app.SherlockActivity;

import android.content.Intent;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MeasureStartActivity extends SherlockActivity implements LocationListener, GpsStatus.Listener, View.OnClickListener {

  public static final  String EXTRA_KEY_BEARINGS = "bearings";
  private static final int    REQUEST_BEARINGS   = 1337;
  private static final String TAG                = MeasureStartActivity.class.getSimpleName();
  private Integer buoy;
  private TextView satellitesTextView, accuracyTextView;
  private ApplicationController ac;
  private Button             startButton;
  private ArrayList<Bearing> bearings;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.measure_start);
    ac = (ApplicationController) this.getApplicationContext();


    startButton = (Button) findViewById(R.id.start_btn);

    startButton.setOnClickListener(this);
    startButton.setEnabled(false);

    ac.getLocationManager().addGpsStatusListener(this);

    buoy = getIntent().getExtras().getInt("buoy");
    satellitesTextView = (TextView) findViewById(R.id.sat_count);
    accuracyTextView = (TextView) findViewById(R.id.accuracy);
    if (ac.getLastLocation() == null) {
      satellitesTextView.setText(getString(R.string.no_gps));
    } else {
      displayAccuracy(ac.getLastLocation());
    }

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
  protected void onResume() {
    super.onResume();
    ac.enableLocationUpdates();

    int numBearings = (bearings == null) ? 0 : bearings.size();
    String subtitle = getResources().getQuantityString(R.plurals.bearings_collected, numBearings, numBearings);
    getSupportActionBar().setSubtitle(subtitle);

    Log.d(TAG, "Display bearings: " + bearings);

  }

  @Override
  public void onLocationChanged(Location location) {
    displayAccuracy(location);
  }

  @Override
  public void onProviderDisabled(String provider) {
  }

  @Override
  public void onProviderEnabled(String provider) {
  }

  @Override
  public void onStatusChanged(String provider, int status, Bundle extras) {
    displayAccuracy(ac.getLastLocation());
  }

  @Override
  public void onClick(View view) {
    Intent startSimpleForm = new Intent(MeasureStartActivity.this, SimpleDataEntryActivity.class);
    startSimpleForm.putExtra(AppDefs.EXTRA_BUOY, buoy);

    startSimpleForm.putExtra(EXTRA_KEY_BEARINGS, bearings);

    startActivityForResult(startSimpleForm, REQUEST_BEARINGS);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == REQUEST_BEARINGS) {
      if (resultCode == RESULT_OK) {

        bearings = data.getParcelableArrayListExtra(EXTRA_KEY_BEARINGS);

        Log.d(TAG, "bearings: " + bearings);

      }
    }

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

        if (ac.getLastLocation()==null || (ac.getLastLocation().getAccuracy()>AppDefs.MIN_ACCURACY)) {
        	startButton.setEnabled(false);
        	startButton.setText("");
        } else {
        	startButton.setEnabled(true);
        	startButton.setText(getString(R.string.start_button_label));
        }
        break;
      default:
        break;
    }
  }

  private void displayAccuracy(Location location) {
    String accuracyFormat = getString(R.string.location_accuracy_format);
    accuracyTextView.setText(String.format(accuracyFormat, location.getAccuracy()));
  }
}
