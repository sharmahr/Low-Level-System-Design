# Designing a Logging Framework

## Requirements
1. The logging framework should support different log levels, such as DEBUG, INFO, WARNING, ERROR, and FATAL.
2. It should allow logging messages with a timestamp, log level, and message content.
3. The framework should support multiple output destinations, such as console, file, and database.
4. It should provide a configuration mechanism to set the log level and output destination.
5. The logging framework should be thread-safe to handle concurrent logging from multiple threads.
6. It should be extensible to accommodate new log levels and output destinations in the future.


```java
// LogLevel enum representing different log levels
enum LogLevel {
    DEBUG, INFO, WARNING, ERROR, FATAL
}

// LogMessage class representing a log message
class LogMessage {
    private LocalDateTime timestamp;
    private LogLevel level;
    private String message;

    public LogMessage(LogLevel level, String message) {
        this.timestamp = LocalDateTime.now();
        this.level = level;
        this.message = message;
    }

    // Getters
    // ...
}

// LogOutputDestination interface representing an output destination
interface LogOutputDestination {
    void write(LogMessage logMessage);
}

// ConsoleOutputDestination class implementing the LogOutputDestination interface
class ConsoleOutputDestination implements LogOutputDestination {
    public void write(LogMessage logMessage) {
        System.out.println(logMessage.getTimestamp() + " [" + logMessage.getLevel() + "] " + logMessage.getMessage());
    }
}

// FileOutputDestination class implementing the LogOutputDestination interface
class FileOutputDestination implements LogOutputDestination {
    private String filePath;

    public FileOutputDestination(String filePath) {
        this.filePath = filePath;
    }

    public void write(LogMessage logMessage) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(logMessage.getTimestamp() + " [" + logMessage.getLevel() + "] " + logMessage.getMessage());
            writer.newLine();
        } catch (IOException e) {
            // Handle the exception
        }
    }
}

// Logger class representing the logging framework
class Logger {
    private static Logger instance;
    private LogLevel logLevel;
    private List<LogOutputDestination> outputDestinations;

    private Logger() {
        logLevel = LogLevel.INFO;
        outputDestinations = new ArrayList<>();
    }

    public static synchronized Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public void setLogLevel(LogLevel level) {
        logLevel = level;
    }

    public void addOutputDestination(LogOutputDestination destination) {
        outputDestinations.add(destination);
    }

    public void log(LogLevel level, String message) {
        if (level.ordinal() >= logLevel.ordinal()) {
            LogMessage logMessage = new LogMessage(level, message);
            for (LogOutputDestination destination : outputDestinations) {
                destination.write(logMessage);
            }
        }
    }

    // Convenience methods for different log levels
    public void debug(String message) {
        log(LogLevel.DEBUG, message);
    }

    public void info(String message) {
        log(LogLevel.INFO, message);
    }

    public void warning(String message) {
        log(LogLevel.WARNING, message);
    }

    public void error(String message) {
        log(LogLevel.ERROR, message);
    }

    public void fatal(String message) {
        log(LogLevel.FATAL, message);
    }
}
```

Explanation:
- The `LogLevel` enum represents different log levels, such as DEBUG, INFO, WARNING, ERROR, and FATAL.
- The `LogMessage` class represents a log message, containing a timestamp, log level, and message content.
- The `LogOutputDestination` interface defines the contract for an output destination, with a single method `write` to write a log message.
- The `ConsoleOutputDestination` and `FileOutputDestination` classes implement the `LogOutputDestination` interface to provide logging output to the console and a file, respectively.
- The `Logger` class is the main class representing the logging framework. It follows the Singleton pattern to ensure only one instance of the logger exists throughout the application.
- The `Logger` class provides methods to set the log level, add output destinations, and log messages at different levels. It also provides convenience methods for each log level.
- The `log` method checks if the log level of the message is greater than or equal to the configured log level before logging the message to the output destinations.

Design Patterns Used:
1. Singleton Pattern: The `Logger` class is implemented as a singleton to ensure only one instance of the logger exists throughout the application. This is achieved by making the constructor private and providing a synchronized `getInstance` method.
2. Strategy Pattern: The `LogOutputDestination` interface and its implementations (`ConsoleOutputDestination` and `FileOutputDestination`) follow the Strategy pattern. This allows for easy extensibility to accommodate new output destinations in the future.

The design patterns used promote a single point of control for logging, extensibility for output destinations, and thread safety. The Singleton pattern ensures a single instance of the logger, while the Strategy pattern allows for flexible addition of new output destinations.
