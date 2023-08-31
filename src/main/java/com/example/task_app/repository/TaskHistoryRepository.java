package com.example.task_app.repository;

import com.example.task_app.model.TaskHistory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TaskHistoryRepository extends JpaRepository<TaskHistory, Long> {
    @Query(value = "SELECT t  FROM TaskHistory t WHERE  " +
            "(DATE_FORMAT(t.start, '%Y-%m-%d') <= FUNCTION( 'DATE_FORMAT',:date, '%Y-%m-%d') AND " +
            "DATE_FORMAT(t.end, '%Y-%m-%d') >= FUNCTION( 'DATE_FORMAT',:date, '%Y-%m-%d')  AND t.type = 'NON_DAILY')" +
            "OR (DATE_FORMAT(t.start, '%Y-%m-%d') = FUNCTION( 'DATE_FORMAT',:date, '%Y-%m-%d')  AND t.type = 'DAILY')" +
            "ORDER BY t.start ASC")
    List<TaskHistory> findTaskHistoriesForDate(
            @Param("date") LocalDate date
    );

    @Query("SELECT e.created FROM TaskHistory e ORDER BY e.created DESC limit 1")
    List<LocalDate> findCreatedTask(PageRequest pageRequest);

    @Query(value = "SELECT t FROM TaskHistory t WHERE " +
            "(FUNCTION('DATE_FORMAT', t.start, '%Y-%m-%d') >= FUNCTION('DATE_FORMAT', :firstDate, '%Y-%m-%d') AND " +
            "FUNCTION('DATE_FORMAT', t.end, '%Y-%m-%d') <= FUNCTION('DATE_FORMAT', :lastDate, '%Y-%m-%d')) ORDER BY t.start ASC")
    List<TaskHistory> findTaskHistoriesForWeek(
            @Param("firstDate") LocalDate firstDate,
            @Param("lastDate") LocalDate lastDate
    );

    @Query(value = "SELECT t  FROM TaskHistory t WHERE  " +
            "(DATE_FORMAT(t.start, '%Y-%m-%d') >= FUNCTION( 'DATE_FORMAT',:firstDate, '%Y-%m-%d') AND " +
            "DATE_FORMAT(t.end, '%Y-%m-%d') <= FUNCTION( 'DATE_FORMAT',:lastDate, '%Y-%m-%d'))")
    List<TaskHistory> findTaskHistoriesDateToDate(
            @Param("firstDate") LocalDate firstDate,
            @Param("lastDate") LocalDate lastDate);
}
