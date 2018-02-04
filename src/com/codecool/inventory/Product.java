package com.codecool.inventory;

public abstract class Product {
    private String name;
    private int price;

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public abstract int getSize();
}

class CDProduct extends Product {
    private int numOfTracks;

    public CDProduct(String name, int price, int numOfTracks){
        this.setName(name);
        this.setPrice(price);
        this.numOfTracks = numOfTracks;
    }

    public int getSize() {
        return numOfTracks;
    }
}

class BookProduct extends Product {
    private int numOfPages;

    public BookProduct(String name, int price, int numOfPages) {
        this.setName(name);
        this.setPrice(price);
        this.numOfPages = numOfPages;
    }

    public int getSize() {
        return numOfPages;
    }
}