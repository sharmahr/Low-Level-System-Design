# Designing Restaurant Management System

## Requirements
1. The restaurant management system should allow customers to place orders, view the menu, and make reservations.
2. The system should manage the restaurant's inventory, including ingredients and menu items.
3. The system should handle order processing, including order preparation, billing, and payment.
4. The system should support multiple payment methods, such as cash, credit card, and mobile payments.
5. The system should manage staff information, including roles, schedules, and performance tracking.
6. The system should generate reports and analytics for management, such as sales reports and inventory analysis.
7. The system should handle concurrent access and ensure data consistency.


```java
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class MenuItem {
    private String name;
    private double price;
    private List<String> ingredients;

    // Constructor, getters, and setters
}

class Order {
    private int orderId;
    private List<MenuItem> items;
    private double totalAmount;
    private String paymentMethod;
    private String status;

    // Constructor, getters, and setters
}

class Reservation {
    private int reservationId;
    private String customerName;
    private String date;
    private String time;
    private int partySize;

    // Constructor, getters, and setters
}

class Staff {
    private int staffId;
    private String name;
    private String role;
    private String schedule;

    // Constructor, getters, and setters
}

interface PaymentProcessor {
    boolean processPayment(double amount, String paymentMethod);
}

class CashPaymentProcessor implements PaymentProcessor {
    @Override
    public boolean processPayment(double amount, String paymentMethod) {
        // Process cash payment
        return true;
    }
}

class CreditCardPaymentProcessor implements PaymentProcessor {
    @Override
    public boolean processPayment(double amount, String paymentMethod) {
        // Process credit card payment
        return true;
    }
}

class MobilePaymentProcessor implements PaymentProcessor {
    @Override
    public boolean processPayment(double amount, String paymentMethod) {
        // Process mobile payment
        return true;
    }
}

class InventoryManager {
    private List<String> ingredients;
    private List<MenuItem> menuItems;
    private Lock lock;

    public InventoryManager() {
        this.ingredients = new ArrayList<>();
        this.menuItems = new ArrayList<>();
        this.lock = new ReentrantLock();
    }

    public void addIngredient(String ingredient) {
        lock.lock();
        try {
            ingredients.add(ingredient);
        } finally {
            lock.unlock();
        }
    }

    public void removeIngredient(String ingredient) {
        lock.lock();
        try {
            ingredients.remove(ingredient);
        } finally {
            lock.unlock();
        }
    }

    public void addMenuItem(MenuItem menuItem) {
        lock.lock();
        try {
            menuItems.add(menuItem);
        } finally {
            lock.unlock();
        }
    }

    public void removeMenuItem(MenuItem menuItem) {
        lock.lock();
        try {
            menuItems.remove(menuItem);
        } finally {
            lock.unlock();
        }
    }

    // Other methods for managing inventory
}

class OrderManager {
    private List<Order> orders;
    private Lock lock;

    public OrderManager() {
        this.orders = new ArrayList<>();
        this.lock = new ReentrantLock();
    }

    public void placeOrder(Order order) {
        lock.lock();
        try {
            orders.add(order);
            // Process order
        } finally {
            lock.unlock();
        }
    }

    public void updateOrderStatus(int orderId, String status) {
        lock.lock();
        try {
            for (Order order : orders) {
                if (order.getOrderId() == orderId) {
                    order.setStatus(status);
                    break;
                }
            }
        } finally {
            lock.unlock();
        }
    }

    public boolean processPayment(int orderId, PaymentProcessor paymentProcessor) {
        lock.lock();
        try {
            for (Order order : orders) {
                if (order.getOrderId() == orderId) {
                    return paymentProcessor.processPayment(order.getTotalAmount(), order.getPaymentMethod());
                }
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    // Other methods for managing orders
}

class ReservationManager {
    private List<Reservation> reservations;
    private Lock lock;

    public ReservationManager() {
        this.reservations = new ArrayList<>();
        this.lock = new ReentrantLock();
    }

    public void makeReservation(Reservation reservation) {
        lock.lock();
        try {
            reservations.add(reservation);
        } finally {
            lock.unlock();
        }
    }

    public void cancelReservation(int reservationId) {
        lock.lock();
        try {
            reservations.removeIf(reservation -> reservation.getReservationId() == reservationId);
        } finally {
            lock.unlock();
        }
    }

    // Other methods for managing reservations
}

class StaffManager {
    private List<Staff> staff;
    private Lock lock;

    public StaffManager() {
        this.staff = new ArrayList<>();
        this.lock = new ReentrantLock();
    }

    public void addStaff(Staff staffMember) {
        lock.lock();
        try {
            staff.add(staffMember);
        } finally {
            lock.unlock();
        }
    }

    public void removeStaff(int staffId) {
        lock.lock();
        try {
            staff.removeIf(staffMember -> staffMember.getStaffId() == staffId);
        } finally {
            lock.unlock();
        }
    }

    // Other methods for managing staff
}

class ReportGenerator {
    private InventoryManager inventoryManager;
    private OrderManager orderManager;

    public ReportGenerator(InventoryManager inventoryManager, OrderManager orderManager) {
        this.inventoryManager = inventoryManager;
        this.orderManager = orderManager;
    }

    public void generateSalesReport() {
        // Generate sales report based on order data
    }

    public void generateInventoryReport() {
        // Generate inventory report based on inventory data
    }

    // Other methods for generating reports
}

public class RestaurantManagementSystem {
    public static void main(String[] args) {
        InventoryManager inventoryManager = new InventoryManager();
        OrderManager orderManager = new OrderManager();
        ReservationManager reservationManager = new ReservationManager();
        StaffManager staffManager = new StaffManager();
        ReportGenerator reportGenerator = new ReportGenerator(inventoryManager, orderManager);

        // Add menu items and ingredients
        MenuItem item1 = new MenuItem("Item 1", 10.99, List.of("Ingredient 1", "Ingredient 2"));
        MenuItem item2 = new MenuItem("Item 2", 12.99, List.of("Ingredient 2", "Ingredient 3"));
        inventoryManager.addMenuItem(item1);
        inventoryManager.addMenuItem(item2);
        inventoryManager.addIngredient("Ingredient 1");
        inventoryManager.addIngredient("Ingredient 2");
        inventoryManager.addIngredient("Ingredient 3");

        // Place an order
        Order order = new Order(1, List.of(item1, item2), 23.98, "Credit Card", "Pending");
        orderManager.placeOrder(order);

        // Process payment
        PaymentProcessor paymentProcessor = new CreditCardPaymentProcessor();
        boolean paymentResult = orderManager.processPayment(1, paymentProcessor);
        if (paymentResult) {
            orderManager.updateOrderStatus(1, "Paid");
        }

        // Make a reservation
        Reservation reservation = new Reservation(1, "John Doe", "2023-06-10", "19:00", 4);
        reservationManager.makeReservation(reservation);

        // Add staff member
        Staff staff = new Staff(1, "Staff 1", "Waiter", "Monday-Friday");
        staffManager.addStaff(staff);

        // Generate reports
        reportGenerator.generateSalesReport();
        reportGenerator.generateInventoryReport();
    }
}
```

