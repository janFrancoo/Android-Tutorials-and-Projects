package com.janfranco.travelbook;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMapLongClickListener {

    String info;
    Intent intent;
    boolean first;
    private GoogleMap mMap;
    SQLiteDatabase database;
    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        intent = getIntent();
        info = intent.getStringExtra("info");
        database = this.openOrCreateDatabase("Places", MODE_PRIVATE, null);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if(info.matches("old")) {
            int id = intent.getIntExtra("id", 1);
            try {
                Cursor cursor = database.rawQuery("SELECT * FROM places WHERE id = ?",
                        new String[]{String.valueOf(id)});

                int latitudeIndex = cursor.getColumnIndex("latitude");
                int longitudeIndex = cursor.getColumnIndex("longitude");
                double latitude = 0, longitude = 0;

                while (cursor.moveToNext()) {
                    latitude = cursor.getDouble(latitudeIndex);
                    longitude = cursor.getDouble(longitudeIndex);
                }

                cursor.close();

                LatLng goTo = new LatLng(latitude, longitude);
                mMap.addMarker(new MarkerOptions().position(goTo).title("Your Selection"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(goTo, 15));

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    if(!first) {
                        LatLng userLocation = new LatLng(location.getLatitude(),
                                location.getLongitude());
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15));
                    }
                    first = true;
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
            };

            if(Build.VERSION.SDK_INT >= 23){
                if(ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this,
                            new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                } else {
                  locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER,
                          0, 0, locationListener);
                }
            } else {
                locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0,
                        0, locationListener);
            }

        }

        mMap.setOnMapLongClickListener(this);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (grantResults.length > 0) {
            if (requestCode == 1) {
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER,
                            0, 0, locationListener);
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        if(info.matches("new")){
            Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());
            String name = "";

            try {
                List<Address> addressList = geocoder.getFromLocation(latLng.latitude,
                        latLng.longitude, 1);
                if (addressList != null && addressList.size() > 0){
                    if(addressList.get(0).getThoroughfare() != null){
                        name = addressList.get(0).getThoroughfare();
                    }
                }
            } catch (IOException e){
                e.printStackTrace();
            }

            if(!name.matches("")){
                try {
                    database.execSQL("CREATE TABLE IF NOT EXISTS places (id INTEGER PRIMARY KEY, " +
                            "name VARCHAR, latitude DOUBLE, longitude DOUBLE)");
                    String query = "INSERT INTO places (name, latitude, longitude) VALUES (?, ?, ?)";
                    SQLiteStatement sqLiteStatement = database.compileStatement(query);
                    sqLiteStatement.bindString(1, name);
                    sqLiteStatement.bindDouble(2, latLng.latitude);
                    sqLiteStatement.bindDouble(3, latLng.longitude);
                    sqLiteStatement.execute();

                    Toast.makeText(MapsActivity.this, "Saved",
                            Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(MapsActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }

        }
    }
}
