package com.example.DoAnAngular.Controller;

import com.example.DoAnAngular.Entities.*;
import com.example.DoAnAngular.Repository.AnswerRepository;
import com.example.DoAnAngular.Repository.ExamDetailRepository;
import com.example.DoAnAngular.Repository.ExamInfoRepository;
import com.example.DoAnAngular.Repository.QuestionRepository;
import com.example.DoAnAngular.RequestResponse.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/question")
public class QuestionController {
    @Autowired
    QuestionRepository repository;
    @Autowired
    ExamDetailRepository examDetailRepository;
    @Autowired
    ExamInfoRepository examInfoRepository;
    @Autowired
    AnswerRepository answerRepository;
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/")
    public ResponseEntity<ResponseObject> getAll(){
        List<Question> list = repository.findByCheck();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","",list));
    }
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getById(@PathVariable Long id){
        if(repository.existsById(id)){
            Optional<Question> object = repository.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","",object.get()));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("fail","",null));
    }
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/info/{id}")
    public ResponseEntity<ResponseObject> getByExamInfo(@PathVariable Long id){
        List<Question> result = new ArrayList<>();
        if(examInfoRepository.existsById(id)){
            Optional<ExamInfo> examInfo = examInfoRepository.findById(id);
            List<ExamDetail> list = examDetailRepository.findByExamInfo(examInfo.get());
            for(ExamDetail item: list){

                if(repository.existsById(item.getQuestion().getId())){
                    Question question = new Question();
                    Optional<Question> object = repository.findById(item.getQuestion().getId());
                    question.setId(object.get().getId());
                    question.setQuestion(object.get().getQuestion());
                    question.setImage(object.get().getImage());
                    List<Answer> list1 = new ArrayList<>();

                    for (Answer answer : answerRepository.findByQuestion(object.get())){
                        Answer answer1 = new Answer();
                        answer1.setId(answer.getId());
                        answer1.setAnswer(answer.getAnswer());
                        answer1.setCheck(answer1.isCheck());
                        list1.add(answer1);
                    }
                    question.setAnswerList(list1);

                    question.setCheck(object.get().isCheck());
                    result.add(question);
                }

            }

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","",result));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("fail","",null));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/")
    public ResponseEntity<ResponseObject> create(@RequestBody Question question){
        question.setId(null);
        repository.save(question);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","",question));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> update(@PathVariable Long id, @RequestBody Question question){
        if(repository.existsById(id)){
            Optional<Question> object = repository.findById(id);
            question.setId(id);
            question.setCategory(object.get().getCategory());
            question.setExamDetailList(object.get().getExamDetailList());
            question.setAnswerList(object.get().getAnswerList());

                repository.save(question);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","",question));
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
