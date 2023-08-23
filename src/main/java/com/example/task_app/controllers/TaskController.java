package com.example.task_app.controllers;


import com.example.task_app.model.Task;
import com.example.task_app.model.enumeration.TaskStatus;
import com.example.task_app.model.enumeration.TaskType;
import com.example.task_app.services.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/tasks")
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;
    @GetMapping
    public ModelAndView showListTasks() {
        ModelAndView view = new ModelAndView("task/daily");
        view.addObject("tasks", taskService.findAll());
        return view;
    }
    @GetMapping("/create")
    public ModelAndView showCreate(){
        ModelAndView view = new ModelAndView("task/create");
        view.addObject("task", new Task());
        view.addObject("taskTypes", TaskType.values());
        view.addObject("taskStatuses", TaskStatus.values());
        return view;
    }
    @GetMapping("/edit/{id}")
    public ModelAndView showEdit(@PathVariable Long id){
        Task task = TaskService.getInstance().findById(id);
        ModelAndView view = new ModelAndView("task/create");
        view.addObject("task", task);
        view.addObject("taskTypes", TaskType.values());
        view.addObject("taskStatuses", TaskStatus.values());
        return view;
    }
    @PostMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable Long id, @ModelAttribute Task task){
        task.setId(id);
        TaskService.getInstance().edit(task);
        ModelAndView view = new ModelAndView("task/daily");
        view.addObject("message", "Edited successfully");
        view.addObject("tasks", taskService.findAll());
        view.addObject("taskTypes", TaskType.values());
        view.addObject("taskStatuses", TaskStatus.values());
        return view;
    }

    @PostMapping("/create")
    public ModelAndView showCreate(@ModelAttribute Task task){
        ModelAndView view = new ModelAndView("task/create");
        TaskService.getInstance().save(task);
        view.addObject("message", "Created successfully");
        view.addObject("task", new Task());
        view.addObject("taskTypes", TaskType.values());
        view.addObject("taskStatuses", TaskStatus.values());
        return view;
    }
    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable Long id){
        TaskService.getInstance().findById(id);
        TaskService.getInstance().deleteById(id);
        ModelAndView view = new ModelAndView("task/daily");
        view.addObject("message", "Delete successfully");
        view.addObject("tasks", taskService.findAll());
        return view;
    }

}