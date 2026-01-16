# Exercise 9: Increase Coverage with JaCoCo

## 1. Initial Analysis

I generated the initial JaCoCo report using `mvn clean test jacoco:report`.
I identified the **`Bank`** class as having several uncovered methods.

**Target Method:** `getAverageBalance()`
* **Initial Coverage:** 0% (Shown in Red in the report).
* **Why:** No existing test was calling this method.

## 2. The Test

I added the following unit test to `BankTest.java` to execute the logic of calculating the average balance:

```java
@Test
public void testGetAverageBalance() {
   // Given: Two accounts with balances 100.0 and 200.0
   BankAccount acc1 = new BankAccount(100.0, withdrawLimit, dateCreated, accountHolder);
   BankAccount acc2 = new BankAccount(200.0, withdrawLimit, dateCreated, accountHolder);
   
   bank.addAccount(acc1, 0);
   bank.addAccount(acc2, 0);

   // When: We calculate the average
   double average = bank.getAverageBalance();

   // Then: The result should be 150.0
   assertEquals(150.0, average, 0.001);
}

```

## 3. Results

After adding the test and re-running `mvn clean test jacoco:report`:

* **Old Coverage for `getAverageBalance`:** 0%
* **New Coverage for `getAverageBalance`:** 100% (Green)
* **Global Class Coverage:** Increased significantly as a key logic method is now covered.
 