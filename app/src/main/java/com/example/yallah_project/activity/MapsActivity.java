package com.example.yallah_project.activity;


import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;

import com.example.yallah_project.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


// MapsActivity.java
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private Marker selectedMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng defaultLocation = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(defaultLocation).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 10));
        mMap.setOnMapClickListener(latLng -> {
            if (selectedMarker != null) {
                selectedMarker.remove();
            }
            selectedMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("Selected Location"));
        });

        mMap.setOnMarkerClickListener(marker -> {
            LatLng position = marker.getPosition();
            Intent resultIntent = new Intent();
            resultIntent.putExtra("latitude", position.latitude);
            resultIntent.putExtra("longitude", position.longitude);
            setResult(RESULT_OK, resultIntent);
            finish();
            return true;
        });
    }
}
