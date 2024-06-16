# Low-Level-System-Design
Low Level System Design

1. Classes and Objects:
   - Classes are blueprints or templates that define the properties (attributes) and behaviors (methods) of objects.
   - Objects are instances of classes, created using the `new` keyword.
```java
class Car {
    String brand;
    String model;
    int year;

    void startEngine() {
        System.out.println("Engine started!");
    }
}

Car myCar = new Car();
myCar.brand = "Toyota";
myCar.model = "Camry";
myCar.year = 2022;
myCar.startEngine();
```


2. Encapsulation:
   - Encapsulation is the process of hiding the internal details of an object and providing a public interface to interact with it.
   - It is achieved through access modifiers (`private`, `protected`, `public`) and getter/setter methods.
```java
class BankAccount {
    private double balance;

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }
}

BankAccount account = new BankAccount();
account.deposit(1000);
System.out.println(account.getBalance());
```


3. Inheritance:
   - Inheritance allows a class to inherit properties and methods from another class.
   - The class that inherits is called the subclass or derived class, and the class being inherited from is called the superclass or base class.
   - Inheritance promotes code reuse and establishes an "is-a" relationship between classes.
```java
class Animal {
    void eat() {
        System.out.println("Animal is eating");
    }
}

class Dog extends Animal {
    void bark() {
        System.out.println("Dog is barking");
    }
}

Dog dog = new Dog();
dog.eat();
dog.bark();
```

4. Polymorphism:
   - Polymorphism allows objects of different classes to be treated as objects of a common superclass.
   - It enables the use of a single interface to represent different types of objects.
   - Polymorphism is achieved through method overriding and method overloading.
```java
class Shape {
    void draw() {
        System.out.println("Drawing a shape");
    }
}

class Circle extends Shape {
    void draw() {
        System.out.println("Drawing a circle");
    }
}

class Rectangle extends Shape {
    void draw() {
        System.out.println("Drawing a rectangle");
    }
}

Shape shape1 = new Circle();
Shape shape2 = new Rectangle();
shape1.draw();
shape2.draw();
```

5. Abstraction:
   - Abstraction focuses on defining the essential features of an object without specifying the details.
   - It is achieved through abstract classes and interfaces.
   - Abstract classes can contain both abstract and non-abstract methods, while interfaces can only contain abstract methods.
```java
abstract class Employee {
    abstract void calculateSalary();
}

class Manager extends Employee {
    void calculateSalary() {
        System.out.println("Calculating manager's salary");
    }
}

Manager manager = new Manager();
manager.calculateSalary();
```

6. Method Overriding:
   - Method overriding occurs when a subclass provides its own implementation of a method that is already defined in its superclass.
   - The overridden method must have the same name, return type, and parameters as the method in the superclass.
```java
class Animal {
    void makeSound() {
        System.out.println("Animal is making a sound");
    }
}

class Cat extends Animal {
    void makeSound() {
        System.out.println("Meow!");
    }
}

Cat cat = new Cat();
cat.makeSound();
```

7. Method Overloading:
   - Method overloading allows multiple methods with the same name but different parameters within the same class.
   - The compiler distinguishes overloaded methods based on the number, type, and order of the parameters.
```java
class Calculator {
    int add(int a, int b) {
        return a + b;
    }

    double add(double a, double b) {
        return a + b;
    }
}

Calculator calc = new Calculator();
System.out.println(calc.add(5, 3));
System.out.println(calc.add(2.5, 1.7));
```

8. Interfaces:
   - Interfaces define a contract or a set of methods that a class must implement.
   - They provide a way to achieve abstraction and multiple inheritance in Java.
   - Classes can implement multiple interfaces, but they can only inherit from a single superclass.
```java
interface Drawable {
    void draw();
}

class Circle implements Drawable {
    public void draw() {
        System.out.println("Drawing a circle");
    }
}

Drawable drawable = new Circle();
drawable.draw();
```

9. Composition and Aggregation:
   - Composition represents a "has-a" relationship, where one object contains another object as its part.
   - Aggregation represents a weaker "has-a" relationship, where one object uses another object.
```java
class Engine {
    void start() {
        System.out.println("Engine started");
    }
}

class Car {
    private Engine engine;

    Car(Engine engine) {
        this.engine = engine;
    }

    void startCar() {
        engine.start();
    }
}

Engine engine = new Engine();
Car car = new Car(engine);
car.startCar();
```

10. Association:
    - Association represents a relationship between two classes where one class uses or interacts with another class.
    - It can be one-to-one, one-to-many, or many-to-many.
```java
class Student {
    void attendCourse(Course course) {
        System.out.println("Student attending course: " + course.name);
    }
}

class Course {
    String name;

    Course(String name) {
        this.name = name;
    }
}

Student student = new Student();
Course course = new Course("Math");
student.attendCourse(course);
```

# SOLID

