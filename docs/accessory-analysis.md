# Accessory Module Analysis - GameZoneUnicesar

1. **Hierarchy Integration**: Accessories should extend the abstract class `Product` to maximize code reuse (id, title, price, stock) and maintain consistency across the catalog.
2. **Accessory Attributes**: Common attributes are handled by `Product` and `Accessory`. Specific attributes are: connection type and compatible consoles for controllers; length and connector type for cables; storage capacity and memory type for memories.
3. **Compatibility Representation**: Compatibility is a many-to-many relationship between accessories (specifically controllers and some memories) and consoles, represented as a list of compatible console IDs (`List<String> compatibleConsoleIds`) inside the `Accessory` class.
4. **SaleService Modifications**: `SaleService.registerSale` must accept a unified list of sellable items (`Product`), validating stock and delegating inventory updates to either `ProductService` or `AccessoryService` dynamically based on the item type.
5. **Layer Placement**: New classes are distributed across the four layers: `model` (`Accessory`, `Controller`, `Cable`, `Memory`), `persistence` (`AccessoryRepository`), `service` (`AccessoryService`), and integrated into `ui` and `service` (`SaleService`, `ConsoleUI`).

