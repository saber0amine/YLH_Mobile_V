package com.example.yallah_project.activity;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yallah_project.R;
import com.example.yallah_project.model.Location;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {

    private List<Location> locationList;
    private Context context;

    public LocationAdapter(Context context, List<Location> locationList) {
        this.context = context;
        this.locationList = locationList;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_item, parent, false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        Location location = locationList.get(position);
        holder.bind(location);
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

    public class LocationViewHolder extends RecyclerView.ViewHolder {

        private TextView addressTextView;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            addressTextView = itemView.findViewById(R.id.addressTextView);
        }

        public void bind(Location location) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            String address = getAddressFromLatLng(latitude, longitude);
            addressTextView.setText(getAddressFromLatLng(latitude, longitude));
        }

        private String getAddressFromLatLng(double latitude, double longitude) {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                if (addresses != null && !addresses.isEmpty()) {
                    Address address = addresses.get(0);
                    return address.getAddressLine(0);
                } else {
                    return "Unknown address";
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "Error fetching address";
            }
        }
    }
}
