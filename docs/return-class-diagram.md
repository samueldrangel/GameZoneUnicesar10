```mermaid
classDiagram
    class Return {
        -String returnId
        -LocalDate returnDate
        -Sale originalSale
        -List~Product~ returnedProducts
        -String reason
        -double refundAmount
        +Return(String returnId, LocalDate returnDate, Sale originalSale, List~Product~ returnedProducts, String reason)
        +getReturnId() String
        +getReturnDate() LocalDate
        +getOriginalSale() Sale
        +getReturnedProducts() List~Product~
        +getReason() String
        +getRefundAmount() double
        +calculateRefundAmount() double
        +generateReturnReceipt() String
    }
    class Sale {
        -String saleId
        -LocalDate date
        -List~Product~ items
        +getSaleId() String
        +getDate() LocalDate
        +getItems() List~Product~
        +canBeReturned() boolean
    }
    class ReturnRepository {
        -String filePath
        -SaleService saleService
        -ProductService productService
        +ReturnRepository(SaleService saleService, ProductService productService)
        +loadAll() List~Return~
        +saveAll(List~Return~ returns)
    }
    class ReturnService {
        -ReturnRepository returnRepository
        -SaleService saleService
        -ProductService productService
        -List~Return~ returns
        +ReturnService(ReturnRepository returnRepository, SaleService saleService, ProductService productService)
        +registerReturn(String saleId, List~String~ productIds, String reason) Return
        +viewAllReturns() List~Return~
        +viewReturnsByCustomer(String customerId) List~Return~
        +viewReturnsBySale(String saleId) List~Return~
        +generateMonthlyBalance(int month, int year) double
    }
    class ProductService {
        +restoreStock(String productId, int quantity)
        +getProductById(String productId) Product
    }
    Return --> Sale : references
    ReturnRepository --> Return : persists
    ReturnService --> ReturnRepository : uses
    ReturnService --> SaleService : uses
    ReturnService --> ProductService : uses