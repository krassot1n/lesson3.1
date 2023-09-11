package ru.hogwarts.school.service;

import antlr.actions.python.CodeLexer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(StudentService.class);

    public Student creatStudent(Student student){
        logger.debug("Вызван метов создания студента");
        return repository.save(student);
    }

    public Student findStudent(long id){
        logger.debug("Вызван метов поиска студента");
        return repository.findById(id).get();
    }

    public void removeStudent(long id){
        logger.debug("Вызван метов удаления студента");
        repository.deleteById(id);
    }

    public Student editStudent(Student student){
        logger.debug("Вызван метов изменения студента");
        return repository.save(student);
    }

    public Collection<Student> findStudentsByAge(int age) {
        logger.debug("Вызван метов поиска студента по возрасту");
        List<Student> studentList = new ArrayList<>();
        for (Student student: repository.findAll()) {
            if (student.getAge()==age){
                studentList.add(student);
            }

        }
        return studentList;

    }

    public Collection<Student> getStudentByName(String name){
        logger.debug("Вызван метов поиска студента по имени");
        return repository.getStudentsByName(name);
    }

    public Collection<Student> findStudentsByAgeBetween(int minAge, int maxAge){
        logger.debug("Вызван метов поиска студентов с возрастными ограничениями");
        return repository.findByAgeBetween(minAge, maxAge);
    }

    public int getCountOfStudent(){
        logger.debug("Вызван метов подсчета количества студентов");
        return repository.countStudents();
    }
    public int getAverageAge(){
        logger.debug("Вызван метов получения среднего возраста студентов");
        return repository.averageAgeOfStudents();
    }

    public Collection<Student> findLastFiveStudents(){
        logger.debug("Вызван метов получения последних пяти студентов в базе");
        return repository.findLastFiveStudents();
    }

}
