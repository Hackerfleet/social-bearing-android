package org.hackerfleet;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import org.holoeverywhere.app.Application;

import java.util.ArrayList;
import java.util.List;

public class ApplicationController extends Application implements LocationListener {

  private Location last_loc;
  private LocationManager mLocationManager;
  private List<LocationListener> externalListeners;

  @Override
  public void onCreate() {
    mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
    enableLocationUpdates();
    externalListeners = new ArrayList<LocationListener>();
    super.onCreate();
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
}
