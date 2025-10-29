Spring Boot MVC + REST (MongoDB, JWT) — Neo‑Brutalism UI

How to run

1) Prerequisites
- Java 17+
- MongoDB running locally on default port 27017
- Maven installed (or build in your IDE)

2) Configure (optional)
- application.properties already points to mongodb://localhost:27017/spring_demo
- Change JWT secret by editing `app.jwt.secret` if needed

3) Start
- Development: `mvn spring-boot:run`
- Or build and run: `mvn -DskipTests package && java -jar target/simple-crud-war.war`

Features

- MVC (Thymeleaf):
  - `/` home, `/contact` GET/POST, `/about` GET returns text
  - Custom error pages: 404, 405 (no Whitelabel)
  - Neo‑brutalism styling in `static/css/neo.css`

- Employees (MongoDB + Thymeleaf):
  - `/employees` list
  - `/employees/add` GET/POST
  - `/employees/edit/{id}` GET/POST
  - `/employees/delete/{id}` POST

- REST API (MongoDB + JWT + BCrypt + CORS):
  - `POST /api/account/register`
  - `POST /api/account/login` -> returns `{ token }`
  - `GET /api/products`, `POST /api/products`
  - `GET /api/products/{id}`, `PUT|PATCH|DELETE /api/products/{id}`
  - `GET /api/orders`, `POST /api/orders`
  - `GET /api/orders/{id}`, `PUT|DELETE /api/orders/{id}`
  - Auth required for write methods; GET is public

Testing REST quickly

1) Register:
```
POST http://localhost:8080/api/account/register
{"email":"user@example.com","password":"pass","firstName":"A","lastName":"B"}
```
2) Login -> token:
```
POST http://localhost:8080/api/account/login
{"email":"user@example.com","password":"pass"}
```
3) Use Authorization: `Bearer <token>` for POST/PUT/PATCH/DELETE on `/api/products` and `/api/orders`.

Notes

- H2/JPA dependencies are kept optional to not break existing sample; app stores real data in MongoDB.
