# Các hạng mục đã triển khai cho Lab 08 & Lab 09–10 (chi tiết)

Tài liệu này mô tả CHI TIẾT những gì mình đã làm để đáp ứng yêu cầu của 2 lab, đồng thời giữ nguyên các ràng buộc bạn yêu cầu: build dạng WAR (demo tại chỗ), cơ sở dữ liệu MongoDB (local), và giao diện frontend theo phong cách neo‑brutalism (không thay đổi template engine).

## Lab 08

### Exercise 1
- Thêm HomeController (kiểu MVC, không trả JSON):
  - `GET /` → forward sang file tĩnh `static/index.html` (trang chủ). Mục tiêu: phù hợp yêu cầu “trang chủ là index.html”.
  - `GET /contact` → forward sang `static/contact.html` (tạo mới file này). Mục tiêu: có đường dẫn `/contact` như yêu cầu.
- Cập nhật `index.html` để có hyperlink trỏ tới `/contact`.
- Giữ nguyên đóng gói WAR theo yêu cầu demo tại chỗ; không chuyển sang JAR.
- Không thêm Thymeleaf/Lombok theo chỉ đạo (UI vẫn là static HTML/CSS phong cách neo).

File/chức năng liên quan:
- `com.example.crud.HomeController` — định tuyến `/` và `/contact`.
- `src/main/resources/static/contact.html` — trang liên hệ đơn giản.
- `src/main/resources/static/index.html` — thêm link đến `/contact` (theme neo giữ nguyên).

### Exercise 2 (Employee Management dạng MVC)
- Triển khai đầy đủ các URL và method theo đề bài, render HTML trực tiếp từ controller (không dùng Thymeleaf), lưu dữ liệu bằng MongoDB collection `employees`.
- Các endpoint:
  - `GET /employees`:
    - Trả về HTML Table liệt kê tất cả nhân viên.
    - Có link tới trang thêm và nút xoá (form POST) cho từng dòng.
  - `POST /employees`:
    - Chủ động trả về 405 Method Not Allowed đúng theo yêu cầu “POST không hỗ trợ ở /employees”.
  - `GET /employees/add`:
    - Trả về form HTML để nhập thông tin nhân viên (name, email, position).
  - `POST /employees/add`:
    - Nhận dữ liệu form, lưu nhân viên mới vào MongoDB, rồi redirect 303/SEE_OTHER về `/employees` để hiển thị kết quả.
  - `GET /employees/edit/{id}`:
    - Trả về form HTML đã được điền trước (prefill) thông tin của nhân viên để sửa.
  - `POST /employees/edit/{id}`:
    - Nhận dữ liệu form, cập nhật nhân viên, redirect về `/employees`.
  - `GET /employees/delete/{id}`:
    - Chủ động trả về 405 Method Not Allowed (đúng yêu cầu GET không hỗ trợ).
  - `POST /employees/delete/{id}`:
    - Xoá nhân viên theo id và redirect về `/employees`.
- Lưu ý: Tuân thủ đúng hành vi method theo đề, và sử dụng redirect sau khi thêm/sửa/xoá để tránh submit lại form.

File/chức năng liên quan:
- `com.example.crud.Employee` — model/Document Mongo.
- `com.example.crud.EmployeeRepository` — repository Mongo cho employees.
- `com.example.crud.EmployeesController` — controller MVC render HTML và xử lý form add/edit/delete.

## Lab 09–10

### REST API theo yêu cầu đề (Products, Orders, Account)
- Products API (`/api/products`): CRUD đầy đủ.
  - `GET /api/products` — lấy danh sách.
  - `GET /api/products/{id}` — lấy theo id.
  - `POST /api/products` — tạo mới.
  - `PUT /api/products/{id}` — cập nhật.
  - `DELETE /api/products/{id}` — xoá.
- Orders API (`/api/orders`): CRUD đầy đủ với cấu trúc tương tự Products.
  - `GET /api/orders`
  - `GET /api/orders/{id}`
  - `POST /api/orders`
  - `PUT /api/orders/{id}`
  - `DELETE /api/orders/{id}`
- Account API (`/api/account`):
  - `POST /api/account/register` — đăng ký tài khoản (demo): lưu `username/password` (plain) vào MongoDB collection `users` (mục tiêu là minh hoạ endpoint; nếu cần có thể thêm mã hoá/validation).
  - `POST /api/account/login` — đăng nhập (demo): kiểm tra `username/password` khớp với DB thì trả về `200 {"message":"ok"}`, sai trả `401`.
- Xử lý method không hỗ trợ: Spring Boot mặc định trả 405 Method Not Allowed — khớp yêu cầu “invalid endpoint/method unsupported” trong đề. Nếu cần tuỳ biến message, có thể bổ sung `@ControllerAdvice`.

File/chức năng liên quan:
- `com.example.crud.Product`, `ProductRepository`, `ProductController` (tag Swagger: Products).
- `com.example.crud.Order`, `OrderRepository`, `OrderController` (tag Swagger: Orders).
- `com.example.crud.User`, `UserRepository`, `AccountController` (tag Swagger: Account).

### Swagger / OpenAPI
- Thêm `springdoc-openapi-starter-webmvc-ui`.
- Khai báo bean OpenAPI trong `SimpleCrudWarApplication` để đặt metadata cơ bản (title/version).
- Swagger UI: `/swagger-ui.html`, OpenAPI JSON: `/v3/api-docs`.
- Thêm `@Tag` và `@Operation` cho các controller REST để hiển thị rõ ràng trong UI.

## Lưu trữ dữ liệu
- Sử dụng MongoDB local như bạn yêu cầu (không đổi sang H2/JPA).
- Các collection: `books` (cũ), `products`, `orders`, `users`, `employees`.

## Giữ nguyên các ràng buộc theo yêu cầu
- Đóng gói WAR (không chuyển thành JAR) — phục vụ demo tại chỗ.
- Frontend style neo‑brutalism giữ nguyên (UI static), không chuyển sang Thymeleaf.
- Database: MongoDB giữ nguyên.

## Cách chạy nhanh
```
cd /Users/antt/Desktop/java-labs
mvn spring-boot:run
```
- Trang Employees (MVC): http://localhost:8080/employees
- Trang chủ: http://localhost:8080/
- Swagger UI: http://localhost:8080/swagger-ui.html

## Gợi ý kiểm thử nhanh
- Employees (MVC):
  - Mở `/employees` → Add → quay lại danh sách → Edit → Delete (POST) → xác nhận danh sách cập nhật.
- REST:
  - Products/Orders: dùng Swagger để thử POST/GET/PUT/DELETE.
  - Account: thử `POST /api/account/register` rồi `POST /api/account/login`.

Nếu cần, mình có thể bổ sung seed/fixtures và ví dụ payload chi tiết ngay trong phần mô tả của Swagger để tiện chấm điểm/demonstration.
