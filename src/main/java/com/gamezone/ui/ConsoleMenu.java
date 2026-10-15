package com.gamezone.ui;

import com.gamezone.model.Customer;
import com.gamezone.model.Product;
import com.gamezone.model.Sale;
import com.gamezone.model.Seller;
import com.gamezone.service.PersonService;
import com.gamezone.service.ProductService;
import com.gamezone.service.PromotionService;
import com.gamezone.service.SaleService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Console user interface for managing GameZoneUnicesar operations.
 */
public class ConsoleMenu {
    private Scanner scanner;
    private SaleService saleService;
    private ProductService productService;
    private PersonService personService;
    private PromotionService promotionService;

    public ConsoleMenu(SaleService saleService, ProductService productService, PersonService personService, PromotionService promotionService) {
        this.scanner = new Scanner(System.in);
        this.saleService = saleService;
        this.productService = productService;
        this.personService = personService;
        this.promotionService = promotionService;
    }

    public void start() {
        int option = 0;
        do {
            System.out.println("\n========================================");
            System.out.println("       GAMEZONE UNICESAR - MENU         ");
            System.out.println("========================================");
            System.out.println("1. Register New Sale (with Promotions)");
            System.out.println("2. View All Sales & Receipts");
            System.out.println("3. View Active Promotions");
            System.out.println("0. Exit");
            System.out.print("Select an option: ");

            try {
                option = Integer.parseInt(scanner.nextLine());
                switch (option) {
                    case 1:
                        handleRegisterSale();
                        break;
                    case 2:
                        handleViewSales();
                        break;
                    case 3:
                        handleViewPromotions();
                        break;
                    case 0:
                        System.out.println("Exiting application. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid numeric option.");
            }
        } while (option != 0);
    }

    private void handleRegisterSale() {
        System.out.println("\n--- Register New Sale ---");
        
        System.out.print("Enter Customer ID: ");
        String customerId = scanner.nextLine();
        Customer customer = personService != null ? personService.findCustomerById(customerId) : null;
        if (customer == null) {
            System.out.println("Customer not found. Creating a default guest customer.");
            // Usando el constructor: (id, name, phone, email)
            customer = new Customer(customerId, "Guest Customer", "0000000000", "guest@mail.com");
        }

        System.out.print("Enter Seller ID: ");
        String sellerId = scanner.nextLine();
        Seller seller = personService != null ? personService.findSellerById(sellerId) : null;
        if (seller == null) {
            System.out.println("Seller not found. Using default seller.");
            // Usando el constructor: (id, name, phone, employeeCode, workShift)
            seller = new Seller(sellerId, "Default Seller", "0000000000", "EMP-001", "Morning");
        }

        List<Product> items = new ArrayList<>();
        String addMore;
        do {
            System.out.print("Enter Product ID to add: ");
            String productId = scanner.nextLine();
            Product product = productService != null ? productService.findById(productId) : null;
            if (product != null) {
                items.add(product);
                System.out.println("Added: " + product.getTitle() + " ($" + product.getPrice() + ")");
            } else {
                System.out.println("Product not found.");
            }
            System.out.print("Add another product? (y/n): ");
            addMore = scanner.nextLine();
        } while (addMore.equalsIgnoreCase("y"));

        if (items.isEmpty()) {
            System.out.println("Sale cancelled: No items added.");
            return;
        }

        Sale sale = saleService.registerSale(customer, seller, items);
        System.out.println("\nSale registered successfully!");
        System.out.println(sale.generateReceipt());
    }

    private void handleViewSales() {
        System.out.println("\n--- Sales History ---");
        List<Sale> sales = saleService.getAllSales();
        if (sales.isEmpty()) {
            System.out.println("No sales recorded yet.");
            return;
        }
        for (Sale s : sales) {
            System.out.println(s.generateReceipt());
        }
    }

    private void handleViewPromotions() {
        System.out.println("\n--- Active Promotions ---");
        if (promotionService != null) {
            for (String promo : promotionService.getActivePromotions()) {
                System.out.println(" - " + promo);
            }
        } else {
            System.out.println("No promotions service available.");
        }
    }
}