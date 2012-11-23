package org.hackerfleet;
import org.holoeverywhere.app.Activity;

import android.os.Bundle;

public class BearingActivity extends Activity {
  /**
   * Called when the activity is first created.
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate (savedInstanceState);
    setContentView (R.layout.main);
    getSupportActionBar().setTitle("Corellian Engineering Corporation");
  }
}
