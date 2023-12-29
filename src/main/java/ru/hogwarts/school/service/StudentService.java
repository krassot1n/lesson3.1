package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }
    private int counter = 0;

    Logger logger = LoggerFactory.getLogger(StudentService.class);

    public Student creatStudent(Student student) {
        logger.debug("Вызван метов создания студента");
        return repository.save(student);
    }

    public Student findStudent(long id) {
        logger.debug("Вызван метов поиска студента");
        return repository.findById(id).get();
    }

    public void removeStudent(long id) {
        logger.debug("Вызван метов удаления студента");
        repository.deleteById(id);
    }

    public Student editStudent(Student student) {
        logger.debug("Вызван метов изменения студента");
        return repository.save(student);
    }

    public Collection<Student> findStudentsByAge(int age) {
        logger.debug("Вызван метов поиска студента по возрасту");
        List<Student> studentList = new ArrayList<>();
        for (Student student : repository.findAll()) {
            if (student.getAge() == age) {
                studentList.add(student);
            }

        }
        return studentList;

    }

    public Collection<Student> getStudentByName(String name) {
        logger.debug("Вызван метов поиска студента по имени");
        return repository.getStudentsByName(name);
    }

    public Collection<Student> findStudentsByAgeBetween(int minAge, int maxAge) {
        logger.debug("Вызван метов поиска студентов с возрастными ограничениями");
        return repository.findByAgeBetween(minAge, maxAge);
    }

    public int getCountOfStudent() {
        logger.debug("Вызван метов подсчета количества студентов");
        return repository.countStudents();
    }

    public double getAverageAge() {
        logger.debug("Вызван метов получения среднего возраста студентов");
        return repository.findAll().stream()
                .mapToDouble(Student::getAge)
                .average()
                .orElse(0d);
    }

    public Collection<Student> findLastFiveStudents() {
        logger.debug("Вызван метов получения последних пяти студентов в базе");
        return repository.findLastFiveStudents();
    }

    public List<Student> getStudentsAlphabetOrder() {
        return repository.findAll().stream().
                filter(student -> student.getName().startsWith("А")).
                sorted(Comparator.comparing(Student::getName)).
                collect(Collectors.toList());
    }


    public void studentsThread() {
        List<Student> studentList = repository.findAll();
        logger.info("Name of the 0 student: " + studentList.get(0).getName());
        logger.info("Name of the 1st student: " + studentList.get(1).getName());

        Thread thread1 = new Thread(() -> {
            logger.info("Name of the 2nd student: " + studentList.get(2).getName());
            logger.info("Name of the 3rd student: " + studentList.get(3).getName());
        });
        thread1.start();

        Thread thread2 = new Thread(() -> {
            logger.info("Name of the 4th student: " + studentList.get(4).getName());
            logger.info("Name of the 5th student: " + studentList.get(5).getName());
        });
        thread2.start();
    }

    public List<Student> getAll(){
        return repository.findAll();
    }

    public void synchronizedStudentsThread() {
        List<Student> students = repository.findAll();

        Thread thread1 = new Thread(() -> {
            synchronized (this) {
                for (int i = 0; i < students.size() / 2; i++) {
                    Student student = getNextStudent(students);
                    printStudent(student);
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (this) {
                for (int i = 0; i < students.size() / 2; i++) {
                    Student student = getNextStudent(students);
                    printStudent(student);
                }
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private synchronized Student getNextStudent(List<Student> list) {
        if (counter >= list.size()) {
            return null;
        }
        Student nextStudent = list.get(counter);
        counter = (counter + 1) % list.size();
        return nextStudent;
    }

    private synchronized void printStudent(Student student) {
        logger.info("Name of the " + student.getId() + " student: " +  student.getName());
    }
}


