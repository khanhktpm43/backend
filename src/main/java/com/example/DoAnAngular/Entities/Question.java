package com.example.DoAnAngular.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="question")
public class Question {
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long id;
    private String question;
    private String image;
    private boolean isCheck;
    @JsonBackReference("2")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id")
    private Category category;
    @JsonManagedReference("1")
    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    private List<Answer> answerList;
    @JsonManagedReference("4")
    @OneToMany(mappedBy = "question",fetch = FetchType.LAZY)
    private List<ExamDetail> examDetailList;
}