SOLID is an acronym that represents five fundamental principles of object-oriented design. These principles help in creating software that is maintainable, flexible, and easy to understand. Let's go through each principle in detail with simple examples in Java.

1. Single Responsibility Principle (SRP):
   - This principle states that a class should have only one reason to change, meaning it should have a single responsibility or a single job.
   - Example:
     ```java
     class Employee {
         void calculateSalary() {
             // Salary calculation logic
         }
     }

     class EmployeeRepository {
         void saveEmployee(Employee employee) {
             // Save employee to database
         }
     }
     ```
     In this example, the `Employee` class is responsible for calculating the salary, while the `EmployeeRepository` class is responsible for saving the employee to the database. Each class has a single responsibility.

2. Open-Closed Principle (OCP):
   - This principle states that classes should be open for extension but closed for modification.
   - It means that you should be able to extend the behavior of a class without modifying its existing code.
   - Example:
     ```java
     interface Shape {
         void draw();
     }

     class Circle implements Shape {
         public void draw() {
             // Draw a circle
         }
     }

     class Rectangle implements Shape {
         public void draw() {
             // Draw a rectangle
         }
     }
     ```
     In this example, the `Shape` interface defines a contract for drawing shapes. New shapes can be added by implementing the `Shape` interface without modifying the existing code.

3. Liskov Substitution Principle (LSP):
   - This principle states that objects of a superclass should be replaceable with objects of its subclasses without affecting the correctness of the program.
   - It means that subclasses should be substitutable for their base classes.
   - Example:
     ```java
     class Bird {
         void fly() {
             // Bird can fly
         }
     }

     class Sparrow extends Bird {
         void fly() {
             // Sparrow can fly
         }
     }

     class Ostrich extends Bird {
         void run() {
             // Ostrich can run but not fly
         }
     }
     ```
     In this example, the `Sparrow` class can be substituted for the `Bird` class without any issues. However, the `Ostrich` class violates the LSP because it cannot fly, which is expected behavior of the `Bird` class.

4. Interface Segregation Principle (ISP):
   - This principle states that clients should not be forced to depend on interfaces they do not use.
   - It promotes the idea of having smaller, more specific interfaces instead of large, monolithic interfaces.
   - Example:
     ```java
     interface Printable {
         void print();
     }

     interface Scannable {
         void scan();
     }

     class Printer implements Printable {
         public void print() {
             // Print logic
         }
     }

     class Scanner implements Scannable {
         public void scan() {
             // Scan logic
         }
     }
     ```
     In this example, the `Printable` and `Scannable` interfaces are segregated based on their specific functionalities. The `Printer` class only implements the `Printable` interface, while the `Scanner` class only implements the `Scannable` interface.

5. Dependency Inversion Principle (DIP):
   - This principle states that high-level modules should not depend on low-level modules. Both should depend on abstractions.
   - It promotes loose coupling between classes by depending on interfaces or abstract classes rather than concrete implementations.
   - Example:
     ```java
     interface Database {
         void saveData(String data);
     }

     class MySQLDatabase implements Database {
         public void saveData(String data) {
             // Save data to MySQL database
         }
     }

     class Application {
         private Database database;

         Application(Database database) {
             this.database = database;
         }

         void saveData(String data) {
             database.saveData(data);
         }
     }
     ```
     In this example, the `Application` class depends on the `Database` interface rather than a concrete implementation. The `MySQLDatabase` class implements the `Database` interface. This allows the `Application` class to work with any database implementation that adheres to the `Database` interface.


# Some Other Design Principles

