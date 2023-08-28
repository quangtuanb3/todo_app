package com.example.task_app.repository;

import com.example.task_app.model.TaskHistory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface TaskHistoryRepository extends JpaRepository<TaskHistory, Long> {
    @Query(value = "SELECT t  FROM TaskHistory t WHERE  " +
            "(DATE_FORMAT(t.start, '%Y-%m-%d') <= DATE_FORMAT(CURRENT_DATE, '%Y-%m-%d') AND " +
            "DATE_FORMAT(t.end, '%Y-%m-%d') >= DATE_FORMAT(CURRENT_DATE, '%Y-%m-%d') AND t.type = 'NON_DAILY')" +
            "OR (DATE_FORMAT(t.start, '%Y-%m-%d') = DATE_FORMAT(CURRENT_DATE, '%Y-%m-%d') AND t.type = 'DAILY')" )
    List<TaskHistory> findTaskHistoriesForToday();
    @Query("SELECT e.created FROM TaskHistory e ORDER BY e.created DESC limit 1" )
    List<LocalDate> findCreatedTask(PageRequest pageRequest);
}
