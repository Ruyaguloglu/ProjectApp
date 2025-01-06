package com.example.projectapp.models;

public class CategoryModel {
    private String name;
    private String image_url;

    // Boş Constructor (Firebase için gereklidir)
    public CategoryModel() {
    }

    // Parametreli Constructor
    public CategoryModel(String name, String image_url) {
        this.name = name;
        this.image_url = image_url;
    }

    // Getter ve Setter Metotları
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
