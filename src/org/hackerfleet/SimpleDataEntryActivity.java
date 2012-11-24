package org.hackerfleet;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import org.apache.http.StatusLine;
import org.hackerfleet.etc.AppDefs;
import org.hackerfleet.etc.Network;
import org.hackerfleet.model.Bearing;
import org.hackerfleet.model.Buoy;
import org.json.JSONException;

import java.io.IOException;
import java.util.*;

/**
 * @author flashmop
 * @date 24.11.12 09:49
 */
public class SimpleDataEntryActivity extends SherlockActivity implements Network.ResultListener {
  private final static String TAG = SimpleDataEntryActivity.class.getSimpleName();

  private static final String EXTRA_BUOYS = "buoys";

  private ApplicationController ac;
  LocationManager locationManager;
  EditText lat;
  EditText lon;
  EditText acc;
  EditText timestamp;
  EditText uuid;
  private Location lastLocation;

  public static void start(Context context, ArrayList<Buoy> buoys) {
    Intent start = new Intent(context,
        SimpleDataEntryActivity.class);
    start.putParcelableArrayListExtra(EXTRA_BUOYS, buoys);
  }

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

    lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
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

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = this.getSupportMenuInflater();
    inflater.inflate(R.menu.measure_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.menu_add:
        break;
      case R.id.menu_done:
        try {
          Bearing bearing
              = new Bearing(System.currentTimeMillis(), lastLocation);
          List<Bearing> bearings = new ArrayList<Bearing>();
          bearings.add(bearing);
          Buoy buoy = new Buoy(AppDefs.buoyDefinitions.get(R.id.north_btn), null, bearings);
          Network.upload(this, buoy);
        } catch (IOException ioe) {
          Log.e(AppDefs.TAG, "Error while uploading", ioe);
        } catch (JSONException jsonE) {
          Log.e(AppDefs.TAG, "Error while uploading", jsonE);
        }
        break;
    }
    return super.onOptionsItemSelected(item);    //To change body of overridden methods use File | Settings | File Templates.
  }


  @Override
  public void onResponse(StatusLine statusLine) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void onResult(String result) {
    //To change body of implemented methods use File | Settings | File Templates.
  }
}