package com.example.DoAnAngular.Controller;

import com.example.DoAnAngular.Entities.Answer;
import com.example.DoAnAngular.Entities.ExamDetail;
import com.example.DoAnAngular.Entities.ExamInfo;
import com.example.DoAnAngular.Entities.Question;
import com.example.DoAnAngular.Repository.AnswerRepository;
import com.example.DoAnAngular.Repository.ExamDetailRepository;
import com.example.DoAnAngular.Repository.ExamInfoRepository;
import com.example.DoAnAngular.Repository.QuestionRepository;
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
@RequestMapping("/api/answer")
public class AnswerController {
    @Autowired
    AnswerRepository repository;
    @Autowired
    ExamDetailRepository examDetailRepository;
    @Autowired
    ExamInfoRepository examInfoRepository;
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/")
    public ResponseEntity<ResponseObject> getAll(){
        List<Answer> list = repository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","",list));
    }
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getById(@PathVariable Long id){
        if(repository.existsById(id)){
            Optional<Answer> object = repository.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","",object.get()));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("fail","",null));
    }
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/info/{id}")
    public ResponseEntity<ResponseObject> getByExamInfo(@PathVariable Long id){
        List<Answer> result = new ArrayList<>();
        if(examInfoRepository.existsById(id)){
            Optional<ExamInfo> examInfo = examInfoRepository.findById(id);
            List<ExamDetail> list = examDetailRepository.findByExamInfo(examInfo.get());
            for(ExamDetail item: list){

                if(repository.existsById(item.getAnswerID())){
                    Answer answer = new Answer();
                    Optional<Answer> object = repository.findById(item.getAnswerID());
                    answer.setId(object.get().getId());
                    answer.setAnswer(object.get().getAnswer());
                    answer.setCheck(object.get().isCheck());
                    result.add(answer);
                }

            }

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","",result));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("fail","",null));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/")
    public ResponseEntity<ResponseObject> create(@RequestBody Answer answer){
        answer.setId(null);
        repository.save(answer);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","",answer));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> update(@PathVariable Long id, @RequestBody Answer answer){
        if(repository.existsById(id)){
            Optional<Answer> object = repository.findById(id);
            answer.setId(id);


            repository.save(answer);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","",answer));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("fail","",null));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable Long id){
        if(repository.existsById(id)){
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","",null));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("fail","",null));
    }
}
