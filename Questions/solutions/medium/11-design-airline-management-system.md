# Designing an Airline Management System

## Requirements
1. The airline management system should allow users to search for flights based on source, destination, and date.
2. Users should be able to book flights, select seats, and make payments.
3. The system should manage flight schedules, aircraft assignments, and crew assignments.
4. The system should handle passenger information, including personal details and baggage information.
5. The system should support different types of users, such as passengers, airline staff, and administrators.
6. The system should be able to handle cancellations, refunds, and flight changes.
7. The system should ensure data consistency and handle concurrent access to shared resources.
8. The system should be scalable and extensible to accommodate future enhancements and new features.


```java
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Flight {
    private String flightNumber;
    private String source;
    private String destination;
    private String date;
    private List<Seat> seats;

    // Constructor, getters, and setters
}

class Seat {
    private String seatNumber;
    private boolean isAvailable;

    // Constructor, getters, and setters
}

class Booking {
    private String bookingId;
    private Flight flight;
    private Passenger passenger;
    private List<Seat> seats;
    private double totalAmount;
    private String status;

    // Constructor, getters, and setters
}

class Passenger {
    private String passengerId;
    private String name;
    private String contactInfo;
    private List<Booking> bookings;

    // Constructor, getters, and setters
}

class Aircraft {
    private String aircraftId;
    private String model;
    private int capacity;

    // Constructor, getters, and setters
}

class Crew {
    private String crewId;
    private String name;
    private String role;

    // Constructor, getters, and setters
}

interface SearchStrategy {
    List<Flight> searchFlights(String source, String destination, String date);
}

class SimpleSearchStrategy implements SearchStrategy {
    private List<Flight> flights;

    public SimpleSearchStrategy(List<Flight> flights) {
        this.flights = flights;
    }

    @Override
    public List<Flight> searchFlights(String source, String destination, String date) {
        List<Flight> result = new ArrayList<>();
        for (Flight flight : flights) {
            if (flight.getSource().equalsIgnoreCase(source) &&
                    flight.getDestination().equalsIgnoreCase(destination) &&
                    flight.getDate().equals(date)) {
                result.add(flight);
            }
        }
        return result;
    }
}

class FlightManager {
    private List<Flight> flights;
    private Lock lock;

    public FlightManager() {
        this.flights = new ArrayList<>();
        this.lock = new ReentrantLock();
    }

    public void addFlight(Flight flight) {
        lock.lock();
        try {
            flights.add(flight);
        } finally {
            lock.unlock();
        }
    }

    public void removeFlight(Flight flight) {
        lock.lock();
        try {
            flights.remove(flight);
        } finally {
            lock.unlock();
        }
    }

    public List<Flight> searchFlights(SearchStrategy searchStrategy, String source, String destination, String date) {
        return searchStrategy.searchFlights(source, destination, date);
    }

    // Other methods for managing flights, aircraft, and crew assignments
}

class BookingManager {
    private List<Booking> bookings;
    private Lock lock;

    public BookingManager() {
        this.bookings = new ArrayList<>();
        this.lock = new ReentrantLock();
    }

    public void createBooking(Booking booking) {
        lock.lock();
        try {
            bookings.add(booking);
            // Update flight and seat availability
        } finally {
            lock.unlock();
        }
    }

    public void cancelBooking(String bookingId) {
        lock.lock();
        try {
            for (Booking booking : bookings) {
                if (booking.getBookingId().equals(bookingId)) {
                    booking.setStatus("Cancelled");
                    // Update flight and seat availability
                    break;
                }
            }
        } finally {
            lock.unlock();
        }
    }

    // Other methods for managing bookings, refunds, and flight changes
}

class PassengerManager {
    private List<Passenger> passengers;
    private Lock lock;

    public PassengerManager() {
        this.passengers = new ArrayList<>();
        this.lock = new ReentrantLock();
    }

    public void addPassenger(Passenger passenger) {
        lock.lock();
        try {
            passengers.add(passenger);
        } finally {
            lock.unlock();
        }
    }

    public void removePassenger(String passengerId) {
        lock.lock();
        try {
            passengers.removeIf(passenger -> passenger.getPassengerId().equals(passengerId));
        } finally {
            lock.unlock();
        }
    }

    // Other methods for managing passenger information
}

public class AirlineManagementSystem {
    public static void main(String[] args) {
        FlightManager flightManager = new FlightManager();
        BookingManager bookingManager = new BookingManager();
        PassengerManager passengerManager = new PassengerManager();

        // Add flights
        Flight flight1 = new Flight("FL001", "New York", "London", "2023-06-10", new ArrayList<>());
        Flight flight2 = new Flight("FL002", "London", "New York", "2023-06-12", new ArrayList<>());
        flightManager.addFlight(flight1);
        flightManager.addFlight(flight2);

        // Search flights
        SearchStrategy searchStrategy = new SimpleSearchStrategy(flightManager.getFlights());
        List<Flight> searchResults = flightManager.searchFlights(searchStrategy, "New York", "London", "2023-06-10");
        // Display search results

        // Create a booking
        Passenger passenger = new Passenger("P001", "John Doe", "john@example.com", new ArrayList<>());
        passengerManager.addPassenger(passenger);
        List<Seat> selectedSeats = new ArrayList<>(); // Assume seats are selected by the user
        Booking booking = new Booking("B001", flight1, passenger, selectedSeats, 1000.0, "Confirmed");
        bookingManager.createBooking(booking);

        // Cancel a booking
        bookingManager.cancelBooking("B001");
    }
}
```

Explanation:
1. The code defines classes for different entities in the airline management system, such as `Flight`, `Seat`, `Booking`, `Passenger`, `Aircraft`, and `Crew`.

2. The `SearchStrategy` interface and its implementation `SimpleSearchStrategy` represent a strategy for searching flights based on source, destination, and date. This allows for flexibility in implementing different search algorithms.

3. The `FlightManager` class manages flight-related operations, including adding and removing flights, and searching for flights using a `SearchStrategy`. It uses a `ReentrantLock` to ensure thread safety when modifying the flight data.

4. The `BookingManager` class manages booking-related operations, including creating and canceling bookings. It also handles updating flight and seat availability when a booking is created or canceled. It uses a `ReentrantLock` for thread safety.

5. The `PassengerManager` class manages passenger-related operations, including adding and removing passengers. It uses a `ReentrantLock` for thread safety.

6. The `AirlineManagementSystem` class contains the main method and demonstrates the usage of the airline management system by creating instances of the managers, adding flights, searching for flights, creating a booking, and canceling a booking.

Design Patterns Used:
- Strategy Pattern: The `SearchStrategy` interface and its implementation `SimpleSearchStrategy` follow the Strategy pattern. They encapsulate different search algorithms and allow for easy extensibility and interchangeability of search strategies.

- Singleton Pattern: The `FlightManager`, `BookingManager`, and `PassengerManager` classes can be implemented as singletons to ensure that only one instance of each manager exists throughout the system. This allows for centralized management of data and ensures data consistency.

Thread Safety:
- The `FlightManager`, `BookingManager`, and `PassengerManager` classes use `ReentrantLock` to ensure thread safety when accessing and modifying shared data. The lock is acquired before accessing or modifying the data and released after the operation is completed.

Scalability and Extensibility:
- The use of interfaces and loose coupling between classes allows for easy extensibility of the system. For example, new search strategies can be added by implementing the `SearchStrategy` interface without modifying the existing code.

- The system can be further enhanced to handle more complex scenarios, such as seat selection, baggage management, and integration with external systems for payment processing and notification services.
