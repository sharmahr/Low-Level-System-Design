Design Pattern: Decorator Design Pattern

Diagram:
```
        +---------------------+
        |     Component       |
        +---------------------+
        | + operation()       |
        +---------------------+
                  ^
                  |
         +--------+----------+
         |                   |
+----------------+    +-----------------+
|    Concrete    |    |    Decorator    |
|    Component   |    +-----------------+
+----------------+    | - component     |
| + operation()  |    | + operation()   |
+----------------+    +-----------------+
                                ^
                                |
                      +---------+---------+
                      |                   |
             +--------------------+  +--------------------+
             | ConcreteDecorator1 |  | ConcreteDecorator2 |
             +--------------------+  +--------------------+
             | + operation()      |  | + operation()      |
             | + addedBehavior1() |  | + addedBehavior2() |
             +--------------------+  +--------------------+
```

Explanation:
The Decorator Design Pattern is a structural pattern that allows adding new behaviors or responsibilities to an object dynamically without modifying its structure. It provides a flexible alternative to subclassing for extending the functionality of an object. The pattern involves creating a set of decorator classes that wrap the original object and provide additional behaviors. These decorators have the same interface as the original object, allowing them to be used interchangeably.

Key Points:
- Allows adding new behaviors to an object dynamically
- Provides a flexible alternative to subclassing for extending functionality
- Decorators have the same interface as the original object
- Decorators can be stacked to combine multiple behaviors
- Enhances the extensibility and flexibility of the system

Real-Life Applications:
- Adding extra toppings or condiments to a pizza or burger
- Applying filters or effects to an image in an image editing application
- Encrypting or compressing data in a data processing pipeline

Remember:
"The Decorator Pattern is like dressing up an object with additional accessories or behaviors, without changing its core identity."

Sample Code:
```java
// Component interface
interface Coffee {
    double getCost();
    String getDescription();
}

// Concrete Component
class SimpleCoffee implements Coffee {
    public double getCost() {
        return 1.0;
    }

    public String getDescription() {
        return "Simple Coffee";
    }
}

// Decorator abstract class
abstract class CoffeeDecorator implements Coffee {
    protected Coffee decoratedCoffee;

    public CoffeeDecorator(Coffee decoratedCoffee) {
        this.decoratedCoffee = decoratedCoffee;
    }

    public double getCost() {
        return decoratedCoffee.getCost();
    }

    public String getDescription() {
        return decoratedCoffee.getDescription();
    }
}

// Concrete Decorators
class MilkDecorator extends CoffeeDecorator {
    public MilkDecorator(Coffee decoratedCoffee) {
        super(decoratedCoffee);
    }

    public double getCost() {
        return super.getCost() + 0.5;
    }

    public String getDescription() {
        return super.getDescription() + ", Milk";
    }
}

class SugarDecorator extends CoffeeDecorator {
    public SugarDecorator(Coffee decoratedCoffee) {
        super(decoratedCoffee);
    }

    public double getCost() {
        return super.getCost() + 0.2;
    }

    public String getDescription() {
        return super.getDescription() + ", Sugar";
    }
}

// Client code
class CoffeeShop {
    public static void main(String[] args) {
        Coffee simpleCoffee = new SimpleCoffee();
        System.out.println(simpleCoffee.getDescription() + " $" + simpleCoffee.getCost());

        Coffee coffeeWithMilk = new MilkDecorator(simpleCoffee);
        System.out.println(coffeeWithMilk.getDescription() + " $" + coffeeWithMilk.getCost());

        Coffee coffeeWithMilkAndSugar = new SugarDecorator(coffeeWithMilk);
        System.out.println(coffeeWithMilkAndSugar.getDescription() + " $" + coffeeWithMilkAndSugar.getCost());
    }
}
```

In this example, the Decorator Pattern is used to add extra ingredients to a coffee order. The `Coffee` interface defines the base component, and the `SimpleCoffee` class represents the concrete component. The `CoffeeDecorator` abstract class serves as the base decorator, and the concrete decorators `MilkDecorator` and `SugarDecorator` add specific ingredients and behaviors to the coffee. The client code demonstrates how the decorators can be stacked to create different combinations of coffee orders.