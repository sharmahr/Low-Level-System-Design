# Designing Splitwise

## Requirements
1. The system should allow users to create accounts and manage their profile information.
2. Users should be able to create groups and add other users to the groups.
3. Users should be able to add expenses within a group, specifying the amount, description, and participants.
4. The system should automatically split the expenses among the participants based on their share.
5. Users should be able to view their individual balances with other users and settle up the balances.
6. The system should support different split methods, such as equal split, percentage split, and exact amounts.
7. Users should be able to view their transaction history and group expenses.
8. The system should handle concurrent transactions and ensure data consistency.


```java
class User {
    private String userId;
    private String name;
    private String email;
    private double balance;
    private List<Transaction> transactions;

    // Constructor, getters, and setters
}

class Group {
    private String groupId;
    private String name;
    private List<User> members;
    private List<Expense> expenses;

    // Constructor, getters, and setters
}

class Expense {
    private String expenseId;
    private String groupId;
    private double amount;
    private String description;
    private List<Split> splits;

    // Constructor, getters, and setters
}

interface SplitStrategy {
    void calculateSplits(Expense expense);
}

class EqualSplitStrategy implements SplitStrategy {
    @Override
    public void calculateSplits(Expense expense) {
        double amount = expense.getAmount();
        List<User> participants = expense.getParticipants();
        int numParticipants = participants.size();
        double splitAmount = amount / numParticipants;

        for (User participant : participants) {
            Split split = new Split(participant, splitAmount);
            expense.addSplit(split);
        }
    }
}

class PercentageSplitStrategy implements SplitStrategy {
    @Override
    public void calculateSplits(Expense expense) {
        // Implementation for percentage split
    }
}

class ExactAmountSplitStrategy implements SplitStrategy {
    @Override
    public void calculateSplits(Expense expense) {
        // Implementation for exact amount split
    }
}

class Split {
    private User user;
    private double amount;

    // Constructor, getters, and setters
}

class Transaction {
    private String transactionId;
    private User payer;
    private User payee;
    private double amount;
    private Timestamp timestamp;

    // Constructor, getters, and setters
}

class ExpenseManager {
    private Map<String, User> users;
    private Map<String, Group> groups;
    private Map<String, Expense> expenses;

    public ExpenseManager() {
        users = new HashMap<>();
        groups = new HashMap<>();
        expenses = new HashMap<>();
    }

    public void createUser(String userId, String name, String email) {
        User user = new User(userId, name, email);
        users.put(userId, user);
    }

    public void createGroup(String groupId, String name, List<String> memberIds) {
        List<User> members = new ArrayList<>();
        for (String memberId : memberIds) {
            User member = users.get(memberId);
            if (member != null) {
                members.add(member);
            }
        }
        Group group = new Group(groupId, name, members);
        groups.put(groupId, group);
    }

    public void addExpense(String expenseId, String groupId, double amount, String description, List<String> participantIds, SplitStrategy splitStrategy) {
        Group group = groups.get(groupId);
        if (group != null) {
            List<User> participants = new ArrayList<>();
            for (String participantId : participantIds) {
                User participant = users.get(participantId);
                if (participant != null) {
                    participants.add(participant);
                }
            }
            Expense expense = new Expense(expenseId, groupId, amount, description, participants);
            splitStrategy.calculateSplits(expense);
            expenses.put(expenseId, expense);
            group.addExpense(expense);
        }
    }

    public void settleBalance(String payerId, String payeeId, double amount) {
        User payer = users.get(payerId);
        User payee = users.get(payeeId);
        if (payer != null && payee != null) {
            Transaction transaction = new Transaction(generateTransactionId(), payer, payee, amount, Timestamp.now());
            payer.addTransaction(transaction);
            payee.addTransaction(transaction);
            payer.setBalance(payer.getBalance() - amount);
            payee.setBalance(payee.getBalance() + amount);
        }
    }

    // Other methods for retrieving user balances, transaction history, etc.

    private String generateTransactionId() {
        // Generate a unique transaction ID
    }
}
```

Explanation:
1. The `User` class represents a user in the system, with properties such as user ID, name, email, balance, and a list of transactions.

2. The `Group` class represents a group created by users, with properties such as group ID, name, list of members, and a list of expenses.

3. The `Expense` class represents an expense added by a user within a group, with properties such as expense ID, group ID, amount, description, and a list of splits.

4. The `SplitStrategy` interface defines the contract for different split strategies. It has a single method `calculateSplits` that takes an expense and calculates the splits among the participants based on the specific strategy.

5. The `EqualSplitStrategy`, `PercentageSplitStrategy`, and `ExactAmountSplitStrategy` classes are concrete implementations of the `SplitStrategy` interface, representing different split methods.

6. The `Split` class represents the split of an expense for a participant, with properties such as the user and the split amount.

7. The `Transaction` class represents a transaction between two users, with properties such as transaction ID, payer, payee, amount, and timestamp.

8. The `ExpenseManager` class is the main class that manages the expenses, groups, and users. It provides methods for creating users, creating groups, adding expenses, settling balances, and retrieving user balances and transaction history.

Design Patterns Used:
- Strategy Pattern: The `SplitStrategy` interface and its implementations (`EqualSplitStrategy`, `PercentageSplitStrategy`, `ExactAmountSplitStrategy`) follow the Strategy pattern. It allows for different splitting strategies to be used interchangeably based on the specific requirements of each expense.

- Repository Pattern: The `ExpenseManager` class acts as a repository, providing methods to manage and retrieve data related to users, groups, and expenses. It encapsulates the data storage and retrieval logic.

- Factory Pattern: The `ExpenseManager` class can be extended to include factory methods for creating different types of expenses or split strategies based on specific criteria.

Concurrency and Data Consistency:
- To handle concurrent transactions and ensure data consistency, proper synchronization mechanisms should be implemented.

- For example, the methods in the `ExpenseManager` class that modify shared data (e.g., creating users, creating groups, adding expenses, settling balances) should be synchronized to prevent concurrent access and maintain data integrity.

- Additionally, if the system is deployed in a distributed environment, distributed locking or transactional mechanisms should be used to ensure consistency across multiple instances of the application.

This low-level design provides a basic structure for the expense splitting system that meets the given requirements. It can be further enhanced with additional features such as user authentication, notifications, error handling, and more advanced split strategies.

The use of the Strategy pattern allows for flexibility in implementing different split methods, while the Repository pattern provides a centralized way to manage and retrieve data. The Factory pattern can be used to create expenses or split strategies based on specific criteria.

Overall, this design aims to provide a scalable and maintainable solution for the expense splitting system, taking into account the requirements of user management, group creation, expense tracking, and balance settlement.