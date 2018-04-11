package com.example.apple.PaddysAssignmentBookStore.BookStore;

/**
 * Created by zackeryorourke on 20/03/2018.
 */

public class ShoppingCartModel {

    private String id;
    private String title;
    private String author;
    private String price;
    private String category;
    private String imageUrl;
    private String quantity;
    private String total;


    public ShoppingCartModel(){

    };

    public ShoppingCartModel( String title, String author, String price, String quantity, String category,String imageUrl,String total) {
        this.id = id;
        this.title = title;
        this.author= author;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.imageUrl= imageUrl;
        this.total= total;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
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