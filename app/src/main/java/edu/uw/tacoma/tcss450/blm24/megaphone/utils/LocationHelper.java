package edu.uw.tacoma.tcss450.blm24.megaphone.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

public final class LocationHelper {

    private static Double LAT, LON;

    private static LocationManager MANAGER;
    private static LocationListener LISTENER;
    private static String PROVIDER;

    private static String[] PERMISSIONS = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    public static boolean setup(Activity activity) {
        if (LISTENER != null) {
            return true;
        }
        if (MANAGER == null) {
            MANAGER = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        }
        boolean enabled = false;
        boolean permissed = false;
        while (!permissed) {
            ActivityCompat.requestPermissions(activity, PERMISSIONS, 0);
            permissed |= ActivityCompat.checkSelfPermission(activity,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED;
            permissed |= ActivityCompat.checkSelfPermission(activity,
                    Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED;
        }
        enabled |= MANAGER.isProviderEnabled(LocationManager.GPS_PROVIDER);
        enabled |= MANAGER.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (!enabled) {
            return false;
        }
        LocationListener listener = new Listener();
        Criteria crit = new Criteria();
        crit.setAccuracy(Criteria.ACCURACY_FINE);
        PROVIDER = MANAGER.getBestProvider(crit, true);
        MANAGER.requestLocationUpdates(PROVIDER, 5000, 1f, listener);
        LISTENER = listener;
        return true;
    }

    public static Double getLAT() {
        return LAT;
    }

    public static Double getLON() {
        return LON;
    }

    public static boolean hasLocation() {
        return getLON() != null && getLON() != null;
    }

    public static Float distance(double lat, double lon) {
        if (LAT == null || LON == null) {
            return null;
        }
        Location other = new Location(PROVIDER);
        other.setLatitude(lat);
        other.setLongitude(lon);
        Float distance = null;
        try {
            distance = MANAGER.getLastKnownLocation(PROVIDER).distanceTo(other);
        } catch (SecurityException err) {
            Log.e("Location", "Cannot get last location.");
        }
        return distance;
    }

    public static String distanceString(double lat, double lon) {
        Float distance = distance(lat, lon);
        if(distance == null) {
            return null;
        }
        return String.format("%.2f", distance);
    }

    private static class Listener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            LAT = location.getLatitude();
            LON = location.getLongitude();
            Log.i("Location", LAT +", " + LON);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }
}
