package com.gamezone.model;

import java.time.LocalDate;
import java.util.List;

/**
 * Represents a transaction sale in the store, supporting date, subtotal, items, and promotion discounts.
 */
public class Sale {
    private String id;
    private LocalDate date;
    private Customer customer;
    private Seller seller;
    private List<Product> purchasedItems;
    private String appliedPromotionName;
    private double discountAmount;
    private double finalTotal;

    public Sale(String id, LocalDate date, Customer customer, Seller seller, List<Product> purchasedItems) {
        this.id = id;
        this.date = date != null ? date : LocalDate.now();
        this.customer = customer;
        this.seller = seller;
        this.purchasedItems = purchasedItems;
        this.appliedPromotionName = "None";
        this.discountAmount = 0.0;
        calculateTotals();
    }

    public Sale(String id, Customer customer, Seller seller, List<Product> purchasedItems) {
        this(id, LocalDate.now(), customer, seller, purchasedItems);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public List<Product> getPurchasedItems() {
        return purchasedItems;
    }

    public void setPurchasedItems(List<Product> purchasedItems) {
        this.purchasedItems = purchasedItems;
        calculateTotals();
    }

    public String getAppliedPromotionName() {
        return appliedPromotionName;
    }

    public void setAppliedPromotionName(String appliedPromotionName) {
        this.appliedPromotionName = appliedPromotionName;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
        calculateTotals();
    }

    /**
     * Calculates the subtotal as the sum of prices of all purchased items.
     * 
     * @return the subtotal amount
     */
    public double getSubtotal() {
        if (purchasedItems == null) {
            return 0.0;
        }
        return purchasedItems.stream().mapToDouble(Product::getPrice).sum();
    }

    public double getFinalTotal() {
        return finalTotal;
    }

    private void calculateTotals() {
        double subtotal = getSubtotal();
        this.finalTotal = subtotal - this.discountAmount;
        if (this.finalTotal < 0) {
            this.finalTotal = 0.0;
        }
    }

    /**
     * Generates a formatted receipt showing date, customer, seller, subtotal, applied discount, and final total.
     * 
     * @return formatted receipt string
     */
    public String generateReceipt() {
        StringBuilder sb = new StringBuilder();
        sb.append("========================================\n");
        sb.append("         GAMEZONE UNICESAR RECEIPT      \n");
        sb.append("========================================\n");
        sb.append("Sale ID: ").append(id).append("\n");
        sb.append("Date: ").append(date != null ? date.toString() : LocalDate.now()).append("\n");
        if (customer != null) sb.append("Customer: ").append(customer.getName()).append("\n");
        if (seller != null) sb.append("Seller: ").append(seller.getName()).append("\n");
        sb.append("Items Purchased:\n");
        if (purchasedItems != null) {
            for (Product p : purchasedItems) {
                sb.append(" - ").append(p.getTitle()).append(": $").append(p.getPrice()).append("\n");
            }
        }
        sb.append("----------------------------------------\n");
        sb.append(String.format("Subtotal: $%.2f\n", getSubtotal()));
        sb.append(String.format("Applied Promotion: %s\n", appliedPromotionName));
        sb.append(String.format("Discount Amount: -$%.2f\n", discountAmount));
        sb.append("----------------------------------------\n");
        sb.append(String.format("Final Total: $%.2f\n", finalTotal));
        sb.append("========================================\n");
        return sb.toString();
    }
    
    /**
     * Checks if the sale is within the 30-day return window from the current
     * date.
     *
     * @return true if within 30 days, false otherwise
     */
    public boolean canBeReturned() {
        if (this.date == null) {
            return false;
        }
        long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(this.date, LocalDate.now());
        return daysBetween >= 0 && daysBetween <= 30;
    }
}
