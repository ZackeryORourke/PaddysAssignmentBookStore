package com.example.apple.PaddysAssignmentBookStore.BookStore;

/**
 * Created by zackeryorourke on 20/03/2018.
 */

public class Catalogue {

    private String id;
    private String title;
    private String author;
    private String price;
    private String category;
    private String imageUrl;

    public Catalogue(){

    };

    public Catalogue(String id, String title, String author, String price) {
        this.id = id;
        this.title = title;
        this.author= author;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
