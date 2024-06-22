
# Behavioural Design Pattern
| Pattern | Note | Code |
|:-------------|:--------------:|-------------:|
| State Design Pattern | The State Pattern is like a chameleon that changes its behavior based on its environment or internal state. | [State Design Pattern](01-state.md) |
| Observer Design Pattern | The Observer Pattern is like a newspaper subscription, where subscribers (observers) receive updates whenever a new edition (state change) is published by the newspaper (subject). | [Observer Design Pattern](02-observer.md) |
| Strategy Design Pattern | The Strategy Pattern is like a toolbox where you can choose the appropriate tool (algorithm) for a specific task, without changing the overall structure of your code. | [Strategy Design Pattern](03-strategy.md) |
| Chain Of Responsibility Design Pattern | The Chain of Responsibility Pattern is like a relay race, where each participant has the chance to handle the baton (request) or pass it to the next runner. | [Chain Of Responsibility Design Pattern](04-chain-of-responsibility.md) |
| Template Design Pattern | The Template Method Pattern is like a recipe, where the main steps are defined in the base class, and the specific ingredients can be added by the subclasses. | [Template Design Pattern](05-template.md) |
| Interpreter Design Pattern | The Interpreter Pattern is like a language translator that understands the grammar and can interpret sentences in that language. | [Interpreter Design Pattern](06-interpreter.md) |
| Command Design Pattern | The Command Pattern is like a remote control, where each button represents a command that can be executed independently of the device it controls. | [Command Design Pattern](07-command.md) |
| Iterator Design Pattern | The Iterator Pattern is like a tour guide who knows the best route to visit all the attractions without the tourists needing to know the details of the tour. | [Iterator Design Pattern](08-iterator.md) |
| Visitor Design Pattern | The Visitor Pattern is like a tourist who visits different cities (elements) and experiences them without the cities having to change their behavior for each tourist. | [Visitor Design Pattern](09-visitor.md) |
| Mediator Design Pattern | The Mediator Pattern is like a traffic control tower that manages the communication between airplanes (objects), ensuring they don't directly communicate with each other. | [Mediator Design Pattern](10-mediator.md) |
| Memento Design Pattern | The Memento Pattern is like a time machine that allows an object to travel back to its previous state without revealing the details of its journey. | [Memento Design Pattern](11-memento.md) |



Certainly! As an expert software engineer, I'd be happy to help you build an intuition for when to use these behavioral design patterns. Each pattern addresses specific behavioral challenges in software design. Let's break them down:

1. State Pattern

Use when:
- An object's behavior depends on its state, and it must change its behavior at runtime depending on that state.
- You have a class with multiple conditional statements that depend on the object's state.

Example scenario:
You're implementing a vending machine where its behavior changes based on its current state (e.g., no coin inserted, coin inserted, item selected, etc.).

```java
interface VendingMachineState {
    void insertCoin();
    void ejectCoin();
    void selectItem();
    void dispense();
}

class VendingMachine {
    private VendingMachineState currentState;

    public void setState(VendingMachineState state) {
        this.currentState = state;
    }

    public void insertCoin() {
        currentState.insertCoin();
    }

    // Other methods delegating to currentState
}
```

2. Template Method Pattern

Use when:
- You have an algorithm with a fixed structure but with steps that may vary.
- You want to avoid code duplication in similar algorithms.

Example scenario:
You're building a data mining application with different algorithms that follow a similar overall process but differ in specific steps.

```java
abstract class DataMiner {
    public final void mine() {
        openFile();
        extractData();
        parseData();
        analyzeData();
        sendReport();
        closeFile();
    }

    abstract void extractData();
    abstract void analyzeData();

    // Common methods
    void openFile() { /* ... */ }
    void parseData() { /* ... */ }
    void sendReport() { /* ... */ }
    void closeFile() { /* ... */ }
}
```

3. Observer Pattern

Use when:
- You need a one-to-many dependency between objects so that when one object changes state, all its dependents are notified and updated automatically.
- You want to achieve loose coupling between objects.

Example scenario:
You're implementing a news subscription service where multiple subscribers need to be notified when new articles are published.

```java
interface Observer {
    void update(String news);
}

class NewsAgency {
    private List<Observer> observers = new ArrayList<>();
    private String news;

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void setNews(String news) {
        this.news = news;
        notifyObservers();
    }

    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(news);
        }
    }
}
```

4. Mediator Pattern

Use when:
- You want to reduce chaotic dependencies between objects.
- You want to centralize complex communications and control between related objects.

