package com.codecool.inventory;

import java.util.List;

public class StoreManager   {
    public StorageCapable store1;

    public void addStorage(StorageCapable storage){
        store1 = storage;
    }

    public void addCDProduct(String name, int price, int tracks){
        store1.storeCDProduct(name, price, tracks);
    }

    public void addBookProduct(String name, int price, int pages){
        store1.storeBookProduct(name, price, pages);
    }

    public String listProducts(){
        String allProducts = "";
        List<Product> prods = store1.getAllProduct();
        for (Product product : prods) {
            allProducts = allProducts + "\n" + product.getName();
        }
        return allProducts;
    }

    public int getTotalProductPrice(){
        int totalPrice = 0;
        List<Product> prods = store1.getAllProduct();
        for (Product product : prods) {
            totalPrice = totalPrice + product.getPrice();
        }
        return totalPrice;
    }
}
