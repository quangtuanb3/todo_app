package com.example.task_app.API;

import com.example.task_app.model.Task;
import com.example.task_app.model.enumeration.TaskStatus;
import com.example.task_app.model.enumeration.TaskType;
import com.example.task_app.repository.TaskHistoryRepository;
import com.example.task_app.services.task.TaskService;
import com.example.task_app.services.taskHistory.TaskHistoryService;
import com.example.task_app.services.taskHistory.response.TaskHistoryListResponse;
import com.example.task_app.util.AppUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/")
@AllArgsConstructor
public class StatisticalAPI {
    private final TaskService taskService;
    private final TaskHistoryService taskHistoryService;
    @GetMapping("/discharged/{startDate}/{endDate}")
    @ResponseBody // This annotation indicates that the returned object should be serialized into the response body
    public ResponseEntity<Map<String, Object>> getDischargedData(@PathVariable String startDate, @PathVariable String endDate) {
        List<TaskHistoryListResponse> tasks = taskHistoryService.getTaskDateToDate(AppUtil.mapper.map(startDate, LocalDate.class), AppUtil.mapper.map(endDate, LocalDate.class));

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("taskTypes", TaskType.values());
        responseData.put("taskStatuses", TaskStatus.values());
        responseData.put("dayTasks", tasks);
        responseData.put("weeklyTasks", taskHistoryService.getTaskInWeek(LocalDate.now()));

        return ResponseEntity.ok(responseData);
    }
    @GetMapping("/weeklyChart/{yearweek}")
    @ResponseBody // This annotation indicates that the returned object should be serialized into the response body
    public ResponseEntity<Map<String, Object>> getDischargedWeekData( @PathVariable String yearweek) {
        LocalDate[] dates = AppUtil.getStartAndEndDatesFromWeek(yearweek);
        List<TaskHistoryListResponse> weeklyTasks = taskHistoryService.getTaskDateToDate(dates[0], dates[1]);
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("weeklyTasks", weeklyTasks);
        return ResponseEntity.ok(responseData);
    }

}
