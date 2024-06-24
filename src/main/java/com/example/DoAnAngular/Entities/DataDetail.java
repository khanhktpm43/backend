package com.example.DoAnAngular.Entities;

import lombok.Data;

@Data
public class DataDetail extends ExamDetail{
    public Long answerId;
    public Long questionId;
    public Long examInfoId;
}
