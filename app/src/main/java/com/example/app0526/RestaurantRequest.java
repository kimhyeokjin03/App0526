package com.example.app0526;

public class RestaurantRequest {
    private String name;
    private Double latitude;
    private Double longitude;
    private String feature;
    private String imageUrl;

    public RestaurantRequest(String name, Double latitude, Double longitude,
                             String feature, String imageUrl) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.feature = feature;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getFeature() {
        return feature;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
