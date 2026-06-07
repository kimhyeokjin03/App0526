package com.example.app0526;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class RestaurantSharedViewModel extends ViewModel {

    private final MutableLiveData<List<RestaurantResponse>> restaurants = new MutableLiveData<>();
    private final MutableLiveData<RestaurantResponse> selectedRestaurant = new MutableLiveData<>();

    public LiveData<List<RestaurantResponse>> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<RestaurantResponse> list) {
        restaurants.setValue(list);
    }

    public LiveData<RestaurantResponse> getSelectedRestaurant() {
        return selectedRestaurant;
    }

    public void setSelectedRestaurant(RestaurantResponse restaurant) {
        selectedRestaurant.setValue(restaurant);
    }
}