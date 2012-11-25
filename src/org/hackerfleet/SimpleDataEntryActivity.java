package org.hackerfleet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.http.StatusLine;
import org.hackerfleet.etc.AppDefs;
import org.hackerfleet.etc.Network;
import org.hackerfleet.model.Bearing;
import org.hackerfleet.model.Buoy;
import org.holoeverywhere.widget.Toast;
import org.json.JSONException;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.EditText;

/**
 * @author flashmop
 * @date 24.11.12 09:49
 */
public class SimpleDataEntryActivity extends SherlockActivity implements Network.ResultListener {
  private final static String TAG = SimpleDataEntryActivity.class.getSimpleName();

  private static final String EXTRA_BEARINGS = "bearings";
  private static final int ENOUGH_BEARINGS = 10;

  private ApplicationController ac;
  LocationManager locationManager;
  EditText lat;
  EditText lon;
  EditText acc;
  EditText timestamp;
//  EditText uuid;
  EditText angle;
  EditText buoyType;

  private Location lastLocation;
  private ArrayList<Bearing> bearings;

  public static void start(Context context, ArrayList<Bearing> bearings) {
    Intent start = new Intent(context,
        SimpleDataEntryActivity.class);
    start.putParcelableArrayListExtra(EXTRA_BEARINGS, bearings);
    context.startActivity(start);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    ac = (ApplicationController) this.getApplicationContext();
    bearings = new ArrayList<Bearing>();
    setContentView(R.layout.simple_data_entry);
    getSupportActionBar().setTitle("Corellian Engineering Corporation");

    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

    lat = (EditText) findViewById(R.id.gps_lat);
    lon = (EditText) findViewById(R.id.gps_lon);
    acc = (EditText) findViewById(R.id.gps_acc);
    timestamp = (EditText) findViewById(R.id.timestamp);
    angle = (EditText) findViewById(R.id.angle);
    buoyType = (EditText) findViewById(R.id.buoy_type);
    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

//    uuid = (EditText) findViewById(R.id.uuid);
  }

  @Override
  protected void onNewIntent(Intent intent) {
    bearings = intent.getParcelableArrayListExtra(EXTRA_BEARINGS);
  }

  @Override
  public void onResume() {
    super.onResume();

    lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    Log.d(TAG, "lastLocation: " + lastLocation);

    if (lastLocation != null) {

      lat.setText("" + String.format(Locale.getDefault(), "%.4f", lastLocation.getLatitude()));
      lon.setText("" + String.format(Locale.getDefault(), "%.4f", lastLocation.getLongitude()));
      String accuracyString = String.format("%.1f", lastLocation.getAccuracy());
      acc.setText(String.format(getString(R.string.location_accuracy_format), accuracyString));

      Date locationDate = new Date(lastLocation.getTime());
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(locationDate);
      int hour = calendar.get(Calendar.HOUR_OF_DAY);
      int minute = calendar.get(Calendar.MINUTE);
      int second = calendar.get(Calendar.SECOND);
      timestamp.setText(String.format("%02d:%02d:%02d", hour, minute, second));

//      uuid.setText(ac.getUuid().toString());
    }

    if (bearings != null && bearings.size() == 1) {
      getSupportActionBar().setSubtitle(bearings.size() + " Bearing collected");
    }
    if (bearings != null && bearings.size() > 1) {
      getSupportActionBar().setSubtitle(bearings.size() + " Bearings collected");
    }
    if (bearings == null || bearings.size() == 0) {
      getSupportActionBar().setSubtitle("0 Bearings collected");
    }

  }

  @Override
  protected void onStart() {
    super.onStart();

    bearings = getIntent().getParcelableArrayListExtra(MeasureStartActivity.EXTRA_KEY_BEARINGS);
    int buoyExtra = getIntent().getIntExtra(AppDefs.EXTRA_BUOY, 0);
    buoyType.setText(AppDefs.buoyDefinitions.get(buoyExtra).tag);
    if (bearings == null) {
      bearings = new ArrayList<Bearing>();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = this.getSupportMenuInflater();
    inflater.inflate(R.menu.measure_menu, menu);
    return true;
  }

  @Override
  protected Dialog onCreateDialog(int id, Bundle args) {
    switch (id) {
      case ENOUGH_BEARINGS:
        return new AlertDialog.Builder(SimpleDataEntryActivity.this).setTitle(R.string.enough_bearings).setMessage(R.string.enough_bearings_message).setPositiveButton(android.R.string.ok
            , new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            uploadBearing();
          }
        }).create();
      default:
        return super.onCreateDialog(id, args);
    }
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.menu_add: {
        if (bearings.size() > ENOUGH_BEARINGS) {
          showDialog(ENOUGH_BEARINGS);
        } else {
          if (createAndAddBearing()) {
            Toast.makeText(this, R.string.bearing_added, Toast.LENGTH_SHORT).show();
            Intent resultIntent = new Intent();
            resultIntent.putExtra(MeasureStartActivity.EXTRA_KEY_BEARINGS, bearings);

            setResult(MeasureStartActivity.RESULT_OK, resultIntent);
            finish();
          }
        }
        return true;
      }
      case R.id.menu_done: {
        uploadBearing();
        return true;
      }
      default: {
        return super.onOptionsItemSelected(item);
      }
    }
  }

  @Override
  public void onResponse(StatusLine statusLine) {
    //TODO define exit condtitions
    if (statusLine != null) {
      Toast.makeText(this, "response: " + statusLine.getStatusCode() + ": " + statusLine.getReasonPhrase(), Toast.LENGTH_SHORT).show();
    } else {
      Toast.makeText(this, "response: was nullo", Toast.LENGTH_SHORT).show();
    }
  }

  @Override
  public void onResult(String result) {
    //To change body of implemen
    // #ted methods use File | Settings | File Templates.
  }

  private boolean createAndAddBearing() {
    int bearingAngle;
    try {

      bearingAngle = validateAngle(angle.getText().toString());

      Bearing bearing = new Bearing(lastLocation, bearingAngle);
      bearings.add(bearing);
      return true;

    } catch (NumberFormatException e) {

      Toast.makeText(this, "Please enter valid angle (0-359)", android.widget.Toast.LENGTH_LONG).show();
      return false;
    }


  }

  private int validateAngle(String s) {

    return Integer.valueOf(s);
  }


  private void uploadBearing() {
    if (createAndAddBearing()) {
      try {

        int numBearings = bearings.size();
        String toastText = getResources().getQuantityString(R.plurals.bearings_sent, numBearings, numBearings);
        Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show();

        Buoy buoy = new Buoy(AppDefs.buoyDefinitions.get(R.id.north_btn), null, bearings);
        Log.d(AppDefs.TAG, buoy.toJSON().toString());
        Network.upload(this, buoy);
      } catch (IOException ioe) {

        Log.e(AppDefs.TAG, "Error while uploading", ioe);
      } catch (JSONException jsonE) {

        Log.e(AppDefs.TAG, "Error while uploading", jsonE);
      }
      finish();
    }

  }
}