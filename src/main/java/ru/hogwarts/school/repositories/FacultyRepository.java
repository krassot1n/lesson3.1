package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.List;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Collection<Faculty> findAllByColorOrNameIgnoreCase(String name, String color);

    List<Faculty> findFacultyByNameContainingIgnoreCaseAndColorContainingIgnoreCase(String name, String color);


}
