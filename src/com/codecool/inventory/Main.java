package com.codecool.inventory;

public class Main {
    public static void main(String args[]) {
        PersistentStore sample = new PersistentStore();
        StoreManager manager = new StoreManager();
        manager.addStorage(sample);

        manager.addBookProduct("Valami", 1, 2);
        manager.addCDProduct("Mas Valami", 3,4);

        System.out.println(manager.listProducts());
        System.out.println(manager.getTotalProductPrice());
    }
}
