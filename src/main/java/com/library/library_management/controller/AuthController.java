package com.library.library_management.controller;

import com.library.library_management.model.User;
import com.library.library_management.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    // Show login page
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    // Process login
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        User user = userRepository.findByUsernameAndPassword(username, password);
        if (user != null) {
            session.setAttribute("user", user);
            return "redirect:/books";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/books";
    }
}