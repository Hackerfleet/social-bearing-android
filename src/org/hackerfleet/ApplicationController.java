package org.hackerfleet;

import java.util.UUID;

import org.holoeverywhere.app.Application;
import org.holoeverywhere.preference.SharedPreferences;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class ApplicationController extends Application implements LocationListener{

  public static final String PREFS_KEY_UUID = "uuid";
  private Location        last_loc;
  private LocationManager mLocationManager;
  private UUID            uuid;

  @Override
  public void onCreate() {
    mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

    super.onCreate();

    final SharedPreferences sharedPreferences = getSharedPreferences(ApplicationController.class.getSimpleName(), Context.MODE_PRIVATE);
    String uuidString = sharedPreferences.getString(PREFS_KEY_UUID, null);

    if (uuidString != null) {
      uuid = UUID.fromString(uuidString);
    } else {

      uuid = UUID.randomUUID();
      SharedPreferences.Editor editor = sharedPreferences.edit();

      editor.putString(PREFS_KEY_UUID, uuid.toString());

      editor.commit();

    }

  }

  public Location getLastLocation() {
    return last_loc;
  }

  public LocationManager getLocationManager() {
		return mLocationManager;
	}

  public void onLocationChanged(Location location) {
    last_loc=location;
  }

  public void onStatusChanged(String provider, int status, Bundle extras) {}

  public void onProviderEnabled(String provider) {}

  public void onProviderDisabled(String provider) {}


  public UUID getUuid() {
    return uuid;
  }

  public void setUuid(UUID uuid) {
    this.uuid = uuid;
  }

}
