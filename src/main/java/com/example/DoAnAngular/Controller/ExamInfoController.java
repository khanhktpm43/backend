package com.example.DoAnAngular.Controller;

import com.example.DoAnAngular.Entities.ExamInfo;
import com.example.DoAnAngular.Entities.User;
import com.example.DoAnAngular.Repository.ExamInfoRepository;
import com.example.DoAnAngular.RequestResponse.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/exam-info")
public class ExamInfoController {
    @Autowired
    ExamInfoRepository repository;
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/")
    public ResponseEntity<ResponseObject> getAll(){
        List<ExamInfo> list = repository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","",list));
    }
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getById(@PathVariable Long id){
        if(repository.existsById(id)){
            Optional<ExamInfo> object = repository.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","",object.get()));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("fail","",null));
    }
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/")
    public ResponseEntity<ResponseObject> create(@RequestBody ExamInfo examInfo){
        examInfo.setId(null);
        repository.save(examInfo);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","",examInfo));
    }
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/check")
    public ResponseEntity<ResponseObject> create(@RequestBody User user){
        Optional<ExamInfo> info = repository.findByUser(user);
if(info.isPresent()){
    return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","",info.get()));
}

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(user.getId().toString(),"",null));
    }
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> update(@PathVariable Long id, @RequestBody ExamInfo examInfo){
        if(repository.existsById(id)){
            Optional<ExamInfo> object = repository.findById(id);
            examInfo.setId(id);
            examInfo.setExamDetailList(object.get().getExamDetailList());
            examInfo.setDate(object.get().getDate());

            repository.save(examInfo);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","",examInfo));
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
