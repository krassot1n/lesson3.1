package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;

import java.util.*;

@Service
public class FacultyService {
    HashMap<Long, Faculty> facultyHashMap = new HashMap<>();
    private long idCount = 0;

    public Faculty creatFaculty(Faculty faculty){
        faculty.setId(++idCount);
        facultyHashMap.put(faculty.getId(),faculty);
        return faculty;
    }

    public Faculty findFaculty(long id){
        return facultyHashMap.get(id);
    }

    public Faculty removeFaculty(long id){
        return facultyHashMap.remove(id);
    }

    public Faculty editFaculty(Faculty faculty){
        if (facultyHashMap.containsKey(faculty.getId())) {
            return facultyHashMap.put(faculty.getId(), faculty);
        }
        throw new FacultyNotFoundException("Факультет с таким ID не найден");
    }

    public Collection<Faculty> findFacultyByColor(String color) {
        ArrayList<Faculty> facultyList = new ArrayList<>();
        for (Faculty faculty: facultyHashMap.values()) {
            if (Objects.equals(faculty.getColor(),color)){
                facultyList.add(faculty);
            }

        }
        return facultyList;
    }
}
