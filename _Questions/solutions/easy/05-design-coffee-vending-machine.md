# Designing a Coffee Vending Machine

## Requirements
1. The coffee vending machine should support different types of coffee, such as espresso, cappuccino, and latte.
2. Each type of coffee should have a specific price and recipe (ingredients and their quantities).
3. The machine should have a menu to display the available coffee options and their prices.
4. Users should be able to select a coffee type and make a payment.
5. The machine should dispense the selected coffee and provide change if necessary.
6. The machine should track the inventory of ingredients and notify when they are running low.
7. The machine should handle multiple user requests concurrently and ensure thread safety.

```java
// CoffeeType enum representing different types of coffee
enum CoffeeType {
    ESPRESSO, CAPPUCCINO, LATTE
}

// CoffeeRecipe class representing the recipe for a coffee type
class CoffeeRecipe {
    private Map<String, Integer> ingredients;

    public CoffeeRecipe(Map<String, Integer> ingredients) {
        this.ingredients = ingredients;
    }

    // Getters and setters
    // ...
}

// CoffeeItem class representing a coffee item in the vending machine
class CoffeeItem {
    private CoffeeType type;
    private double price;
    private CoffeeRecipe recipe;

    public CoffeeItem(CoffeeType type, double price, CoffeeRecipe recipe) {
        this.type = type;
        this.price = price;
        this.recipe = recipe;
    }

    // Getters and setters
    // ...
}

// CoffeeVendingMachine class representing the coffee vending machine
class CoffeeVendingMachine {
    private Map<CoffeeType, CoffeeItem> coffeeItems;
    private Map<String, Integer> inventory;
    private double balance;

    public CoffeeVendingMachine() {
        coffeeItems = new HashMap<>();
        inventory = new HashMap<>();
        balance = 0.0;
    }

    public synchronized void addCoffeeItem(CoffeeItem coffeeItem) {
        coffeeItems.put(coffeeItem.getType(), coffeeItem);
    }

    public synchronized void addInventory(String ingredient, int quantity) {
        inventory.put(ingredient, inventory.getOrDefault(ingredient, 0) + quantity);
    }

    public synchronized void displayMenu() {
        System.out.println("Coffee Menu:");
        for (CoffeeItem coffeeItem : coffeeItems.values()) {
            System.out.println(coffeeItem.getType() + " - $" + coffeeItem.getPrice());
        }
    }

    public synchronized void selectCoffee(CoffeeType type, double payment) {
        CoffeeItem coffeeItem = coffeeItems.get(type);
        if (coffeeItem != null) {
            if (payment >= coffeeItem.getPrice()) {
                if (hasEnoughIngredients(coffeeItem.getRecipe())) {
                    dispenseCoffee(coffeeItem.getRecipe());
                    balance += coffeeItem.getPrice();
                    System.out.println("Enjoy your " + type + " coffee!");
                    if (payment > coffeeItem.getPrice()) {
                        double change = payment - coffeeItem.getPrice();
                        System.out.println("Your change: $" + change);
                    }
                } else {
                    System.out.println("Insufficient ingredients. Please try again later.");
                }
            } else {
                System.out.println("Insufficient payment. Please insert more money.");
            }
        } else {
            System.out.println("Invalid coffee type selected.");
        }
    }

    private synchronized boolean hasEnoughIngredients(CoffeeRecipe recipe) {
        for (Map.Entry<String, Integer> entry : recipe.getIngredients().entrySet()) {
            String ingredient = entry.getKey();
            int requiredQuantity = entry.getValue();
            int availableQuantity = inventory.getOrDefault(ingredient, 0);
            if (availableQuantity < requiredQuantity) {
                return false;
            }
        }
        return true;
    }

    private synchronized void dispenseCoffee(CoffeeRecipe recipe) {
        for (Map.Entry<String, Integer> entry : recipe.getIngredients().entrySet()) {
            String ingredient = entry.getKey();
            int requiredQuantity = entry.getValue();
            inventory.put(ingredient, inventory.get(ingredient) - requiredQuantity);
            if (inventory.get(ingredient) <= 5) {
                System.out.println("Warning: " + ingredient + " is running low!");
            }
        }
    }

    public synchronized double collectMoney() {
        double money = balance;
        balance = 0.0;
        return money;
    }
}
```

Explanation:
- The `CoffeeType` enum represents different types of coffee, such as espresso, cappuccino, and latte.
- The `CoffeeRecipe` class represents the recipe for a coffee type, containing a map of ingredients and their quantities.
- The `CoffeeItem` class represents a coffee item in the vending machine, with attributes such as coffee type, price, and recipe.
- The `CoffeeVendingMachine` class represents the coffee vending machine itself and contains maps of coffee items and inventory. It provides methods for adding coffee items, updating inventory, displaying the menu, selecting a coffee, and collecting money.
- The `selectCoffee` method handles the user's selection of a coffee type and the payment process. It checks if the selected coffee type is valid, if the payment is sufficient, and if there are enough ingredients to dispense the coffee.
- The `hasEnoughIngredients` method checks if the machine has sufficient ingredients to prepare the selected coffee based on its recipe.
- The `dispenseCoffee` method deducts the required ingredients from the inventory and notifies when an ingredient is running low.
- The methods in the `CoffeeVendingMachine` class are synchronized to ensure thread safety and handle concurrent user requests.

Design Patterns Used:
1. Singleton Pattern (optional): The `CoffeeVendingMachine` class can be implemented as a singleton to ensure only one instance of the vending machine exists throughout the application.
2. Composition: The `CoffeeItem` class is composed of a `CoffeeRecipe` object, promoting code reuse and modularity.
3. Synchronization: The methods in the `CoffeeVendingMachine` class are synchronized to handle concurrent access and ensure thread safety.

The design patterns used promote encapsulation, thread safety, and modularity. The singleton pattern ensures a single instance of the coffee vending machine, while composition allows for the association between coffee items and their recipes. Synchronization handles concurrent user requests and ensures data consistency.