1. DRY (Don't Repeat Yourself):
   - The DRY principle states that you should avoid duplication of code and logic in your codebase.
   - It promotes the idea of abstracting common functionality into reusable components.
   - Example:
     ```java
     class MathUtils {
         static int square(int num) {
             return num * num;
         }
     }

     class Calculator {
         int calculateSum(int a, int b) {
             return MathUtils.square(a) + MathUtils.square(b);
         }
     }
     ```
     In this example, the `square` method is abstracted into the `MathUtils` class, which can be reused by other classes like `Calculator`. This avoids duplication of the square calculation logic.

2. KISS (Keep It Simple, Stupid):
   - The KISS principle emphasizes the importance of keeping your code simple and avoiding unnecessary complexity.
   - It suggests that simpler solutions are often better than complex ones.
   - Example:
     ```java
     class StringUtils {
         static String reverseString(String str) {
             return new StringBuilder(str).reverse().toString();
         }
     }
     ```
     In this example, the `reverseString` method uses the built-in `StringBuilder` class to reverse a string in a simple and concise way, adhering to the KISS principle.

3. YAGNI (You Ain't Gonna Need It):
   - The YAGNI principle states that you should only implement functionality when it is actually needed, rather than anticipating future requirements.
   - It suggests avoiding over-engineering and adding unnecessary features upfront.
   - Example:
     ```java
     class User {
         private String name;
         private String email;

         // Getters and setters

         // Implement additional methods only when needed
     }
     ```
     In this example, the `User` class only includes the necessary properties (`name` and `email`) and their getters and setters. Additional methods or features should be added only when they are actually required.

4. Composition over Inheritance:
   - This principle suggests favoring composition (using objects as part of other objects) over inheritance when designing class relationships.
   - Composition allows for more flexibility and avoids the pitfalls of deep inheritance hierarchies.
   - Example:
     ```java
     class Logger {
         void log(String message) {
             System.out.println("Logging: " + message);
         }
     }

     class UserService {
         private Logger logger;

         UserService(Logger logger) {
             this.logger = logger;
         }

         void createUser(String name) {
             // Create user logic
             logger.log("User created: " + name);
         }
     }
     ```
     In this example, the `UserService` class uses composition by having a `Logger` object as a dependency, rather than inheriting from a base class. This allows for better flexibility and modularity.

5. Separation of Concerns:
   - This principle emphasizes the idea of organizing code into distinct sections or modules, each responsible for a specific functionality or concern.
   - It promotes a clear separation between different aspects of the system, making it more maintainable and easier to understand.
   - Example:
     ```java
     class UserController {
         private UserService userService;

         // Constructor and other methods

         void createUser(String name) {
             userService.createUser(name);
         }
     }

     class UserService {
         void createUser(String name) {
             // Create user logic
         }
     }
     ```
     In this example, the `UserController` class is responsible for handling user-related requests, while the `UserService` class is responsible for the actual user creation logic. This separation of concerns makes the code more modular and easier to maintain.

# Inheritance

In Java, there are three types of inheritance:

1. Single Inheritance:
   - Single inheritance occurs when a class inherits from a single parent class.
   - It represents an "is-a" relationship between the derived class and the base class.
   - Example:
     ```java
     class Animal {
         // Animal class members
     }

     class Dog extends Animal {
         // Dog class members
     }
     ```
     In this example, the `Dog` class inherits from the `Animal` class, establishing a single inheritance relationship.

2. Multilevel Inheritance:
   - Multilevel inheritance occurs when a class inherits from a class that also inherits from another class.
   - It creates a chain of inheritance, where a subclass becomes the superclass for another class.
   - Example:
     ```java
     class Animal {
         // Animal class members
     }

     class Mammal extends Animal {
         // Mammal class members
     }

     class Dog extends Mammal {
         // Dog class members
     }
     ```
     In this example, the `Dog` class inherits from the `Mammal` class, which in turn inherits from the `Animal` class, forming a multilevel inheritance hierarchy.

3. Hierarchical Inheritance:
   - Hierarchical inheritance occurs when multiple classes inherit from a single base class.
   - It represents a one-to-many inheritance relationship.
   - Example:
     ```java
     class Animal {
         // Animal class members
     }

     class Dog extends Animal {
         // Dog class members
     }

     class Cat extends Animal {
         // Cat class members
     }
     ```
     In this example, both the `Dog` and `Cat` classes inherit from the `Animal` class, establishing a hierarchical inheritance relationship.

Now, let's discuss the difference between an interface and an abstract class:

1. Interface:
   - An interface is a contract that specifies a set of abstract methods that a class must implement.
   - It defines the behavior that a class should have, without specifying the implementation details.
   - An interface can only contain abstract methods, default methods (since Java 8), and static methods.
   - A class can implement multiple interfaces, achieving a form of multiple inheritance.
   - Interfaces are used to define a common behavior that can be shared by unrelated classes.
   - Example:
     ```java
     interface Drawable {
         void draw();
     }

     class Circle implements Drawable {
         public void draw() {
             // Drawing a circle
         }
     }
     ```

2. Abstract Class:
   - An abstract class is a class that cannot be instantiated and may contain both abstract and non-abstract methods.
   - It provides a base for subclasses to inherit from and can contain implementation details.
   - An abstract class can have instance variables, constructors, and concrete methods.
   - A class can only inherit from a single abstract class.
   - Abstract classes are used when there is a need for a base implementation that subclasses can build upon.
   - Example:
     ```java
     abstract class Shape {
         abstract double calculateArea();
     }

     class Rectangle extends Shape {
         double length;
         double width;

         double calculateArea() {
             return length * width;
         }
     }
     ```

When to use an interface or an abstract class:
- Use an interface when you want to define a contract for behavior that can be implemented by unrelated classes. Interfaces are suitable when you have a set of methods that multiple classes should implement, regardless of their inheritance hierarchy.
- Use an abstract class when you want to provide a base implementation that subclasses can inherit from and extend. Abstract classes are useful when you have common functionality that needs to be shared among related classes, and you want to enforce a certain level of inheritance.

It's important to note that a class can implement multiple interfaces but can only extend a single class (abstract or concrete). This allows for a form of multiple inheritance through interfaces while avoiding the complexities of multiple inheritance of classes.
