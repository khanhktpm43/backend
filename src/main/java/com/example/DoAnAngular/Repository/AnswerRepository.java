package com.example.DoAnAngular.Repository;

import com.example.DoAnAngular.Entities.Answer;
import com.example.DoAnAngular.Entities.ExamDetail;
import com.example.DoAnAngular.Entities.ExamInfo;
import com.example.DoAnAngular.Entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer,Long> {
     List<Answer> findByQuestion(Question question);
}
