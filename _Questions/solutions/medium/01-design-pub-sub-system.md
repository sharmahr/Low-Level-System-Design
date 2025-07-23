# Designing a Pub-Sub System in Java

## Requirements
1. The Pub-Sub system should allow publishers to publish messages to specific topics.
2. Subscribers should be able to subscribe to topics of interest and receive messages published to those topics.
3. The system should support multiple publishers and subscribers.
4. Messages should be delivered to all subscribers of a topic in real-time.
5. The system should handle concurrent access and ensure thread safety.
6. The Pub-Sub system should be scalable and efficient in terms of message delivery.

```java
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

interface Publisher {
    void publish(String topic, String message);
}

interface Subscriber {
    void subscribe(String topic);
    void unsubscribe(String topic);
    void receiveMessage(String topic, String message);
}

class PubSubSystem {
    private ConcurrentHashMap<String, CopyOnWriteArrayList<Subscriber>> subscribers;

    public PubSubSystem() {
        subscribers = new ConcurrentHashMap<>();
    }

    public void subscribe(String topic, Subscriber subscriber) {
        subscribers.putIfAbsent(topic, new CopyOnWriteArrayList<>());
        subscribers.get(topic).add(subscriber);
    }

    public void unsubscribe(String topic, Subscriber subscriber) {
        if (subscribers.containsKey(topic)) {
            subscribers.get(topic).remove(subscriber);
        }
    }

    public void publish(String topic, String message) {
        if (subscribers.containsKey(topic)) {
            for (Subscriber subscriber : subscribers.get(topic)) {
                subscriber.receiveMessage(topic, message);
            }
        }
    }
}

class ConcretePublisher implements Publisher {
    private PubSubSystem pubSubSystem;

    public ConcretePublisher(PubSubSystem pubSubSystem) {
        this.pubSubSystem = pubSubSystem;
    }

    @Override
    public void publish(String topic, String message) {
        pubSubSystem.publish(topic, message);
    }
}

class ConcreteSubscriber implements Subscriber {
    private String name;
    private PubSubSystem pubSubSystem;

    public ConcreteSubscriber(String name, PubSubSystem pubSubSystem) {
        this.name = name;
        this.pubSubSystem = pubSubSystem;
    }

    @Override
    public void subscribe(String topic) {
        pubSubSystem.subscribe(topic, this);
    }

    @Override
    public void unsubscribe(String topic) {
        pubSubSystem.unsubscribe(topic, this);
    }

    @Override
    public void receiveMessage(String topic, String message) {
        System.out.println(name + " received message: " + message + " from topic: " + topic);
    }
}
```

Explanation:
1. The code defines two interfaces: `Publisher` and `Subscriber`. The `Publisher` interface declares a `publish` method to publish messages to specific topics. The `Subscriber` interface declares methods to subscribe, unsubscribe, and receive messages from topics.

2. The `PubSubSystem` class acts as the central hub for managing subscriptions and message distribution. It uses a `ConcurrentHashMap` to store the mapping between topics and their corresponding subscribers. The `ConcurrentHashMap` ensures thread safety for concurrent access.

3. The `subscribe` method of `PubSubSystem` allows subscribers to subscribe to a specific topic. If the topic doesn't exist in the `subscribers` map, a new `CopyOnWriteArrayList` is created to store the subscribers for that topic. The `CopyOnWriteArrayList` is used to handle concurrent modifications and ensure thread safety.

4. The `unsubscribe` method allows subscribers to unsubscribe from a topic. It removes the subscriber from the corresponding `CopyOnWriteArrayList` of the topic.

5. The `publish` method is used by publishers to publish messages to a specific topic. It iterates over all the subscribers of the topic and invokes their `receiveMessage` method to deliver the message in real-time.

6. The `ConcretePublisher` class implements the `Publisher` interface and acts as a publisher in the system. It holds a reference to the `PubSubSystem` and delegates the publishing of messages to it.

7. The `ConcreteSubscriber` class implements the `Subscriber` interface and represents a subscriber in the system. It has a name and a reference to the `PubSubSystem`. It implements the `subscribe`, `unsubscribe`, and `receiveMessage` methods to interact with the `PubSubSystem` and handle received messages.

Design Patterns Used:
- Observer Pattern: The Pub-Sub system follows the Observer pattern, where subscribers (observers) register their interest in topics and receive notifications when publishers (subjects) publish messages to those topics. The `PubSubSystem` acts as the subject, and the `Subscriber` acts as the observer.

- Singleton Pattern: The `PubSubSystem` can be implemented as a singleton to ensure a single instance of the system exists throughout the application. This allows centralized management of subscriptions and message distribution.

The Observer pattern provides a loosely coupled communication mechanism between publishers and subscribers, allowing for scalability and flexibility. The use of `ConcurrentHashMap` and `CopyOnWriteArrayList` ensures thread safety and efficient concurrent access to the data structures.