Explanation:
1. The code defines classes for different entities in the restaurant management system, such as `MenuItem`, `Order`, `Reservation`, and `Staff`.

2. The `PaymentProcessor` interface and its implementations (`CashPaymentProcessor`, `CreditCardPaymentProcessor`, `MobilePaymentProcessor`) represent different payment methods and provide a way to process payments.

3. The `InventoryManager` class manages the restaurant's inventory, including ingredients and menu items. It provides methods to add and remove ingredients and menu items, ensuring thread safety using a `ReentrantLock`.

4. The `OrderManager` class manages customer orders. It provides methods to place orders, update order status, and process payments. It uses a `ReentrantLock` to ensure thread safety when accessing and modifying order data.

5. The `ReservationManager` class manages customer reservations. It provides methods to make and cancel reservations, using a `ReentrantLock` for thread safety.

6. The `StaffManager` class manages staff information, including adding and removing staff members. It also uses a `ReentrantLock` for thread safety.

7. The `ReportGenerator` class generates reports and analytics based on inventory and order data. It takes dependencies on `InventoryManager` and `OrderManager` to access the necessary data.

8. The `RestaurantManagementSystem` class contains the main method and demonstrates the usage of the restaurant management system by creating instances of the various managers, adding menu items and ingredients, placing an order, processing payment, making a reservation, adding a staff member, and generating reports.

Design Patterns Used:
- Strategy Pattern: The `PaymentProcessor` interface and its implementations follow the Strategy pattern. They encapsulate different payment strategies and allow for easy extensibility and interchangeability of payment methods.

- Singleton Pattern: The `InventoryManager`, `OrderManager`, `ReservationManager`, and `StaffManager` classes can be implemented as singletons to ensure that only one instance of each manager exists throughout the system. This allows for centralized management of data and ensures data consistency.

Thread Safety:
- The `InventoryManager`, `OrderManager`, `ReservationManager`, and `StaffManager` classes use `ReentrantLock` to ensure thread safety when accessing and modifying shared data. The lock is acquired before accessing or modifying the data and released after the operation is completed.

Extensibility:
- The use of interfaces and dependencies allows for easy extension of the system. For example, new payment methods can be added by creating new implementations of the `PaymentProcessor` interface without modifying the existing code.

- The `ReportGenerator` class can be extended to generate additional types of reports based on the specific requirements of the restaurant management system.