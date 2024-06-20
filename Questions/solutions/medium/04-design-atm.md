# Designing an ATM System

## Requirements
1. The ATM system should support basic operations such as balance inquiry, cash withdrawal, and cash deposit.
2. Users should be able to authenticate themselves using a card and a PIN (Personal Identification Number).
3. The system should interact with a bank's backend system to validate user accounts and perform transactions.
4. The ATM should have a cash dispenser to dispense cash to users.
5. The system should handle concurrent access and ensure data consistency.
6. The ATM should have a user-friendly interface for users to interact with.


Certainly! Here's an example design and implementation of an ATM system in Java based on the given requirements, along with explanations of the code and design patterns used:

```java
interface ATM {
    void authenticateUser(String cardNumber, String pin);
    double checkBalance();
    void withdrawCash(double amount);
    void depositCash(double amount);
}

class ATMImpl implements ATM {
    private BankAPI bankAPI;
    private CashDispenser cashDispenser;
    private String authenticatedUser;

    public ATMImpl(BankAPI bankAPI, CashDispenser cashDispenser) {
        this.bankAPI = bankAPI;
        this.cashDispenser = cashDispenser;
    }

    @Override
    public synchronized void authenticateUser(String cardNumber, String pin) {
        if (bankAPI.validateUser(cardNumber, pin)) {
            authenticatedUser = cardNumber;
        } else {
            throw new IllegalArgumentException("Invalid card number or PIN.");
        }
    }

    @Override
    public synchronized double checkBalance() {
        if (authenticatedUser != null) {
            return bankAPI.getBalance(authenticatedUser);
        } else {
            throw new IllegalStateException("User not authenticated.");
        }
    }

    @Override
    public synchronized void withdrawCash(double amount) {
        if (authenticatedUser != null) {
            if (bankAPI.withdrawAmount(authenticatedUser, amount)) {
                cashDispenser.dispenseCash(amount);
            } else {
                throw new IllegalStateException("Insufficient funds.");
            }
        } else {
            throw new IllegalStateException("User not authenticated.");
        }
    }

    @Override
    public synchronized void depositCash(double amount) {
        if (authenticatedUser != null) {
            bankAPI.depositAmount(authenticatedUser, amount);
        } else {
            throw new IllegalStateException("User not authenticated.");
        }
    }
}

interface BankAPI {
    boolean validateUser(String cardNumber, String pin);
    double getBalance(String cardNumber);
    boolean withdrawAmount(String cardNumber, double amount);
    void depositAmount(String cardNumber, double amount);
}

class BankAPIImpl implements BankAPI {
    // Implementation of BankAPI methods
    // Communicates with the bank's backend system
}

interface CashDispenser {
    void dispenseCash(double amount);
}

class CashDispenserImpl implements CashDispenser {
    // Implementation of CashDispenser methods
    // Handles the physical dispensing of cash
}

class ATMUserInterface {
    private ATM atm;

    public ATMUserInterface(ATM atm) {
        this.atm = atm;
    }

    public void displayWelcomeScreen() {
        // Display welcome screen and prompt for card insertion
    }

    public void displayAuthenticationScreen() {
        // Display authentication screen and prompt for PIN input
    }

    public void displayMainMenu() {
        // Display main menu options (balance inquiry, withdrawal, deposit)
    }

    public void displayBalanceScreen(double balance) {
        // Display balance information
    }

    public void displayWithdrawalScreen() {
        // Display withdrawal screen and prompt for amount
    }

    public void displayDepositScreen() {
        // Display deposit screen and prompt for amount
    }

    // Other UI methods
}
```

Explanation:
1. The `ATM` interface defines the contract for the ATM system, specifying methods for user authentication, balance inquiry, cash withdrawal, and cash deposit.

2. The `ATMImpl` class implements the `ATM` interface and provides the actual implementation of the ATM system. It has dependencies on the `BankAPI` and `CashDispenser` interfaces.

3. The `authenticateUser` method in `ATMImpl` authenticates the user by validating the card number and PIN through the `BankAPI`. If the authentication is successful, it sets the `authenticatedUser` field.

4. The `checkBalance`, `withdrawCash`, and `depositCash` methods in `ATMImpl` perform the respective operations by interacting with the `BankAPI` and `CashDispenser`. They ensure that the user is authenticated before executing the operations.

5. The `BankAPI` interface defines the contract for communicating with the bank's backend system. It specifies methods for user validation, balance retrieval, withdrawal, and deposit.

6. The `BankAPIImpl` class (not shown in the code) implements the `BankAPI` interface and handles the actual communication with the bank's backend system.

7. The `CashDispenser` interface defines the contract for dispensing cash. It specifies a method for dispensing a specified amount of cash.

8. The `CashDispenserImpl` class (not shown in the code) implements the `CashDispenser` interface and handles the physical dispensing of cash.

9. The `ATMUserInterface` class represents the user interface of the ATM system. It has methods for displaying different screens and prompts to the user, such as the welcome screen, authentication screen, main menu, balance screen, withdrawal screen, and deposit screen.

Design Patterns Used:
- Dependency Injection: The `ATMImpl` class receives its dependencies (`BankAPI` and `CashDispenser`) through constructor injection. This allows for loose coupling and easier testing.

- Facade Pattern: The `ATM` interface acts as a facade, providing a simplified and unified interface for interacting with the ATM system. It hides the complexity of the underlying implementation and provides a clean API for clients to use.

- Model-View-Controller (MVC) Pattern: The code follows the principles of the MVC pattern. The `ATMImpl` class represents the model, encapsulating the business logic and data. The `ATMUserInterface` class represents the view, handling the presentation and user interaction. The controller aspect is not explicitly shown in the code but would handle the coordination between the model and the view.

The code ensures data consistency and handles concurrent access by using the `synchronized` keyword on the methods in `ATMImpl`. This prevents multiple threads from accessing and modifying the shared data simultaneously, avoiding conflicts and maintaining data integrity.