package com.example.app0526;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class RestaurantMapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private RestaurantSharedViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_restaurant_map, container, false);

        viewModel = new ViewModelProvider(requireActivity())
                .get(RestaurantSharedViewModel.class);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager()
                        .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;

        viewModel.getRestaurants().observe(getViewLifecycleOwner(), restaurants -> {
            googleMap.clear();

            for (RestaurantResponse r : restaurants) {
                if (r.getLatitude() == null || r.getLongitude() == null) continue;

                LatLng position = new LatLng(r.getLatitude(), r.getLongitude());

                Marker marker = googleMap.addMarker(
                        new MarkerOptions()
                                .position(position)
                                .title(r.getName())
                                .icon(BitmapDescriptorFactory.defaultMarker())
                );

                marker.setTag(r);
            }

            if (!restaurants.isEmpty()) {
                RestaurantResponse first = restaurants.get(0);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(first.getLatitude(), first.getLongitude()), 14
                ));
            }
        });

        googleMap.setOnMarkerClickListener(marker -> {
            RestaurantResponse restaurant = (RestaurantResponse) marker.getTag();

            viewModel.setSelectedRestaurant(restaurant);

            NavHostFragment.findNavController(RestaurantMapFragment.this)
                    .navigate(R.id.action_mapFragment_to_detailFragment);

            return true;
        });
    }
}