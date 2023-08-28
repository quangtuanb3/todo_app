package com.example.task_app.model;

import com.example.task_app.model.enumeration.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "profiles")
@Where(clause = "deleted = 0")
@SQLDelete(sql = "UPDATE profiles SET `deleted` = 1 WHERE (`id` = ?);")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private LocalDate dob;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private boolean deleted = false;
    @OneToMany
    private Set<Task> taskList;
}
