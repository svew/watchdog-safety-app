package hackisu.fall2019.watchdog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

    private LocationManager location_manager;
    private LocationListener location_listener;

    private String link = "https://maps.google.com/?q=";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(MainActivity.this, Alarm.class));

        location_manager = (LocationManager) getSystemService(LOCATION_SERVICE);

        location_listener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {

                // Ensure the location is accurate
                if (location.getAccuracy() < 25) {
                    link += location.getLatitude() + "," + location.getLongitude();
                    // Send sms here

                    location_manager.removeUpdates(location_listener);
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) { }

            @Override
            public void onProviderEnabled(String s) { }

            @Override
            public void onProviderDisabled(String s) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };

        requestLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                requestLocation();
                break;
            default:
                break;
        }
    }

    void requestLocation() {

        // Location permission check
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // Request permission
            requestPermissions(new String[] { Manifest.permission.ACCESS_COARSE_LOCATION }, 10);
            return;
        }

        location_manager.requestLocationUpdates("gps", 5000, 0, location_listener);
    }

    protected void onDestroy() {
        stopService(new Intent(MainActivity.this, Alarm.class));
        super.onDestroy();
    }
}