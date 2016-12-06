package com.example.admin.pagination.Serializables;

/**
 * Created by ulan on 12/5/16.
 */
public class CountryRX {
    int id;
    String country,image;

    public void setCountry(String country) {
        this.country = country;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public int getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }
}
