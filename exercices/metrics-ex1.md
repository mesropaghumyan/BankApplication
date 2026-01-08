# Exercice 1 metrics

## CK report

| Class          | LOC (approx.) | NOM | Short description of responsibility                                                                           |
|----------------|---------------|-----|---------------------------------------------------------------------------------------------------------------|
| Bank           | 413           | 14  | Manages bank accounts, including registration, deletion, transfers, and balance-related operations.           |
| BankAccount    | 462           | 20  | Represents a bank account, stores account data, and handles deposits, withdrawals, and account-related logic. |
| Person         | 325           | 23  | Represents a person with personal information and validation logic (name, age, gender, contact details).      |
| BankAccountApp | 491           | 2   | Application entry point responsible for starting the program and executing the main workflow.                 |

## Reflection

> ⇒ Do you feel its size roughly matches its responsibility?

The analysis based on NOM (Number of Methods) and LCO (Lines of Code) reveals several design imbalances.

`Bank` (LCO = 413, NOM = 14) contains a moderate number of methods but a large amount of code. This indicates that several methods are long and contain substantial logic, such as account management, balance computation, file persistence, and interaction with external services. The relatively low NOM compared to LCO suggests that responsibilities are implemented through large methods rather than being decomposed into smaller ones.

`BankAccount` (LCO = 462, NOM = 20) combines both a high number of methods and a very large code size. The class includes account state management, business rules (deposit and withdrawal), file parsing, and text serialization. The combination of high NOM and high LCO indicates a class that is both functionally dense and large, making it harder to understand and evolve.

`Person` (LCO = 325, NOM = 23) has the highest number of methods among the classes. Most of these methods are getters, setters, constructors, and validation logic. While each method is relatively small, the high NOM indicates a wide interface and many responsibilities related to data access and validation, which increases the overall complexity of the class.

`BankAccountApp` (LCO = 491, NOM = 2) shows the strongest imbalance between size and number of methods. Almost all the logic is concentrated in the `main` method, which handles user interaction, account creation, account operations, and program flow control. A very high LCO combined with a very low NOM indicates excessively long methods, which negatively impacts readability and maintainability.

Overall, the NOM and LCO values show that several classes are larger and more complex than expected for their responsibilities. High LCO values indicate long methods or dense logic, while high NOM values indicate broad class interfaces, both of which can reduce maintainability.
