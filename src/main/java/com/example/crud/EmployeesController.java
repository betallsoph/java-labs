package com.example.crud;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
public class EmployeesController {

    private final EmployeeRepository employeeRepository;

    public EmployeesController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping(value = "/employees", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> listEmployees() {
        List<Employee> all = employeeRepository.findAll();
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html><html lang='vi'><head><meta charset='utf-8'><meta name='viewport' content='width=device-width, initial-scale=1.0'><title>Employees · Neo</title>"
                + "<style>"
                + ":root{--bg:#f6f7f9;--ink:#111827;--primary:#93c5fd;--primary-ink:#0f172a;--accent:#ffd803;--danger:#ff6b6b;--card:#fff;--shadow:6px 6px 0 #111827;--radius:10px;--border:3px solid #111827}"
                + "*{box-sizing:border-box}body{font-family:ui-sans-serif,system-ui,-apple-system,Segoe UI,Roboto,sans-serif;margin:40px;color:var(--ink);background:var(--bg);background-image:linear-gradient(90deg,rgba(0,0,0,.04) 1px,transparent 1px),linear-gradient(0deg,rgba(0,0,0,.04) 1px,transparent 1px);background-size:24px 24px}"
                + ".wrap{max-width:980px}"
                + ".title{display:inline-block;padding:10px 16px;background:var(--accent);border:var(--border);border-radius:var(--radius);box-shadow:var(--shadow);margin:0 0 20px 0}"
                + ".panel{background:var(--card);border:var(--border);border-radius:var(--radius);box-shadow:var(--shadow);padding:18px;margin-bottom:20px}"
                + ".row{display:flex;gap:10px;flex-wrap:wrap}"
                + "input{padding:12px 14px;border:var(--border);border-radius:var(--radius);box-shadow:var(--shadow);outline:none;background:#fff;min-width:220px}"
                + "button,a.btn{padding:12px 16px;border:var(--border);border-radius:var(--radius);box-shadow:var(--shadow);cursor:pointer;background:var(--primary);color:var(--primary-ink);text-decoration:none;display:inline-block}"
                + "button:active,a.btn:active{transform:translate(2px,2px);box-shadow:4px 4px 0 #111827}"
                + ".btn-accent{background:var(--accent);color:#111827} .btn-danger{background:var(--danger)}"
                + "table{width:100%;border-collapse:separate;border-spacing:0}thead th{background:#e5e7eb;text-align:left;padding:12px;border-top:var(--border);border-bottom:var(--border);border-left:var(--border)}thead th:last-child{border-right:var(--border)}tbody td{padding:12px;border-left:var(--border);border-bottom:var(--border);background:#fff}tbody tr td:last-child{border-right:var(--border)}"
                + ".actions form{display:inline} .muted{color:#6b7280} .space{height:8px}"
                + "</style><script>(function(){try{if(!localStorage.getItem('token')) location.replace('/');}catch(e){}})();</script></head><body>");
        html.append("<div class='wrap'>");
        html.append("<h1 class='title'>Danh sách nhân viên</h1>");
        html.append("<p class='muted'>Trang MVC theo yêu cầu Lab 08 (Exercise 2) · <a class='btn btn-accent' href='/home'>Trang chủ</a> · <button class='btn btn-danger' onclick=\"localStorage.removeItem('token');location.replace('/');\">Logout</button></p>");
        html.append("<div class='panel'><div class='row'><a class='btn btn-accent' href='/employees/add'>Thêm nhân viên</a></div></div>");
        html.append("<div class='panel'><table><thead><tr><th>ID</th><th>Tên</th><th>Email</th><th>Vị trí</th><th>Hành động</th></tr></thead><tbody>");
        for (Employee e : all) {
            html.append("<tr>")
                .append("<td>").append(e.getId()).append("</td>")
                .append("<td>").append(e.getName()).append("</td>")
                .append("<td>").append(e.getEmail()).append("</td>")
                .append("<td>").append(e.getPosition()).append("</td>")
                .append("<td class='actions'>")
                .append("<a class='btn btn-accent' href='/employees/edit/").append(e.getId()).append("'>Sửa</a> ")
                .append("<form method='post' action='/employees/delete/").append(e.getId()).append("'>")
                .append("<button class='btn btn-danger' type='submit'>Xoá</button></form>")
                .append("</td>")
                .append("</tr>");
        }
        html.append("</tbody></table></div></div><script>function logout(){localStorage.removeItem('token');location.replace('/');}</script></body></html>");
        return ResponseEntity.ok(html.toString());
    }

    @PostMapping("/employees")
    public ResponseEntity<String> employeesPostNotAvailable() {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("POST /employees is not available");
    }

