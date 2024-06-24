package com.example.DoAnAngular.Entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="category")
public class Category {
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long id;
    private String name;
    private Integer number;
    @JsonManagedReference("2")
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Question> questionList;
}
