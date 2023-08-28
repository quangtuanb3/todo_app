package com.example.task_app.services.user.response;

import com.example.task_app.model.enumeration.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class UserListResponse {

    private Long id;

    private String username;

    private String email;

    private LocalDate dob;

    private Gender gender;

    private String fullName;
}