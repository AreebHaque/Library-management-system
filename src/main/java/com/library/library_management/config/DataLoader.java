package com.library.library_management.config;

import com.library.library_management.model.User;
import com.library.library_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        // Always ensure we have the test users
        createUserIfNotExists("student1", "student1@uni.edu", "pass123", "STUDENT");
        createUserIfNotExists("student2", "student2@uni.edu", "pass123", "STUDENT");
        createUserIfNotExists("librarian1", "librarian@library.edu", "admin123", "LIBRARIAN");

        System.out.println("User accounts ready for login!");
    }

    private void createUserIfNotExists(String username, String email, String password, String role) {
        if (userRepository.findByUsername(username) == null) {
            userRepository.save(new User(username, email, password, role));
            System.out.println("Created user: " + username);
        }
    }
}
