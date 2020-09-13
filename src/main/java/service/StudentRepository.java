package main.java.service;

import main.java.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface StudentRepository extends  JpaRepository<Student, Long>{


    Optional<Student> findByNameAndSurname(String n,String sn);

    List<Student> findAllByNameLikeAndSurnameLike(String name, String sname);

}
