package com.example.DoAnAngular.Controller;

import com.example.DoAnAngular.Entities.Category;
import com.example.DoAnAngular.Entities.Question;
import com.example.DoAnAngular.Repository.CategoryRepository;
import com.example.DoAnAngular.RequestResponse.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RestController
@RequestMapping("/api/category")
@CrossOrigin(origins = "*")
public class CategoryController {
    @Autowired
    CategoryRepository repository;
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/")
    public ResponseEntity<ResponseObject> getAll(){
        List<Category> list = repository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","",list));
    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/exam")
    public ResponseEntity<ResponseObject> getQuestion(){
        List<Question> list = new ArrayList<>();
        List<Category> categoryList = repository.findAll();
        for (Category item: categoryList) {

            List<Question> questionList = item.getQuestionList();
            questionList.removeIf(object -> object.isCheck() == true);

            Collections.shuffle(questionList);
//            for(Question question: questionList){
//                Collections.shuffle(question.getAnswerList());
//            }
            list.addAll(questionList.subList(0,item.getNumber()));
        }
        Collections.shuffle(list);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","",list));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getById(@PathVariable Long id){
        if(repository.existsById(id)){
            Optional<Category> object = repository.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","",object.get()));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("fail","",null));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/")
    public ResponseEntity<ResponseObject> create(@RequestBody Category category){
        category.setId(null);
        repository.save(category);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","",category));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> update(@PathVariable Long id, @RequestBody Category category){
        if(repository.existsById(id)){
            Optional<Category> object = repository.findById(id);
            category.setId(id);
            category.setQuestionList(object.get().getQuestionList());
            repository.save(category);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","",category));
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
