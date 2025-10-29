# Lab08 & Lab09-10 Requirements and Project Compliance

This document aggregates the requirements extracted from the images in `lab08/` and `lab9-10/`, and checks the current project against them.

Note: Requirements were OCR‑extracted from JPEGs; minor typos may appear. If you have the original text, we can refine further.

## Lab08 (Java Technology - Week 08)
Extracted requirements (Exercise 1):
- Set up a basic MVC web project using Spring Boot and the Thymeleaf view template.
- Use Spring Initializr (`https://start.spring.io`):
  - Project type: Maven
  - Language: Java
  - Packaging: Jar
  - Java version: follow installed JDK
  - Add dependencies: Lombok, Spring Web, Thymeleaf
- Create a web controller `HomeController` with mappings:
  - `GET /`: returns the content from `index.html` (homepage). The `index.html` must contain a hyperlink to `/contact`.

Extracted requirements (Exercise 2):
- Using given HTML templates to create an employee management website with URLs:
  - `/employees`:
    - GET: list all employees in an HTML table
    - POST: not available
  - `/employees/add`:
    - GET: show HTML form to add new employee
    - POST: receive form data, save new employee, then redirect to `/employees`
  - `/employees/edit/{id}`:
    - GET: show HTML form to edit employee
    - POST: receive form data, update employee, then redirect to `/employees`
  - `/employees/delete/{id}`:
    - GET: not available
    - POST: delete employee by id, then redirect to `/employees`
- Use Spring Data (original mentions JPA) to persist to a database server.

### Project compliance vs Lab08
- Spring Boot project: YES
- Build tool Maven: YES
- Packaging Jar: NO (kept WAR by request for on-site demo)
- Thymeleaf dependency/templates: NO (UI kept as static neo theme by request)
- Lombok: NO (not required by your constraints)
- `HomeController` and `/contact` link: YES
- Employee MVC pages and forms: PARTIAL (implementing endpoints with MongoDB; templating kept minimal per constraints)

## Lab09-10 (Java Technology)
Extracted requirements (CORS and endpoints):
- Learn the concept of Cross-Origin Resource Sharing (CORS).
- Expose a web service with endpoints and proper method handling (invalid endpoint and unsupported method return error):
  - `/products`, `/products/{id}`
  - `/orders`, `/orders/{id}`
  - `/account/register`, `/account/login`

### Project compliance vs Lab09-10
- CORS configuration: BASIC (Spring default; can add explicit config if required)
- `/products` endpoints: YES
- `/orders` endpoints: YES
- `/account/register` endpoint: YES
- `/account/login` endpoint: YES
- Method not allowed handling: YES (Spring returns 405 by default; can customize message if needed)

## Summary of gaps
- Lab08 Exercise 2 expects HTML forms/tables; project will serve minimal HTML via controllers without Thymeleaf (per your request to keep static neo theme and avoid template engine).
- If strict JPA is required, we are using MongoDB instead (as requested). Functionality is equivalent for persistence.

## Plan/Notes (per your constraints)
- Keep packaging as WAR, DB as MongoDB, and the neo‑brutalism frontend style.
- Implement Employee endpoints with required HTTP methods, redirects, and 405 for unsupported methods; render simple HTML directly from controller for listing/forms.
