package com.gamezone.ui;

import com.gamezone.model.Accessory;
import com.gamezone.model.Customer;
import com.gamezone.model.Product;
import com.gamezone.model.Return;
import com.gamezone.model.Sale;
import com.gamezone.model.Seller;
import com.gamezone.persistence.ReturnRepository;
import com.gamezone.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main console user interface for managing GameZone Unicesar system operations,
 * including products, accessories, sales, automated promotions, and returns.
 */
public class ConsoleMenu {
    private Scanner scanner;
    private ProductService productService;
    private AccessoryService accessoryService;
    private SaleService saleService;
    private PersonService personService;
    private PromotionService promotionService;
    private ReturnService returnService;

    public ConsoleMenu(ProductService productService, AccessoryService accessoryService, SaleService saleService, PersonService personService, PromotionService promotionService) {
        this.productService = productService;
        this.accessoryService = accessoryService;
        this.saleService = saleService;
        this.personService = personService;
        this.promotionService = promotionService;
        this.returnService = new ReturnService(new ReturnRepository(saleService, productService), saleService, productService);
        this.scanner = new Scanner(System.in);
    }

    public void showMainMenu() {
        int option;
        do {
            System.out.println("\n=== GAME ZONE UNICESAR - MAIN MENU ===");
            System.out.println("1. Gestión de productos (Consolas y Videojuegos)");
            System.out.println("2. Gestión de accesorios");
            System.out.println("3. Gestión de ventas (con Promociones)");
            System.out.println("4. Ver Promociones Activas");
            System.out.println("5. Gestión de Devoluciones y Balance Mensual");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            
            while (!scanner.hasNextInt()) {
                System.out.print("Por favor, ingrese un número válido: ");
                scanner.next();
            }
            option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    System.out.println("[Módulo de productos existente]");
                    break;
                case 2:
                    showAccessorySubMenu();
                    break;
                case 3:
                    showSalesSubMenu();
                    break;
                case 4:
                    showActivePromotions();
                    break;
                case 5:
                    showReturnSubmenu();
                    break;
                case 0:
                    System.out.println("¡Gracias por usar GameZone Unicesar!");
                    break;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        } while (option != 0);
    }

