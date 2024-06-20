# Designing a Car Rental System

## Requirements
1. The car rental system should allow customers to browse and reserve available cars for specific dates.
2. Each car should have details such as make, model, year, license plate number, and rental price per day.
3. Customers should be able to search for cars based on various criteria, such as car type, price range, and availability.
4. The system should handle reservations, including creating, modifying, and canceling reservations.
5. The system should keep track of the availability of cars and update their status accordingly.
6. The system should handle customer information, including name, contact details, and driver's license information.
7. The system should handle payment processing for reservations.
8. The system should be able to handle concurrent reservations and ensure data consistency.

Certainly! Here's an example design and implementation of a car rental system in Java based on the given requirements, along with explanations of the code and design patterns used:

```java
import java.util.*;

class Car {
    private String make;
    private String model;
    private int year;
    private String licensePlate;
    private double rentalPricePerDay;
    private boolean isAvailable;

    // Constructor, getters, and setters
}

class Customer {
    private String name;
    private String contactDetails;
    private String driversLicense;

    // Constructor, getters, and setters
}

class Reservation {
    private Customer customer;
    private Car car;
    private Date startDate;
    private Date endDate;
    private double totalPrice;

    // Constructor, getters, and setters
}

interface CarRentalSystem {
    List<Car> searchCars(String carType, double minPrice, double maxPrice);
    void createReservation(Customer customer, Car car, Date startDate, Date endDate);
    void modifyReservation(Reservation reservation, Date newStartDate, Date newEndDate);
    void cancelReservation(Reservation reservation);
    boolean isCarAvailable(Car car, Date startDate, Date endDate);
    void processPayment(Reservation reservation, String paymentDetails);
}

class CarRentalSystemImpl implements CarRentalSystem {
    private List<Car> cars;
    private List<Reservation> reservations;

    public CarRentalSystemImpl() {
        cars = new ArrayList<>();
        reservations = new ArrayList<>();
    }

    @Override
    public synchronized List<Car> searchCars(String carType, double minPrice, double maxPrice) {
        List<Car> availableCars = new ArrayList<>();
        for (Car car : cars) {
            if (car.getType().equals(carType) && car.getRentalPricePerDay() >= minPrice &&
                    car.getRentalPricePerDay() <= maxPrice && car.isAvailable()) {
                availableCars.add(car);
            }
        }
        return availableCars;
    }

    @Override
    public synchronized void createReservation(Customer customer, Car car, Date startDate, Date endDate) {
        if (isCarAvailable(car, startDate, endDate)) {
            Reservation reservation = new Reservation(customer, car, startDate, endDate);
            reservations.add(reservation);
            car.setAvailable(false);
        } else {
            throw new IllegalStateException("Car is not available for the specified dates.");
        }
    }

    @Override
    public synchronized void modifyReservation(Reservation reservation, Date newStartDate, Date newEndDate) {
        if (isCarAvailable(reservation.getCar(), newStartDate, newEndDate)) {
            reservation.setStartDate(newStartDate);
            reservation.setEndDate(newEndDate);
        } else {
            throw new IllegalStateException("Car is not available for the specified dates.");
        }
    }

    @Override
    public synchronized void cancelReservation(Reservation reservation) {
        reservations.remove(reservation);
        reservation.getCar().setAvailable(true);
    }

    @Override
    public synchronized boolean isCarAvailable(Car car, Date startDate, Date endDate) {
        for (Reservation reservation : reservations) {
            if (reservation.getCar().equals(car) &&
                    (startDate.before(reservation.getEndDate()) && endDate.after(reservation.getStartDate()))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void processPayment(Reservation reservation, String paymentDetails) {
        // Process the payment using the provided payment details
        // Update the reservation status or perform any necessary actions
    }
}
```

Explanation:
1. The `Car` class represents a car in the rental system, with properties such as make, model, year, license plate, rental price per day, and availability status.

2. The `Customer` class represents a customer in the system, with properties like name, contact details, and driver's license information.

3. The `Reservation` class represents a reservation made by a customer for a specific car and date range. It holds references to the customer, car, start date, end date, and total price.

4. The `CarRentalSystem` interface defines the contract for the car rental system, specifying methods for searching cars, creating reservations, modifying reservations, canceling reservations, checking car availability, and processing payments.

5. The `CarRentalSystemImpl` class implements the `CarRentalSystem` interface and provides the actual implementation of the car rental system.

6. The `searchCars` method allows customers to search for available cars based on criteria like car type, price range, and availability. It iterates through the list of cars and returns a list of matching cars.

7. The `createReservation` method handles the creation of a new reservation. It checks if the car is available for the specified dates and creates a reservation object if it is. It also marks the car as unavailable.

8. The `modifyReservation` method allows customers to modify an existing reservation by updating the start and end dates. It checks if the car is available for the new dates before modifying the reservation.

9. The `cancelReservation` method handles the cancellation of a reservation. It removes the reservation from the list and marks the car as available again.

10. The `isCarAvailable` method checks if a car is available for a specific date range by iterating through the list of reservations and checking for any overlapping reservations.

11. The `processPayment` method handles the payment processing for a reservation. It can be implemented to integrate with a payment gateway or perform any necessary actions related to payment.

Design Patterns Used:
- Repository Pattern: The `CarRentalSystemImpl` class acts as a repository for managing the cars and reservations. It provides methods to perform CRUD (Create, Read, Update, Delete) operations on the data.

- Singleton Pattern: The `CarRentalSystemImpl` class can be implemented as a singleton to ensure that only one instance of the car rental system exists throughout the application. This can be achieved by making the constructor private and providing a static method to retrieve the instance.

- Facade Pattern: The `CarRentalSystem` interface acts as a facade, providing a simplified and unified interface for interacting with the car rental system. It hides the complexity of the underlying implementation and provides a clean API for clients to use.

The code ensures data consistency and handles concurrent reservations by using the `synchronized` keyword on the methods that modify the shared data (cars and reservations). This prevents multiple threads from accessing and modifying the data simultaneously, avoiding conflicts and maintaining data integrity.
