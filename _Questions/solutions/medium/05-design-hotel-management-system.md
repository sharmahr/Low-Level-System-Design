# Designing a Hotel Management System

## Requirements
1. The hotel management system should allow guests to book rooms, check-in, and check-out.
2. The system should manage different types of rooms, such as single, double, deluxe, and suite.
3. The system should handle room availability and reservation status.
4. The system should allow the hotel staff to manage guest information, room assignments, and billing.
5. The system should support multiple payment methods, such as cash, credit card, and online payment.
6. The system should handle concurrent bookings and ensure data consistency.
7. The system should provide reporting and analytics features for hotel management.
8. The system should be scalable and handle a large number of rooms and guests.


```java
enum RoomType {
    SINGLE, DOUBLE, DELUXE, SUITE
}

class Room {
    private String roomNumber;
    private RoomType type;
    private boolean isAvailable;

    // Constructor, getters, and setters
}

class Guest {
    private String name;
    private String contactInfo;

    // Constructor, getters, and setters
}

class Reservation {
    private String reservationNumber;
    private Guest guest;
    private Room room;
    private Date checkInDate;
    private Date checkOutDate;

    // Constructor, getters, and setters
}

class Bill {
    private String billNumber;
    private Reservation reservation;
    private double totalAmount;
    private String paymentMethod;

    // Constructor, getters, and setters
}

interface HotelManagementSystem {
    void bookRoom(Guest guest, RoomType roomType, Date checkInDate, Date checkOutDate);
    void checkIn(String reservationNumber);
    void checkOut(String reservationNumber);
    List<Room> getAvailableRooms(Date checkInDate, Date checkOutDate);
    List<Reservation> getReservations(Date date);
    Bill generateBill(String reservationNumber);
    void makePayment(Bill bill, String paymentMethod);
}

class HotelManagementSystemImpl implements HotelManagementSystem {
    private List<Room> rooms;
    private List<Reservation> reservations;
    private List<Bill> bills;

    public HotelManagementSystemImpl() {
        rooms = new ArrayList<>();
        reservations = new ArrayList<>();
        bills = new ArrayList<>();
        initializeRooms();
    }

    private void initializeRooms() {
        // Initialize rooms with different types and room numbers
    }

    @Override
    public synchronized void bookRoom(Guest guest, RoomType roomType, Date checkInDate, Date checkOutDate) {
        Room availableRoom = findAvailableRoom(roomType, checkInDate, checkOutDate);
        if (availableRoom != null) {
            Reservation reservation = new Reservation(generateReservationNumber(), guest, availableRoom, checkInDate, checkOutDate);
            reservations.add(reservation);
            availableRoom.setAvailable(false);
        } else {
            throw new IllegalStateException("No available room of the specified type for the given dates.");
        }
    }

    @Override
    public synchronized void checkIn(String reservationNumber) {
        Reservation reservation = findReservation(reservationNumber);
        if (reservation != null) {
            // Perform check-in operations
        } else {
            throw new IllegalArgumentException("Invalid reservation number.");
        }
    }

    @Override
    public synchronized void checkOut(String reservationNumber) {
        Reservation reservation = findReservation(reservationNumber);
        if (reservation != null) {
            // Perform check-out operations
            reservation.getRoom().setAvailable(true);
            reservations.remove(reservation);
        } else {
            throw new IllegalArgumentException("Invalid reservation number.");
        }
    }

    @Override
    public synchronized List<Room> getAvailableRooms(Date checkInDate, Date checkOutDate) {
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (isRoomAvailable(room, checkInDate, checkOutDate)) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    @Override
    public synchronized List<Reservation> getReservations(Date date) {
        List<Reservation> reservationsOnDate = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.getCheckInDate().equals(date) || reservation.getCheckOutDate().equals(date) ||
                    (reservation.getCheckInDate().before(date) && reservation.getCheckOutDate().after(date))) {
                reservationsOnDate.add(reservation);
            }
        }
        return reservationsOnDate;
    }

    @Override
    public synchronized Bill generateBill(String reservationNumber) {
        Reservation reservation = findReservation(reservationNumber);
        if (reservation != null) {
            double totalAmount = calculateTotalAmount(reservation);
            Bill bill = new Bill(generateBillNumber(), reservation, totalAmount, "");
            bills.add(bill);
            return bill;
        } else {
            throw new IllegalArgumentException("Invalid reservation number.");
        }
    }

    @Override
    public synchronized void makePayment(Bill bill, String paymentMethod) {
        bill.setPaymentMethod(paymentMethod);
        // Process the payment
    }

    // Helper methods for finding available rooms, generating reservation and bill numbers, etc.
}
```

Explanation:
1. The `RoomType` enum represents different types of rooms available in the hotel.

2. The `Room` class represents a room in the hotel, with properties such as room number, type, and availability status.

3. The `Guest` class represents a hotel guest, with properties like name and contact information.

4. The `Reservation` class represents a room reservation made by a guest, with details such as reservation number, guest, room, check-in date, and check-out date.

5. The `Bill` class represents a bill generated for a reservation, including the bill number, reservation, total amount, and payment method.

6. The `HotelManagementSystem` interface defines the contract for the hotel management system, specifying methods for booking rooms, checking in, checking out, retrieving available rooms and reservations, generating bills, and processing payments.

7. The `HotelManagementSystemImpl` class implements the `HotelManagementSystem` interface and provides the actual implementation of the hotel management system.

8. The `bookRoom` method allows guests to book a room by specifying the guest details, room type, check-in date, and check-out date. It searches for an available room of the specified type and creates a reservation if a room is found.

9. The `checkIn` and `checkOut` methods handle the check-in and check-out processes, respectively. They find the corresponding reservation based on the reservation number and perform the necessary operations.

10. The `getAvailableRooms` method retrieves a list of available rooms for a specific date range.

11. The `getReservations` method retrieves a list of reservations for a specific date.

12. The `generateBill` method generates a bill for a reservation by calculating the total amount based on the room type and duration of stay.

13. The `makePayment` method processes the payment for a bill using the specified payment method.

Design Patterns Used:
- Repository Pattern: The `HotelManagementSystemImpl` class acts as a repository for managing the rooms, reservations, and bills. It provides methods to perform CRUD (Create, Read, Update, Delete) operations on the data.

- Factory Pattern: The `HotelManagementSystemImpl` class can be extended to include factory methods for creating different types of rooms, reservations, and bills. This allows for easy extensibility and flexibility in creating objects.

- Singleton Pattern: The `HotelManagementSystemImpl` class can be implemented as a singleton to ensure that only one instance of the hotel management system exists throughout the application. This can be achieved by making the constructor private and providing a static method to retrieve the instance.

The code ensures data consistency and handles concurrent bookings by using the `synchronized` keyword on the methods in `HotelManagementSystemImpl`. This prevents multiple threads from accessing and modifying the shared data simultaneously, avoiding conflicts and maintaining data integrity.