package com.example.DoAnAngular.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="exam_detail")
public class ExamDetail {
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    protected Long id;
//    @JsonBackReference("3")
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="answer_id")
    @Column(name = "answer_id")
    protected Long answerID;
    @JsonBackReference("4")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="question_id")
    protected Question question;
    @JsonBackReference("5")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="exam_info_id")
    protected ExamInfo examInfo;
}
