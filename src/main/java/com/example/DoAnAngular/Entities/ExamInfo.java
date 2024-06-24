package com.example.DoAnAngular.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name="exam_info")
public class ExamInfo {
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long id;
    private Date date;
    @JsonBackReference("user")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;
    private Double score;
    @JsonManagedReference("5")
    @OneToMany(mappedBy = "examInfo",fetch = FetchType.LAZY)
    private List<ExamDetail> examDetailList;
}
