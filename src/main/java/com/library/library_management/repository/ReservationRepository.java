package com.library.library_management.repository;

import com.library.library_management.model.reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<reservation, Long> {
    List<reservation> findByUserId(Long userId);
    List<reservation> findByBookId(Long bookId);
    List<reservation> findByStatus(String status);
    List<reservation> findByExpiryDateBeforeAndStatus(LocalDateTime date, String status);
}
