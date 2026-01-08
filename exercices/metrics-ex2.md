# Exercice 1 metrics

## CK report

| Class       | LOC (approx.) | WMC / NOM | CC | Short description of responsibility                                                                           |
|-------------|---------------|-----------|----|---------------------------------------------------------------------------------------------------------------|
| BankAccount | 462           | 20        | 33 | Represents a bank account, stores account data, and handles deposits, withdrawals, and account-related logic. |

## Reflection

I choose withdrawMoney(double withdrawAmount), as it is clearly one of the most functionally complex methods with a complexity of 5.

```java
public boolean withdrawMoney(double withdrawAmount) {
        // [CC+1] Decision point: IF statement (start of condition)
        // [CC+1] Decision point: && operator (short-circuit evaluation creates a branch)
        // [CC+1] Decision point: && operator
        // [CC+1] Decision point: && operator
        // Total for this method: 1 (base) + 4 = 5
        if (withdrawAmount >= 0 && balance >= withdrawAmount && withdrawAmount < withdrawLimit
             && withdrawAmount + amountWithdrawn <= withdrawLimit) {
          balance = balance - withdrawAmount;
          success = true;
          amountWithdrawn += withdrawAmount;
       } else {
          // Note: ELSE is not a decision point, it follows the IF logic.
          success = false;
       }
       return success;
    }
```

I propose refactoring the withdrawMoney method by extracting the complex compound boolean expression inside the if statement into a private helper method. This new method would encapsulate all validation rules, such as checking for positive amounts and ensuring the balance and withdrawal limits are respected. I would name this helper method `isValidWithdrawal`. This refactoring reduces the complexity of the main method to a single readable check and separates the business rules from the transaction logic.