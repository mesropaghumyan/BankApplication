# Exercise 4: SonarQube Analysis & Fixes

## 1. Selected Issues

I have analyzed `BankAccountApp.java` and identified the following important issues:

**1. Cognitive Complexity is too high**
* **Rule:** Refactor this method to reduce its Cognitive Complexity from 125 to the 15 allowed.
* **Location:** `BankAccountApp.java:20` (Method `main`)
* **Explanation:** The `main` method is massive and contains deeply nested structures (loops inside loops inside if-statements). A complexity of 125 is extreme (8x the limit), making the code impossible to test and very hard to read or maintain without breaking it.

**2. Duplicate String Literals (Magic Strings)**
* **Rule:** Define a constant instead of duplicating this literal "DEPOSIT" 3 times.
* **Location:** `BankAccountApp.java:96` (and also lines 101, 92 for "WITHDRAW", "BALANCE")
* **Explanation:** The strings "DEPOSIT", "WITHDRAW", and "BALANCE" are hardcoded multiple times throughout the code. If we need to change a command name later, we would have to modify it in multiple places, increasing the risk of typos and bugs.

**3. Commented-out Code**
* **Rule:** This block of commented-out lines of code should be removed.
* **Location:** `BankAccountApp.java:22` (and line 28)
* **Explanation:** Keeping dead code (like `// Person accountHolder = null;`) clutters the source file and decreases readability. Version control (Git) is responsible for keeping history, not the source code itself.

## 2. Fixes Implemented

I have applied the following fixes to `BankAccountApp.java`:
1.  **Fixed Duplicate Strings:** Extracted "DEPOSIT", "WITHDRAW", "BALANCE", etc., into `private static final String` constants.
2.  **Fixed Commented-out Code:** Removed all unused commented lines (lines 22, 23, 27, 28).

### Code Refactoring (Snippet)

```java
public class BankAccountApp {

    // FIX: Define constants to avoid Magic Strings
    private static final String OP_DEPOSIT = "DEPOSIT";
    private static final String OP_WITHDRAW = "WITHDRAW";
    private static final String OP_BALANCE = "BALANCE";
    private static final String OP_QUIT = "QUIT";
    private static final String MSG_NO_ACCOUNT = "Account dosen't exist";

    public static void main(String[] args) {
       // FIX: Removed commented-out lines (Person accountHolder...)
       
       String operation = "";
       // ... rest of variables ...

       // Example of usage with constants:
       // if (operation.equalsIgnoreCase(OP_DEPOSIT)) { ... }
    }
}

```

## 3. Analysis

**Do SonarLint issues appear more often in the classes with higher WMC / CBO you saw earlier?**

**Yes, definitely.** The class `BankAccountApp` has a very high WMC (due to the giant `main` method) and SonarQube flagged it with critical structural issues (Cognitive Complexity of 125!). Similarly, complex classes often accumulate "Code Smells" like duplicated logic or massive nesting because they try to do too much (violating Single Responsibility Principle), whereas smaller, cohesive classes tend to have fewer and simpler Sonar issues.
