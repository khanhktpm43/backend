package com.example.DoAnAngular.Repository;

import com.example.DoAnAngular.Entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Long> {
    @Query("SELECT a FROM Question a WHERE a.isCheck = false")
    public List<Question> findByCheck();
}
