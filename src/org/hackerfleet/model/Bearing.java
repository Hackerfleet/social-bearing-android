package org.hackerfleet.model;

import android.location.Location;
import org.json.JSONException;
import org.json.JSONObject;

public class Bearing {

  private static final String TIMESTAMP = "timestamp";
  private static final String LAT = "lat";
  private static final String LON = "lon";
  private static final String BEARING = "bearing";
  private static final String ACCURACY = "accuracy";

  private long timestamp;
  private Location location;
  private JSONObject json;

  public Bearing(long timestamp, Location location) {
    this.timestamp = timestamp;
    this.location = location;
  }

  public JSONObject toJSON() throws JSONException {
    if (json == null) {
      json = new JSONObject();
      json.put(TIMESTAMP, timestamp);
      json.put(LAT, location.getLatitude());
      json.put(LON, location.getLongitude());
      json.put(BEARING, location.getBearing());
      json.put(ACCURACY, location.getAccuracy());
    }
    return json;
  }
}
