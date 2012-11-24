package org.hackerfleet.model;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import org.json.JSONException;
import org.json.JSONObject;

public class Bearing implements Parcelable {


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

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeLong(timestamp);
    dest.writeParcelable(location, flags);
  }

  public static final Parcelable.Creator<Bearing> CREATOR
      = new Parcelable.Creator<Bearing>() {
    public Bearing createFromParcel(Parcel in) {
      return new Bearing(in);
    }

    public Bearing[] newArray(int size) {
      return new Bearing[size];
    }
  };

  private Bearing(Parcel in) {
    timestamp = in.readLong();
    location = in.readParcelable(ClassLoader.getSystemClassLoader());
  }
}
