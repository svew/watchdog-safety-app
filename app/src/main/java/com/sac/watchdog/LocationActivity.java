package com.sac.watchdog;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
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
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.user.speechrecognizationasservice.R;

public class LocationActivity extends AppCompatActivity {

    private LocationManager location_manager;
    private LocationListener location_listener;

    private String link = "https://maps.google.com/?q=";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        AudioManager am =
                (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        am.setStreamVolume(
                AudioManager.STREAM_MUSIC,
                am.getStreamMaxVolume(AudioManager.STREAM_MUSIC),
                0);

        MediaPlayer mpintro = MediaPlayer.create(this, R.raw.police);
        mpintro.setLooping(true);
        mpintro.start();
        final Toast[] mToast = {null};

        CountDownTimer timer = new CountDownTimer(10000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                if (mToast[0] != null) mToast[0].cancel();
                mToast[0] = Toast.makeText(getApplicationContext(), "will notify emergency \ncontacts in : \n" + millisUntilFinished / 1000 + " Seconds", Toast.LENGTH_SHORT);
                mToast[0].show();
            }

            @Override
            public void onFinish() {
                if (mToast[0] != null) mToast[0].cancel();
                mToast[0] = Toast.makeText(getApplicationContext(), "notifying emergency contacts", Toast.LENGTH_LONG);
                mToast[0].show();


                location_manager = (LocationManager) getSystemService(LOCATION_SERVICE);

                location_listener = new LocationListener() {

                    @Override
                    public void onLocationChanged(Location location) {

                        // Ensure the location is accurate
                        if (location.getAccuracy() < 25) {
                            link += location.getLatitude() + "," + location.getLongitude();
                            // Send sms here
                            String fullMessage = "CALL ME, THIS AN EMERGENCY. IF I DON'T ANSWER, PLEASE CALL 911." +
                                    "THIS WAS MY LAST KNOWN LOCATION\n" + link;
                            Util.notifyEmergencyContacts(getBaseContext(), fullMessage);
                            location_manager.removeUpdates(location_listener);
                        }
                    }

                    @Override
                    public void onStatusChanged(String s, int i, Bundle bundle) {
                    }

                    @Override
                    public void onProviderEnabled(String s) {
                    }

                    @Override
                    public void onProviderDisabled(String s) {
                        Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(i);
                    }
                };
                requestLocation();
            }
        }.start();


        ((Button) findViewById(R.id.stopButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.cancel();
                mpintro.stop();
            }
        });
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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // Request permissions
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 10);
            return;
        }

        location_manager.requestLocationUpdates("gps", 500, 0, location_listener);
    }

    protected void onDestroy() {
//        stopService(new Intent(LocationActivity.this, Alarm.class));
        super.onDestroy();
    }
}