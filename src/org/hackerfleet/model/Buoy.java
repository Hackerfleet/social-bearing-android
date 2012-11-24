package org.hackerfleet.model;

import android.os.Parcel;
import android.os.Parcelable;
import org.hackerfleet.etc.BuoyDefinition;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Buoy implements Parcelable {

  private static final String BUOY_TYPE = "buoy_type";
  private static final String BUOY_ID = "buoy_id";
  private static final String BEARINGS = "bearings";
  private List<Bearing> bearings;
  private String buoyType;
  private String buoyId;
  private JSONObject json;

  public Buoy(BuoyDefinition definition, String buoyId, List<Bearing> bearings) {
    this.bearings = bearings;
    buoyType = definition.tag;
    this.buoyId = buoyId;
  }

  public JSONObject toJSON() throws JSONException {
    if (json == null) {
      json = new JSONObject();
      json.put(BUOY_TYPE, buoyType);
      json.put(BUOY_ID, buoyId);
      JSONArray array = new JSONArray();
      for (Bearing bearing : bearings) {
        array.put(bearing.toJSON());
      }
      json.put(BEARINGS, array);
    }
    return json;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeParcelableArray((Parcelable[]) bearings.toArray(), flags);
    dest.writeString(buoyType);
    dest.writeString(buoyId);
  }


  public static final Creator<Buoy> CREATOR = new Creator<Buoy>() {
    public Buoy createFromParcel(Parcel in) {
      return new Buoy(in);
    }

    public Buoy[] newArray(int size) {
      return new Buoy[size];
    }
  };

  private Buoy(Parcel in) {
    bearings = new ArrayList<Bearing>();
    bearings.addAll(
        Arrays.asList((Bearing[]) in.readArray(ClassLoader.getSystemClassLoader())));
    buoyType = in.readString();
    buoyId = in.readString();
  }
}


