package com.gamezone;

import com.gamezone.persistence.SaleRepository;
import com.gamezone.service.PersonService;
import com.gamezone.service.ProductService;
import com.gamezone.service.PromotionService;
import com.gamezone.service.SaleService;
import com.gamezone.ui.ConsoleMenu;

/**
 * Main entry point for the GameZoneUnicesar application.
 */
public class Main {
    public static void main(String[] args) {
        // Inicializar repositorios y servicios
        SaleRepository saleRepository = new SaleRepository();
        PersonService personService = new PersonService();
        ProductService productService = new ProductService();
        PromotionService promotionService = new PromotionService();
        SaleService saleService = new SaleService(saleRepository, promotionService, productService, personService);

        // Inicializar y arrancar el menú de consola
        ConsoleMenu menu = new ConsoleMenu(saleService, productService, personService, promotionService);
        menu.start();
    }
}
