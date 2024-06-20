# Designing a Parking Lot System

## Requirements
1. The parking lot should have multiple levels, each level with a certain number of parking spots.
2. The parking lot should support different types of vehicles, such as cars, motorcycles, and trucks.
3. Each parking spot should be able to accommodate a specific type of vehicle.
4. The system should assign a parking spot to a vehicle upon entry and release it when the vehicle exits.
5. The system should track the availability of parking spots and provide real-time information to customers.
6. The system should handle multiple entry and exit points and support concurrent access.

```
               +----------------+
               |     Vehicle    |
               +----------------+
               | + getType()    |
               +----------------+
                   ^        ^
                   |        |
           +-------+        +-------+
           |                        |
+------------------+        +------------------+
|       Car        |        |    Motorcycle    |
+------------------+        +------------------+
| + getType()      |        | + getType()      |
+------------------+        +------------------+
           ^
           |
+------------------+
|      Truck       |
+------------------+
| + getType()      |
+------------------+

       +------------------+
       | ParkingSpot      |
       +------------------+
       | - id             |
       | - type           |
       | - isAvailable    |
       +------------------+
       | + ParkingSpot()  |
       | + setAvailable() |
       | + isAvailable()  |
       | + getType()      |
       +------------------+
              ^
              |
       +----------------------+
       | ParkingLevel         |
       +----------------------+
       | - levelId            |
       | - spots              |
       +----------------------+
       | + ParkingLevel()     |
       | + findAvailableSpot()|
       +----------------------+
              ^
              |
       +----------------------+
       | ParkingLot           |
       +----------------------+
       | - levels             |
       +----------------------+
       | + ParkingLot()       |
       | + findAvailableSpot()|
       +----------------------+
              ^
              |
       +------------------+
       | ParkingSystem    |
       +------------------+
       | - parkingLot     |
       +------------------+
       | + ParkingSystem()|
       | + assignSpot()   |
       | + releaseSpot()  |
       +------------------+
```

```java
// Vehicle interface representing different types of vehicles
interface Vehicle {
    String getType();
}

// Car class implementing the Vehicle interface
class Car implements Vehicle {
    public String getType() {
        return "Car";
    }
}

// Motorcycle class implementing the Vehicle interface
class Motorcycle implements Vehicle {
    public String getType() {
        return "Motorcycle";
    }
}

// Truck class implementing the Vehicle interface
class Truck implements Vehicle {
    public String getType() {
        return "Truck";
    }
}

// ParkingSpot class representing a parking spot
class ParkingSpot {
    private String id;
    private String type;
    private boolean isAvailable;

    public ParkingSpot(String id, String type) {
        this.id = id;
        this.type = type;
        this.isAvailable = true;
    }

    // Getters and setters
    // ...
}

// ParkingLevel class representing a level in the parking lot
class ParkingLevel {
    private String levelId;
    private List<ParkingSpot> spots;

    public ParkingLevel(String levelId, List<ParkingSpot> spots) {
        this.levelId = levelId;
        this.spots = spots;
    }

    public ParkingSpot findAvailableSpot(String type) {
        for (ParkingSpot spot : spots) {
            if (spot.getType().equals(type) && spot.isAvailable()) {
                return spot;
            }
        }
        return null;
    }

    // Other methods
    // ...
}

// ParkingLot class representing the parking lot
class ParkingLot {
    private List<ParkingLevel> levels;

    public ParkingLot(List<ParkingLevel> levels) {
        this.levels = levels;
    }

    public ParkingSpot findAvailableSpot(String type) {
        for (ParkingLevel level : levels) {
            ParkingSpot spot = level.findAvailableSpot(type);
            if (spot != null) {
                return spot;
            }
        }
        return null;
    }

    // Other methods
    // ...
}

// ParkingSystem class representing the parking system
class ParkingSystem {
    private ParkingLot parkingLot;

    public ParkingSystem(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public synchronized ParkingSpot assignSpot(Vehicle vehicle) {
        ParkingSpot spot = parkingLot.findAvailableSpot(vehicle.getType());
        if (spot != null) {
            spot.setAvailable(false);
        }
        return spot;
    }

    public synchronized void releaseSpot(ParkingSpot spot) {
        spot.setAvailable(true);
    }

    // Other methods for real-time information, entry/exit points, etc.
    // ...
}
```

Explanation:
- The `Vehicle` interface is used to represent different types of vehicles, and the `Car`, `Motorcycle`, and `Truck` classes implement this interface.
- The `ParkingSpot` class represents a parking spot with an ID, type, and availability status.
- The `ParkingLevel` class represents a level in the parking lot and contains a list of parking spots. It provides a method to find an available spot of a specific type.
- The `ParkingLot` class represents the entire parking lot and contains a list of parking levels. It provides a method to find an available spot across all levels.
- The `ParkingSystem` class is the main entry point and handles the assignment and release of parking spots. It uses synchronized methods to ensure thread safety for concurrent access.

Design Patterns Used:
1. Interface Segregation Principle (ISP): The `Vehicle` interface is used to define the common behavior of different vehicle types, allowing for flexibility and extensibility.
2. Composition: The `ParkingLot` class is composed of `ParkingLevel` objects, and each `ParkingLevel` is composed of `ParkingSpot` objects, promoting code reuse and modularity.
3. Singleton Pattern (optional): The `ParkingSystem` class can be implemented as a singleton to ensure only one instance of the parking system exists throughout the application.

The design patterns used promote loose coupling, extensibility, and maintainability. The interface segregation allows for easy addition of new vehicle types, while composition enables the creation of a hierarchical structure for the parking lot. The synchronized methods in the `ParkingSystem` class ensure thread safety for concurrent access.