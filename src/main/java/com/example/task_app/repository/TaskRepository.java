package com.example.task_app.repository;

import com.example.task_app.model.Task;
import com.example.task_app.model.enumeration.TaskType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;


public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByRenewalDate(LocalDate currentDate);
    @Query("SELECT e.renewalDate FROM Task e ORDER BY e.renewalDate ASC limit 1" )
    List<LocalDate> findByRenewalDate(PageRequest pageRequest);

    Task findTopByOrderByRenewalDateAsc();
}
