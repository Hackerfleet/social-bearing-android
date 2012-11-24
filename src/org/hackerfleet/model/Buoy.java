package org.hackerfleet.model;

import org.hackerfleet.etc.BuoyDefinition;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Buoy {

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


}


