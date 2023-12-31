package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Student;

import java.awt.print.Pageable;
import java.util.Collection;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findByAgeBetween(int minAge, int maxAge);

    List<Student> getStudentsByName(String name);

    @Query(nativeQuery = true,value = "select COUNT(*) from student")
    int countStudents();

    @Query(nativeQuery = true,value = "select AVG(age) from student")
    int averageAgeOfStudents();

    @Query(nativeQuery = true,value = "select * from student offset 2")
    Collection<Student> findLastFiveStudents();

}
