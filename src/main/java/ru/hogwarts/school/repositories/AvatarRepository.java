package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Avatar;

import java.awt.print.Pageable;
import java.util.Collection;
import java.util.Optional;

@Repository
public interface AvatarRepository extends JpaRepository<Avatar,Long> {

    Optional<Avatar> findByStudentId(Long studentId);


}
