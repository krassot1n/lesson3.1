package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.StudentNotFoundException;
import ru.hogwarts.school.model.Student;

import java.util.*;

@Service
public class StudentService {
    HashMap<Long, Student> students = new HashMap<>();
    private long idCount = 0;

    public Student creatStudent(Student student){
        student.setId(++idCount);
        students.put(student.getId(),student);
        return student;
    }

    public Student findStudent(long id){
        return students.get(id);
    }

    public Student removeStudent(long id){
        return students.remove(id);
    }

    public Student editStudent(Student student){
        if (students.containsKey(student.getId())) {
            return students.put(student.getId(),student);
        }
        throw new StudentNotFoundException("Студент с таким ID не найден");
    }

    public Collection<Student> findStudentsByAge(int age) {
        ArrayList<Student> studentList = new ArrayList<>();
        for (Student student: students.values()) {
            if (student.getAge()==age){
                studentList.add(student);
            }

        }
        return studentList;

    }
}
