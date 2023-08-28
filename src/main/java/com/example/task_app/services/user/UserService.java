package com.example.task_app.services.user;


import com.example.task_app.model.Exception.ResourceNotFoundException;
import com.example.task_app.model.Profile;
import com.example.task_app.model.User;
import com.example.task_app.model.enumeration.Gender;
import com.example.task_app.repository.ProfileRepository;
import com.example.task_app.repository.UserRepository;
import com.example.task_app.services.user.request.UserEditRequest;
import com.example.task_app.services.user.request.UserSaveRequest;
import com.example.task_app.services.user.response.UserListResponse;
import com.example.task_app.util.AppMsg;
import com.example.task_app.util.AppUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    public void create(UserSaveRequest request){
        var user = AppUtil.mapper.map(request, User.class);
        var profile = AppUtil.mapper.map(request, Profile.class);
        profile = profileRepository.save(profile);
        user.setProfile(profile);
        userRepository.save(user);
    }

    public List<UserListResponse> findAll(){
        return userRepository.findAll().stream().map(user -> {
            //
//            var result = AppUtil.mapper.map(user, UserListResponse.class);
//            result = AppUtil.mapper.map(user.getProfile(), UserListResponse.class);
//            return result;
            var result = new UserListResponse();
            result.setDob(user.getProfile().getDob());
            result.setEmail(user.getEmail());
            result.setGender(user.getProfile().getGender());
            result.setFullName(user.getProfile().getFullName());
            result.setUsername(user.getUsername());
            result.setId(user.getId());
            return result;
        }).toList();
    }

    public User findById(Long id){
        //de tai su dung
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException
                        (String.format(AppMsg.ID_NOT_FOUND, "User", id)));
    }

    public void deleteById(Long id){
        User user = findById(id);
        userRepository.delete(user);
    }

    public void edit(UserEditRequest request, Long id) throws Exception{
        var userInDb = findById(id);
        if(!Objects.equals(userInDb.getPassword(), request.getOldPassword())){
            throw new Exception("Incorrect Password");
        }
        userInDb.setPassword(request.getPassword());
        userInDb.getProfile().setDob(LocalDate.parse(request.getDob(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        userInDb.getProfile().setGender(Gender.valueOf(request.getGender()));
        userInDb.getProfile().setFullName(request.getFullName());
        request.setId(id.toString());
        userRepository.save(userInDb);
    }

    public UserEditRequest showEditById(Long id){
        User user = findById(id);
        return userToUserEditRequest(user);
    }

    private UserEditRequest userToUserEditRequest(User user){
        var result = new UserEditRequest();
        result.setDob(String.valueOf(user.getProfile().getDob()));
        result.setGender(String.valueOf(user.getProfile().getGender()));
        result.setFullName(user.getProfile().getFullName());
        result.setId(String.valueOf(user.getId()));
        return result;
    }
}