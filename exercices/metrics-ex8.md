# Exercise 8: Unit Testing for the Bank Domain

## 1. Test Strategy

I chose to test the **`Bank`** class as it contains core business logic for managing multiple accounts.

I focused on two key behaviors:
1.  **Happy Path:** Verifying that accounts are correctly loaded/counted.
2.  **Edge Case:** Verifying that `getMaximumBalance()` works correctly even when all accounts have **negative balances** (overdrafts).

## 2. Implementation

I updated `src/test/java/bankAccountApp/BankTest.java` to use **JUnit 5** and added specific test cases.

### The Tests

```java
// Happy Path
@Test
public void testGetAccountsLoaded() {
   // ... setup accounts ...
   assertEquals(4, bank.getAccountsLoaded());
}

// Edge Case (The Bug Hunter)
@Test
public void testGetMaximumBalance_WithAllNegativeAccounts() {
    // Scenario: All accounts are in overdraft.
    // Account 1: -100, Account 2: -50.
    // The maximum (closest to zero) should be -50.
    BankAccount acc1 = new BankAccount(-100, withdrawLimit, dateCreated, accountHolder);
    BankAccount acc2 = new BankAccount(-50, withdrawLimit, dateCreated, accountHolder);

    bank.addAccount(acc1, 0);
    bank.addAccount(acc2, 0);

    assertEquals(-50.0, bank.getMaximumBalance(), 0.001, "Max balance should be -50.0");
}

```

## 3. Analysis of Failures

### Issue 1: Compilation Error (Access Modifier)

Initially, `mvn test` failed to compile because the method `getAccountsLoaded()` was **`protected`**. The test class could not access it.

* **Fix:** I changed the visibility of `getAccountsLoaded()` in `Bank.java` from `protected` to **`public`**.

### Issue 2: Logic Bug (The Edge Case Failure)

When running the tests, `testGetMaximumBalance_WithAllNegativeAccounts` failed:

> `expected: <-50.0> but was: <0.0>`

**Explanation:**
The method `getMaximumBalance` initialized the tracking variable to `0.0`.

```java
double maximumBalance = 0.0; // WRONG INITIALIZATION

```

Since `-50.0` is smaller than `0.0`, the condition `if (balance > maximumBalance)` was never true, and the method incorrectly returned `0.0` instead of the actual highest negative balance.

## 4. The Fix

I refactored `Bank.java` to correctly initialize the comparison variable:

```java
public double getMaximumBalance() {
   // Fix: Initialize with the smallest possible value
   double maximumBalance = -Double.MAX_VALUE;

   for (int i = 0; i < Accounts.size(); i++) {
      BankAccount tmpAcc = Accounts.get(i);
      if (tmpAcc.getBalance() > maximumBalance) {
         maximumBalance = tmpAcc.getBalance();
      }
   }
   return maximumBalance;
}

```

## 5. Final Result

After applying these fixes, I re-ran `mvn test`:

* **Result:** `BUILD SUCCESS`
* All tests, including the edge case, are now **Green**.
