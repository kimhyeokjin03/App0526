package com.example.app0526;

public class RestaurantResponse {
    private Long id;
    private String name;
    private Double latitude;
    private Double longitude;
    private String feature;
    private String imageUrl;

    public Long getId() {
        return id;
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