package com.example.DoAnAngular.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="answer")
public class Answer {
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long id;
    private String answer;
    private boolean isCheck;
    @JsonBackReference("1")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="question_id")
    private Question question;
//    @JsonManagedReference("3")
//    @OneToMany(mappedBy = "answer",fetch = FetchType.LAZY)
//    private List<ExamDetail> examDetailList;
}
