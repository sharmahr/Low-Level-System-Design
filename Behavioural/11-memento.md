Design Pattern: Memento Design Pattern

Diagram:
```
    +---------------------------------+
    |    Originator                   |
    +---------------------------------+
    | - state                         |
    +---------------------------------+
    | + setState()                    |
    | + saveStateToMemento(): Memento |
    | + getStateFromMemento(Memento)  |
    +---------------------------------+
                     |
                     | creates
                     |
                     v
    +---------------------------------+
    |     Memento                     |
    +---------------------------------+
    | - state                         |
    +---------------------------------+
    | + getState()                    |
    +---------------------------------+
                     ^
                     |
                     | manages
                     |
    +---------------------------------+
    |    Caretaker                    |
    +---------------------------------+
    | - mementoList                   |
    +---------------------------------+
    | + addMemento(Memento)           |
    | + getMemento(int): Memento      |
    +---------------------------------+
```

Explanation:
The Memento Design Pattern is a behavioral pattern that allows capturing and externalizing an object's internal state without violating encapsulation, so that the object can be restored to this state later. It provides a way to save and restore the state of an object without exposing its internal structure. The pattern involves three main components: Originator, Memento, and Caretaker. The Originator is the object whose state needs to be saved and restored, the Memento is an object that stores the state of the Originator, and the Caretaker is responsible for storing and managing the mementos.

Key Points:
- Allows capturing and externalizing an object's internal state
- Provides a way to save and restore the state of an object
- Maintains encapsulation by not exposing the internal structure of the object
- Involves three main components: Originator, Memento, and Caretaker
- Useful for implementing undo/redo functionality or checkpoints

Real-Life Applications:
- Implementing undo/redo functionality in text editors or graphics applications
- Saving and restoring game progress or checkpoints in video games
- Managing transaction rollbacks in database systems
- Capturing and restoring system snapshots or backups

Remember:
"The Memento Pattern is like a time machine that allows an object to travel back to its previous state without revealing the details of its journey."

Sample Code:
```java
// Memento class
class Memento {
    private String state;

    public Memento(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}

// Originator class
class Originator {
    private String state;

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public Memento saveStateToMemento() {
        return new Memento(state);
    }

    public void getStateFromMemento(Memento memento) {
        state = memento.getState();
    }
}

// Caretaker class
class Caretaker {
    private List<Memento> mementoList = new ArrayList<>();

    public void addMemento(Memento memento) {
        mementoList.add(memento);
    }

    public Memento getMemento(int index) {
        return mementoList.get(index);
    }
}

// Client code
public class MementoDemo {
    public static void main(String[] args) {
        Originator originator = new Originator();
        Caretaker caretaker = new Caretaker();

        originator.setState("State 1");
        caretaker.addMemento(originator.saveStateToMemento());

        originator.setState("State 2");
        caretaker.addMemento(originator.saveStateToMemento());

        originator.setState("State 3");
        System.out.println("Current State: " + originator.getState());

        originator.getStateFromMemento(caretaker.getMemento(0));
        System.out.println("First Saved State: " + originator.getState());

        originator.getStateFromMemento(caretaker.getMemento(1));
        System.out.println("Second Saved State: " + originator.getState());
    }
}
```

In this example, the Memento Pattern is used to save and restore the state of an `Originator` object. The `Memento` class represents the saved state, the `Originator` class is the object whose state needs to be saved and restored, and the `Caretaker` class manages the mementos. The client code demonstrates how the state of the `Originator` is saved at different points and later restored using the mementos stored by the `Caretaker`.