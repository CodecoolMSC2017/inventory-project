package com.codecool.inventory;

import java.util.List;
import java.util.ArrayList;
import java.lang.Integer;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public abstract class Store implements StorageCapable {
    private List<Product> products = new ArrayList<>();


    private void saveToXml(Product product) {
        List<Product> products = getAllProduct();

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("Store");
            doc.appendChild(rootElement);

            for (Product toSave : products) {
                System.out.println(toSave.getName());

                Element prod = doc.createElement("product");
                Element type = doc.createElement("type");
                Element name = doc.createElement("name");
                Element price = doc.createElement("price");
                Element size = doc.createElement("size");
                rootElement.appendChild(prod);
                prod.appendChild(type);
                prod.appendChild(name);
                prod.appendChild(price);
                prod.appendChild(size);
                name.appendChild(doc.createTextNode(toSave.getName()));
                price.appendChild(doc.createTextNode(Integer.toString(toSave.getPrice())));
                size.appendChild(doc.createTextNode(Integer.toString(toSave.getSize())));
                if (toSave instanceof BookProduct) {
                    type.appendChild(doc.createTextNode("Book"));
                } else {
                    type.appendChild(doc.createTextNode("CD"));
                }
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("Store.xml"));
            transformer.transform(source, result);
        } catch (ParserConfigurationException | TransformerException tfe) {
            tfe.printStackTrace();
        }
    }


    protected abstract void storeProduct(Product product);


    protected abstract Product createProduct(String type, String name, int price, int size);


    public List<Product> loadProducts() {
        List<Product> products = new ArrayList<Product>();

        try {
            File storage = new File("Store.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(storage);

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("product");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;
                    if (eElement.getElementsByTagName("type").item(0).getTextContent().equals("CD")) {
                        String name = eElement.getElementsByTagName("name").item(0).getTextContent();
                        int price = Integer.parseInt(eElement.getElementsByTagName("price").item(0).getTextContent());
                        int size = Integer.parseInt(eElement.getElementsByTagName("size").item(0).getTextContent());
                        products.add(createProduct("CD", name, price, size));
                    } else {
                        String name = eElement.getElementsByTagName("name").item(0).getTextContent();
                        int price = Integer.parseInt(eElement.getElementsByTagName("price").item(0).getTextContent());
                        int size = Integer.parseInt(eElement.getElementsByTagName("size").item(0).getTextContent());
                        products.add(createProduct("Book", name, price, size));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }


    public void store(Product product) {
        storeProduct(product);
        saveToXml(product);
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

class PersistentStore extends Store {
    public DocumentBuilderFactory docFactory;
    public DocumentBuilder docBuilder;
    public Document doc;
    public Element rootElement;

    public PersistentStore() {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("Store");
            doc.appendChild(rootElement);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("Store.xml"));
            transformer.transform(source, result);
        } catch (ParserConfigurationException | TransformerException pce) {
            pce.printStackTrace();
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
