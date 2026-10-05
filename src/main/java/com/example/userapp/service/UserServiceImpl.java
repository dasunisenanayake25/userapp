package com.example.userapp.service;

import com.example.userapp.dto.UserRequestDto;
import com.example.userapp.dto.UserResponseDto;
import com.example.userapp.exception.ResourceNotFoundException;
import com.example.userapp.model.User;
import com.example.userapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;   // dependency injection

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDto createUser(UserRequestDto dto) {
        User user = new User();
        user.setFullName(dto.fullName());
        user.setEmail(dto.email());
        user.setStatus("ACTIVE");
        return toDto(userRepository.save(user));
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        return toDto(findOrThrow(id));
    }

    @Override
    public UserResponseDto updateUser(Long id, UserRequestDto dto) {
        User user = findOrThrow(id);
        user.setFullName(dto.fullName());
        user.setEmail(dto.email());
        return toDto(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.delete(findOrThrow(id));
    }

    // helper methods
    private User findOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    private UserResponseDto toDto(User u) {
        return new UserResponseDto(u.getId(), u.getFullName(), u.getEmail(), u.getStatus());
    }
}