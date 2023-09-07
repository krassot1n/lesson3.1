package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.*;

@Service
public class FacultyService {
    private final FacultyRepository repository;

    public FacultyService(FacultyRepository repository) {
        this.repository = repository;
    }

    public Faculty creatFaculty(Faculty faculty){
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
}
