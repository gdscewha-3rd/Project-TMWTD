package com.todo.Task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/tmwtd")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping
    public List<SingleTask> getTasks(){
        return taskRepository.findAll();
    }

    @PostMapping("/add")
    public SingleTask addTask(@Valid @RequestBody SingleTask singleTask){
        return taskRepository.save(singleTask);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateTask(@PathVariable Long id){
        boolean exist = taskRepository.existsById(id);
        if(exist){
            SingleTask task = taskRepository.getById(id);
            boolean done = task.isDone();
            task.setDone(!done);
            taskRepository.save(task);
            return new ResponseEntity<>("목표 달성을 축하합니다!", HttpStatus.OK);
        }
        return new ResponseEntity<>("목표가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteTask(@PathVariable Long id){
        boolean exist = taskRepository.existsById(id);
        if(exist){
            taskRepository.deleteById(id);
            return new ResponseEntity<>("목표 삭제 완료! ㅠ.ㅠ", HttpStatus.OK);
        }
        return new ResponseEntity<>("존재하지 않는 목표입니다.", HttpStatus.BAD_REQUEST);
    }
}
