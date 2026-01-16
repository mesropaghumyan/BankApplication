# Exercise 7: Maven Lifecycle

## 1. Commands Analysis

### Command: `mvn clean`
* **Lifecycle Phases Executed:**
    * The **Clean Lifecycle** is executed.
    * Specific phase: `clean`.
* **Changes in `target/`:**
    * The `target/` directory is deleted.
    * **Log confirmation:** `[INFO] Deleting /Users/.../bank-application/target`

### Command: `mvn test`
* **Lifecycle Phases Executed:**
    * The **Default Lifecycle** is executed up to the `test` phase.
    * Main phases observed in logs: `resources` -> `compile` -> `testResources` -> `testCompile` -> `test`.
* **Changes in `target/`:**
    * `target/` folder is recreated.
    * `target/classes/`: Contains the compiled `.class` files (e.g., BankAccount.class).
    * `target/test-classes/`: Created (empty for now as no tests exist yet).
    * `target/surefire-reports/`: Created by the Surefire plugin to store test results.

### Command: `mvn package`
* **Lifecycle Phases Executed:**
    * All phases from `test` are executed again (if not already done).
    * **New phase:** `package` (via `maven-jar-plugin`).
* **Changes in `target/`:**
    * A new artifact is created: `target/bank-application-1.0-SNAPSHOT.jar`.
    * **Log confirmation:** `[INFO] Building jar: .../bank-application-1.0-SNAPSHOT.jar`

## 2. Hypothesis: `mvn verify`

**How is verify different from test and package?**

* **Observation:** In my specific logs, `mvn verify` produced the exact same output as `mvn package`.
* **Hypothesis & Explanation:**
    * `mvn verify` runs **after** the `package` phase in the Default Lifecycle.
    * Its purpose is to run integrity checks and **Integration Tests** (usually handled by the `maven-failsafe-plugin`).
    * **Difference:**
        * `test`: Runs unit tests using compiled classes.
        * `package`: Bundles the compiled code into a JAR/WAR.
        * `verify`: Checks the final package (JAR) validity and runs integration tests against it.
    * **Why no difference in my logs?** Since I haven't added integration tests or the Failsafe plugin to my `pom.xml` yet, Maven has no "verify" actions to perform, so it simply completes after the packaging step.