Design Pattern: Strategy Design Pattern

Diagram:
```
                +---------------------+
                |    Context          |
                +---------------------+
                | - strategy          |
                +---------------------+
                | + executeStrategy() |
                +---------------------+
                           |
                           |
                    +------+------+
                    |   Strategy  |
                    +-------------+
                    | + execute() |
                    +-------------+
                       ^       ^
                       |       |
            +----------+-------+--------+
            |                           |
+-------------------+           +-------------------+
| ConcreteStrategyA |           | ConcreteStrategyB |
+-------------------+           +-------------------+
| + execute()       |           | + execute()       |
+-------------------+           +-------------------+
```

Explanation:
The Strategy Design Pattern is a behavioral pattern that defines a family of interchangeable algorithms and encapsulates each one as a separate class. It allows the algorithm to vary independently from the clients that use it. The pattern consists of a context class that maintains a reference to a strategy object and delegates the specific behavior to the strategy. The strategy interface defines the common interface for all the concrete strategies, and each concrete strategy class implements the specific algorithm or behavior.

Key Points:
- Defines a family of interchangeable algorithms
- Encapsulates each algorithm as a separate class
- Allows the algorithm to vary independently from the clients
- Consists of a context class, strategy interface, and concrete strategy classes
- Promotes loose coupling and flexibility in selecting algorithms at runtime

Real-Life Applications:
- Implementing different payment strategies (credit card, PayPal, bank transfer) in an e-commerce application
- Applying various sorting algorithms (quick sort, merge sort, heap sort) in a sorting system
- Using different compression algorithms (ZIP, RAR, 7z) in a file compression utility

Remember:
"The Strategy Pattern is like a toolbox where you can choose the appropriate tool (algorithm) for a specific task, without changing the overall structure of your code."

Sample Code:
```java
// Strategy interface
interface SortStrategy {
    void sort(List<Integer> list);
}

// Concrete Strategy classes
class BubbleSortStrategy implements SortStrategy {
    public void sort(List<Integer> list) {
        // Bubble sort implementation
        System.out.println("Sorting using Bubble Sort");
        // ...
    }
}

class QuickSortStrategy implements SortStrategy {
    public void sort(List<Integer> list) {
        // Quick sort implementation
        System.out.println("Sorting using Quick Sort");
        // ...
    }
}

// Context class
class Sorter {
    private SortStrategy strategy;

    public Sorter(SortStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(SortStrategy strategy) {
        this.strategy = strategy;
    }

    public void executeStrategy(List<Integer> list) {
        strategy.sort(list);
    }
}

// Client code
class StrategyDemo {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(5, 2, 7, 1, 9);

        Sorter sorter = new Sorter(new BubbleSortStrategy());
        sorter.executeStrategy(numbers);

        sorter.setStrategy(new QuickSortStrategy());
        sorter.executeStrategy(numbers);
    }
}
```

In this example, the Strategy Pattern is used to implement different sorting algorithms. The `SortStrategy` interface defines the `sort()` method that is implemented by the concrete strategy classes (`BubbleSortStrategy` and `QuickSortStrategy`). The `Sorter` class acts as the context and maintains a reference to the current sorting strategy. It delegates the sorting behavior to the strategy object through the `executeStrategy()` method. The client code demonstrates how different sorting strategies can be set and executed on a list of numbers.