    @GetMapping(value = "/employees/add", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> addForm() {
        String html = "<!DOCTYPE html><html lang='vi'><head><meta charset='utf-8'><meta name='viewport' content='width=device-width, initial-scale=1.0'><title>Thêm nhân viên · Neo</title>" +
                "<style>:root{--bg:#f6f7f9;--ink:#111827;--primary:#93c5fd;--primary-ink:#0f172a;--accent:#ffd803;--danger:#ff6b6b;--card:#fff;--shadow:6px 6px 0 #111827;--radius:10px;--border:3px solid #111827}*{box-sizing:border-box}body{font-family:ui-sans-serif,system-ui,-apple-system,Segoe UI,Roboto,sans-serif;margin:40px;color:var(--ink);background:var(--bg);background-image:linear-gradient(90deg,rgba(0,0,0,.04) 1px,transparent 1px),linear-gradient(0deg,rgba(0,0,0,.04) 1px,transparent 1px);background-size:24px 24px}.wrap{max-width:720px}.title{display:inline-block;padding:10px 16px;background:var(--accent);border:var(--border);border-radius:var(--radius);box-shadow:var(--shadow);margin:0 0 20px 0}.panel{background:var(--card);border:var(--border);border-radius:var(--radius);box-shadow:var(--shadow);padding:18px;margin-bottom:20px}.row{display:flex;gap:10px;flex-wrap:wrap}input{padding:12px 14px;border:var(--border);border-radius:var(--radius);box-shadow:var(--shadow);outline:none;background:#fff;min-width:240px;width:100%}button,a.btn{padding:12px 16px;border:var(--border);border-radius:var(--radius);box-shadow:var(--shadow);cursor:pointer;background:var(--primary);color:var(--primary-ink);text-decoration:none;display:inline-block}.btn-accent{background:var(--accent);color:#111827}</style></head><body>" +
                "<div class='wrap'><h1 class='title'>Thêm nhân viên</h1>" +
                "<div class='panel'><form method='post' action='/employees/add'>" +
                "<div class='row'><input name='name' placeholder='Họ tên' required></div>" +
                "<div class='row'><input name='email' placeholder='Email' required></div>" +
                "<div class='row'><input name='position' placeholder='Vị trí' required></div>" +
                "<div class='row'><button class='btn-accent' type='submit'>Lưu</button><a class='btn' style='margin-left:10px' href='/employees'>Huỷ</a></div>" +
                "</form></div></div><script>function logout(){localStorage.removeItem('token');location.replace('/');}</script></body></html>";
        return ResponseEntity.ok(html);
    }

    @PostMapping("/employees/add")
    public ResponseEntity<Void> addSubmit(@RequestParam String name, @RequestParam String email, @RequestParam String position) {
        Employee e = new Employee();
        e.setName(name);
        e.setEmail(email);
        e.setPosition(position);
        employeeRepository.save(e);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/employees"));
        return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
    }

    @GetMapping(value = "/employees/edit/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> editForm(@PathVariable String id) {
        return employeeRepository.findById(id)
                .map(e -> {
                    String html = "<!DOCTYPE html><html lang='vi'><head><meta charset='utf-8'><meta name='viewport' content='width=device-width, initial-scale=1.0'><title>Sửa nhân viên · Neo</title>" +
                            "<style>:root{--bg:#f6f7f9;--ink:#111827;--primary:#93c5fd;--primary-ink:#0f172a;--accent:#ffd803;--danger:#ff6b6b;--card:#fff;--shadow:6px 6px 0 #111827;--radius:10px;--border:3px solid #111827}*{box-sizing:border-box}body{font-family:ui-sans-serif,system-ui,-apple-system,Segoe UI,Roboto,sans-serif;margin:40px;color:var(--ink);background:var(--bg);background-image:linear-gradient(90deg,rgba(0,0,0,.04) 1px,transparent 1px),linear-gradient(0deg,rgba(0,0,0,.04) 1px,transparent 1px);background-size:24px 24px}.wrap{max-width:720px}.title{display:inline-block;padding:10px 16px;background:var(--accent);border:var(--border);border-radius:var(--radius);box-shadow:var(--shadow);margin:0 0 20px 0}.panel{background:var(--card);border:var(--border);border-radius:var(--radius);box-shadow:var(--shadow);padding:18px;margin-bottom:20px}.row{display:flex;gap:10px;flex-wrap:wrap}input{padding:12px 14px;border:var(--border);border-radius:var(--radius);box-shadow:var(--shadow);outline:none;background:#fff;min-width:240px;width:100%}button,a.btn{padding:12px 16px;border:var(--border);border-radius:var(--radius);box-shadow:var(--shadow);cursor:pointer;background:var(--primary);color:var(--primary-ink);text-decoration:none;display:inline-block}.btn-accent{background:var(--accent);color:#111827}</style></head><body>" +
                            "<div class='wrap'><h1 class='title'>Sửa nhân viên</h1>" +
                            "<div class='panel'><form method='post' action='/employees/edit/" + e.getId() + "'>" +
                            "<div class='row'><input name='name' value='" + e.getName() + "' placeholder='Họ tên' required></div>" +
                            "<div class='row'><input name='email' value='" + e.getEmail() + "' placeholder='Email' required></div>" +
                            "<div class='row'><input name='position' value='" + e.getPosition() + "' placeholder='Vị trí' required></div>" +
                            "<div class='row'><button class='btn-accent' type='submit'>Cập nhật</button><a class='btn' style='margin-left:10px' href='/employees'>Huỷ</a></div>" +
                            "</form></div></div><script>function logout(){localStorage.removeItem('token');location.replace('/');}</script></body></html>";
                    return ResponseEntity.ok(html);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found"));
    }

    @PostMapping("/employees/edit/{id}")
    public ResponseEntity<?> editSubmit(@PathVariable String id, @RequestParam String name, @RequestParam String email, @RequestParam String position) {
        return employeeRepository.findById(id)
                .map(e -> {
                    e.setName(name);
                    e.setEmail(email);
                    e.setPosition(position);
                    employeeRepository.save(e);
                    HttpHeaders headers = new HttpHeaders();
                    headers.setLocation(URI.create("/employees"));
                    return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found"));
    }

    @GetMapping("/employees/delete/{id}")
    public ResponseEntity<String> deleteGetNotAvailable(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("GET /employees/delete/{id} is not available");
    }

    @PostMapping("/employees/delete/{id}")
    public ResponseEntity<Void> deleteSubmit(@PathVariable String id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/employees"));
        return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
    }
}


