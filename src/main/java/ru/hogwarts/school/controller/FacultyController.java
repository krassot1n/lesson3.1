package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService service;

    public FacultyController(FacultyService service) {
        this.service = service;
    }
    @GetMapping("{id}")
    public ResponseEntity<Faculty> findFaculty(@PathVariable long id){
        Faculty faculty = service.findFaculty(id);
        if (faculty == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(faculty);
    }
    @PostMapping
    public Faculty addFaculty(@RequestBody Faculty faculty){
        return service.creatFaculty(faculty);
    }
    @DeleteMapping("{id}")
    public void removeFaculty(@PathVariable long id){
        service.removeFaculty(id);
    }
    @PutMapping
    public Faculty changeFaculty(@RequestBody Faculty faculty){
        return service.editFaculty(faculty);
    }
    @GetMapping("/findByColor")
    public Collection<Faculty> facultyByColorOrName(@RequestParam(required = false) String name,@RequestParam(required = false) String color){
        return service.findFacultyByColorOrName(name, color);
    }
    @GetMapping("{facId}/students")
    public Collection<Student> studentsByFaculty(@PathVariable long facId){
        return service.findFaculty(facId).getStudents();
    }
}
