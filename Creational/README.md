
# Creational Patterns
| Pattern | Note | Code |
|:-------------|:--------------:|-------------:|
| Factory Design Pattern | The Factory Pattern is like a vending machine that dispenses objects based on the selected type, without exposing the internal creation process. | [Factory Design Pattern](01-factory.md) |
| Abstract Factory Design Pattern | The Abstract Factory Pattern is like a factory of factories, where each factory creates a family of related objects that work together seamlessly. | [Abstract Factory Design Pattern](02-abstract-factory.md) |
| Builer Design Pattern | The Builder Pattern is like a recipe for constructing complex objects, where each step can be customized to create different variations of the final product. | [Builer Design Pattern](03-builder.md) |
| Prototype Design Pattern | The Prototype Pattern is like using a copy machine to create identical copies of an object, avoiding the need to create objects from scratch. | [Prototype Design Pattern](04-prototype.md) |
| Singleton Design Pattern | The Singleton Pattern is like having a single key to a locked room, ensuring that only one person can enter at a time. | [Singleton Design Pattern](05-singleton.md) |


Certainly! As an expert software engineer, I'd be happy to help you build an intuition for when to use these creational design patterns. Each pattern has its own strengths and is suited for different scenarios. Let's break them down:

1. Factory Pattern

Use when:
- You need a simple, straightforward way to create objects without exposing the creation logic.
- The exact type of object you need to create isn't known until runtime.
- You want to centralize object creation logic in one place.

Example scenario:
You're building a payment processing system that needs to create different types of payment methods (Credit Card, PayPal, Bank Transfer) based on user selection.

```java
public class PaymentMethodFactory {
    public PaymentMethod createPaymentMethod(String type) {
        if (type.equals("CreditCard")) return new CreditCardPayment();
        if (type.equals("PayPal")) return new PayPalPayment();
        if (type.equals("BankTransfer")) return new BankTransferPayment();
        throw new IllegalArgumentException("Unknown payment method");
    }
}
```

2. Abstract Factory Pattern

Use when:
- You need to create families of related or dependent objects without specifying their concrete classes.
- You want to ensure that created objects work together consistently.
- You're dealing with multiple possible platforms or environments.

Example scenario:
You're developing a cross-platform UI toolkit that needs to create different UI elements (buttons, checkboxes) that are consistent within each platform (Windows, MacOS, Linux).

```java
public interface UIFactory {
    Button createButton();
    Checkbox createCheckbox();
}

public class WindowsUIFactory implements UIFactory {
    public Button createButton() { return new WindowsButton(); }
    public Checkbox createCheckbox() { return new WindowsCheckbox(); }
}

public class MacUIFactory implements UIFactory {
    public Button createButton() { return new MacButton(); }
    public Checkbox createCheckbox() { return new MacCheckbox(); }
}
```

3. Builder Pattern

Use when:
- You need to create complex objects with many optional parameters or configurations.
- You want to create different representations of an object using the same construction process.
- You want to separate the construction of a complex object from its representation.

Example scenario:
You're creating a meal ordering system where a meal can have multiple components (main course, side dish, drink, dessert) with various options for each.

```java
public class MealBuilder {
    private Meal meal = new Meal();

    public MealBuilder addMainCourse(String main) {
        meal.setMainCourse(main);
        return this;
    }

    public MealBuilder addSideDish(String side) {
        meal.setSideDish(side);
        return this;
    }

    public MealBuilder addDrink(String drink) {
        meal.setDrink(drink);
        return this;
    }

    public MealBuilder addDessert(String dessert) {
        meal.setDessert(dessert);
        return this;
    }

    public Meal build() {
        return meal;
    }
}

// Usage
Meal meal = new MealBuilder()
    .addMainCourse("Steak")
    .addSideDish("Mashed Potatoes")
    .addDrink("Cola")
    .build();
```

4. Prototype Pattern

Use when:
- You need to create new objects by copying existing objects (cloning) instead of creating new instances from scratch.
- The cost of creating a new object is more expensive than copying an existing one.
- You want to reduce the number of classes you need to implement.

Example scenario:
You're developing a graphic editor where users can create complex shapes and then duplicate them with slight modifications.

```java
public abstract class Shape implements Cloneable {
    private String color;

    public abstract void draw();

    public Shape clone() {
        try {
            return (Shape) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}

public class Circle extends Shape {
    private int radius;

    public void draw() {
        System.out.println("Drawing a circle");
    }
}

// Usage
Circle circle = new Circle();
circle.setColor("Red");
circle.setRadius(10);

Circle clonedCircle = (Circle) circle.clone();
clonedCircle.setRadius(15);
```

5. Singleton Pattern

Use when:
- You need to ensure that a class has only one instance throughout the application.
- You need global access to that single instance.
- You want to control access to a shared resource, like a database connection or a file.

Example scenario:
You're implementing a logging system for your application, and you want to ensure that all parts of the application use the same logger instance.

```java
public class Logger {
    private static Logger instance;

    private Logger() {}

    public static synchronized Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public void log(String message) {
        System.out.println("LOG: " + message);
    }
}

// Usage
Logger.getInstance().log("This is a log message");
```

Building Intuition:
- Use Factory when you need simple, runtime object creation.
- Use Abstract Factory when you need to create families of related objects.
- Use Builder for complex objects with many optional components.
- Use Prototype when object creation is expensive and you can clone instead.
- Use Singleton when you need one and only one instance of an object.