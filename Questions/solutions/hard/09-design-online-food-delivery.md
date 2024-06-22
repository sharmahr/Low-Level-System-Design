# Designing an Online Food Delivery Service Like Swiggy

## Requirements
1. The food delivery service should allow customers to browse restaurants, view menus, and place orders.
2. Restaurants should be able to manage their menus, prices, and availability.
3. Delivery agents should be able to accept and fulfill orders.
4. The system should handle order tracking and status updates.
5. The system should support multiple payment methods.
6. The system should handle concurrent orders and ensure data consistency.
7. The system should be scalable and handle a high volume of orders.
8. The system should provide real-time notifications to customers, restaurants, and delivery agents.


```java
public class Customer {
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    // Other customer properties and methods
}

public class Restaurant {
    private String id;
    private String name;
    private String address;
    private List<MenuItem> menu;
    // Other restaurant properties and methods
}

public class MenuItem {
    private String id;
    private String name;
    private double price;
    private boolean available;
    // Other menu item properties and methods
}

public class DeliveryAgent {
    private String id;
    private String name;
    private String phoneNumber;
    private boolean available;
    // Other delivery agent properties and methods
}

public class Order {
    private String id;
    private Customer customer;
    private Restaurant restaurant;
    private List<MenuItem> items;
    private double totalAmount;
    private OrderStatus status;
    private DeliveryAgent deliveryAgent;
    // Other order properties and methods
}

public enum OrderStatus {
    PENDING, ACCEPTED, PREPARING, OUT_FOR_DELIVERY, DELIVERED, CANCELLED
}

public interface PaymentProcessor {
    void processPayment(Order order);
}

public class CreditCardPaymentProcessor implements PaymentProcessor {
    public void processPayment(Order order) {
        // Process payment using credit card
    }
}

public class PayPalPaymentProcessor implements PaymentProcessor {
    public void processPayment(Order order) {
        // Process payment using PayPal
    }
}

public class OrderTracker {
    private Map<String, Order> activeOrders;

    public void trackOrder(Order order) {
        activeOrders.put(order.getId(), order);
        // Update order status and notify relevant parties
    }

    public void updateOrderStatus(String orderId, OrderStatus status) {
        Order order = activeOrders.get(orderId);
        if (order != null) {
            order.setStatus(status);
            // Notify customer, restaurant, and delivery agent about the status update
        }
    }
}

public class FoodDeliveryService {
    private List<Restaurant> restaurants;
    private List<DeliveryAgent> deliveryAgents;
    private OrderTracker orderTracker;
    private PaymentProcessor paymentProcessor;

    public FoodDeliveryService(OrderTracker orderTracker, PaymentProcessor paymentProcessor) {
        this.orderTracker = orderTracker;
        this.paymentProcessor = paymentProcessor;
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurants;
    }

    public List<MenuItem> getMenuForRestaurant(String restaurantId) {
        // Retrieve the menu for a specific restaurant
    }

    public Order placeOrder(Customer customer, Restaurant restaurant, List<MenuItem> items) {
        Order order = new Order(customer, restaurant, items);
        orderTracker.trackOrder(order);
        paymentProcessor.processPayment(order);
        // Notify restaurant and assign a delivery agent
        return order;
    }

    public void updateOrderStatus(String orderId, OrderStatus status) {
        orderTracker.updateOrderStatus(orderId, status);
    }

    public void addRestaurant(Restaurant restaurant) {
        restaurants.add(restaurant);
    }

    public void addDeliveryAgent(DeliveryAgent deliveryAgent) {
        deliveryAgents.add(deliveryAgent);
    }
}

public class FoodDeliveryApp {
    public static void main(String[] args) {
        OrderTracker orderTracker = new OrderTracker();
        PaymentProcessor paymentProcessor = new CreditCardPaymentProcessor();
        FoodDeliveryService deliveryService = new FoodDeliveryService(orderTracker, paymentProcessor);

        // Add sample restaurants and delivery agents
        Restaurant restaurant1 = new Restaurant("R1", "Restaurant 1", "Address 1");
        Restaurant restaurant2 = new Restaurant("R2", "Restaurant 2", "Address 2");
        deliveryService.addRestaurant(restaurant1);
        deliveryService.addRestaurant(restaurant2);

        DeliveryAgent agent1 = new DeliveryAgent("A1", "Agent 1", "1234567890");
        DeliveryAgent agent2 = new DeliveryAgent("A2", "Agent 2", "9876543210");
        deliveryService.addDeliveryAgent(agent1);
        deliveryService.addDeliveryAgent(agent2);

        // Create a sample customer and place an order
        Customer customer = new Customer("C1", "John Doe", "john@example.com", "1234567890");
        List<MenuItem> items = new ArrayList<>();
        items.add(new MenuItem("I1", "Item 1", 10.0, true));
        items.add(new MenuItem("I2", "Item 2", 15.0, true));
        Order order = deliveryService.placeOrder(customer, restaurant1, items);

        // Update order status
        deliveryService.updateOrderStatus(order.getId(), OrderStatus.PREPARING);
        deliveryService.updateOrderStatus(order.getId(), OrderStatus.OUT_FOR_DELIVERY);
        deliveryService.updateOrderStatus(order.getId(), OrderStatus.DELIVERED);
    }
}
```

