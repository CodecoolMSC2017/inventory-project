package com.codecool.inventory;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.lang.Integer;

public abstract class CsvStore implements StorageCapable {
    private List<Product> products = new ArrayList<>();


    private void saveToCsv(Product product) throws FileNotFoundException {
        List<Product> products = getAllProduct();
        PrintWriter writer = new PrintWriter(new File("store.csv"));
        StringBuilder builder = new StringBuilder();

        System.out.println("I'm here");

        for (Product prod : products) {
            if (prod instanceof BookProduct) {
                builder.append("book;");
            } else {
                builder.append("cd;");
            }
            builder.append(product.getName());
            builder.append(";");
            builder.append(product.getPrice());
            builder.append(";");
            builder.append(product.getSize());
            builder.append("\n");
        }

        writer.write(builder.toString());
        writer.close();
    }


    protected abstract void storeProduct(Product product);


    protected abstract Product createProduct(String type, String name, int price, int size);


    private List<Product> loadProducts() throws FileNotFoundException {
        List<Product> products = new ArrayList<>();
        BufferedReader breader = new BufferedReader(new FileReader("store.csv"));

        try {
            for (String line = breader.readLine(); line != null; line = breader.readLine()) {
                System.out.println(line);
                String[] values = line.split(";");
                if (values[0].equals("book")) {
                    System.out.println(values[1]);
                    products.add(new BookProduct(values[1], Integer.parseInt(values[2]), Integer.parseInt(values[3])));
                } else {
                    products.add(new CDProduct(values[1], Integer.parseInt(values[2]), Integer.parseInt(values[3])));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }

    public void store(Product product) {
        storeProduct(product);
        try {
            saveToCsv(product);
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
    }

    public List<Product> getAllProduct() {
        return products;
    }

    public void storeCDProduct(String name, int price, int tracks) {
        store(createProduct("CD", name, price, tracks));
    }

    public void storeBookProduct(String name, int price, int pages) {
        store(createProduct("Book", name, price, pages));
    }
}

class PersistentCsvStore extends CsvStore {

    public PersistentCsvStore() {
        try {
            PrintWriter writer = new PrintWriter(new File("store.csv"));
            String init = "";
            writer.write(init);
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }


    public void storeProduct(Product product) {
        List<Product> products = this.getAllProduct();
        products.add(product);

    }


    public Product createProduct(String type, String name, int price, int size) {
        if (type.equals("CD")) {
            return new CDProduct(name, price, size);
        } else {
            return new BookProduct(name, price, size);
        }
    }
}