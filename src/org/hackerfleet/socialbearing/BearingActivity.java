package org.hackerfleet.socialbearing;
import org.holoeverywhere.app.Activity;

import android.os.Bundle;
import android.util.Log;

public class BearingActivity extends Activity {
  /**
   * Called when the activity is first created.
   */

  private final static String TAG = BearingActivity.class.getSimpleName();

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.buoy_select);
    getSupportActionBar().setTitle(R.string.buoy_select);
  }

  @Override
  public void onResume() {
    super.onResume();
    Log.d(TAG, "onResume(): ");

  }
}
