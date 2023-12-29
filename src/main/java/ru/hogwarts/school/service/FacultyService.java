package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.*;
import java.util.stream.Stream;

@Service
public class FacultyService {
    private final FacultyRepository repository;

    Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public FacultyService(FacultyRepository repository) {
        this.repository = repository;
    }

    public Faculty creatFaculty(Faculty faculty){
        logger.debug("Вызван метод создания студента");
        return repository.save(faculty);
    }

    public Faculty findFaculty(long id){
        return repository.findById(id).get();
    }

    public void removeFaculty(long id){
        repository.deleteById(id);
    }

    public Faculty editFaculty(Faculty faculty){
        return repository.save(faculty);
    }

    public Collection<Faculty> findFacultyByColor(String color) {
        List<Faculty> facultyList = new ArrayList<>();
        for (Faculty faculty: repository.findAll()) {
            if (Objects.equals(faculty.getColor(),color)){
                facultyList.add(faculty);
            }

        }
        return facultyList;
    }

    public Collection<Faculty> findFacultyByColorOrName(String name,String color){
        return repository.findAllByColorOrNameIgnoreCase(name,color);
    }

    public List<Faculty> findFacultyByNameAndColor(String name, String color) {
        return repository.findFacultyByNameContainingIgnoreCaseAndColorContainingIgnoreCase(name,color);
    }

    public Faculty findLongestNameOfFaculty() {
        return repository.findAll().stream()
                .max(Comparator.comparing(faculty -> faculty.getName().length()))
                .orElse(null);
    }

    public int getIntegerNumber() {
        return Stream
                .iterate(1, a -> a + 1)
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b);
    }
}
