package com.example.yallah_project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;
import com.example.yallah_project.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Arrays;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker selectedMarker;
    private TextInputEditText searchLocation;
    private PlacesClient placesClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_step_three_);

        // Initialize the Places client
        Places.initialize(getApplicationContext(), getString(R.string.google_maps_key));
        placesClient = Places.createClient(this);

        // Initialize Map Fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_container);
        mapFragment.getMapAsync(this);

        searchLocation = findViewById(R.id.searchLocation);
        searchLocation.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String query = searchLocation.getText().toString();
                searchForLocation(query);
                return true;
            }
            return false;
        });
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
            resultIntent.putExtra("address", marker.getTitle());
            setResult(RESULT_OK, resultIntent);
            finish();
            return true;
        });
    }

    private void searchForLocation(String query) {
        // Define the fields to retrieve
        List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);

        // Create a FetchPlaceRequest
        FetchPlaceRequest request = FetchPlaceRequest.builder(query, placeFields).build();

        // Fetch the place details
        placesClient.fetchPlace(request).addOnSuccessListener(response -> {
            Place place = response.getPlace();
            LatLng latLng = place.getLatLng();
            if (latLng != null) {
                // Move the camera to the location and zoom in
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

                // Remove any existing marker
                if (selectedMarker != null) {
                    selectedMarker.remove();
                }

                // Add a new marker at the location
                selectedMarker = mMap.addMarker(new MarkerOptions().position(latLng).title(place.getName()));
            }
        }).addOnFailureListener(exception -> {
            // Handle the error
            Log.e("MapsActivity", "Place not found: " + exception.getMessage());
        });
        }}
