# Exercise 10: CI Workflow (GitHub Actions)

## 1. Implementation Strategy

I implemented a Continuous Integration (CI) workflow using GitHub Actions to automate the testing and packaging process of the application.

**File Location:** `.github/workflows/maven-ci.yml`

### Handling Project Structure
Since the repository contains multiple project folders (e.g., `bank-application`, `jay-bank`), I had to configure the workflow to specifically target the **`bank-application`** directory.
* **Solution:** I used the `defaults.run` configuration to set `working-directory: ./bank-application` for all command-line steps.

## 2. Workflow Steps

The workflow runs on an `ubuntu-latest` runner and performs the following sequence on every push to the `master` branch:

1.  **Checkout Code:** Retrieves the source code from the repository using `actions/checkout`.
2.  **Setup Java:** Installs **JDK 17** (Temurin distribution) to match the project's `pom.xml` configuration.
3.  **Run Tests:** Executes `mvn clean test`. This ensures that all unit tests (including the new JaCoCo coverage tests) pass before proceeding.
4.  **Package Application:** Executes `mvn package -DskipTests`.
    * *Note:* I added `-DskipTests` here because tests were already successfully executed in the previous step, saving build time.
5.  **Artifact Upload:** Uses `actions/upload-artifact` to save the content of the `bank-application/target/` folder. This allows retrieval of the compiled JAR file and reports (Surefire, JaCoCo) after the build.

## 3. Workflow Configuration (`maven-ci.yml`)

```yaml
name: Java CI with Maven

on:
  push:
    branches: [ "master" ]
  pull_request:
    branches: [ "master" ]

jobs:
  build:
    runs-on: ubuntu-latest

    # Set the working directory to the correct subfolder
    defaults:
      run:
        working-directory: ./bank-application

    steps:
    - name: Checkout code
      uses: actions/checkout@v4

    - name: Set up JDK 17
      uses: actions/setup-java@v4
      with:
        java-version: '17'
        distribution: 'temurin'
        cache: maven
        cache-dependency-path: bank-application/pom.xml

    - name: Run Tests
      run: mvn clean test

    - name: Package Application
      run: mvn package -DskipTests

    - name: Upload Build Artifact
      uses: actions/upload-artifact@v4
      with:
        name: bank-app-target-folder
        path: bank-application/target/
        retention-days: 1

```

## 4. Verification Results

* **Status:** The workflow successfully triggered on the last push and finished with a **Green (Success)** status.
* **Artifacts:** A zip file named `bank-app-target-folder` was generated and is available for download in the GitHub Actions summary tab. It contains the compiled `.jar` file and the generated reports.
