# Exercise 3: CK Metrics Across Classes

## Data Table

| Class       | LOC | WMC | CBO | LCOM | Quick notes                                                                 |
|-------------|-----|-----|-----|------|-----------------------------------------------------------------------------|
| Bank        | 412 | 14  | 4   | 0    | Highest coupling (CBO), but seems very cohesive (LCOM 0).                   |
| BankAccount | 477 | 21  | 3   | 46   | Highest Lines of Code. High lack of cohesion. Handles I/O and Logic.        |
| Person      | 324 | 23  | 3   | 79   | Highest WMC and extremly high LCOM. Looks like a "God Class" for data.      |

## Analysis

### Which class has the highest WMC?
**Person** has the highest WMC (**23**), followed closely by `BankAccount` (21).

### Which class has the highest CBO?
**Bank** has the highest CBO (**4**). It interacts with `BankAccount` and likely other structures to manage the collection of accounts.

### Looking at WMC + CBO + LCOM together: Which one class would you worry about most for future maintenance, and why?

I would worry most about the **Person** class.

**Why?**
1.  **Extreme Lack of Cohesion (LCOM = 79):** This is significantly higher than any other class. It suggests that the class fields and methods are not working together closely. It likely acts as a "bloated" data container with too many independent attributes (Weight, Height, EyeColor, HairColor, etc.) that don't relate to each other.
2.  **Highest WMC (23):** Despite being a data class, it has the highest complexity weight. Looking at the logs, methods like `validateGender` and `setHairColor` surprisingly have a complexity of 5 each, which indicates unnecessary over-engineering for simple property setters.
3.  **Refactoring candidate:** It violates the Single Responsibility Principle by trying to manage too many disparate physical attributes of a person that might not be relevant to a banking context.