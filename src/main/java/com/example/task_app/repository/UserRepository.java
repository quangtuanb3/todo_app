package com.example.task_app.repository;

import com.example.task_app.model.Task;
import com.example.task_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {

}
