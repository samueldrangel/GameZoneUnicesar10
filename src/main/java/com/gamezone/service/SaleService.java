package com.gamezone.service;

import com.gamezone.model.Customer;
import com.gamezone.model.Product;
import com.gamezone.model.Sale;
import com.gamezone.model.Seller;
import com.gamezone.persistence.SaleRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Service layer for managing sales operations, business logic, and promotion integration.
 */
public class SaleService {
    private SaleRepository saleRepository;
    private PromotionService promotionService;
    private ProductService productService;
    private PersonService personService;

    public SaleService(SaleRepository saleRepository, PromotionService promotionService, ProductService productService, PersonService personService) {
        this.saleRepository = saleRepository;
        this.promotionService = promotionService;
        this.productService = productService;
        this.personService = personService;
    }

    public List<Sale> getAllSales() {
        return saleRepository.loadAll(personService, productService);
    }

    /**
     * Finds and returns a sale by its unique identifier.
     * @param saleId the sale identifier
     * @return the Sale object, or null if not found
     */
    public Sale getSaleById(String saleId) {
        List<Sale> allSales = getAllSales();
        if (allSales != null) {
            for (Sale s : allSales) {
                if (s.getId() != null && s.getId().equals(saleId)) {
                    return s;
                }
            }
        }
        return null;
    }

    /**
     * Registers a new sale, automatically calculates totals, applies the best valid promotion, and persists it.
     */
    public Sale registerSale(Customer customer, Seller seller, List<Product> purchasedItems) {
        String saleId = "SALE-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        Sale sale = new Sale(saleId, LocalDate.now(), customer, seller, purchasedItems);

        // Apply best promotion if available
        if (promotionService != null) {
            promotionService.applyBestPromotion(sale);
        }

        // Save to CSV persistence
        saleRepository.saveSale(sale);
        return sale;
    }
}
