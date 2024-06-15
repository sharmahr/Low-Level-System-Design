Design Pattern: Factory Design Pattern

Diagram:
```
         Client
            |
            |
      ----------------
      |              |
  ProductA      ProductB
      |              |
      ----------------
            |
            |
        Factory
```

Explanation:
The Factory Design Pattern is a creational pattern that provides an interface for creating objects without specifying their concrete classes. It encapsulates the object creation logic, allowing the client code to work with an abstract factory and products, promoting loose coupling and extensibility.

Key Points:
- Encapsulates object creation logic in a separate factory class or interface
- Provides a common interface for creating related objects
- Allows the client code to work with abstract factories and products
- Promotes loose coupling and flexibility in the codebase

Real-Life Applications:
- Creating different types of documents (e.g., PDFDocument, WordDocument) in a document processing application
- Generating various types of database connections (e.g., MySQLConnection, OracleConnection) in a database management system
- Constructing different types of UI elements (e.g., Button, TextField) in a GUI framework

Remember:
"The Factory Pattern is like a vending machine that dispenses objects based on the selected type, without exposing the internal creation process."

Sample Code:
```java
// AbstractFactory interface
interface DocumentFactory {
    Document createDocument();
}

// ConcreteFactory classes
class PDFDocumentFactory implements DocumentFactory {
    public Document createDocument() {
        return new PDFDocument();
    }
}

class WordDocumentFactory implements DocumentFactory {
    public Document createDocument() {
        return new WordDocument();
    }
}

// Product interface
interface Document {
    void open();
    void close();
}

// ConcreteProduct classes
class PDFDocument implements Document {
    public void open() {
        System.out.println("Opening PDF document");
    }
    public void close() {
        System.out.println("Closing PDF document");
    }
}

class WordDocument implements Document {
    public void open() {
        System.out.println("Opening Word document");
    }
    public void close() {
        System.out.println("Closing Word document");
    }
}

// Client code
class Client {
    private DocumentFactory documentFactory;

    public Client(DocumentFactory documentFactory) {
        this.documentFactory = documentFactory;
    }

    public void openDocument() {
        Document document = documentFactory.createDocument();
        document.open();
        document.close();
    }
}
```

In this example, the Factory Pattern is used to create different types of documents (PDFDocument and WordDocument) through their respective factory classes (PDFDocumentFactory and WordDocumentFactory). The client code works with the abstract DocumentFactory and Document interfaces, allowing for easy extensibility and loose coupling between the client and the concrete document classes.