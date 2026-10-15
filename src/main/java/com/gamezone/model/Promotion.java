package com.gamezone.model;

import java.time.LocalDate;

/**
 * Abstract base class representing all promotional discounts in the store.
 */
public abstract class Promotion {
    private String id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;

    public Promotion(String id, String name, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * Checks if the promotion is active on a given date.
     * 
     * @param date the date to check
     * @return true if date is within range, false otherwise
     */
    public boolean isActive(LocalDate date) {
        if (date == null) {
            return false;
        }
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }

    /**
     * Calculates the monetary discount amount for a given sale.
     * 
     * @param sale the sale to calculate discount for
     * @return discount amount in currency
     */
    public abstract double calculateDiscount(Sale sale);
}
