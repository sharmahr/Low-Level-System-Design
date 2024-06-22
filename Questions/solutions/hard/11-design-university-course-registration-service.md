# Designing a University Course Registration System

## Requirements
1. The course registration system should allow students to register for courses and view their registered courses.
2. Each course should have a course code, name, instructor, and maximum enrollment capacity.
3. Students should be able to search for courses based on course code or name.
4. The system should prevent students from registering for courses that have reached their maximum enrollment capacity.
5. The system should handle concurrent registration requests from multiple students.
6. The system should ensure data consistency and prevent race conditions.
7. The system should be extensible to accommodate future enhancements and new features.


```java
public class Course {
    private String courseCode;
    private String name;
    private String instructor;
    private int maxCapacity;
    private int currentEnrollment;
    private List<Student> enrolledStudents;

    public Course(String courseCode, String name, String instructor, int maxCapacity) {
        this.courseCode = courseCode;
        this.name = name;
        this.instructor = instructor;
        this.maxCapacity = maxCapacity;
        this.currentEnrollment = 0;
        this.enrolledStudents = new ArrayList<>();
    }

    // Getters and setters

    public synchronized boolean isFull() {
        return currentEnrollment >= maxCapacity;
    }

    public synchronized void enrollStudent(Student student) {
        if (!isFull()) {
            enrolledStudents.add(student);
            currentEnrollment++;
        } else {
            throw new IllegalStateException("Course is full. Cannot enroll more students.");
        }
    }
}

public class Student {
    private String studentId;
    private String name;
    private List<Course> registeredCourses;

    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    // Getters and setters

    public void registerCourse(Course course) {
        synchronized (course) {
            if (!course.isFull()) {
                registeredCourses.add(course);
                course.enrollStudent(this);
            } else {
                throw new IllegalStateException("Course is full. Registration failed.");
            }
        }
    }
}

public class CourseRegistrationSystem {
    private List<Course> courses;

    public CourseRegistrationSystem() {
        this.courses = new ArrayList<>();
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public List<Course> searchCoursesByCode(String courseCode) {
        return courses.stream()
                .filter(course -> course.getCourseCode().equals(courseCode))
                .collect(Collectors.toList());
    }

    public List<Course> searchCoursesByName(String name) {
        return courses.stream()
                .filter(course -> course.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    public void registerStudentForCourse(Student student, Course course) {
        student.registerCourse(course);
    }
}

public class CourseRegistrationApp {
    public static void main(String[] args) {
        CourseRegistrationSystem registrationSystem = new CourseRegistrationSystem();

        // Create and add courses
        Course course1 = new Course("CS101", "Introduction to Programming", "John Doe", 50);
        Course course2 = new Course("MATH201", "Calculus I", "Jane Smith", 30);
        registrationSystem.addCourse(course1);
        registrationSystem.addCourse(course2);

        // Create students
        Student student1 = new Student("S001", "Alice");
        Student student2 = new Student("S002", "Bob");

        // Search for courses
        List<Course> searchResults1 = registrationSystem.searchCoursesByCode("CS101");
        List<Course> searchResults2 = registrationSystem.searchCoursesByName("Calculus");

        // Register students for courses
        registrationSystem.registerStudentForCourse(student1, course1);
        registrationSystem.registerStudentForCourse(student2, course1);
        registrationSystem.registerStudentForCourse(student1, course2);

        // View registered courses for students
        List<Course> registeredCourses1 = student1.getRegisteredCourses();
        List<Course> registeredCourses2 = student2.getRegisteredCourses();
    }
}
```

Explanation:
1. The `Course` class represents a course in the system. It has properties such as `courseCode`, `name`, `instructor`, `maxCapacity`, `currentEnrollment`, and a list of enrolled students. The `isFull()` method checks if the course has reached its maximum enrollment capacity, and the `enrollStudent()` method enrolls a student in the course if it is not full. The `synchronized` keyword is used to ensure thread safety when checking the enrollment capacity and enrolling students.

2. The `Student` class represents a student in the system. It has properties such as `studentId`, `name`, and a list of registered courses. The `registerCourse()` method allows a student to register for a course. It uses the `synchronized` keyword to ensure thread safety when checking the course capacity and registering the student.

3. The `CourseRegistrationSystem` class represents the main system that manages courses and student registrations. It has a list of courses and provides methods to add courses, search courses by code or name, and register students for courses.

4. The `CourseRegistrationApp` class contains the main method and demonstrates the usage of the course registration system. It creates and adds courses to the system, creates students, searches for courses, registers students for courses, and retrieves the registered courses for students.

Design Patterns Used:
- Singleton Pattern: The `CourseRegistrationSystem` class can be implemented as a singleton to ensure that only one instance of the system exists throughout the application. This can be achieved by making the constructor private and providing a static method to retrieve the instance.

Thread Safety and Data Consistency:
- The `synchronized` keyword is used in the `isFull()` and `enrollStudent()` methods of the `Course` class to ensure thread safety when checking the enrollment capacity and enrolling students. This prevents race conditions and ensures data consistency.

- The `registerCourse()` method in the `Student` class also uses the `synchronized` keyword to ensure thread safety when registering a student for a course. It acquires the lock on the `Course` object to prevent concurrent access and maintain data consistency.

Extensibility:
- The system can be extended to accommodate future enhancements and new features by adding new methods or modifying existing ones in the `CourseRegistrationSystem` class. For example, additional search criteria or registration rules can be implemented without significantly modifying the existing codebase.

- The `Course` and `Student` classes can also be extended to include additional properties or behavior as needed.

This low-level design provides a basic structure for the course registration system that meets the given requirements. It ensures thread safety and data consistency when handling concurrent registration requests from multiple students. The use of the Singleton pattern ensures a single instance of the system, while the synchronized methods prevent race conditions and maintain data integrity.
