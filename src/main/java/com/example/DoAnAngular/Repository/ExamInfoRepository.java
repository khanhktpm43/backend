package com.example.DoAnAngular.Repository;

import com.example.DoAnAngular.Entities.ExamInfo;
import com.example.DoAnAngular.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExamInfoRepository extends JpaRepository<ExamInfo,Long> {
    boolean existsByUser(User user);


    Optional<ExamInfo>  findByUser(User user);


}