Example scenario:
You're developing a chat application where multiple users can communicate, but you want to centralize the communication logic.

```java
interface ChatMediator {
    void sendMessage(String msg, User user);
    void addUser(User user);
}

class User {
    private ChatMediator mediator;
    private String name;

    public User(ChatMediator med, String name) {
        this.mediator = med;
        this.name = name;
    }

    public void send(String msg) {
        mediator.sendMessage(msg, this);
    }

    public void receive(String msg) {
        System.out.println(name + " received: " + msg);
    }
}
```

5. Memento Pattern

Use when:
- You need to save and restore the previous state of an object without revealing the details of its implementation.
- You want to implement undo mechanisms.

Example scenario:
You're building a text editor with undo functionality.

```java
class EditorMemento {
    private final String state;

    public EditorMemento(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}

class Editor {
    private String content;

    public void setContent(String content) {
        this.content = content;
    }

    public EditorMemento save() {
        return new EditorMemento(content);
    }

    public void restore(EditorMemento memento) {
        content = memento.getState();
    }
}
```

6. Interpreter Pattern

Use when:
- You need to implement a simple language interpreter.
- You have a language to interpret, and you can represent its grammar as abstract syntax trees.

Example scenario:
You're implementing a simple arithmetic expression evaluator.

```java
interface Expression {
    int interpret();
}

class NumberExpression implements Expression {
    private int number;

    public NumberExpression(int number) {
        this.number = number;
    }

    public int interpret() {
        return number;
    }
}

class AddExpression implements Expression {
    private Expression left;
    private Expression right;

    public AddExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    public int interpret() {
        return left.interpret() + right.interpret();
    }
}
```

7. Chain of Responsibility Pattern

Use when:
- You want to give more than one object a chance to handle a request.
- You want to decouple senders and receivers of a request.

Example scenario:
You're implementing a logging system with different levels of logging (INFO, DEBUG, ERROR).

```java
abstract class Logger {
    protected Logger nextLogger;
    protected int level;

    public void setNextLogger(Logger nextLogger) {
        this.nextLogger = nextLogger;
    }

    public void logMessage(int level, String message) {
        if (this.level <= level) {
            write(message);
        }
        if (nextLogger != null) {
            nextLogger.logMessage(level, message);
        }
    }

    abstract protected void write(String message);
}
```

8. Visitor Pattern

Use when:
- You need to perform operations on a complex object structure.
- You want to add new operations to existing object structures without modifying those structures.

Example scenario:
You're building a document object model where you need to perform various operations on different types of elements.

```java
interface DocumentPart {
    void accept(Visitor visitor);
}

interface Visitor {
    void visit(Paragraph paragraph);
    void visit(Hyperlink hyperlink);
}

class Paragraph implements DocumentPart {
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

class Hyperlink implements DocumentPart {
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
```

9. Iterator Pattern

Use when:
- You want to access elements of a collection without exposing its underlying representation.
- You need to support multiple traversal methods for a collection.

Example scenario:
You're implementing a custom collection class and want to provide a way to iterate over its elements.

```java
interface Iterator<T> {
    boolean hasNext();
    T next();
}

class MyCollection<T> {
    private T[] elements;

    public Iterator<T> iterator() {
        return new MyIterator();
    }

    private class MyIterator implements Iterator<T> {
        private int index = 0;

        public boolean hasNext() {
            return index < elements.length;
        }

        public T next() {
            return elements[index++];
        }
    }
}
```

10. Command Pattern

Use when:
- You want to parameterize objects with different requests or operations.
- You need to queue, log, or support undoable operations.

Example scenario:
You're building a remote control system where each button can be programmed to perform different operations.

```java
interface Command {
    void execute();
}

class Light {
    public void turnOn() {
        System.out.println("Light is on");
    }
}

class LightOnCommand implements Command {
    private Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    public void execute() {
        light.turnOn();
    }
}

class RemoteControl {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void pressButton() {
        command.execute();
    }
}
```

Building Intuition:
- Use State for objects with state-dependent behavior.
- Use Template Method for algorithms with a fixed structure but varying steps.
- Use Observer for implementing distributed event handling systems.
- Use Mediator to reduce dependencies between tightly-coupled objects.
- Use Memento for implementing undo mechanisms.
- Use Interpreter for simple language processing.
- Use Chain of Responsibility for processing varied requests sequentially.
- Use Visitor to define new operations on an object structure without changing the objects.
- Use Iterator for accessing elements of a collection without exposing its structure.
- Use Command to parameterize objects with operations or requests.