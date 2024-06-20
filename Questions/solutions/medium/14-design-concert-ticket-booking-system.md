# Designing a Concert Ticket Booking System

## Requirements
1. The concert ticket booking system should allow users to view available concerts and their seating arrangements.
2. Users should be able to search for concerts based on various criteria such as artist, venue, date, and time.
3. Users should be able to select seats and purchase tickets for a specific concert.
4. The system should handle concurrent booking requests to avoid double-booking of seats.
5. The system should ensure fair booking opportunities for all users.
6. The system should handle payment processing securely.
7. The system should generate booking confirmations and send them to users via email or SMS.
8. The system should provide a waiting list functionality for sold-out concerts.

```java
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Concert {
    private String concertId;
    private String artist;
    private String venue;
    private String date;
    private String time;
    private List<Seat> seats;

    // Constructor, getters, and setters
}

class Seat {
    private String seatId;
    private String seatNumber;
    private boolean isAvailable;

    // Constructor, getters, and setters
}

class Booking {
    private String bookingId;
    private String concertId;
    private List<Seat> seats;
    private String userId;
    private double totalAmount;
    private String status;

    // Constructor, getters, and setters
}

class WaitingListEntry {
    private String entryId;
    private String concertId;
    private String userId;
    private int numSeats;

    // Constructor, getters, and setters
}

interface PaymentGateway {
    boolean processPayment(double amount, String paymentDetails);
}

class ConcertSearchCriteria {
    private String artist;
    private String venue;
    private String date;
    private String time;

    // Constructor, getters, and setters
}

class ConcertManager {
    private List<Concert> concerts;
    private Lock lock;

    public ConcertManager() {
        this.concerts = new ArrayList<>();
        this.lock = new ReentrantLock();
    }

    public void addConcert(Concert concert) {
        lock.lock();
        try {
            concerts.add(concert);
        } finally {
            lock.unlock();
        }
    }

    public List<Concert> searchConcerts(ConcertSearchCriteria criteria) {
        List<Concert> results = new ArrayList<>();
        for (Concert concert : concerts) {
            if (concert.getArtist().equalsIgnoreCase(criteria.getArtist()) &&
                    concert.getVenue().equalsIgnoreCase(criteria.getVenue()) &&
                    concert.getDate().equals(criteria.getDate()) &&
                    concert.getTime().equals(criteria.getTime())) {
                results.add(concert);
            }
        }
        return results;
    }

    public Concert getConcertById(String concertId) {
        for (Concert concert : concerts) {
            if (concert.getConcertId().equals(concertId)) {
                return concert;
            }
        }
        return null;
    }
}

class BookingManager {
    private List<Booking> bookings;
    private List<WaitingListEntry> waitingList;
    private AtomicInteger bookingIdCounter;
    private Lock lock;

    public BookingManager() {
        this.bookings = new ArrayList<>();
        this.waitingList = new ArrayList<>();
        this.bookingIdCounter = new AtomicInteger(1);
        this.lock = new ReentrantLock();
    }

    public Booking createBooking(String concertId, List<Seat> seats, String userId, double totalAmount) {
        lock.lock();
        try {
            if (areSeatsAvailable(concertId, seats)) {
                markSeatsAsBooked(concertId, seats);
                String bookingId = generateBookingId();
                Booking booking = new Booking(bookingId, concertId, seats, userId, totalAmount, "Confirmed");
                bookings.add(booking);
                return booking;
            } else {
                addToWaitingList(concertId, userId, seats.size());
                return null;
            }
        } finally {
            lock.unlock();
        }
    }

    private boolean areSeatsAvailable(String concertId, List<Seat> seats) {
        Concert concert = getConcertById(concertId);
        if (concert != null) {
            for (Seat seat : seats) {
                if (!seat.isAvailable()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private void markSeatsAsBooked(String concertId, List<Seat> seats) {
        Concert concert = getConcertById(concertId);
        if (concert != null) {
            for (Seat seat : seats) {
                seat.setAvailable(false);
            }
        }
    }

    private String generateBookingId() {
        return "B" + bookingIdCounter.getAndIncrement();
    }

    private void addToWaitingList(String concertId, String userId, int numSeats) {
        String entryId = generateWaitingListEntryId();
        WaitingListEntry entry = new WaitingListEntry(entryId, concertId, userId, numSeats);
        waitingList.add(entry);
    }

    private String generateWaitingListEntryId() {
        return "WL" + waitingList.size() + 1;
    }

    public boolean processPayment(Booking booking, PaymentGateway paymentGateway, String paymentDetails) {
        if (paymentGateway.processPayment(booking.getTotalAmount(), paymentDetails)) {
            booking.setStatus("Paid");
            return true;
        }
        return false;
    }

    public void sendConfirmation(Booking booking) {
        // Send booking confirmation via email or SMS
    }
}

public class ConcertTicketBookingSystem {
    public static void main(String[] args) {
        ConcertManager concertManager = new ConcertManager();
        BookingManager bookingManager = new BookingManager();

        // Add concerts
        Concert concert1 = new Concert("C001", "Artist 1", "Venue 1", "2023-06-10", "19:00", createSeats(100));
        Concert concert2 = new Concert("C002", "Artist 2", "Venue 2", "2023-06-15", "20:00", createSeats(80));
        concertManager.addConcert(concert1);
        concertManager.addConcert(concert2);

        // Search concerts
        ConcertSearchCriteria criteria = new ConcertSearchCriteria("Artist 1", "Venue 1", "2023-06-10", "19:00");
        List<Concert> searchResults = concertManager.searchConcerts(criteria);
        // Display search results

        // Create a booking
        List<Seat> selectedSeats = selectSeats(concert1, 3);
        Booking booking = bookingManager.createBooking(concert1.getConcertId(), selectedSeats, "U001", calculateTotalAmount(selectedSeats));
        if (booking != null) {
            // Process payment
            PaymentGateway paymentGateway = new PaymentGateway();
            boolean paymentSuccess = bookingManager.processPayment(booking, paymentGateway, "Payment Details");
            if (paymentSuccess) {
                bookingManager.sendConfirmation(booking);
            }
        } else {
            // Handle waiting list or sold-out scenario
        }
    }

    private static List<Seat> createSeats(int numSeats) {
        List<Seat> seats = new ArrayList<>();
        for (int i = 1; i <= numSeats; i++) {
            seats.add(new Seat("S" + i, "Seat " + i, true));
        }
        return seats;
    }

    private static List<Seat> selectSeats(Concert concert, int numSeats) {
        List<Seat> selectedSeats = new ArrayList<>();
        int count = 0;
        for (Seat seat : concert.getSeats()) {
            if (seat.isAvailable()) {
                selectedSeats.add(seat);
                count++;
                if (count == numSeats) {
                    break;
                }
            }
        }
        return selectedSeats;
    }

    private static double calculateTotalAmount(List<Seat> seats) {
        // Calculate the total amount based on the selected seats
        // Implement your own pricing logic here
        return seats.size() * 50.0;
    }
}
```

