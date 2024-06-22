# Designing a Movie Ticket Booking System like BookMyShow

## Requirements
1. The system should allow users to view the list of movies playing in different theaters.
2. Users should be able to select a movie, theater, and show timing to book tickets.
3. The system should display the seating arrangement of the selected show and allow users to choose seats.
4. Users should be able to make payments and confirm their booking.
5. The system should handle concurrent bookings and ensure seat availability is updated in real-time.
6. The system should support different types of seats (e.g., normal, premium) and pricing.
7. The system should allow theater administrators to add, update, and remove movies, shows, and seating arrangements.
8. The system should be scalable to handle a large number of concurrent users and bookings.


```java
class Movie {
    private String movieId;
    private String title;
    private String description;
    private int duration;

    // Constructor, getters, and setters
}

class Theater {
    private String theaterId;
    private String name;
    private String location;
    private List<Screen> screens;

    // Constructor, getters, and setters
}

class Screen {
    private String screenId;
    private String name;
    private int capacity;
    private List<Show> shows;

    // Constructor, getters, and setters
}

class Show {
    private String showId;
    private Movie movie;
    private Screen screen;
    private DateTime startTime;
    private DateTime endTime;
    private List<Seat> seats;

    // Constructor, getters, and setters
}

abstract class Seat {
    protected String seatId;
    protected int rowNumber;
    protected int seatNumber;
    protected boolean isReserved;

    // Constructor, getters, and setters
}

class NormalSeat extends Seat {
    // Additional properties or methods specific to normal seats
}

class PremiumSeat extends Seat {
    // Additional properties or methods specific to premium seats
}

class Booking {
    private String bookingId;
    private Show show;
    private List<Seat> seats;
    private double totalAmount;
    private String paymentStatus;

    // Constructor, getters, and setters
}

class MovieTicketBookingSystem {
    private Map<String, Movie> movies;
    private Map<String, Theater> theaters;
    private Map<String, Booking> bookings;

    public MovieTicketBookingSystem() {
        movies = new HashMap<>();
        theaters = new HashMap<>();
        bookings = new HashMap<>();
    }

    public void addMovie(Movie movie) {
        movies.put(movie.getMovieId(), movie);
    }

    public void updateMovie(Movie movie) {
        movies.put(movie.getMovieId(), movie);
    }

    public void removeMovie(String movieId) {
        movies.remove(movieId);
    }

    public void addTheater(Theater theater) {
        theaters.put(theater.getTheaterId(), theater);
    }

    public void updateTheater(Theater theater) {
        theaters.put(theater.getTheaterId(), theater);
    }

    public void removeTheater(String theaterId) {
        theaters.remove(theaterId);
    }

    public List<Movie> getAllMovies() {
        return new ArrayList<>(movies.values());
    }

    public List<Theater> getAllTheaters() {
        return new ArrayList<>(theaters.values());
    }

    public List<Show> getShowsForMovie(String movieId) {
        List<Show> shows = new ArrayList<>();
        for (Theater theater : theaters.values()) {
            for (Screen screen : theater.getScreens()) {
                for (Show show : screen.getShows()) {
                    if (show.getMovie().getMovieId().equals(movieId)) {
                        shows.add(show);
                    }
                }
            }
        }
        return shows;
    }

    public synchronized Booking bookTickets(Show show, List<Seat> selectedSeats) {
        if (areSeatsAvailable(show, selectedSeats)) {
            reserveSeats(show, selectedSeats);
            Booking booking = createBooking(show, selectedSeats);
            bookings.put(booking.getBookingId(), booking);
            return booking;
        }
        return null;
    }

    private boolean areSeatsAvailable(Show show, List<Seat> selectedSeats) {
        for (Seat seat : selectedSeats) {
            if (isSeatReserved(show, seat)) {
                return false;
            }
        }
        return true;
    }

    private boolean isSeatReserved(Show show, Seat seat) {
        for (Seat showSeat : show.getSeats()) {
            if (showSeat.getSeatId().equals(seat.getSeatId()) && showSeat.isReserved()) {
                return true;
            }
        }
        return false;
    }

    private void reserveSeats(Show show, List<Seat> selectedSeats) {
        for (Seat seat : selectedSeats) {
            for (Seat showSeat : show.getSeats()) {
                if (showSeat.getSeatId().equals(seat.getSeatId())) {
                    showSeat.setReserved(true);
                    break;
                }
            }
        }
    }

    private Booking createBooking(Show show, List<Seat> selectedSeats) {
        // Create a new booking with the selected show and seats
        // Calculate the total amount based on the seat types
        // Set the payment status as pending
        // Return the created booking
    }

    public synchronized void confirmBooking(String bookingId, String paymentStatus) {
        Booking booking = bookings.get(bookingId);
        if (booking != null) {
            booking.setPaymentStatus(paymentStatus);
        }
    }

    // Other methods for updating seat availability, handling cancellations, etc.
}
```

