package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService service;

    public FacultyController(FacultyService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> findFaculty(@PathVariable long id) {
        Faculty faculty = service.findFaculty(id);
        if (faculty == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PostMapping
    public Faculty addFaculty(@RequestBody Faculty faculty) {
        return service.creatFaculty(faculty);
    }

    @DeleteMapping("{id}")
    public void removeFaculty(@PathVariable long id) {
        service.removeFaculty(id);
    }

    @PutMapping
    public ResponseEntity<Faculty> changeFaculty(@RequestBody Faculty faculty) {
        Faculty editiedFaculty = service.editFaculty(faculty);
        if (editiedFaculty == null) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);

    }

    @GetMapping("/findByColor")
    public Collection<Faculty> facultyByColorOrName(@RequestParam(required = false) String name, @RequestParam(required = false) String color) {
        return service.findFacultyByColorOrName(name, color);
    }

    @GetMapping("{facId}/students")
    public Collection<Student> studentsByFaculty(@PathVariable long facId) {
        return service.findFaculty(facId).getStudents();
    }

    @GetMapping("/find_by_name_color")
    public ResponseEntity<List<Faculty>> findFacultyByNameAndColor(@RequestParam String name,
                                                                   @RequestParam String color) {
        List<Faculty> faculties = service.findFacultyByNameAndColor(name, color);
        return ResponseEntity.ok(faculties);
    }
}
