# Designing a Library Management System

## Requirements
1. The library management system should allow librarians to manage books, members, and borrowing activities.
2. The system should support adding, updating, and removing books from the library catalog.
3. Each book should have details such as title, author, ISBN, publication year, and availability status.
4. The system should allow members to borrow and return books.
5. Each member should have details such as name, member ID, contact information, and borrowing history.
6. The system should enforce borrowing rules, such as a maximum number of books that can be borrowed at a time and loan duration.
7. The system should handle concurrent access to the library catalog and member records.
8. The system should be extensible to accommodate future enhancements and new features.


```java
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Book {
    private String title;
    private String author;
    private String isbn;
    private int publicationYear;
    private boolean isAvailable;

    // Constructor, getters, and setters
}

class Member {
    private String name;
    private String memberId;
    private String contactInfo;
    private List<Book> borrowedBooks;

    // Constructor, getters, and setters
}

interface LibraryOperation {
    void execute();
}

class AddBookOperation implements LibraryOperation {
    private List<Book> books;
    private Book book;

    public AddBookOperation(List<Book> books, Book book) {
        this.books = books;
        this.book = book;
    }

    @Override
    public void execute() {
        books.add(book);
    }
}

class RemoveBookOperation implements LibraryOperation {
    private List<Book> books;
    private Book book;

    public RemoveBookOperation(List<Book> books, Book book) {
        this.books = books;
        this.book = book;
    }

    @Override
    public void execute() {
        books.remove(book);
    }
}

class LibraryManager {
    private List<Book> books;
    private List<Member> members;
    private int maxBooksPerMember;
    private int loanDuration;
    private Lock lock;

    public LibraryManager(int maxBooksPerMember, int loanDuration) {
        this.books = new ArrayList<>();
        this.members = new ArrayList<>();
        this.maxBooksPerMember = maxBooksPerMember;
        this.loanDuration = loanDuration;
        this.lock = new ReentrantLock();
    }

    public void executeOperation(LibraryOperation operation) {
        lock.lock();
        try {
            operation.execute();
        } finally {
            lock.unlock();
        }
    }

    public void addBook(Book book) {
        executeOperation(new AddBookOperation(books, book));
    }

    public void removeBook(Book book) {
        executeOperation(new RemoveBookOperation(books, book));
    }

    public void borrowBook(Member member, Book book) {
        lock.lock();
        try {
            if (book.isAvailable() && member.getBorrowedBooks().size() < maxBooksPerMember) {
                book.setAvailable(false);
                member.getBorrowedBooks().add(book);
            }
        } finally {
            lock.unlock();
        }
    }

    public void returnBook(Member member, Book book) {
        lock.lock();
        try {
            if (member.getBorrowedBooks().contains(book)) {
                book.setAvailable(true);
                member.getBorrowedBooks().remove(book);
            }
        } finally {
            lock.unlock();
        }
    }

    // Other methods for managing members, retrieving book information, etc.
}

public class LibraryManagementSystem {
    public static void main(String[] args) {
        LibraryManager libraryManager = new LibraryManager(3, 14);

        // Add books to the library
        Book book1 = new Book("Book 1", "Author 1", "ISBN1", 2021, true);
        Book book2 = new Book("Book 2", "Author 2", "ISBN2", 2022, true);

        libraryManager.addBook(book1);
        libraryManager.addBook(book2);

        // Create members
        Member member1 = new Member("John Doe", "M001", "john@example.com");
        Member member2 = new Member("Jane Smith", "M002", "jane@example.com");

        // Borrow books
        libraryManager.borrowBook(member1, book1);
        libraryManager.borrowBook(member2, book2);

        // Return books
        libraryManager.returnBook(member1, book1);
    }
}
```

Explanation:
1. The `Book` class represents a book in the library, with properties such as title, author, ISBN, publication year, and availability status.

2. The `Member` class represents a library member, with properties such as name, member ID, contact information, and a list of borrowed books.

3. The `LibraryOperation` interface defines a common operation that can be executed on the library, such as adding or removing a book. The `AddBookOperation` and `RemoveBookOperation` classes implement this interface to provide specific operations.

4. The `LibraryManager` class acts as the central manager for the library system. It maintains lists of books and members, and provides methods for adding and removing books, borrowing and returning books, and executing library operations.

5. The `executeOperation` method in `LibraryManager` takes a `LibraryOperation` object and executes it while ensuring thread safety using a `ReentrantLock`.

6. The `borrowBook` and `returnBook` methods handle the borrowing and returning of books, respectively. They enforce the borrowing rules, such as checking the availability of the book and the maximum number of books a member can borrow.

7. The `LibraryManagementSystem` class contains the main method and demonstrates the usage of the library management system by creating a `LibraryManager`, adding books, creating members, and performing borrowing and returning operations.

Design Patterns Used:
- Command Pattern: The `LibraryOperation` interface and its implementing classes (`AddBookOperation`, `RemoveBookOperation`) follow the Command pattern. They encapsulate library operations as objects, allowing for extensibility and flexibility in adding new operations without modifying the existing code.

- Singleton Pattern: The `LibraryManager` class can be implemented as a singleton to ensure that only one instance of the library manager exists throughout the system. This allows for centralized management of the library resources and ensures data consistency.

Thread Safety:
- The `LibraryManager` class uses a `ReentrantLock` to ensure thread safety when executing library operations and accessing shared data (books and members lists). The lock is acquired before executing an operation or accessing the shared data and released after the operation is completed.

Extensibility:
- The use of the `LibraryOperation` interface allows for easy extension of the system to accommodate new operations. New operations can be added by creating new classes that implement the `LibraryOperation` interface, without modifying the existing code.

- The `LibraryManager` class can be extended to include additional methods for managing members, retrieving book information, generating reports, and more, based on the specific requirements of the library management system.