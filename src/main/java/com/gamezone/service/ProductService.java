package com.gamezone.service;

import com.gamezone.model.*;
import com.gamezone.persistence.*;
import java.util.List;

public class ProductService {
    private ProductRepository repository;
    private List<Product> products;

    public ProductService() {
        this.repository = new ProductRepository();
        this.products = repository.loadAll();
    }

    public List<Product> listAllProducts() { return products; }

    public void addProduct(Product product) {
        products.add(product);
        repository.saveAll(products);
    }

    public Product findById(String id) {
        for (Product p : products) if (p.getId().equals(id)) return p;
        return null;
    }

    public void updateStock(String productId, int quantitySold) {
        Product p = findById(productId);
        if (p != null) {
            p.setStock(p.getStock() - quantitySold);
            repository.saveAll(products);
        }
    }
    
    /**
     * Restores stock for a specific product and persists the change.
     * @param productId the product ID
     * @param quantity the quantity to restore
     */
    public void restoreStock(String productId, int quantity) {
        Product product = findById(productId);
        if (product != null) {
            product.setStock(product.getStock() + quantity);
            repository.saveAll(products);
        }
    }
}
