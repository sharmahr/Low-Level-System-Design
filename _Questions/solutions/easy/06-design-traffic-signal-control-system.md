# Designing a Traffic Signal Control System

## Requirements
1. The traffic signal system should control the flow of traffic at an intersection with multiple roads.
2. The system should support different types of signals, such as red, yellow, and green.
3. The duration of each signal should be configurable and adjustable based on traffic conditions.
4. The system should handle the transition between signals smoothly, ensuring safe and efficient traffic flow.
5. The system should be able to detect and handle emergency situations, such as an ambulance or fire truck approaching the intersection.
6. The system should be scalable and extensible to support additional features and functionality.

```java
// Signal enum representing different types of signals
enum Signal {
    RED, YELLOW, GREEN
}

// TrafficLight class representing a traffic light
class TrafficLight {
    private String id;
    private Signal currentSignal;
    private int redDuration;
    private int yellowDuration;
    private int greenDuration;

    public TrafficLight(String id, int redDuration, int yellowDuration, int greenDuration) {
        this.id = id;
        this.redDuration = redDuration;
        this.yellowDuration = yellowDuration;
        this.greenDuration = greenDuration;
        this.currentSignal = Signal.RED;
    }

    // Getters and setters
    // ...
}

// TrafficIntersection class representing an intersection with multiple roads
class TrafficIntersection {
    private List<TrafficLight> trafficLights;

    public TrafficIntersection() {
        trafficLights = new ArrayList<>();
    }

    public void addTrafficLight(TrafficLight trafficLight) {
        trafficLights.add(trafficLight);
    }

    public List<TrafficLight> getTrafficLights() {
        return trafficLights;
    }
}

// TrafficSignalSystem class representing the traffic signal system
class TrafficSignalSystem {
    private TrafficIntersection intersection;
    private boolean isEmergency;

    public TrafficSignalSystem(TrafficIntersection intersection) {
        this.intersection = intersection;
        this.isEmergency = false;
    }

    public void startTrafficControl() {
        while (true) {
            for (TrafficLight trafficLight : intersection.getTrafficLights()) {
                if (isEmergency) {
                    handleEmergency();
                } else {
                    changeSignal(trafficLight, Signal.GREEN, trafficLight.getGreenDuration());
                    changeSignal(trafficLight, Signal.YELLOW, trafficLight.getYellowDuration());
                    changeSignal(trafficLight, Signal.RED, trafficLight.getRedDuration());
                }
            }
        }
    }

    private void changeSignal(TrafficLight trafficLight, Signal signal, int duration) {
        trafficLight.setCurrentSignal(signal);
        try {
            Thread.sleep(duration * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void handleEmergency() {
        for (TrafficLight trafficLight : intersection.getTrafficLights()) {
            trafficLight.setCurrentSignal(Signal.RED);
        }
        // Code to handle emergency situation, such as allowing emergency vehicles to pass
        // ...
        isEmergency = false;
    }

    public void setEmergency(boolean isEmergency) {
        this.isEmergency = isEmergency;
    }
}
```

Explanation:
- The `Signal` enum represents different types of signals, such as red, yellow, and green.
- The `TrafficLight` class represents a traffic light, with attributes such as ID, current signal, and durations for each signal.
- The `TrafficIntersection` class represents an intersection with multiple roads and contains a list of traffic lights.
- The `TrafficSignalSystem` class represents the traffic signal system and controls the flow of traffic at the intersection. It has methods to start traffic control, change signals, and handle emergency situations.
- The `startTrafficControl` method runs in a loop, iterating over each traffic light in the intersection and changing the signals based on their durations. It also checks for emergency situations and handles them accordingly.
- The `changeSignal` method changes the current signal of a traffic light and waits for the specified duration using `Thread.sleep`.
- The `handleEmergency` method sets all traffic lights to red and can be extended to handle specific emergency situations, such as allowing emergency vehicles to pass.

Design Patterns Used:
1. State Pattern: The `Signal` enum and the `currentSignal` attribute in the `TrafficLight` class represent the state of the traffic light. The `changeSignal` method transitions the traffic light between different states (signals).
2. Observer Pattern (optional): The traffic signal system can be extended to include observers, such as pedestrian crossings or other systems, that need to be notified when the signal changes.
3. Strategy Pattern (optional): Different algorithms or strategies can be implemented for controlling the traffic flow based on traffic conditions or time of day. These strategies can be encapsulated in separate classes and used interchangeably by the traffic signal system.

The design patterns used promote flexibility, extensibility, and maintainability. The State pattern allows for clear representation and transition between different signal states. The Observer pattern enables loose coupling between the traffic signal system and other components that depend on signal changes. The Strategy pattern allows for easy switch between different traffic control algorithms.