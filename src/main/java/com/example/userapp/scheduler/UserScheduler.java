package com.example.userapp.scheduler;

import com.example.userapp.repository.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UserScheduler {

    private final UserRepository userRepository;

    public UserScheduler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Scheduled(fixedRate = 60000)
    public void logUserCount() {
        System.out.println("Total users in DB: " + userRepository.count());
    }
}