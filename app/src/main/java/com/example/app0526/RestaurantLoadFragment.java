package com.example.app0526;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantLoadFragment extends Fragment {

    private RestaurantSharedViewModel viewModel;
    private static final String TAG = "RestaurantLoadFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_restaurant_load, container, false);

        viewModel = new ViewModelProvider(requireActivity())
                .get(RestaurantSharedViewModel.class);

        Button btnLoad = view.findViewById(R.id.btnLoadRestaurants);

        btnLoad.setOnClickListener(v -> {
            RestaurantApi api = RetrofitClient.getRestaurantApi();

            api.findAll().enqueue(new Callback<List<RestaurantResponse>>() {
                @Override
                public void onResponse(@NonNull Call<List<RestaurantResponse>> call,
                                       @NonNull Response<List<RestaurantResponse>> response) {

                    if (response.isSuccessful() && response.body() != null) {
                        viewModel.setRestaurants(response.body());
                        NavHostFragment.findNavController(RestaurantLoadFragment.this)
                                .navigate(R.id.action_loadFragment_to_mapFragment);
                    } else {
                        String errorMsg = "응답 실패: " + response.code();
                        Log.e(TAG, errorMsg);
                        Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<RestaurantResponse>> call, @NonNull Throwable t) {
                    String errorMsg = "네트워크 오류: " + t.getMessage();
                    Log.e(TAG, errorMsg, t);
                    Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT).show();
                }
            });
        });

        return view;
    }
}