Explanation:
1. The code defines several classes to represent the different entities in the food delivery service, such as `Customer`, `Restaurant`, `MenuItem`, `DeliveryAgent`, and `Order`.

2. The `OrderStatus` enum defines the possible statuses of an order.

3. The `PaymentProcessor` interface and its implementations `CreditCardPaymentProcessor` and `PayPalPaymentProcessor` handle the payment processing for orders using different payment methods.

4. The `OrderTracker` class is responsible for tracking active orders and updating their statuses. It maintains a map of active orders and provides methods to track an order and update its status.

5. The `FoodDeliveryService` class is the main orchestrator of the food delivery service. It manages the list of restaurants and delivery agents, and provides methods for retrieving restaurants, menus, placing orders, and updating order statuses. It collaborates with the `OrderTracker` and `PaymentProcessor` to fulfill these responsibilities.

6. The `FoodDeliveryApp` class contains the main method and demonstrates the usage of the food delivery service by adding sample restaurants and delivery agents, creating a sample customer, placing an order, and updating the order status.

Design Patterns Used:
- Strategy Pattern: The `PaymentProcessor` interface and its implementations `CreditCardPaymentProcessor` and `PayPalPaymentProcessor` follow the Strategy pattern. This allows for different payment processing strategies to be used interchangeably, making the system more flexible and extensible.

- Dependency Injection: The `FoodDeliveryService` class receives its dependencies (`OrderTracker` and `PaymentProcessor`) through constructor injection. This promotes loose coupling and makes the code more testable and maintainable.

- Observer Pattern: Although not explicitly implemented in the provided code, the observer pattern can be used to handle real-time notifications to customers, restaurants, and delivery agents. The `OrderTracker` class can act as the subject, and the relevant parties can be observers that receive notifications about order status updates.

The use of design patterns in this food delivery service implementation provides several benefits:
- The Strategy pattern allows for easy addition and switching of payment processors, enabling the system to support multiple payment methods and adapt to new payment options in the future.
- Dependency Injection promotes loose coupling and makes the code more modular and testable by allowing the dependencies to be easily mocked or replaced during testing.
- The Observer pattern, when implemented, can facilitate real-time communication and updates between the system, customers, restaurants, and delivery agents, ensuring that all parties are kept informed about the order status.

To handle concurrent orders and ensure data consistency, proper synchronization mechanisms and database transactions should be implemented in the relevant classes and methods.

Scalability can be achieved by employing techniques such as load balancing, caching, and horizontal scaling of the system components.

This low-level design provides a basic structure for the food delivery service that meets the given requirements. It can be further enhanced with additional features such as user authentication, order history, delivery tracking, and more advanced order management and assignment algorithms.