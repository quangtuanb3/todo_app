package com.example.task_app.repository;

import com.example.task_app.model.Profile;
import com.example.task_app.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;


public interface ProfileRepository extends JpaRepository<Profile, Long> {

}
