Design Pattern: Flyweight Design Pattern

Diagram:
```
                            +------------------+
                            |    Flyweight     |
                            +------------------+
                            | - intrinsicState |
                            +------------------+
                            | + operation()    |
                            +------------------+
                                    ^
                                    |
                +-------------------+-------------------------+
                |                   |                         |
    +--------------------+ +--------------------+ +---------------------------+
    | ConcreteFlyweight1 | | ConcreteFlyweight2 | | UnsharedConcreteFlyweight |
    +--------------------+ +--------------------+ +---------------------------+
    | - intrinsicState   | | - intrinsicState   | | - allState                |
    +--------------------+ +--------------------+ +---------------------------+
    | + operation()      | | + operation()      | | + operation()             |
    +--------------------+ +--------------------+ +---------------------------+
```

Explanation:
The Flyweight Design Pattern is a structural pattern that minimizes memory usage by sharing as much data as possible with other similar objects. It allows the reuse of objects by storing the common state (intrinsic state) externally and the unique state (extrinsic state) is passed to the flyweight object when needed. The pattern involves creating a factory that manages the pool of flyweight objects and returns an existing object if it matches the requested state, otherwise, it creates a new one. This pattern is useful when dealing with a large number of objects that have similar or identical state, and the creation of individual objects is expensive in terms of memory and performance.

Key Points:
- Minimizes memory usage by sharing data with similar objects
- Allows reuse of objects by storing common state externally
- Involves creating a factory that manages the pool of flyweight objects
- Useful when dealing with a large number of objects with similar state
- Helps in reducing the overall memory footprint of the application

Real-Life Applications:
- Representing characters in a text editor or word processor
- Managing game objects in a game engine, such as trees, bullets, or enemies
- Handling user sessions in a web application or a multi-user system

Remember:
"The Flyweight Pattern is like a shared library where books with the same content are not duplicated, but instead, multiple library cards point to the same book."

Sample Code:
```java
// Flyweight interface
interface Shape {
    void draw(int x, int y, String color);
}

// Concrete Flyweight class
class Circle implements Shape {
    private int radius;

    public Circle(int radius) {
        this.radius = radius;
        System.out.println("Creating circle with radius: " + radius);
    }

    public void draw(int x, int y, String color) {
        System.out.println("Drawing circle at (" + x + ", " + y + ") with color: " + color);
    }
}

// Flyweight Factory class
class ShapeFactory {
    private Map<Integer, Shape> circleMap = new HashMap<>();

    public Shape getCircle(int radius) {
        Shape circle = circleMap.get(radius);
        if (circle == null) {
            circle = new Circle(radius);
            circleMap.put(radius, circle);
        }
        return circle;
    }
}

// Client code
class FlyweightDemo {
    private static final String[] COLORS = {"Red", "Green", "Blue", "Yellow"};

    public static void main(String[] args) {
        ShapeFactory factory = new ShapeFactory();

        for (int i = 0; i < 10; i++) {
            Circle circle = (Circle) factory.getCircle(i % 3 + 1);
            circle.draw(i, i, COLORS[i % COLORS.length]);
        }
    }
}
```

In this example, the Flyweight Pattern is used to manage a pool of `Circle` objects. The `Shape` interface defines the common method `draw()` that takes the extrinsic state (x, y coordinates and color) as parameters. The `Circle` class is the concrete flyweight that implements the `Shape` interface and stores the intrinsic state (radius). The `ShapeFactory` class acts as the flyweight factory, managing the pool of `Circle` objects. It checks if a `Circle` with the requested radius already exists in the pool and returns it, otherwise, it creates a new one and adds it to the pool. The client code demonstrates how the flyweight objects are created and reused, reducing memory usage by sharing common state among similar objects.