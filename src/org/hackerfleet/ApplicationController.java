package org.hackerfleet;

import org.hackerfleet.etc.AppDefs;
import org.holoeverywhere.app.Application;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class ApplicationController extends Application {

	private Location last_loc;
	
	@Override
	public void onCreate() {
		LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

		Bundle b;
		
		LocationListener locationListener = new LocationListener() {
		    public void onLocationChanged(Location location) {
		    	last_loc=location;
		    }

		    public void onStatusChanged(String provider, int status, Bundle extras) {}

		    public void onProviderEnabled(String provider) {}

		    public void onProviderDisabled(String provider) {}
		  };

		// Register the listener with the Location Manager to receive location updates
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
		
		super.onCreate();
	}

	public Location getLastLocation() {
		return last_loc;
	}
	
}
