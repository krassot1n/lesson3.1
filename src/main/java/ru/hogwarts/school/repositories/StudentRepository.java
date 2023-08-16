package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Student;

import java.awt.print.Pageable;
import java.util.Collection;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    public Collection<Student> findByAgeBetween(int minAge, int maxAge);

    @Query(nativeQuery = true,value = "select COUNT(*) from student")
    int countStudents();

    @Query(nativeQuery = true,value = "select AVG(age) from student")
    int averageAgeOfStudents();

    @Query(nativeQuery = true,value = "select * from student offset 2")
    Collection<Student> findLastFiveStudents();

}
