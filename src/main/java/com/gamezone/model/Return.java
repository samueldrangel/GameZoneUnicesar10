package com.gamezone.model;

import java.time.LocalDate;
import java.util.List;

/**
 * Represents a product return transaction referencing an original sale.
 */
public class Return {
    private String returnId;
    private LocalDate returnDate;
    private Sale originalSale;
    private List<Product> returnedProducts;
    private String reason;
    private double refundAmount;

    public Return(String returnId, LocalDate returnDate, Sale originalSale, List<Product> returnedProducts, String reason) {
        this.returnId = returnId;
        this.returnDate = returnDate;
        this.originalSale = originalSale;
        this.returnedProducts = returnedProducts;
        this.reason = reason;
        this.refundAmount = calculateRefundAmount();
    }

    public String getReturnId() {
        return returnId;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public Sale getOriginalSale() {
        return originalSale;
    }

    public List<Product> getReturnedProducts() {
        return returnedProducts;
    }

    public String getReason() {
        return reason;
    }

    public double getRefundAmount() {
        return refundAmount;
    }

    /**
     * Calculates the total refund amount by summing up the prices of returned products.
     * @return the calculated refund amount
     */
    public double calculateRefundAmount() {
        double total = 0.0;
        if (returnedProducts != null) {
            for (Product p : returnedProducts) {
                total += p.getPrice();
            }
        }
        this.refundAmount = total;
        return total;
    }

    /**
     * Generates a formatted return receipt string in Spanish.
     * @return formatted receipt details
     */
    public String generateReturnReceipt() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== RECIBO DE DEVOLUCIÓN ===\n");
        sb.append("ID Devolución: ").append(returnId).append("\n");
        sb.append("Fecha: ").append(returnDate).append("\n");
        sb.append("ID Venta Original: ").append(originalSale != null ? originalSale.getId() : "N/A").append("\n");
        sb.append("Motivo: ").append(reason).append("\n");
        sb.append("Productos Devueltos:\n");
        if (returnedProducts != null) {
            for (Product p : returnedProducts) {
                sb.append(" - ").append(p.getTitle()).append(" ($").append(p.getPrice()).append(")\n");
            }
        }
        sb.append("Monto Reembolsado: $").append(refundAmount).append("\n");
        return sb.toString();
    }
}
