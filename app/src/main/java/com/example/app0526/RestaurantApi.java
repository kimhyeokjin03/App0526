package com.example.app0526;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RestaurantApi {

    @GET("/api/restaurants")
    Call<List<RestaurantResponse>> findAll();

    @GET("/api/restaurants/{id}")
    Call<RestaurantResponse> findById(@Path("id") Long id);

    @POST("/api/restaurants")
    Call<RestaurantResponse> create(@Body RestaurantRequest request);

    @PUT("/api/restaurants/{id}")
    Call<RestaurantResponse> update(
            @Path("id") Long id,
            @Body RestaurantRequest request
    );

    @DELETE("/api/restaurants/{id}")
    Call<Void> delete(@Path("id") Long id);
}