# Demo links theo Lab/Exercise

Giữ nguyên: WAR, MongoDB local, giao diện neo‑brutalism.

## Lab 08

### Exercise 1 (Home + Contact)
- Trang chủ: http://localhost:8080/
- Trang liên hệ: http://localhost:8080/contact

### Exercise 2 (Employees MVC)
- Danh sách nhân viên: http://localhost:8080/employees
- Thêm nhân viên (form): http://localhost:8080/employees/add
- Sửa nhân viên (form): http://localhost:8080/employees/edit/{id}
- Xoá nhân viên: gửi POST tới `/employees/delete/{id}` (nút Xoá có sẵn trên bảng danh sách)

## Lab 09–10

### UI (neo‑brutalism)
- Products UI: http://localhost:8080/products.html
- Orders UI: http://localhost:8080/orders.html
- Account UI: http://localhost:8080/account.html

### Products API
- Swagger UI (thử nhanh tất cả): http://localhost:8080/swagger-ui.html
- List: GET http://localhost:8080/api/products
- Get by id: GET http://localhost:8080/api/products/{id}
- Create: POST http://localhost:8080/api/products
- Update: PUT http://localhost:8080/api/products/{id}
- Delete: DELETE http://localhost:8080/api/products/{id}

### Orders API
- List: GET http://localhost:8080/api/orders
- Get by id: GET http://localhost:8080/api/orders/{id}
- Create: POST http://localhost:8080/api/orders
- Update: PUT http://localhost:8080/api/orders/{id}
- Delete: DELETE http://localhost:8080/api/orders/{id}

### Account API
- Register: POST http://localhost:8080/api/account/register
- Login: POST http://localhost:8080/api/account/login

Ghi chú
- Tất cả REST endpoints có thể thao tác trực tiếp qua Swagger UI: http://localhost:8080/swagger-ui.html
- Employees là trang MVC (HTML) nên truy cập qua các link ở phần Lab 08.
 - Trang chủ có các nút dẫn nhanh tới Employees, Products, Orders, Account: http://localhost:8080/
