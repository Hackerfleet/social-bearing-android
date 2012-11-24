package org.hackerfleet;

import org.holoeverywhere.app.Application;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class ApplicationController extends Application implements LocationListener{

	private Location last_loc;
	private LocationManager mLocationManager;
	
	@Override
	public void onCreate() {
		mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

		mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		
		super.onCreate();
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
}
