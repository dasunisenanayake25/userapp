package com.example.userapp.service;

import com.example.userapp.dto.UserRequestDto;
import com.example.userapp.dto.UserResponseDto;

import java.util.List;

public interface UserService {
    UserResponseDto createUser(UserRequestDto dto);
    List<UserResponseDto> getAllUsers();
    UserResponseDto getUserById(Long id);
    UserResponseDto updateUser(Long id, UserRequestDto dto);
    void deleteUser(Long id);
}