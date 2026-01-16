# Exercise 11: Cucumber (BDD)

## 1. Setup

I added the `cucumber-java`, `cucumber-junit`, and `junit-vintage-engine` dependencies to `pom.xml`.
This allows running Cucumber scenarios using the JUnit 4 runner (`@RunWith`) alongside the existing JUnit 5 tests.

## 2. The Feature File (`bank_account_basic.feature`)

I defined a simple behavior for a new account:

```gherkin
Feature: Basic bank account behavior
  Scenario: A new account has zero balance
    Given I have a new bank account
    When I check its balance
    Then the balance should be 0

```

## 3. The Implementation (`BankAccountBasicSteps.java`)

I mapped the Gherkin steps to Java code using the existing `BankAccount` class.

* **Given:** Instantiates `new BankAccount()`.
* **When:** Calls `account.getBalance()`.
* **Then:** Uses `assertEquals` to verify the balance is 0.

## 4. Execution Results

Ran command: `mvn test`

**Output snippet:**

```text
Feature: Basic bank account behavior
  Scenario: A new account has zero balance
    Given I have a new bank account
    When I check its balance
    Then the balance should be 0

```

The scenario **passed**, confirming that the BDD framework is correctly integrated and the `BankAccount` behaves as expected for a new instance.
