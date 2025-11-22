package com.library.library_management.service;

import com.library.library_management.model.reservation;
import com.library.library_management.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationCleanupService {

    @Autowired
    private ReservationRepository reservationRepository;

    // Run every 5 minutes to check for expired reservations
    @Scheduled(fixedRate = 300000) // 300000 ms = 5 minutes
    public void cleanupExpiredReservations() {
        LocalDateTime now = LocalDateTime.now();
        List<reservation> expiredReservations =
                reservationRepository.findByExpiryDateBeforeAndStatus(now, "RESERVED");

        for (reservation reservation : expiredReservations) {
            reservation.setStatus("EXPIRED");
            reservation.getBook().setAvailable(true); // Make book available again
            reservationRepository.save(reservation);
            System.out.println("Expired reservation: " + reservation.getId() + " for book: " + reservation.getBook().getTitle());
        }

        if (!expiredReservations.isEmpty()) {
            System.out.println("Cleaned up " + expiredReservations.size() + " expired reservations");
        }
    }
}
