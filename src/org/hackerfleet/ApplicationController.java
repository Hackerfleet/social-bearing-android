package org.hackerfleet;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import org.holoeverywhere.app.Application;
import org.holoeverywhere.preference.SharedPreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ApplicationController extends Application implements LocationListener {

  private static final String PREFS_KEY_UUID = "uuid";
  private Location last_loc;
  private LocationManager mLocationManager;
  private List<LocationListener> externalListeners;
  private UUID uuid;

  @Override
  public void onCreate() {
    mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
    enableLocationUpdates();
    externalListeners = new ArrayList<LocationListener>();

    final SharedPreferences sharedPreferences = getSharedPreferences(ApplicationController.class.getSimpleName(), Context.MODE_PRIVATE);
    String uuidString = sharedPreferences.getString(PREFS_KEY_UUID, null);

    if (uuidString != null) {
      uuid = UUID.fromString(uuidString);
    } else {

      uuid = UUID.randomUUID();
      SharedPreferences.Editor editor = sharedPreferences.edit();

      editor.putString(PREFS_KEY_UUID, uuid.toString());

      editor.commit();
      super.onCreate();
    }
  }

  public Location getLastLocation() {
    return last_loc;
  }

  public void addLocationListener(LocationListener listener) {
    if (!externalListeners.contains(listener)) {
      externalListeners.add(listener);
      mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }
  }

  public void disableLocationUpdates() {
    mLocationManager.removeUpdates(this);
    for (LocationListener listener : externalListeners) {
      mLocationManager.removeUpdates(listener);
    }
  }

  public void enableLocationUpdates() {
    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
  }

  public LocationManager getLocationManager() {
    return mLocationManager;
  }

  public void onLocationChanged(Location location) {
    last_loc = location;
    for (LocationListener listener : externalListeners)
      listener.onLocationChanged(location);
  }

  public void onStatusChanged(String provider, int status, Bundle extras) {
    for (LocationListener listener : externalListeners)
      listener.onStatusChanged(provider, status, extras);
  }

  public void onProviderEnabled(String provider) {
    for (LocationListener listener : externalListeners)
      listener.onProviderEnabled(provider);
  }

  public void onProviderDisabled(String provider) {
    for (LocationListener listener : externalListeners)
      listener.onProviderDisabled(provider);
  }

  public void setUuid(UUID uuid) {
    this.uuid = uuid;
  }

  public UUID getUuid() {
    return uuid;
  }
}
