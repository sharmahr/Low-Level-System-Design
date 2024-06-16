Design Pattern: Command Design Pattern

Diagram:
```
+--------+         +--------+         +---------+
| Client |-------->| Invoker|-------->| Command |
+--------+         +--------+         +---------+
                                           |
                                           |
                                      +----------+
                                      | execute()|
                                      +----------+
                                           ^
                                           |
                                      +----------+
                                      | Receiver |
                                      +----------+
```

Explanation:
The Command Design Pattern is a behavioral pattern that encapsulates a request as an object, thereby allowing you to parameterize clients with different requests, queue or log requests, and support undoable operations. It decouples the object that invokes the operation from the one that knows how to perform it. The pattern involves four main components: Command, Receiver, Invoker, and Client. The Command object encapsulates the action and its parameters, the Receiver performs the actual work, the Invoker triggers the command's execution, and the Client creates and configures the Command object.

Key Points:
- Encapsulates a request as an object
- Allows parameterizing clients with different requests
- Supports queuing, logging, and undoable operations
- Decouples the invoker from the object that performs the action
- Promotes loose coupling and flexibility in the system

Real-Life Applications:
- Implementing undo/redo functionality in text editors or graphics applications
- Encapsulating user actions or events in a graphical user interface (GUI) system
- Creating a job queue or task scheduler in a distributed system
- Implementing remote procedure calls (RPC) or remote method invocation (RMI)

Remember:
"The Command Pattern is like a remote control, where each button represents a command that can be executed independently of the device it controls."

Sample Code:
```java
// Command interface
interface Command {
    void execute();
}

// Concrete Command
class LightOnCommand implements Command {
    private Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    public void execute() {
        light.turnOn();
    }
}

// Receiver
class Light {
    public void turnOn() {
        System.out.println("Light is on");
    }

    public void turnOff() {
        System.out.println("Light is off");
    }
}

// Invoker
class RemoteControl {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void pressButton() {
        command.execute();
    }
}

// Client
public class CommandDemo {
    public static void main(String[] args) {
        Light light = new Light();
        Command lightOnCommand = new LightOnCommand(light);

        RemoteControl remoteControl = new RemoteControl();
        remoteControl.setCommand(lightOnCommand);
        remoteControl.pressButton();
    }
}
```

In this example, the Command Pattern is used to control a light using a remote control. The `Command` interface defines the `execute()` method, which is implemented by the concrete command `LightOnCommand`. The `Light` class acts as the receiver and contains the actual logic for turning the light on or off. The `RemoteControl` class acts as the invoker and holds a reference to the command. The client code demonstrates how the command is set on the remote control and executed by pressing a button.