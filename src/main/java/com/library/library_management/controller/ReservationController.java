package com.library.library_management.controller;

import com.library.library_management.model.User;
import com.library.library_management.model.reservation;
import com.library.library_management.repository.BookRepository;
import com.library.library_management.repository.ReservationRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private BookRepository bookRepository; // ADDED

    // View user's reservations
    @GetMapping
    public String viewMyReservations(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        List<reservation> reservations = reservationRepository.findByUserId(user.getId());
        model.addAttribute("reservations", reservations);
        model.addAttribute("user", user);

        return "my-reservations";
    }

    // Cancel reservation - FIXED VERSION
    @PostMapping("/cancel/{reservationId}")
    public String cancelReservation(@PathVariable Long reservationId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        reservation reservation = reservationRepository.findById(reservationId).orElse(null);
        if (reservation != null && reservation.getUser().getId().equals(user.getId())) {
            reservation.setStatus("CANCELLED");
            reservationRepository.save(reservation);

            // FIX: Make book available again
            if (reservation.getBook() != null) {
                reservation.getBook().setAvailable(true);
                bookRepository.save(reservation.getBook()); // Save the book changes
            }
        }

        return "redirect:/reservations";
    }
}