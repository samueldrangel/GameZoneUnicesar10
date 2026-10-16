package com.gamezone.persistence;

import com.gamezone.model.Product;
import com.gamezone.model.Return;
import com.gamezone.model.Sale;
import com.gamezone.service.ProductService;
import com.gamezone.service.SaleService;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles CSV persistence for Return entities.
 */
public class ReturnRepository {
    private final String filePath = "data/returns.csv";
    private final SaleService saleService;
    private final ProductService productService;

    public ReturnRepository(SaleService saleService, ProductService productService) {
        this.saleService = saleService;
        this.productService = productService;
        File file = new File(filePath);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
    }

    /**
     * Loads all returns from the CSV file.
     * @return list of returns
     */
    public List<Return> loadAll() {
        List<Return> returns = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            return returns;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 5) {
                    String returnId = parts[0];
                    LocalDate date = LocalDate.parse(parts[1]);
                    String saleId = parts[2];
                    String reason = parts[3];
                    Sale sale = saleService.getSaleById(saleId);
                    
                    List<Product> products = new ArrayList<>();
                    if (parts.length > 4 && !parts[4].isEmpty()) {
                        String[] prodIds = parts[4].split(",");
                        for (String pId : prodIds) {
                            Product p = productService.findById(pId.trim());
                            if (p != null) {
                                products.add(p);
                            }
                        }
                    }
                    
                    Return ret = new Return(returnId, date, sale, products, reason);
                    returns.add(ret);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returns;
    }

    /**
     * Saves all returns to the CSV file.
     * @param returns list of returns to persist
     */
    public void saveAll(List<Return> returns) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            for (Return r : returns) {
                StringBuilder prodIds = new StringBuilder();
                if (r.getReturnedProducts() != null) {
                    for (int i = 0; i < r.getReturnedProducts().size(); i++) {
                        prodIds.append(r.getReturnedProducts().get(i).getId());
                        if (i < r.getReturnedProducts().size() - 1) {
                            prodIds.append(",");
                        }
                    }
                }
                pw.println(r.getReturnId() + ";" + 
                           r.getReturnDate() + ";" + 
                           (r.getOriginalSale() != null ? r.getOriginalSale().getId() : "") + ";" + 
                           r.getReason() + ";" + 
                           prodIds.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
