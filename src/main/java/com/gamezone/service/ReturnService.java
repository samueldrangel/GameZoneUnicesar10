package com.gamezone.service;

import com.gamezone.model.Product;
import com.gamezone.model.Return;
import com.gamezone.model.Sale;
import com.gamezone.persistence.ReturnRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Service layer handling return business logic and monthly balance calculation.
 */
public class ReturnService {
    private final ReturnRepository returnRepository;
    private final SaleService saleService;
    private final ProductService productService;
    private final List<Return> returns;

    public ReturnService(ReturnRepository returnRepository, SaleService saleService, ProductService productService) {
        this.returnRepository = returnRepository;
        this.saleService = saleService;
        this.productService = productService;
        this.returns = new ArrayList<>(returnRepository.loadAll());
    }

    /**
     * Registers a new product return with all business validations.
     * @param saleId original sale identifier
     * @param productIds list of product IDs to return
     * @param reason return reason
     * @return the created Return object
     */
    public Return registerReturn(String saleId, List<String> productIds, String reason) {
        Sale sale = saleService.getSaleById(saleId);
        if (sale == null) {
            throw new IllegalArgumentException("La venta original especificada no existe.");
        }

        if (!sale.canBeReturned()) {
            throw new IllegalArgumentException("La devolución ha sido rechazada: han pasado más de 30 días desde la fecha de la venta original.");
        }

        List<Product> productsToReturn = new ArrayList<>();
        for (String pId : productIds) {
            Product prod = productService.findById(pId);
            if (prod == null) {
                throw new IllegalArgumentException("El producto con ID " + pId + " no existe en el sistema.");
            }
            boolean belongsToSale = false;
            if (sale.getPurchasedItems() != null) {
                for (Product saleItem : sale.getPurchasedItems()) {
                    if (saleItem.getId().equals(pId)) {
                        belongsToSale = true;
                        break;
                    }
                }
            }
            if (!belongsToSale) {
                throw new IllegalArgumentException("El producto " + prod.getTitle() + " no pertenece a la venta original indicada.");
            }
            productsToReturn.add(prod);
        }

        String returnId = "RET-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        LocalDate returnDate = LocalDate.now();

        Return newReturn = new Return(returnId, returnDate, sale, productsToReturn, reason);
        returns.add(newReturn);
        returnRepository.saveAll(returns);

        for (Product p : productsToReturn) {
            productService.restoreStock(p.getId(), 1);
        }

        return newReturn;
    }

    public List<Return> viewAllReturns() {
        return new ArrayList<>(returns);
    }

    public List<Return> viewReturnsByCustomer(String customerId) {
        List<Return> filtered = new ArrayList<>();
        for (Return r : returns) {
            if (r.getOriginalSale() != null && r.getOriginalSale().getCustomer() != null &&
                r.getOriginalSale().getCustomer().getId().equals(customerId)) {
                filtered.add(r);
            }
        }
        return filtered;
    }

    public List<Return> viewReturnsBySale(String saleId) {
        List<Return> filtered = new ArrayList<>();
        for (Return r : returns) {
            if (r.getOriginalSale() != null && r.getOriginalSale().getId().equals(saleId)) {
                filtered.add(r);
            }
        }
        return filtered;
    }

    /**
     * Generates monthly balance: total sales minus total returns for the specified month and year.
     * @param month month number (1-12)
     * @param year four-digit year
     * @return net balance amount
     */
    public double generateMonthlyBalance(int month, int year) {
        double totalSales = 0.0;
        List<Sale> allSales = saleService.getAllSales();
        if (allSales != null) {
            for (Sale s : allSales) {
                if (s.getDate() != null && s.getDate().getMonthValue() == month && s.getDate().getYear() == year) {
                    totalSales += s.getFinalTotal();
                }
            }
        }

        double totalReturns = 0.0;
        for (Return r : returns) {
            if (r.getReturnDate() != null && r.getReturnDate().getMonthValue() == month && r.getReturnDate().getYear() == year) {
                totalReturns += r.getRefundAmount();
            }
        }

        return totalSales - totalReturns;
    }
}