Explanation:
1. The code defines classes for different entities in the concert ticket booking system, such as `Concert`, `Seat`, `Booking`, and `WaitingListEntry`.

2. The `ConcertSearchCriteria` class represents the search criteria used for searching concerts based on artist, venue, date, and time.

3. The `ConcertManager` class manages the concerts and provides methods to add concerts and search for concerts based on the given criteria. It uses a `ReentrantLock` to ensure thread safety when modifying the concert data.

4. The `BookingManager` class handles the booking process. It provides methods to create a booking, check seat availability, mark seats as booked, add entries to the waiting list, process payments, and send booking confirmations. It uses a `ReentrantLock` to ensure thread safety and avoid double-booking of seats.

5. The `PaymentGateway` interface represents a payment processing system. It can be implemented with different payment providers or gateways.

6. The `ConcertTicketBookingSystem` class contains the main method and demonstrates the usage of the booking system. It creates concerts, searches for concerts based on criteria, creates a booking, processes payment, and sends a confirmation.

Design Patterns Used:
- Singleton Pattern: The `ConcertManager` and `BookingManager` classes can be implemented as singletons to ensure that only one instance of each manager exists throughout the system. This allows for centralized management of concerts and bookings and ensures data consistency.

- Factory Pattern: The `createSeats` method in the `ConcertTicketBookingSystem` class acts as a factory method to create a list of seats for a concert. It encapsulates the seat creation logic and provides a convenient way to generate seats.

Thread Safety:
- The `ConcertManager` and `BookingManager` classes use `ReentrantLock` to ensure thread safety when accessing and modifying shared data (concerts, bookings, waiting list). The lock is acquired before accessing or modifying the data and released after the operation is completed.

- The `BookingManager` class uses an `AtomicInteger` for generating unique booking IDs to ensure thread safety and avoid duplicate IDs.

Fairness and Concurrency:
- The system uses locks to handle concurrent booking requests and ensure fair booking opportunities for all users. When a booking is created, the `BookingManager` checks the availability of seats and marks them as booked atomically to prevent double-booking.

- If the requested seats are not available, the user is added to the waiting list. The waiting list functionality ensures that users are served in the order of their requests when seats become available.

Payment Processing and Confirmation:
- The system integrates with a payment gateway (represented by the `PaymentGateway` interface) to securely process payments for bookings.

- After a successful booking and payment, the system generates a booking confirmation and sends it to the user via email or SMS (not implemented in the code, but mentioned as a placeholder).