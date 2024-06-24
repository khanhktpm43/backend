package com.example.DoAnAngular.Controller;


import com.example.DoAnAngular.Dto.detailDTO;
import com.example.DoAnAngular.Entities.DataDetail;
import com.example.DoAnAngular.Entities.ExamDetail;
import com.example.DoAnAngular.Entities.ExamInfo;
import com.example.DoAnAngular.Repository.ExamDetailRepository;
import com.example.DoAnAngular.Repository.ExamInfoRepository;
import com.example.DoAnAngular.RequestResponse.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/exam-detail")
public class ExamDetailController {
    @Autowired
    ExamDetailRepository repository;
    @Autowired
    ExamInfoRepository infoRepository;
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/")
    public ResponseEntity<ResponseObject> getAll(){
        List<ExamDetail> list = repository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","",list));
    }
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/exam-info/{id}")
    public ResponseEntity<ResponseObject> getByExamInfo(@PathVariable Long id){
        Optional<ExamInfo> info = infoRepository.findById(id);

        List<detailDTO> result = new ArrayList<>();
        if(info.isPresent()){
            List<ExamDetail> list = repository.findByExamInfo(info.get());
            for (ExamDetail item : list) {
                detailDTO dto = new detailDTO();
                dto.id = item.getId();
                dto.infoID = item.getExamInfo().getId();
                dto.answerID = item.getAnswerID();
                dto.questionID = item.getQuestion().getId();
                result.add(dto);
            }
        }


        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","",result));
    }
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getById(@PathVariable Long id){
        if(repository.existsById(id)){
            Optional<ExamDetail> object = repository.findById(id);
            DataDetail detail = new DataDetail();
            detail.setId(object.get().getId());
            detail.setExamInfoId(object.get().getExamInfo().getId());
            detail.setAnswerId(object.get().getAnswerID());
            detail.setQuestionId(object.get().getQuestion().getId());
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","",detail));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("fail","",null));
    }
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/")
    public ResponseEntity<ResponseObject> create(@RequestBody ExamDetail examDetail){
        examDetail.setId(null);
        repository.save(examDetail);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","",examDetail));
    }
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> update(@PathVariable Long id, @RequestBody ExamDetail examDetail){
        if(repository.existsById(id)){
            Optional<ExamDetail> object = repository.findById(id);
            examDetail.setId(id);


            repository.save(examDetail);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","",examDetail));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("fail","",null));
    }
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable Long id){
        if(repository.existsById(id)){
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","",null));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("fail","",null));
    }
}
