Design Pattern: Singleton Design Pattern

Diagram:
```
        +------------------------+
        |       Singleton        |
        +------------------------+
        | - instance: Singleton  |
        | - Singleton()          |
        | + getInstance(): Singleton |
        +------------------------+
```

Explanation:
The Singleton Design Pattern is a creational pattern that ensures a class has only one instance and provides a global point of access to that instance. It restricts the instantiation of a class to a single object, which can be accessed from anywhere in the system. This pattern is useful when exactly one instance of a class is needed to control the action throughout the execution, such as managing a shared resource or providing a global state.

Key Points:
- Ensures a class has only one instance
- Provides a global point of access to the instance
- Restricts the instantiation of a class to a single object
- Useful for managing shared resources or global state
- Provides better control over the instance creation and access

Real-Life Applications:
- Managing a database connection pool in a web application
- Implementing a print spooler or a file system manager
- Creating a configuration manager to store global settings

Remember:
"The Singleton Pattern is like having a single key to a locked room, ensuring that only one person can enter at a time."

Sample Code:
```java
// Singleton class
class DatabaseConnection {
    private static DatabaseConnection instance;
    private String connectionString;

    private DatabaseConnection(String connectionString) {
        this.connectionString = connectionString;
        // Initialize the database connection
    }

    public static synchronized DatabaseConnection getInstance(String connectionString) {
        if (instance == null) {
            instance = new DatabaseConnection(connectionString);
        }
        return instance;
    }

    public void executeQuery(String query) {
        // Execute the database query
        System.out.println("Executing query: " + query);
    }
}

// Client code
class Client {
    public static void main(String[] args) {
        String connectionString = "jdbc:mysql://localhost:3306/mydatabase";

        DatabaseConnection connection1 = DatabaseConnection.getInstance(connectionString);
        connection1.executeQuery("SELECT * FROM users");

        DatabaseConnection connection2 = DatabaseConnection.getInstance(connectionString);
        connection2.executeQuery("INSERT INTO users VALUES ('John', 'Doe')");

        // connection1 and connection2 refer to the same instance
        System.out.println(connection1 == connection2); // Output: true
    }
}
```

In this example, the Singleton Pattern is used to manage a database connection. The `DatabaseConnection` class has a private constructor and a static `getInstance()` method that returns the single instance of the class. The `getInstance()` method is synchronized to ensure thread safety in case of concurrent access. The client code retrieves the singleton instance using the `getInstance()` method and can execute database queries using the `executeQuery()` method. Regardless of how many times the `getInstance()` method is called, it always returns the same instance of the `DatabaseConnection` class.