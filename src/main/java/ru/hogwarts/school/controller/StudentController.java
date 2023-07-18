package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> findStudent(@PathVariable long id) {
        Student student = service.findStudent(id);
        if (student == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(student);
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return service.creatStudent(student);
    }

    @DeleteMapping("{id}")
    public Student removeStudent(@PathVariable long id) {
        return service.removeStudent(id);
    }

    @PutMapping
    public Student changeStudent(@RequestBody Student student) {
        return service.editStudent(student);
    }
    @GetMapping("age")
    public Collection<Student> studentsByAge(@PathVariable int age) {
        if (age > 0) {
            return service.findStudentsByAge(age);
        }
        return Collections.emptyList();
    }
}
