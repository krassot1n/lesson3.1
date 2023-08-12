package ru.hogwarts.school.controller;



import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTests_RestTemplate {
    @LocalServerPort
    private int port;

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    FacultyRepository facultyRepository;
    @Autowired
    private StudentController studentController;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void contextLoads() {
        assertNotNull(studentController);
        assertNotNull(facultyController);
    }

    @Test
    void testPostStudent() {
        Student student = new Student();
        student.setId(1L);
        student.setName("Красотин Андрей");
        student.setAge(26);
        Assertions.assertNotNull(this.testRestTemplate.postForObject(
                "http://localhost:" + port + "/student", student, String.class));
    }
    @Test
    void testPostFaculty() {

        Faculty faculty = new Faculty();
        faculty.setColor("red");
        faculty.setName("Ravenclow");
        faculty.setId(1L);
        Assertions.assertNotNull(this.testRestTemplate.postForObject(
                "http://localhost:" + port + "/faculty", faculty, String.class));
    }


    @Test
    void findStudent() {
        Assertions.assertNotNull(this.testRestTemplate.getForObject(
                "http://localhost:" + port + "/student/1", String.class));
    }


    @Test
    void findFaculty() {
        Assertions.assertNotNull(this.testRestTemplate.getForObject(
                "http://localhost:" + port + "/faculty/1", String.class));
    }

    @Test
    public void testEditStudent() {

        Student student = new Student();
        student.setId(1L);
        student.setName("Красотин Андрей");
        student.setAge(26);


        this.testRestTemplate.put("http://localhost:" + port + "/student", student);


        Optional<Student> optionalStudent = studentRepository.findById(1L);


        assertTrue(((Optional<?>) optionalStudent).isPresent());


        Student actualStudent = optionalStudent.get();
        assertEquals(student, actualStudent);
    }

    @Test
    public void testChangeFaculty() {
        Faculty faculty = new Faculty();
        faculty.setColor("red");
        faculty.setName("Ravenclow");
        faculty.setId(1L);

        ResponseEntity<Faculty> response = facultyController.changeFaculty(faculty);

        int actualStatusCodeValue = response.getStatusCodeValue();
        int expectedCode = 200;

        assertEquals(expectedCode, actualStatusCodeValue, "коды не совпадают");
    }


    @Test
    void deleteStudentTest() {

        Student lastStudent = studentRepository.findById(1L).orElse(null);
        Long lastStudentId = (lastStudent == null) ? null : lastStudent.getId();


        this.testRestTemplate.delete("http://localhost:" + port + "/student/" + 1L);


        assertNotEquals(1L, lastStudentId);
    }

    @Test
    void deleteFacultyTest() {
        Faculty lastFaculty = facultyRepository.findById(1L).orElse(null);
        Long lastFacultyId = (lastFaculty == null) ? null : lastFaculty.getId();

        this.testRestTemplate.delete("http://localhost:" + port + "/faculty/" + 1L);

        assertNotEquals(1L, lastFacultyId);
    }

    @Test
    void getStudentsAccordingAge() {

        Student student = new Student();
        student.setId(1L);
        student.setName("Красотин Андрей");
        student.setAge(26);
        studentRepository.save(student);
        long studentId = 1L;


        try {
            Assertions.assertNotNull(this.testRestTemplate.getForObject(
                    "http://localhost:" + port + "/student/filter_by_age/" + student.getAge(), String.class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            studentRepository.deleteById(studentId);
        }
    }


    @Test
    void getFacultyAccordingColor() {

        Faculty faculty = new Faculty();
        faculty.setColor("red");
        faculty.setName("Ravenclow");
        faculty.setId(1L);
        facultyRepository.save(faculty);
        long facultyId = 1L;

        try {
            Assertions.assertNotNull(this.testRestTemplate.getForObject(
                    "http://localhost:" + port + "/faculty/filter_by_color/?color=" + faculty.getColor(), String.class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            facultyRepository.deleteById(facultyId);
        }
    }

    @Test
    void findStudentByAgeBetween() {
        int studentsAgeMin = 25;
        int studentsAgeMax = 55;

        Student student2 = new Student();
        student2.setId(1L);
        student2.setName("Красотин Андрей");
        student2.setAge(studentsAgeMin);
        studentRepository.save(student2);
        long studentIdMin = 1L;

        Student student1 = new Student();
        student1.setId(1L);
        student1.setName("Красотин Андрей");
        student1.setAge(studentsAgeMax);
        studentRepository.save(student1);
        long studentIdMax = 2L;

        try {
            Assertions.assertNotNull(this.testRestTemplate.getForObject(
                    "http://localhost:" + port + "/student/find_age_between/?minAge=" + studentsAgeMin +
                            "&maxAge=" + studentsAgeMax, String.class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("studentIdMin  studentIdMax = " + studentIdMin + " " + studentIdMax);
            studentRepository.deleteById(1L);
            studentRepository.deleteById(2L);
        }
    }

    @Test
    void findStudentByFaculty() {
        int studentsAge = 25;
        Student student1 = new Student();
        student1.setId(1L);
        student1.setName("Красотин Андрей");
        student1.setAge(studentsAge);
        studentRepository.save(student1);

        try {
            Assertions.assertNotNull(this.testRestTemplate.
                    getForObject(
                            "http://localhost:" + port + "/student/find_student_by_faculty", String.class, student1));
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            studentRepository.deleteById(1L);
        }
    }

    @Test
    void findFacultyByStudent() {
        Faculty faculty = new Faculty();
        faculty.setColor("red");
        faculty.setName("Ravenclow");
        faculty.setId(1L);
        facultyRepository.save(faculty);

        try {
            Assertions.assertNotNull(this.testRestTemplate.
                    getForObject(
                            "http://localhost:" + port + "/student/1/faculty", String.class, faculty));
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            facultyRepository.deleteById(1L);
        }
    }
}
