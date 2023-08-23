package com.example.task_app.API;

import com.example.task_app.model.Task;
import com.example.task_app.model.enumeration.TaskStatus;
import com.example.task_app.model.enumeration.TaskType;
import com.example.task_app.services.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/api/")
@AllArgsConstructor
public class HomeAPI {
    private static TaskService taskService;
    @GetMapping("/edit/{id}/{status}")
    public ModelAndView edit(@PathVariable Long id, @PathVariable TaskStatus status){
        Task task = TaskService.getInstance().findById(id);
        task.setStatus(status);
        TaskService.getInstance().edit(task);
        ModelAndView view = new ModelAndView("task/daily");
        view.addObject("task", new Task());
        view.addObject("message", "Set status successfully");
        view.addObject("taskTypes", TaskType.values());
        view.addObject("taskStatuses", TaskStatus.values());
        return view;
    }


}
