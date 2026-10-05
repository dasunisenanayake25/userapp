package com.example.userapp.controller;

import com.example.userapp.dto.UserRequestDto;
import com.example.userapp.dto.UserResponseDto;
import com.example.userapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<UserResponseDto> create(@Valid @RequestBody UserRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(dto));
    }

    // READ all
    @GetMapping
    public List<UserResponseDto> getAll() {
        return userService.getAllUsers();
    }

    // READ one
    @GetMapping("/{id}")
    public UserResponseDto getById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public UserResponseDto update(@PathVariable Long id, @Valid @RequestBody UserRequestDto dto) {
        return userService.updateUser(id, dto);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}