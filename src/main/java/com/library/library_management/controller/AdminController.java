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

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    // Admin dashboard
    @GetMapping
    public String adminDashboard(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"LIBRARIAN".equals(user.getRole())) {
            return "redirect:/books";
        }

        List<Book> books = bookRepository.findAll();
        List<User> users = userRepository.findAll();

        model.addAttribute("books", books);
        model.addAttribute("users", users);
        model.addAttribute("user", user);
        model.addAttribute("currentUser", user);

        return "admin-dashboard";
    }

    // Add new book form
    @GetMapping("/books/new")
    public String showAddBookForm(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"LIBRARIAN".equals(user.getRole())) {
            return "redirect:/books";
        }

        model.addAttribute("book", new Book());
        return "add-book";
    }

    // Add new book
    @PostMapping("/books")
    public String addBook(@ModelAttribute Book book, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"LIBRARIAN".equals(user.getRole())) {
            return "redirect:/books";
        }

        book.setAvailable(true);
        bookRepository.save(book);
        return "redirect:/admin";
    }

    // Delete book
    @PostMapping("/books/delete/{bookId}")
    public String deleteBook(@PathVariable Long bookId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"LIBRARIAN".equals(user.getRole())) {
            return "redirect:/books";
        }

        bookRepository.deleteById(bookId);
        return "redirect:/admin";
    }

    // Unreserve a book (make it available again)
    @PostMapping("/books/unreserve/{bookId}")
    public String unreserveBook(@PathVariable Long bookId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"LIBRARIAN".equals(user.getRole())) {
            return "redirect:/books";
        }

        Book book = bookRepository.findById(bookId).orElse(null);
        if (book != null && !book.isAvailable()) {
            // Find and cancel the active reservation for this book
            List<reservation> reservations = reservationRepository.findByBookId(bookId);
            for (reservation reservation : reservations) {
                if ("RESERVED".equals(reservation.getStatus())) {
                    reservation.setStatus("CANCELLED");
                    reservationRepository.save(reservation);
                    break;
                }
            }

            // Make book available again
            book.setAvailable(true);
            bookRepository.save(book);
        }

        return "redirect:/admin";
    }
    // Add these methods to your existing AdminController.java

    // Show add student form
    @GetMapping("/students/new")
    public String showAddStudentForm(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"LIBRARIAN".equals(user.getRole())) {
            return "redirect:/books";
        }

        model.addAttribute("newStudent", new User());
        return "add-student";
    }

    // Add new student
    @PostMapping("/students")
    public String addStudent(@ModelAttribute("newStudent") User student, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"LIBRARIAN".equals(user.getRole())) {
            return "redirect:/books";
        }

        // Set student role and save
        student.setRole("STUDENT");
        userRepository.save(student);
        return "redirect:/admin";
    }

    // Delete student
    @PostMapping("/students/delete/{studentId}")
    public String deleteStudent(@PathVariable Long studentId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"LIBRARIAN".equals(user.getRole())) {
            return "redirect:/books";
        }

        // Don't allow deleting yourself
        if (!user.getId().equals(studentId)) {
            userRepository.deleteById(studentId);
        }

        return "redirect:/admin";
    }
}