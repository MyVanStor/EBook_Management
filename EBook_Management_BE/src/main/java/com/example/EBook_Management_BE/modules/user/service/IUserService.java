package com.example.EBook_Management_BE.modules.user.service;

import com.example.EBook_Management_BE.common.entity.User;
import com.example.EBook_Management_BE.modules.user.dto.UserDTO;

public interface IUserService {
    User createUser(UserDTO userDTO) throws Exception;
    
    User getUserById(Long userId);

}
