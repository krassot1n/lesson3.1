package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;


@RestController
@RequestMapping("/student")
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
    public void removeStudent(@PathVariable long id) {
        service.removeStudent(id);
    }

    @PutMapping
    public Student changeStudent(@RequestBody Student student) {
        return service.editStudent(student);
    }

    @GetMapping("/byAge")
    public Collection<Student> studentsByAge(@RequestParam int minAge, @RequestParam int maxAge) {
        return service.findStudentsByAgeBetween(minAge, maxAge);
    }

    @GetMapping("/{studId}/faculty")
    public String findStudentFaculty(@PathVariable int studId) {
        return service.findStudent(studId).getFaculty().getName();
    }

    @GetMapping("/{name}")
    public ResponseEntity<Collection<Student>> getStudentByName(@PathVariable("name") String name) {
        Collection<Student> students = service.getStudentByName(name);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/count")
    public int getCountOfStudents() {
        return service.getCountOfStudent();
    }

    @GetMapping("/average_age")
    public double getAverageAgeOfStudents() {
        return service.getAverageAge();
    }

    @GetMapping("/last_five")
    public Collection<Student> findLastFiveStudents() {
        return service.findLastFiveStudents();
    }

    @GetMapping("/get_students_alphabetic_order")
    public Collection<Student> getStudentAlphabeticOrder() {
        return service.getStudentsAlphabetOrder();
    }

    @GetMapping("/student_thread")
    public void doStudentsThread() {
        service.studentsThread();
    }

    @GetMapping("/synchronized_student_thread")
    public void doSynchronizedStudentsThread() {
        service.synchronizedStudentsThread();

    }
}
