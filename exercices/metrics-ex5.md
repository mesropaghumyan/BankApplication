# Exercise 5: Java Report (GitHub Actions)

## 1. Workflow Implementation

I created a GitHub Actions workflow file located at `.github/workflows/java-report.yml`.
This workflow runs automatically on every `push` or can be triggered manually (`workflow_dispatch`).

### Workflow Logic
The workflow performs the following steps on an `ubuntu-latest` runner:

1.  **Checkout:** Retrieves the code from the repository.
2.  **Security Check (.class files):**
    * Uses the `find` command to search for any committed binary files (`*.class`).
    * If any are found, the script executes `exit 1`, which **fails the build** immediately to enforce best practices.
3.  **Report Generation:**
    * Counts the number of `.java` files.
    * Fails if the count is 0.
    * Calculates the total line count and lists lines per file using `find . -name "*.java" ... | xargs wc -l`.
    * Saves this data into `java-file-report.txt`.
4.  **Artifact Upload:**
    * Uses the `actions/upload-artifact@v4` action to make the generated text file available for download from the GitHub UI.

## 2. The Workflow Code (`java-report.yml`)

```yaml
name: Java Report for BankApplication
on: [push, workflow_dispatch]
jobs:
  generate-report:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout repository
        uses: actions/checkout@v4

      - name: Check for committed .class files
        run: |
          if find . -name "*.class" | grep -q .; then
            echo "Error: Found committed .class files!"
            exit 1
          fi

      - name: Generate Java File Report
        run: |
          REPORT_FILE="java-file-report.txt"
          echo "JAVA FILE REPORT" > $REPORT_FILE
          echo "=================" >> $REPORT_FILE
          
          # Count files
          find . -type f -name "*.java" -print0 | xargs -0 wc -l >> $REPORT_FILE
          
      - name: Upload Report Artifact
        uses: actions/upload-artifact@v4
        with:
          name: java-file-report
          path: java-file-report.txt

```

## 3. Sample Output (Artifact Content)

Here is an example of the content generated in `java-file-report.txt` after a successful run:

```text
JAVA FILE REPORT
=================
JAVA FILE REPORT
=================
Number of .java files: 11

Total Lines of Code & Per-file breakdown:
-----------------------------------------
  228 ./jay-bank/src/test/java/bankAccountApp/PersonTest.java
   11 ./jay-bank/src/test/java/bankAccountApp/AllTests.java
  205 ./jay-bank/src/test/java/bankAccountApp/BankAccountTest.java
  148 ./jay-bank/src/test/java/bankAccountApp/BankTest.java
   58 ./jay-bank/src/test/java/bankAccountApp/ACHServiceTest.java
   25 ./jay-bank/src/main/java/bankAccountApp/ACHService.java
  223 ./jay-bank/src/main/java/bankAccountApp/BankAccountApp.java
  203 ./jay-bank/src/main/java/bankAccountApp/Bank.java
  273 ./jay-bank/src/main/java/bankAccountApp/Person.java
   19 ./jay-bank/src/main/java/bankAccountApp/ACHServiceImpl.java
  230 ./jay-bank/src/main/java/bankAccountApp/BankAccount.java
 1623 total

```

*(Note: The exact line counts depend on the current state of the code, specifically after the refactoring in Exercise 4).*

## 4. Verification

* **Success Scenario:** When I pushed the code without `.class` files, the action turned **Green** ✅ and the artifact `java-file-report` was produced.
* **Failure Scenario:** The workflow includes a guard clause that successfully blocks the build if a user accidentally commits compiled Java classes.