    private void showAccessorySubMenu() {
        int option;
        do {
            System.out.println("\n--- GESTIÓN DE ACCESORIOS ---");
            System.out.println("1. Registrar un nuevo control");
            System.out.println("2. Registrar un nuevo cable");
            System.out.println("3. Registrar una nueva memoria");
            System.out.println("4. Listar todos los accesorios");
            System.out.println("5. Listar accesorios por tipo");
            System.out.println("6. Consultar accesorios compatibles con una consola");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            while (!scanner.hasNextInt()) {
                System.out.print("Por favor, ingrese un número válido: ");
                scanner.next();
            }
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    registerControllerPrompt();
                    break;
                case 2:
                    registerCablePrompt();
                    break;
                case 3:
                    registerMemoryPrompt();
                    break;
                case 4:
                    listAllAccessories();
                    break;
                case 5:
                    listAccessoriesByTypePrompt();
                    break;
                case 6:
                    findAccessoriesByConsolePrompt();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (option != 0);
    }

    private void registerControllerPrompt() {
        System.out.print("Ingrese ID del control: ");
        String id = scanner.nextLine();
        System.out.print("Ingrese título del control: ");
        String title = scanner.nextLine();
        System.out.print("Ingrese precio: ");
        double price = scanner.nextDouble();
        System.out.print("Ingrese stock disponible: ");
        int stock = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Ingrese IDs de consolas compatibles (separados por punto y coma ';'): ");
        String consolesInput = scanner.nextLine();
        List<String> consoles = List.of(consolesInput.split(";"));
        System.out.print("Ingrese tipo de conexión (Alámbrico / Inalámbrico): ");
        String connectionType = scanner.nextLine();

        accessoryService.registerController(id, title, price, stock, consoles, connectionType);
        System.out.println("¡Control registrado exitosamente!");
    }

    private void registerCablePrompt() {
        System.out.print("Ingrese ID del cable: ");
        String id = scanner.nextLine();
        System.out.print("Ingrese título del cable: ");
        String title = scanner.nextLine();
        System.out.print("Ingrese precio: ");
        double price = scanner.nextDouble();
        System.out.print("Ingrese stock disponible: ");
        int stock = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Ingrese IDs de consolas compatibles (separados por ';'): ");
        String consolesInput = scanner.nextLine();
        List<String> consoles = List.of(consolesInput.split(";"));
        System.out.print("Ingrese longitud en metros (ej. 1.5): ");
        double length = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Ingrese tipo de conector (HDMI, USB, Óptico, etc.): ");
        String connector = scanner.nextLine();

        accessoryService.registerCable(id, title, price, stock, consoles, length, connector);
        System.out.println("¡Cable registrado exitosamente!");
    }

    private void registerMemoryPrompt() {
        System.out.print("Ingrese ID de la memoria: ");
        String id = scanner.nextLine();
        System.out.print("Ingrese título de la memoria: ");
        String title = scanner.nextLine();
        System.out.print("Ingrese precio: ");
        double price = scanner.nextDouble();
        System.out.print("Ingrese stock disponible: ");
        int stock = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Ingrese IDs de consolas compatibles (separados por ';'): ");
        String consolesInput = scanner.nextLine();
        List<String> consoles = List.of(consolesInput.split(";"));
        System.out.print("Ingrese capacidad en GB: ");
        int capacity = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Ingrese tipo de memoria (SD, MicroSD, Interna, etc.): ");
        String memoryType = scanner.nextLine();

        accessoryService.registerMemory(id, title, price, stock, consoles, capacity, memoryType);
        System.out.println("¡Memoria registrada exitosamente!");
    }

    private void listAllAccessories() {
        List<Accessory> accessories = accessoryService.listAllAccessories();
        System.out.println("\n--- LISTADO COMPLETO DE ACCESORIOS ---");
        if (accessories.isEmpty()) {
            System.out.println("No hay accesorios registrados.");
        } else {
            for (Accessory acc : accessories) {
                System.out.println("- " + acc.getDescription());
            }
        }
    }

    private void listAccessoriesByTypePrompt() {
        System.out.print("Ingrese el tipo de accesorio a buscar (CONTROLLER / CABLE / MEMORY): ");
        String type = scanner.nextLine();
        List<Accessory> filtered = accessoryService.listAccessoriesByType(type);
        System.out.println("\n--- ACCESORIOS DE TIPO: " + type.toUpperCase() + " ---");
        if (filtered.isEmpty()) {
            System.out.println("No se encontraron accesorios para este tipo.");
        } else {
            for (Accessory acc : filtered) {
                System.out.println("- " + acc.getDescription());
            }
        }
    }

    private void findAccessoriesByConsolePrompt() {
        System.out.print("Ingrese el ID de la consola (ej. CONSOLE-01): ");
        String consoleId = scanner.nextLine();
        List<Accessory> compatible = accessoryService.findAccessoriesCompatibleWith(consoleId);
        System.out.println("\n--- ACCESORIOS COMPATIBLES CON " + consoleId + " ---");
        if (compatible.isEmpty()) {
            System.out.println("No hay accesorios compatibles con esta consola.");
        } else {
            for (Accessory acc : compatible) {
                System.out.println("- " + acc.getDescription());
            }
        }
    }

    private void showSalesSubMenu() {
        System.out.println("\n--- GESTIÓN DE VENTAS ---");
        
        System.out.print("Ingrese ID del Cliente: ");
        String customerId = scanner.nextLine();
        Customer customer = personService != null ? personService.findCustomerById(customerId) : null;
        if (customer == null) {
            System.out.println("Cliente no encontrado. Se creará un cliente invitado por defecto.");
            customer = new Customer(customerId, "Cliente Invitado", "0000000000", "invitado@mail.com");
        }

        System.out.print("Ingrese ID del Vendedor: ");
        String sellerId = scanner.nextLine();
        Seller seller = personService != null ? personService.findSellerById(sellerId) : null;
        if (seller == null) {
            System.out.println("Vendedor no encontrado. Se usará un vendedor por defecto.");
            seller = new Seller(sellerId, "Vendedor Predeterminado", "0000000000", "EMP-001", "General");
        }

        List<Product> purchasedItems = new ArrayList<>();
        char addMore;
        do {
            System.out.print("¿Desea agregar un Accesorio (A) o un Producto general/consola (P)? [A/P]: ");
            String typeChoice = scanner.nextLine();

            if (typeChoice.equalsIgnoreCase("A")) {
                listAllAccessories();
                System.out.print("Ingrese el ID del accesorio a comprar: ");
                String accId = scanner.nextLine();
                Accessory acc = accessoryService != null ? accessoryService.findById(accId) : null;
                if (acc != null) {
                    purchasedItems.add(acc);
                    System.out.println("Accesorio agregado a la venta.");
                } else {
                    System.out.println("Accesorio no encontrado.");
                }
            } else {
                System.out.print("Ingrese el ID del producto general: ");
                String prodId = scanner.nextLine();
                Product prod = productService != null ? productService.findById(prodId) : null;
                if (prod != null) {
                    purchasedItems.add(prod);
                    System.out.println("Producto agregado a la venta.");
                } else {
                    System.out.println("Producto no encontrado.");
                }
            }

            System.out.print("¿Desea agregar otro ítem a la venta? (s/n): ");
            String response = scanner.nextLine();
            addMore = response.isEmpty() ? 'n' : response.charAt(0);
        } while (addMore == 's' || addMore == 'S');

        if (!purchasedItems.isEmpty()) {
            Sale sale = saleService.registerSale(customer, seller, purchasedItems);
            System.out.println("\n¡Venta registrada exitosamente con aplicación de promociones!");
            System.out.println(sale.generateReceipt());
        } else {
            System.out.println("No se agregaron ítems a la venta.");
        }
    }

    private void showActivePromotions() {
        System.out.println("\n--- PROMOCIONES ACTIVAS ---");
        if (promotionService != null) {
            for (String promo : promotionService.getActivePromotions()) {
                System.out.println(" - " + promo);
            }
        } else {
            System.out.println("No hay servicio de promociones disponible.");
        }
    }

    public void showReturnSubmenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- GESTIÓN DE DEVOLUCIONES ---");
            System.out.println("1. Registrar una nueva devolución");
            System.out.println("2. Consultar todas las devoluciones");
            System.out.println("3. Consultar devoluciones por cliente");
            System.out.println("4. Consultar devoluciones por venta");
            System.out.println("5. Consultar balance mensual");
            System.out.println("6. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            
            while (!scanner.hasNextInt()) {
                System.out.print("Por favor, ingrese un número válido: ");
                scanner.next();
            }
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    System.out.print("Ingrese el ID de la venta original: ");
                    String saleId = scanner.nextLine();
                    System.out.print("Ingrese los IDs de los productos a devolver (separados por coma): ");
                    String[] prodIdsArr = scanner.nextLine().split(",");
                    List<String> prodIds = new ArrayList<>();
                    for (String id : prodIdsArr) {
                        prodIds.add(id.trim());
                    }
                    System.out.print("Ingrese el motivo de la devolución: ");
                    String reason = scanner.nextLine();
                    try {
                        Return created = returnService.registerReturn(saleId, prodIds, reason);
                        System.out.println("¡Devolución registrada con éxito!");
                        System.out.println(created.generateReturnReceipt());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 2:
                    List<Return> all = returnService.viewAllReturns();
                    if (all.isEmpty()) {
                        System.out.println("No hay devoluciones registradas.");
                    } else {
                        for (Return r : all) {
                            System.out.println(r.generateReturnReceipt());
                        }
                    }
                    break;
                case 3:
                    System.out.print("Ingrese el ID del cliente: ");
                    String custId = scanner.nextLine();
                    List<Return> byCust = returnService.viewReturnsByCustomer(custId);
                    if (byCust.isEmpty()) {
                        System.out.println("No se encontraron devoluciones para este cliente.");
                    } else {
                        for (Return r : byCust) {
                            System.out.println(r.generateReturnReceipt());
                        }
                    }
                    break;
                case 4:
                    System.out.print("Ingrese el ID de la venta: ");
                    String sId = scanner.nextLine();
                    List<Return> bySale = returnService.viewReturnsBySale(sId);
                    if (bySale.isEmpty()) {
                        System.out.println("No se encontraron devoluciones para esta venta.");
                    } else {
                        for (Return r : bySale) {
                            System.out.println(r.generateReturnReceipt());
                        }
                    }
                    break;
                case 5:
                    System.out.print("Ingrese el número del mes (1-12): ");
                    int month = scanner.nextInt();
                    System.out.print("Ingrese el año (ej. 2026): ");
                    int year = scanner.nextInt();
                    scanner.nextLine();
                    double balance = returnService.generateMonthlyBalance(month, year);
                    System.out.println("Balance neto para el período " + month + "/" + year + ": $" + balance);
                    break;
                case 6:
                    back = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }
}