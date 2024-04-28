package com.pedrocerredelo.app.rest.Controller;

import com.pedrocerredelo.app.rest.Model.Task;
import com.pedrocerredelo.app.rest.Repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TodoController {
    @Autowired
    private TodoRepository todoRepository;


    @GetMapping(value = "/tasks")
    public List<Task> getTasks() {
        return todoRepository.findAll();
    }

    @PostMapping(value = "/savetasks")
    public String saveTask(@RequestBody Task task) {
        todoRepository.save(task);
        return "Saved Task";
    }

    @PutMapping(value = "/update/{id}")
    public String updateTask(@PathVariable long id, @RequestBody Task task){
        Task updatedTask = todoRepository.findById(id).get();
        updatedTask.setTitle(task.getTitle());
        updatedTask.setDescription(task.getDescription());
        todoRepository.save(updatedTask);
        return "Tarea actualizada";
    }

    @PutMapping(value = "/delete/{id}")
    public String deleteTask(@PathVariable long id){
        Task deletedTask = todoRepository.findById(id).get();
        todoRepository.delete(deletedTask);
        return "Tarea eliminada";
    }
}
