# Selenium Automation Framework (Java)

Automated UI tests for an e-commerce application built with **Java + Selenium** using the **Page Object Model (POM)** pattern.

---

## Tech Stack

* Java 17
* Selenium WebDriver
* JUnit 5
* Maven
* Allure Reports
* GitHub Actions (CI)

---

## Project Overview

This project demonstrates a clean and maintainable UI automation framework designed for testing core e-commerce functionalities.

Key focus areas:

* Page Object Model architecture
* Explicit waits and synchronization strategies
* Handling real-world UI issues (overlays, iframes, dynamic elements)
* CI integration with GitHub Actions

---

## Test Coverage

Implemented test scenarios include:

* Cart functionality

    * Add/remove products
    * Update quantity
    * Price recalculation

* Wishlist

    * Add product to wishlist
    * Verify product presence
    * Window handling (switching between tabs/windows)

* Search

    * Search for products
    * Validate results

* Validation scenarios

    * Invalid quantity handling
    * Coupon validation

---

## How to Run Tests

### Clone repository

```bash
git clone https://github.com/YOUR_USERNAME/YOUR_REPO.git
cd YOUR_REPO
```

### Run all tests

```bash
mvn clean test
```

### Run with Allure report

```bash
mvn clean test
allure serve allure-results
```

---

## Reports

Test results are generated using **Allure Reports**.

Includes:

* Test steps
* Screenshots on failure
* Page source on failure

---

## Continuous Integration

Tests are automatically executed on every push using GitHub Actions.

* Runs on Linux environment
* Builds project with Maven
* Executes full test suite

---

## Key Design Decisions

### Explicit waits over implicit waits

Improves stability and control over synchronization.

### Locator strategy

* Use stable and unique attributes whenever possible
* Avoid brittle selectors based on layout or dynamic values
* Narrow locators using page context (e.g. specific sections or containers)

### Handling UI instability

Real-world issues handled in the framework:

* Overlay blocking elements
* Stripe iframe interfering with clicks

Solution:

* Waiting for UI state instead of only element visibility
* Dedicated component for iframe handling

---

## Project Structure

* **components/** — reusable UI components (e.g. Stripe widget handling)
* **core/** — base classes and driver setup
* **extensions/** — custom utilities and extensions
* **helpers/** — utility classes (waits, configuration, etc.)
* **pages/** — Page Object classes
* **tests/** — test scenarios

---

## Goal

The goal of this project is to present a realistic, production-like automation framework that demonstrates:

* clean code practices
* maintainable architecture
* ability to debug and stabilize flaky tests

---

## Author

GitHub: [https://github.com/YOUR_USERNAME](https://github.com/YOUR_USERNAME)
