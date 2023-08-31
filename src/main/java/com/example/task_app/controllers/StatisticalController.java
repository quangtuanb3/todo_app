package com.example.task_app.controllers;


import com.example.task_app.model.Task;
import com.example.task_app.model.enumeration.TaskStatus;
import com.example.task_app.model.enumeration.TaskType;
import com.example.task_app.services.task.TaskService;
import com.example.task_app.services.task.request.TaskSaveRequest;
import com.example.task_app.services.taskHistory.TaskHistoryService;
import com.example.task_app.util.AppUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;

@Controller
@RequestMapping(value = "/statistical")
@AllArgsConstructor
public class StatisticalController {
    private final TaskService taskService;
    private final TaskHistoryService taskHistoryService;
    @GetMapping
    public ModelAndView showListTasks(@RequestParam(required = false) String message) {
        ModelAndView view = new ModelAndView("task/statistical");
        view.addObject("taskStatuses", TaskStatus.values());
        view.addObject("message", "");
        view.addObject("taskTypes", TaskType.values());
        view.addObject("dayTasks", taskHistoryService.getTaskInDay(LocalDate.now()));
        view.addObject("weeklyTasks", taskHistoryService.getTaskInWeek((LocalDate.now())));
        return view;
    }
}