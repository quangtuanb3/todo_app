package com.example.task_app.controllers;

import com.example.task_app.model.Task;
import com.example.task_app.model.enumeration.TaskStatus;
import com.example.task_app.model.enumeration.TaskType;
import com.example.task_app.services.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@AllArgsConstructor
@Controller
@RequestMapping("/")
public class HomeController {
    private final TaskService taskService;

    @GetMapping
    public ModelAndView showListTasks() {
        ModelAndView view = new ModelAndView("task/index");
        view.addObject("taskStatuses", TaskStatus.values());
        view.addObject("task", new Task());
        view.addObject("message", "");
        view.addObject("taskTypes", TaskType.values());
        view.addObject("tasks", taskService.getAll());
        return view;
    }

    @PostMapping("/create")
    public ModelAndView showCreate(@ModelAttribute Task task) {
        ModelAndView view = new ModelAndView("redirect:/");
        TaskService.getInstance().saveNonDailyTask(task);
//        view.addObject("taskStatuses", TaskStatus.values());
//        view.addObject("task", new Task());
//        view.addObject("taskTypes", TaskType.values());
//        view.addObject("tasks", taskService.getAll());
        view.addObject("message", "Created successfully");
        return view;
    }


}
