package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest
public class ApplicationTests_WebMvcTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    StudentRepository studentRepository;
    @MockBean
    AvatarRepository avatarRepository;
    @MockBean
    FacultyRepository facultyRepository;

    @SpyBean
    StudentService studentService;
    @SpyBean
    AvatarService avatarService;
    @SpyBean
    FacultyService facultyService;
    @InjectMocks
    private StudentController studentController;
    @InjectMocks
    private FacultyController facultyController;

    @Test
    public void postAndPutStudentTest() throws Exception {
        final Long id = 1L;
        final String name = "Andrey";
        final int age = 26;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        Student student = new Student();
        student.setName(name);
        student.setId(id);
        student.setAge(age);

        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders.
                        post("/student").
                        content(jsonObject.toString()).
                        contentType(MediaType.APPLICATION_JSON).
                        accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));


        mockMvc.perform(MockMvcRequestBuilders.
                        put("/student").
                        content(jsonObject.toString()).
                        contentType(MediaType.APPLICATION_JSON).
                        accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));


    }

    @Test
    void postAndPutFacultyTest() throws Exception {
        Long id = 1L;
        String name = "Грифендор";
        String color = "красный";

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("id", id);
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }


    @Test
    public void deleteStudentTest() throws Exception {
        final Long id = 1L;
        final String name = "Andrey";
        final int age = 26;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        Student student = new Student();
        student.setName(name);
        student.setId(id);
        student.setAge(age);

        mockMvc.perform(MockMvcRequestBuilders.delete("/student/1").
                        content(jsonObject.toString()).
                        contentType(MediaType.APPLICATION_JSON).
                        accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk());
    }

    @Test
    void deleteFacultyTest() throws Exception {
        Long id = 1L;
        String name = "Грифендор";
        String color = "красный";

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("id", id);
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/1")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void findStudentTest() throws Exception {
        final Long id = 1L;
        final String name = "Andrey";
        final int age = 26;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("name", name);
        jsonObject.put("age", age);

        Student student = new Student();
        student.setName(name);
        student.setId(id);
        student.setAge(age);

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders.get("/student/1").
                        content(jsonObject.toString()).
                        contentType(MediaType.APPLICATION_JSON).
                        accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    void findFacultyTest() throws Exception {
        Long id = 1L;
        String name = "Грифендор";
        String color = "красный";

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("id", id);
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    void findAllByAgeBetweenTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", "");
        facultyObject.put("age", "");

        Student student = new Student();
        student.setName("Гена");
        student.setAge(8);
        student.setId(1L);
        Student student2 = new Student();
        student2.setName("Петя");
        student2.setAge(16);
        student2.setId(2L);
        List<Student> list = new ArrayList<>();
        list.add(student);
        list.add(student2);


        when(studentRepository.findByAgeBetween(any(Integer.class),any(Integer.class))).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/byAge/?minAge=2&maxAge=15")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(student, student2))));
    }
}
