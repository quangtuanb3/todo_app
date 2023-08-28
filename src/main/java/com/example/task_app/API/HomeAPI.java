package com.example.task_app.API;

import com.example.task_app.model.Task;
import com.example.task_app.model.enumeration.TaskStatus;
import com.example.task_app.model.enumeration.TaskType;
import com.example.task_app.services.taskHistory.TaskHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/api/")
@AllArgsConstructor
public class HomeAPI {
    private  TaskHistoryService taskHistoryService;
    @GetMapping("/edit/{id}/{status}")
    public ModelAndView edit(@PathVariable Long id, @PathVariable TaskStatus status){
        taskHistoryService.updateStatus(id, status);
        ModelAndView view = new ModelAndView("task/daily");
        view.addObject("task", new Task());
        view.addObject("message", "Set status successfully");
        view.addObject("taskTypes", TaskType.values());
        view.addObject("taskStatuses", TaskStatus.values());
        return view;
    }


}
