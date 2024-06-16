Design Pattern: Observer Design Pattern

Diagram:
```
            +------------------------+
            |   Subject              |
            +------------------------+
            | + register(Observer)   |
            | + unregister(Observer) |
            | + notifyObservers()    |
            +------------------------+
                        ^
                        |
                        |
                +--------------+
                |   Observer   |
                +--------------+
                | + update()   |
                +--------------+
                    ^      ^
                    |      |
            +-------+------+-------+
            |                      |
+-------------------+   +-------------------+
| ConcreteObserverA |   | ConcreteObserverB |
+-------------------+   +-------------------+
| + update()        |   | + update()        |
+-------------------+   +-------------------+
```

Explanation:
The Observer Design Pattern is a behavioral pattern that establishes a one-to-many relationship between objects, where changes in the state of one object (the subject) are automatically notified to its dependent objects (the observers). It allows the subject to maintain a list of observers and notify them automatically of any state changes, usually by calling one of their methods. This pattern promotes loose coupling between the subject and its observers, allowing them to interact without having direct knowledge of each other.

Key Points:
- Establishes a one-to-many relationship between objects
- Allows the subject to maintain a list of observers
- Automatically notifies observers of state changes in the subject
- Promotes loose coupling between the subject and its observers
- Enables the addition or removal of observers dynamically

Real-Life Applications:
- Implementing a news subscription system where subscribers receive updates
- Updating user interface components when the underlying data changes
- Handling event-driven architectures, such as GUI frameworks or message queues

Remember:
"The Observer Pattern is like a newspaper subscription, where subscribers (observers) receive updates whenever a new edition (state change) is published by the newspaper (subject)."

Sample Code:
```java
// Subject interface
interface Subject {
    void register(Observer observer);
    void unregister(Observer observer);
    void notifyObservers();
}

// Observer interface
interface Observer {
    void update(String message);
}

// Concrete Subject class
class ConcreteSubject implements Subject {
    private List<Observer> observers = new ArrayList<>();
    private String message;

    public void register(Observer observer) {
        observers.add(observer);
    }

    public void unregister(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    public void setMessage(String message) {
        this.message = message;
        notifyObservers();
    }
}

// Concrete Observer classes
class ConcreteObserverA implements Observer {
    public void update(String message) {
        System.out.println("Observer A received: " + message);
    }
}

class ConcreteObserverB implements Observer {
    public void update(String message) {
        System.out.println("Observer B received: " + message);
    }
}

// Client code
class ObserverDemo {
    public static void main(String[] args) {
        ConcreteSubject subject = new ConcreteSubject();

        ConcreteObserverA observerA = new ConcreteObserverA();
        ConcreteObserverB observerB = new ConcreteObserverB();

        subject.register(observerA);
        subject.register(observerB);

        subject.setMessage("First message");
        subject.setMessage("Second message");

        subject.unregister(observerB);

        subject.setMessage("Third message");
    }
}
```

In this example, the Observer Pattern is used to notify observers of changes in the state of a subject. The `Subject` interface defines the methods for registering, unregistering, and notifying observers. The `Observer` interface defines the `update()` method that is called by the subject to notify the observers. The `ConcreteSubject` class maintains a list of observers and notifies them when its state (message) changes. The concrete observer classes (`ConcreteObserverA` and `ConcreteObserverB`) implement the `Observer` interface and define their own behavior for handling updates. The client code demonstrates how observers can be registered, unregistered, and notified of changes in the subject's state.