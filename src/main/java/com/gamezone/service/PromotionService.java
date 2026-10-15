package com.gamezone.service;

import com.gamezone.model.Product;
import com.gamezone.model.Sale;

import java.util.ArrayList;
import java.util.List;

/**
 * Service layer for managing promotions and applying discount logic to sales.
 */
public class PromotionService {
    // Lista temporal o repositorio de promociones activas
    private List<String> activePromotions; // O puedes adaptarlo a tu jerarquía de clases Promotion si ya la tienes

    public PromotionService() {
        this.activePromotions = new ArrayList<>();
        // Puedes inicializar promociones por defecto aquí
        activePromotions.add("Descuento Navideño");
        activePromotions.add("Promo Verano");
    }

    public List<String> getActivePromotions() {
        return activePromotions;
    }

    public void addPromotion(String promotionName) {
        if (promotionName != null && !promotionName.trim().isEmpty()) {
            this.activePromotions.add(promotionName);
        }
    }

    /**
     * Evaluates available promotions and applies the best discount to the given sale.
     */
    public void applyBestPromotion(Sale sale) {
        if (sale == null || sale.getPurchasedItems() == null || sale.getPurchasedItems().isEmpty()) {
            return;
        }

        double subtotal = sale.getSubtotal();
        
        // Lógica de ejemplo: si el subtotal supera los $100, aplica un 10% de descuento automáticamente
        if (subtotal > 100.0) {
            double discount = subtotal * 0.10;
            sale.setAppliedPromotionName("10% Off Over $100");
            sale.setDiscountAmount(discount);
        } else {
            sale.setAppliedPromotionName("None");
            sale.setDiscountAmount(0.0);
        }
    }
}
