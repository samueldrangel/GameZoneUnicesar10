package com.gamezone.persistence;

import com.gamezone.model.Customer;
import com.gamezone.model.Product;
import com.gamezone.model.Sale;
import com.gamezone.model.Seller;
import com.gamezone.service.PersonService;
import com.gamezone.service.ProductService;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository for managing sales persistence using flat files (CSV format).
 */
public class SaleRepository {
    private static final String FILE_PATH = "data/sales.csv";

    public List<Sale> loadAll(PersonService personService, ProductService productService) {
        List<Sale> sales = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return sales;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",", -1);
                if (parts.length < 6) continue;

                String saleId = parts[0];
                LocalDate date = LocalDate.parse(parts[1]);
                Customer customer = personService != null ? personService.findCustomerById(parts[2]) : null;
                Seller seller = personService != null ? personService.findSellerById(parts[3]) : null;
                
                // Nota: Para simplificar el almacenamiento CSV de listas de productos por venta individual,
                // guardamos el ID del producto principal o el primero. Si manejas múltiples ítems, 
                // puedes adaptar el formato según tu estructura de archivos.
                Product product = productService != null ? productService.findById(parts[4]) : null;
                
                List<Product> purchasedItems = new ArrayList<>();
                if (product != null) {
                    purchasedItems.add(product);
                }

                Sale sale = new Sale(saleId, date, customer, seller, purchasedItems);

                if (parts.length >= 7 && !parts[6].isEmpty()) {
                    sale.setAppliedPromotionName(parts[6]);
                }
                if (parts.length >= 8 && !parts[7].isEmpty()) {
                    try {
                        sale.setDiscountAmount(Double.parseDouble(parts[7]));
                    } catch (NumberFormatException ignored) {}
                }

                sales.add(sale);
            }
        } catch (IOException e) {
            System.err.println("Error loading sales: " + e.getMessage());
        }
        return sales;
    }

    public void saveAll(List<Sale> sales) {
        File file = new File(FILE_PATH);
        file.getParentFile().mkdirs();

        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            for (Sale sale : sales) {
                String productId = (sale.getPurchasedItems() != null && !sale.getPurchasedItems().isEmpty()) 
                        ? sale.getPurchasedItems().get(0).getId() : "NONE";
                String customerId = sale.getCustomer() != null ? sale.getCustomer().getId() : "NONE";
                String sellerId = sale.getSeller() != null ? sale.getSeller().getId() : "NONE";

                StringBuilder sb = new StringBuilder();
                sb.append(sale.getId()).append(",")
                  .append(sale.getDate() != null ? sale.getDate().toString() : LocalDate.now()).append(",")
                  .append(customerId).append(",")
                  .append(sellerId).append(",")
                  .append(productId).append(",")
                  .append(sale.getPurchasedItems() != null ? sale.getPurchasedItems().size() : 0).append(",")
                  .append(sale.getAppliedPromotionName()).append(",")
                  .append(sale.getDiscountAmount());
                
                writer.println(sb.toString());
            }
        } catch (IOException e) {
            System.err.println("Error saving sales: " + e.getMessage());
        }
    }

    public void saveSale(Sale sale) {
        List<Sale> sales = loadAll(null, null);
        sales.add(sale);
        saveAll(sales);
    }
}