# Designing a Vending Machine

## Requirements
1. The vending machine should support multiple products with different prices and quantities.
1. The machine should accept coins and notes of different denominations.
1. The machine should dispense the selected product and return change if necessary.
1. The machine should keep track of the available products and their quantities.
1. The machine should handle multiple transactions concurrently and ensure data consistency.
1. The machine should provide an interface for restocking products and collecting money.
1. The machine should handle exceptional scenarios, such as insufficient funds or out-of-stock products.

```
+-------------------------------------------------------+
|     Product                                           |
+-------------------------------------------------------+
| - name: String                                        |
| - price: double                                       |
| - quantity: int                                       |
+-------------------------------------------------------+
| + Product(name: String, price: double, quantity: int) |
| + getName(): String                                   |
| + getPrice(): double                                  |
| + getQuantity(): int                                  |
| + setQuantity(quantity: int): void                    |
+-------------------------------------------------------+

+------------------------------------------------------------+
|  VendingMachine                                            |
+------------------------------------------------------------+
| - products: Map<String, Product>                           |
| - balance: double                                          |
+------------------------------------------------------------+
| + VendingMachine()                                         |
| + addProduct(product: Product): void                       |
| + insertMoney(amount: double): void                        |
| + selectProduct(productName: String): Product              |
| + returnChange(): double                                   |
| + restockProduct(productName: String, quantity: int): void |
| + collectMoney(): double                                   |
+------------------------------------------------------------+
         ^
         |
         |  throws
         |
+-----------------------------------------------+
| InsufficientFundsException                    |
+-----------------------------------------------+
| + InsufficientFundsException(message: String) |
+-----------------------------------------------+

+----------------------------------------+
| OutOfStockException                    |
+----------------------------------------+
| + OutOfStockException(message: String) |
+----------------------------------------+
```

```java
// Product class representing a product in the vending machine
class Product {
    private String name;
    private double price;
    private int quantity;

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters and setters
    // ...
}

// VendingMachine class representing the vending machine
class VendingMachine {
    private Map<String, Product> products;
    private double balance;

    public VendingMachine() {
        products = new HashMap<>();
        balance = 0.0;
    }

    public synchronized void addProduct(Product product) {
        products.put(product.getName(), product);
    }

    public synchronized void insertMoney(double amount) {
        balance += amount;
    }

    public synchronized Product selectProduct(String productName) throws InsufficientFundsException, OutOfStockException {
        Product product = products.get(productName);
        if (product == null) {
            throw new IllegalArgumentException("Product not found");
        }
        if (product.getQuantity() == 0) {
            throw new OutOfStockException("Product is out of stock");
        }
        if (balance < product.getPrice()) {
            throw new InsufficientFundsException("Insufficient funds");
        }
        balance -= product.getPrice();
        product.setQuantity(product.getQuantity() - 1);
        return product;
    }

    public synchronized double returnChange() {
        double change = balance;
        balance = 0.0;
        return change;
    }

    public synchronized void restockProduct(String productName, int quantity) {
        Product product = products.get(productName);
        if (product != null) {
            product.setQuantity(product.getQuantity() + quantity);
        }
    }

    public synchronized double collectMoney() {
        double money = balance;
        balance = 0.0;
        return money;
    }

    // Other methods
    // ...
}

// Exception classes for exceptional scenarios
class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}

class OutOfStockException extends Exception {
    public OutOfStockException(String message) {
        super(message);
    }
}
```

Explanation:
- The `Product` class represents a product in the vending machine, with attributes such as name, price, and quantity.
- The `VendingMachine` class represents the vending machine itself and contains a map of products. It provides methods for adding products, inserting money, selecting a product, returning change, restocking products, and collecting money.
- The `InsufficientFundsException` and `OutOfStockException` classes are custom exception classes used to handle exceptional scenarios, such as insufficient funds or out-of-stock products.
- The methods in the `VendingMachine` class are synchronized to ensure thread safety and data consistency when handling multiple transactions concurrently.

Design Patterns Used:
1. Singleton Pattern (optional): The `VendingMachine` class can be implemented as a singleton to ensure only one instance of the vending machine exists throughout the application.
2. Exception Handling: Custom exception classes (`InsufficientFundsException` and `OutOfStockException`) are used to handle exceptional scenarios and provide meaningful error messages to the user.
3. Synchronization: The methods in the `VendingMachine` class are synchronized to handle concurrent access and ensure data consistency.

The design patterns used promote encapsulation, thread safety, and error handling. The singleton pattern ensures a single instance of the vending machine, while synchronization handles concurrent transactions. Exception handling allows for graceful handling of exceptional scenarios and provides informative error messages to the user.