# Designing an Elevator System

## Requirements
1. The elevator system should consist of multiple elevators serving multiple floors.
2. Each elevator should have a capacity limit and should not exceed it.
3. Users should be able to request an elevator from any floor and select a destination floor.
4. The elevator system should efficiently handle user requests and optimize the movement of elevators to minimize waiting time.
5. The system should prioritize requests based on the direction of travel and the proximity of the elevators to the requested floor.
6. The elevators should be able to handle multiple requests concurrently and process them in an optimal order.
7. The system should ensure thread safety and prevent race conditions when multiple threads interact with the elevators.


```java
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

enum Direction {
    UP, DOWN
}

class Request {
    private int floor;
    private Direction direction;

    public Request(int floor, Direction direction) {
        this.floor = floor;
        this.direction = direction;
    }

    // Getters and setters
}

class Elevator {
    private int id;
    private int currentFloor;
    private int capacity;
    private int numPassengers;
    private Direction currentDirection;
    private Queue<Request> requests;
    private Lock lock;

    public Elevator(int id, int capacity) {
        this.id = id;
        this.currentFloor = 1;
        this.capacity = capacity;
        this.numPassengers = 0;
        this.currentDirection = Direction.UP;
        this.requests = new PriorityQueue<>((r1, r2) -> {
            if (r1.getDirection() != r2.getDirection()) {
                return (r1.getDirection() == currentDirection) ? -1 : 1;
            } else {
                return Integer.compare(r1.getFloor(), r2.getFloor());
            }
        });
        this.lock = new ReentrantLock();
    }

    public void sendRequest(Request request) {
        lock.lock();
        try {
            requests.offer(request);
            processRequests();
        } finally {
            lock.unlock();
        }
    }

    public void processRequests() {
        while (!requests.isEmpty()) {
            Request request = requests.poll();
            int requestFloor = request.getFloor();
            Direction requestDirection = request.getDirection();

            if (requestDirection != currentDirection) {
                currentDirection = requestDirection;
            }

            while (currentFloor != requestFloor) {
                if (currentFloor < requestFloor) {
                    currentFloor++;
                } else {
                    currentFloor--;
                }
                // Simulate elevator movement delay
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Simulate passenger boarding/unboarding delay
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Elevator " + id + " processed request at floor " + requestFloor);
        }
    }

    // Other methods and getters/setters
}

class ElevatorController {
    private Elevator[] elevators;

    public ElevatorController(int numElevators, int capacity) {
        elevators = new Elevator[numElevators];
        for (int i = 0; i < numElevators; i++) {
            elevators[i] = new Elevator(i + 1, capacity);
        }
    }

    public void handleRequest(Request request) {
        int bestElevator = findBestElevator(request);
        elevators[bestElevator].sendRequest(request);
    }

    private int findBestElevator(Request request) {
        int bestElevator = 0;
        int minDistance = Integer.MAX_VALUE;

        for (int i = 0; i < elevators.length; i++) {
            Elevator elevator = elevators[i];
            int distance = Math.abs(elevator.getCurrentFloor() - request.getFloor());

            if (elevator.getCurrentDirection() == request.getDirection() && distance < minDistance) {
                bestElevator = i;
                minDistance = distance;
            }
        }

        return bestElevator;
    }
}

public class ElevatorSystem {
    public static void main(String[] args) {
        ElevatorController controller = new ElevatorController(3, 10);

        // Simulating user requests
        controller.handleRequest(new Request(5, Direction.UP));
        controller.handleRequest(new Request(2, Direction.UP));
        controller.handleRequest(new Request(7, Direction.DOWN));
        controller.handleRequest(new Request(4, Direction.DOWN));
    }
}
```

Explanation:
1. The `Direction` enum represents the direction of travel for the elevators and requests (UP or DOWN).

2. The `Request` class represents a user request, containing the floor number and the direction of travel.

3. The `Elevator` class represents an individual elevator. It has properties such as ID, current floor, capacity, number of passengers, current direction, and a queue of requests. The `sendRequest` method is used to send a request to the elevator, and the `processRequests` method handles the processing of requests in an optimal order based on the direction and proximity of the elevator.

4. The `ElevatorController` class manages multiple elevators and handles user requests. It maintains an array of `Elevator` objects and uses the `handleRequest` method to find the best elevator to serve a given request based on the elevator's current direction and proximity to the requested floor.

5. The `ElevatorSystem` class contains the main method and demonstrates the usage of the elevator system by creating an `ElevatorController` and simulating user requests.

Design Patterns Used:
- Singleton Pattern: The `ElevatorController` class can be implemented as a singleton to ensure that only one instance of the elevator controller exists throughout the system. This allows for centralized control and coordination of the elevators.

- Observer Pattern: The `ElevatorController` acts as an observer, listening for user requests and dispatching them to the appropriate elevators. The elevators act as subjects, processing the requests and notifying the controller when they are completed.

- Strategy Pattern: The `Elevator` class uses a priority queue to prioritize requests based on the direction of travel and proximity to the requested floor. The priority queue's comparator acts as a strategy to determine the optimal order of processing requests.

Thread Safety:
- The `Elevator` class uses a `ReentrantLock` to ensure thread safety when accessing and modifying the shared data (requests queue). The lock is acquired before accessing or modifying the shared data and released after the operation is completed.

- The `ElevatorController` class does not have any shared data that requires explicit thread synchronization. However, if multiple threads are accessing the `handleRequest` method concurrently, it may be necessary to synchronize access to the `findBestElevator` method to prevent race conditions.
