package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.*;

@Service
public class StudentService {
    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public Student creatStudent(Student student){
        return repository.save(student);
    }

    public Student findStudent(long id){
        return repository.findById(id).get();
    }

    public void removeStudent(long id){
        repository.deleteById(id);
    }

    public Student editStudent(Student student){
        return repository.save(student);
    }

    public Collection<Student> findStudentsByAge(int age) {
        List<Student> studentList = new ArrayList<>();
        for (Student student: repository.findAll()) {
            if (student.getAge()==age){
                studentList.add(student);
            }

        }
        return studentList;

    }

    public Collection<Student> findStudentsByAgeBetween(int minAge, int maxAge){
        return repository.findByAgeBetween(minAge, maxAge);
    }

}