Explanation:
1. The `Movie` class represents a movie with properties such as movie ID, title, description, and duration.

2. The `Theater` class represents a theater with properties such as theater ID, name, location, and a list of screens.

3. The `Screen` class represents a screen in a theater with properties such as screen ID, name, capacity, and a list of shows.

4. The `Show` class represents a show of a movie in a specific screen at a particular time. It has properties such as show ID, movie, screen, start time, end time, and a list of seats.

5. The `Seat` class is an abstract class representing a seat in a show. It has properties such as seat ID, row number, seat number, and reservation status. The `NormalSeat` and `PremiumSeat` classes extend the `Seat` class to represent different types of seats.

6. The `Booking` class represents a booking made by a user. It has properties such as booking ID, show, selected seats, total amount, and payment status.

7. The `MovieTicketBookingSystem` class is the main class that manages the movies, theaters, and bookings. It provides methods for adding, updating, and removing movies and theaters, retrieving shows for a movie, booking tickets, and confirming bookings.

8. The `bookTickets` method is synchronized to handle concurrent bookings. It checks seat availability, reserves the selected seats, creates a new booking, and returns the booking object.

9. The `confirmBooking` method is synchronized to update the payment status of a booking.

Design Patterns Used:
- Repository Pattern: The `MovieTicketBookingSystem` class acts as a repository, providing methods to manage and retrieve data related to movies, theaters, and bookings. It encapsulates the data storage and retrieval logic.

- Factory Pattern: The `createBooking` method in the `MovieTicketBookingSystem` class acts as a factory method to create booking objects based on the selected show and seats.

- Singleton Pattern: The `MovieTicketBookingSystem` class can be implemented as a singleton to ensure that only one instance of the booking system exists throughout the application. This allows for centralized management of data and ensures data consistency.

Concurrency and Scalability:
- The `bookTickets` and `confirmBooking` methods are synchronized to handle concurrent bookings and ensure data consistency. This prevents multiple users from booking the same seats simultaneously.

- To handle a large number of concurrent users and bookings, the system can be designed to scale horizontally by deploying multiple instances of the application servers and using load balancers to distribute the traffic.

- The data storage layer can be designed to use distributed databases or NoSQL databases that can handle high read and write throughput and provide scalability.

Real-time Updates:
- To ensure that seat availability is updated in real-time, the system can use techniques like websockets or server-sent events to push updates to the client-side whenever a booking is made or cancelled.

- Alternatively, the client-side can periodically poll the server to fetch the latest seat availability status.

This low-level design provides a basic structure for the movie ticket booking system that meets the given requirements. It can be further enhanced with additional features such as user authentication, payment integration, seat map visualization, and more advanced booking and cancellation policies.

The use of the Repository pattern provides a centralized way to manage and retrieve data, while the Factory pattern allows for the creation of booking objects based on specific criteria. The Singleton pattern ensures a single instance of the booking system for data consistency.

Overall, this design aims to provide a scalable and concurrent solution for the movie ticket booking system, taking into account the requirements of movie and theater management, seat selection, booking, and real-time updates.