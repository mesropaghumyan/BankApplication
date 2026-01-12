# Exercise 2 : Metrics

## CK report

| Class       | LOC (approx.) | WMC / NOM (before refactoring) | CC (before refactoring) | WMC / NOM (after refactoring) | CC (after refactoring) | Short description of responsibility                                                                           |
|-------------|---------------|--------------------------------|-------------------------|-------------------------------|------------------------|---------------------------------------------------------------------------------------------------------------|
| BankAccount | 462           | 20                             | 33                      |                               |                        | Represents a bank account, stores account data, and handles deposits, withdrawals, and account-related logic. |

---

# Exercise 2: Cyclomatic Complexity

## 1. Method Selection & Analysis

I chose the method **`withdrawMoney(double withdrawAmount)`**. 
According to the initial CK metrics report, this method had a Cyclomatic Complexity (CC) of **5**.

Below is the original code with decision points marked:

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

## 2. Refactoring Proposal

I propose refactoring the `withdrawMoney` method by extracting the complex compound boolean expression inside the if statement into a private helper method. This new method would encapsulate all validation rules, such as checking for positive amounts and ensuring the balance and withdrawal limits are respected. I would name this helper method `isValidWithdrawal`. This refactoring reduces the complexity of the main method to a single readable check and separates the business rules from the transaction logic.

## 3. Bonus: Implementation & Verification

I implemented the suggested refactoring by extracting the logic into `isValidWithdrawal` and simplifying the main method.

### Refactored Code

```java
public boolean withdrawMoney(double withdrawAmount) {
    success = false;

    // The complex condition is now delegated to the helper method
    if (isValidWithdrawal(withdrawAmount)) {
        balance = balance - withdrawAmount;
        success = true;
        amountWithdrawn += withdrawAmount;
    }

    return success;
}

/**
 * Helper method containing the validation logic.
 * Keeps the complexity isolated from the transaction logic.
 */
private boolean isValidWithdrawal(double withdrawAmount) {
    return withdrawAmount >= 0
            && balance >= withdrawAmount
            && withdrawAmount < withdrawLimit
            && withdrawAmount + amountWithdrawn <= withdrawLimit;
}

```

### Metrics Verification

After re-running the CK Metrics plugin on the refactored code, the complexity of `withdrawMoney` decreased significantly:

* **Before:** `withdrawMoney` CC = **5**
* **After:** `withdrawMoney` CC = **2**

*Report extract:*

> `~ public boolean withdrawMoney(double withdrawAmount): 2`

While the logic complexity was moved to the new private method `isValidWithdrawal` (which now has a CC of 5), the main public method `withdrawMoney` is now much cleaner, readable, and focused solely on executing the transaction if valid.
