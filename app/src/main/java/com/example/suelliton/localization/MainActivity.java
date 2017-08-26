package com.example.suelliton.localization;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.location.Location;
import android.location.LocationListener;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements LocationListener {
    public TextView latitude;
    public TextView longitude;
    Double lat;
    Double lng;
    private int interval = 2000;//valor default para interval

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView debug = (TextView) findViewById(R.id.debug);
        latitude = (TextView) findViewById(R.id.tx_latitude);
        longitude = (TextView) findViewById(R.id.tx_longitude);

        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(100);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress >0 && progress <25){
                    interval = 500;
                    debug.setText(interval+"ms");
                }else if(progress > 25 && progress < 50){
                    interval = 1000;
                    debug.setText(interval+"ms");
                }else if(progress >50 && progress <75){
                    interval = 3000;
                    debug.setText(interval+"ms");
                }else{
                    interval = 5000;
                    debug.setText(interval+"ms");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Button btn_maps = (Button) findViewById(R.id.btn_maps);
        btn_maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putDouble("latitude", lat);
                bundle.putDouble("longitude", lng);
                Intent intent = new Intent(MainActivity.this,MapsActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        getLocation();

    }

    public void getLocation(){
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            public void run() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        GPSTracker  gps = new GPSTracker(MainActivity.this);
                        if (gps.canGetLocation()) {
                            // passa sua latitude e longitude para duas variaveis
                            lat = gps.getLatitude();
                            lng = gps.getLongitude();
                            latitude.setText(""+lat);
                            longitude.setText(""+lng);
                        }
                        getLocation();
                    }
                }, interval);
            }
        }).start();



}

    @Override
    public void onLocationChanged(Location location) {

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