package com.example.task_app.services.taskHistory.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TaskHistorySaveRequest {
    private String id;
    private String title;
    private String description;
    private String start;
    private String end;
    private String status;

}
