package com.library.library_management.controller;

import com.library.library_management.model.Book;
import com.library.library_management.model.User;
import com.library.library_management.model.reservation;
import com.library.library_management.repository.BookRepository;
import com.library.library_management.repository.ReservationRepository;
import com.library.library_management.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String listBooks(Model model, HttpSession session) {
        List<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);

        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);

        return "books";
    }

    @GetMapping("/search")
    public String searchBooks(@RequestParam String query, Model model, HttpSession session) {
        List<Book> books = bookRepository.findByTitleContainingIgnoreCase(query);
        model.addAttribute("books", books);
        model.addAttribute("query", query);

        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);

        return "books";
    }

    // Reserve a book - UPDATED FOR 1 HOUR
    @PostMapping("/reserve/{bookId}")
    public String reserveBook(@PathVariable Long bookId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        Book book = bookRepository.findById(bookId).orElse(null);
        if (book != null && book.isAvailable()) {
            // Reserve for 1 HOUR (changed from 2 days)
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime expiry = now.plusHours(1); // 1 HOUR

            reservation reservation = new reservation(user, book, now, expiry);
            reservationRepository.save(reservation);

            // Mark book as reserved (not available)
            book.setAvailable(false);
            bookRepository.save(book);
        }

        return "redirect:/books";
    }
}