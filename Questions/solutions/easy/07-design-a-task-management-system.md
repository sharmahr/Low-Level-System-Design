# Designing a Task Management System

## Requirements
1. The task management system should allow users to create, update, and delete tasks.
2. Each task should have a title, description, due date, priority, and status (e.g., pending, in progress, completed).
3. Users should be able to assign tasks to other users and set reminders for tasks.
4. The system should support searching and filtering tasks based on various criteria (e.g., priority, due date, assigned user).
5. Users should be able to mark tasks as completed and view their task history.
6. The system should handle concurrent access to tasks and ensure data consistency.
7. The system should be extensible to accommodate future enhancements and new features.

```java
// Task class representing a task
class Task {
    private int id;
    private String title;
    private String description;
    private Date dueDate;
    private int priority;
    private String status;
    private User assignedUser;

    public Task(int id, String title, String description, Date dueDate, int priority, String status, User assignedUser) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.status = status;
        this.assignedUser = assignedUser;
    }

    // Getters and setters
    // ...
}

// User class representing a user
class User {
    private int id;
    private String name;
    private String email;

    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    // Getters and setters
    // ...
}

// TaskRepository interface defining data access methods for tasks
interface TaskRepository {
    void createTask(Task task);
    void updateTask(Task task);
    void deleteTask(int taskId);
    List<Task> searchTasks(Map<String, Object> criteria);
}

// UserRepository interface defining data access methods for users
interface UserRepository {
    User getUserById(int userId);
    List<User> getAllUsers();
}

// TaskService class implementing the business logic for task management
class TaskService {
    private TaskRepository taskRepository;
    private UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public void createTask(Task task) {
        taskRepository.createTask(task);
    }

    public void updateTask(Task task) {
        taskRepository.updateTask(task);
    }

    public void deleteTask(int taskId) {
        taskRepository.deleteTask(taskId);
    }

    public List<Task> searchTasks(Map<String, Object> criteria) {
        return taskRepository.searchTasks(criteria);
    }

    public void assignTask(int taskId, int userId) {
        User user = userRepository.getUserById(userId);
        Task task = taskRepository.searchTasks(Map.of("id", taskId)).get(0);
        task.setAssignedUser(user);
        taskRepository.updateTask(task);
    }

    public void markTaskAsCompleted(int taskId) {
        Task task = taskRepository.searchTasks(Map.of("id", taskId)).get(0);
        task.setStatus("Completed");
        taskRepository.updateTask(task);
    }

    // Other methods
    // ...
}
```

Explanation:
- The `Task` class represents a task, with attributes such as ID, title, description, due date, priority, status, and assigned user.
- The `User` class represents a user, with attributes such as ID, name, and email.
- The `TaskRepository` and `UserRepository` interfaces define the data access methods for tasks and users, respectively. These interfaces can be implemented by different data access technologies (e.g., database, file system) to provide flexibility and extensibility.
- The `TaskService` class implements the business logic for task management. It depends on the `TaskRepository` and `UserRepository` interfaces for data access.
- The `TaskService` class provides methods for creating, updating, deleting, searching, assigning, and marking tasks as completed. These methods use the repository interfaces to perform the necessary data operations.

Design Patterns Used:
1. Repository Pattern: The `TaskRepository` and `UserRepository` interfaces follow the Repository pattern. They provide an abstraction layer between the business logic and the data access layer, allowing for flexibility in the choice of data storage technology and promoting testability.
2. Dependency Injection: The `TaskService` class depends on the `TaskRepository` and `UserRepository` interfaces, which are injected into its constructor. This allows for loose coupling and easier testing of the `TaskService` class.
3. Criteria Pattern (optional): The `searchTasks` method in the `TaskRepository` interface takes a map of search criteria, following the Criteria pattern. This allows for flexible and dynamic search queries based on various criteria.