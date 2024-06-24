package com.example.DoAnAngular.Repository;

import com.example.DoAnAngular.Entities.ExamDetail;
import com.example.DoAnAngular.Entities.ExamInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamDetailRepository extends JpaRepository<ExamDetail,Long> {
    public List<ExamDetail> findByExamInfo(ExamInfo examInfo);

}
