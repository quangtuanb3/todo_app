package com.example.task_app.controllers;

import com.example.task_app.model.Task;
import com.example.task_app.model.TaskHistory;
import com.example.task_app.model.enumeration.Gender;
import com.example.task_app.model.enumeration.TaskStatus;
import com.example.task_app.model.enumeration.TaskType;
import com.example.task_app.services.taskHistory.TaskHistoryService;
import com.example.task_app.services.taskHistory.request.TaskHistorySaveRequest;
import com.example.task_app.services.user.request.UserEditRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;

@AllArgsConstructor
@Controller
@RequestMapping("/")
public class HomeController {
    private final TaskHistoryService taskHistoryService;

    @GetMapping
    public ModelAndView showListTasks() {
        ModelAndView view = new ModelAndView("task/index");
        view.addObject("taskStatuses", TaskStatus.values());
        view.addObject("task", new Task());
        view.addObject("message", "");
        view.addObject("taskTypes", TaskType.values());
        view.addObject("tasks", taskHistoryService.getTaskInDay(LocalDate.now()));
        return view;
    }

    @PostMapping("/create")
    public ModelAndView showCreate(@ModelAttribute TaskHistorySaveRequest taskHistory) {
        taskHistoryService.saveNonDailyTask(taskHistory);
        return new ModelAndView("redirect:/?message=Created successfully");
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable Long id) {
        taskHistoryService.findById(id);
        taskHistoryService.deleteById(id);
        return new ModelAndView("redirect:/tasks?message=Deleted successfully");
    }

    @PostMapping("/edit/{id}")
    public ModelAndView edit(@ModelAttribute TaskHistorySaveRequest taskHistory,
                             @PathVariable Long id) {
        try {
            taskHistoryService.edit(taskHistory, id);
        } catch (Exception e) {
            return new ModelAndView("redirect:/?message=" + e.getMessage());
        }
        return new ModelAndView("redirect:/?message=Edited successfully");
    }

}
