package com.gamezone;

import com.gamezone.persistence.SaleRepository;
import com.gamezone.service.*;
import com.gamezone.ui.ConsoleMenu;

public class Main {
    public static void main(String[] args) {
        ProductService productService = new ProductService();
        AccessoryService accessoryService = new AccessoryService();
        SaleRepository saleRepository = new SaleRepository();
        PersonService personService = new PersonService();
        PromotionService promotionService = new PromotionService();
        SaleService saleService = new SaleService(saleRepository, promotionService, productService, personService);

        ConsoleMenu menu = new ConsoleMenu(productService, accessoryService, saleService, personService, promotionService);
        menu.showMainMenu();
    }
}
