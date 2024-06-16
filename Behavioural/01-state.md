Design Pattern: State Design Pattern

Diagram:
```
                +--------------+
                |   Context    |
                +--------------+
                | - state      |
                +--------------+
                | + request()  |
                +--------------+
                    |
                    |
                +------+-------+
                |     State    |
                +--------------+
                | + handle()   |
                +--------------+
                    ^       ^
                    |       |
            +-------+-------+--------+
            |                        |
+------------------+        +------------------+
|   ConcreteStateA |        |  ConcreteStateB  |
+------------------+        +------------------+
| + handle()       |        | + handle()       |
+------------------+        +------------------+
```

Explanation:
The State Design Pattern is a behavioral pattern that allows an object to alter its behavior when its internal state changes. It encapsulates state-specific behavior within separate state objects and allows the context object to delegate the behavior to the current state object. This pattern is useful when an object's behavior depends on its state, and the behavior changes at runtime based on the state. It helps in keeping the state-specific behavior separated from the context object, promoting cleaner and more maintainable code.

Key Points:
- Encapsulates state-specific behavior within separate state objects
- Allows an object to alter its behavior when its internal state changes
- Delegates the behavior to the current state object
- Useful when an object's behavior depends on its state and changes at runtime
- Promotes cleaner and more maintainable code by separating state-specific behavior

Real-Life Applications:
- Implementing a traffic light system with different states (red, yellow, green)
- Managing the states of a vending machine (idle, coin inserted, dispensing)
- Handling the states of an order processing system (pending, processing, shipped)

Remember:
"The State Pattern is like a chameleon that changes its behavior based on its environment or internal state."

Sample Code:
```java
// State interface
interface State {
    void handle();
}

// Concrete State classes
class ConcreteStateA implements State {
    public void handle() {
        System.out.println("Handling request in State A");
    }
}

class ConcreteStateB implements State {
    public void handle() {
        System.out.println("Handling request in State B");
    }
}

// Context class
class Context {
    private State state;

    public Context(State state) {
        this.state = state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void request() {
        state.handle();
    }
}

// Client code
class StateDemo {
    public static void main(String[] args) {
        Context context = new Context(new ConcreteStateA());

        context.request();  // Output: Handling request in State A

        context.setState(new ConcreteStateB());
        context.request();  // Output: Handling request in State B
    }
}
```

In this example, the State Pattern is used to manage the behavior of a `Context` object based on its internal state. The `State` interface defines the common method `handle()` that encapsulates the state-specific behavior. The concrete state classes (`ConcreteStateA` and `ConcreteStateB`) implement the `State` interface and provide their own implementations of the `handle()` method. The `Context` class maintains a reference to the current state object and delegates the behavior to it through the `request()` method. The client code demonstrates how the behavior of the `Context` object changes when its state is set to different concrete state objects.