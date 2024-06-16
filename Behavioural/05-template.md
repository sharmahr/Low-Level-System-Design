Design Pattern: Template Method Design Pattern

Diagram:
```
            +-------------------------+
            |       AbstractClass     |
            +-------------------------+
            | + templateMethod()      |
            | # primitiveOperation1() |
            | # primitiveOperation2() |
            +-------------------------+
                            ^
                            |
            +---------------+---------------+
            |                               |
+-------------------------+   +-------------------------+
| ConcreteClass1          |   | ConcreteClass2          |
+-------------------------+   +-------------------------+
| # primitiveOperation1() |   | # primitiveOperation1() |
| # primitiveOperation2() |   | # primitiveOperation2() |
+-------------------------+   +-------------------------+
```

Explanation:
The Template Method Design Pattern is a behavioral pattern that defines the skeleton of an algorithm in a base class, allowing subclasses to override specific steps of the algorithm without changing its overall structure. It provides a template or a fixed sequence of steps for an algorithm, where some steps can be implemented in the base class, and others are left to be implemented by the subclasses. This pattern promotes code reuse and enables the extension of the algorithm while maintaining control over its structure.

Key Points:
- Defines the skeleton of an algorithm in a base class
- Allows subclasses to override specific steps of the algorithm
- Maintains the overall structure of the algorithm
- Promotes code reuse and extensibility
- Follows the Hollywood Principle: "Don't call us, we'll call you"

Real-Life Applications:
- Implementing different types of database connections (MySQL, PostgreSQL) with a common connection template
- Building a framework for creating various types of documents (HTML, PDF) with a shared document generation process
- Designing a game engine with a basic game loop that can be customized by different game implementations

Remember:
"The Template Method Pattern is like a recipe, where the main steps are defined in the base class, and the specific ingredients can be added by the subclasses."

Sample Code:
```java
// Abstract class
abstract class DataProcessor {
    public final void processData() {
        readData();
        processDataImpl();
        writeData();
    }

    protected abstract void readData();
    protected abstract void processDataImpl();

    protected void writeData() {
        System.out.println("Writing processed data to file.");
    }
}

// Concrete class
class CsvDataProcessor extends DataProcessor {
    @Override
    protected void readData() {
        System.out.println("Reading data from CSV file.");
    }

    @Override
    protected void processDataImpl() {
        System.out.println("Processing CSV data.");
    }
}

// Concrete class
class JsonDataProcessor extends DataProcessor {
    @Override
    protected void readData() {
        System.out.println("Reading data from JSON file.");
    }

    @Override
    protected void processDataImpl() {
        System.out.println("Processing JSON data.");
    }
}

// Client code
public class TemplateMethodDemo {
    public static void main(String[] args) {
        DataProcessor csvProcessor = new CsvDataProcessor();
        csvProcessor.processData();

        System.out.println();

        DataProcessor jsonProcessor = new JsonDataProcessor();
        jsonProcessor.processData();
    }
}
```

In this example, the Template Method Pattern is used to define a common data processing algorithm. The `DataProcessor` abstract class provides the template method `processData()`, which defines the overall structure of the algorithm. It calls the `readData()` and `processDataImpl()` abstract methods, which are implemented by the concrete subclasses (`CsvDataProcessor` and `JsonDataProcessor`). The `writeData()` method is already implemented in the base class. The client code demonstrates how different data processors can be used interchangeably, following the same algorithm structure defined by the template method.