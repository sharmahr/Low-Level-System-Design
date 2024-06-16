Design Pattern: Facade Design Pattern

Diagram:
```
            +-------------+
            |   Client    |
            +-------------+
                    |
                    |
            +-------------+
            |   Facade    |
            +-------------+
                    |
                    |
        +-----------+------------+
        |           |            |
+------------+ +------------+ +------------+
| SubsystemA | | SubsystemB | | SubsystemC |
+------------+ +------------+ +------------+
```

Explanation:
The Facade Design Pattern is a structural pattern that provides a simplified interface to a complex subsystem or a set of interfaces. It defines a higher-level interface that makes the subsystem easier to use and understand. The Facade encapsulates the complexity of the subsystem and provides a single entry point for the client, promoting a loose coupling between the client and the subsystem. This pattern is useful when you want to provide a simple and unified interface to a complex system or when you want to decouple the client from the subsystem's implementation details.

Key Points:
- Provides a simplified interface to a complex subsystem or a set of interfaces
- Defines a higher-level interface that makes the subsystem easier to use
- Encapsulates the complexity of the subsystem
- Provides a single entry point for the client
- Promotes loose coupling between the client and the subsystem

Real-Life Applications:
- Providing a simple interface to a complex library or framework
- Encapsulating the functionality of multiple classes or modules into a single facade
- Simplifying the interaction with a complex system, such as a payment gateway or a messaging service

Remember:
"The Facade Pattern is like a concierge at a hotel who handles all the complex tasks for you, providing a simple and unified interface."

Sample Code:
```java
// Subsystem classes
class SubsystemA {
    public void operationA() {
        System.out.println("Subsystem A operation");
    }
}

class SubsystemB {
    public void operationB() {
        System.out.println("Subsystem B operation");
    }
}

class SubsystemC {
    public void operationC() {
        System.out.println("Subsystem C operation");
    }
}

// Facade class
class Facade {
    private SubsystemA subsystemA;
    private SubsystemB subsystemB;
    private SubsystemC subsystemC;

    public Facade() {
        subsystemA = new SubsystemA();
        subsystemB = new SubsystemB();
        subsystemC = new SubsystemC();
    }

    public void operation1() {
        System.out.println("Operation 1:");
        subsystemA.operationA();
        subsystemB.operationB();
    }

    public void operation2() {
        System.out.println("Operation 2:");
        subsystemB.operationB();
        subsystemC.operationC();
    }
}

// Client code
class Client {
    public static void main(String[] args) {
        Facade facade = new Facade();

        facade.operation1();
        facade.operation2();
    }
}
```

In this example, the Facade Pattern is used to provide a simplified interface to a complex subsystem. The subsystem consists of three classes: `SubsystemA`, `SubsystemB`, and `SubsystemC`, each having its own operation. The `Facade` class encapsulates the subsystem and provides two high-level operations (`operation1` and `operation2`) that internally call the operations of the subsystem classes. The client code interacts with the Facade, which simplifies the usage of the subsystem and hides its complexity. The Facade acts as a single entry point for the client, promoting loose coupling and making the system easier to use and understand.