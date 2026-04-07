package com.sadi.FinanceManager.repo;

import com.sadi.FinanceManager.entity.Expense;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ExpenseRepo extends JpaRepository<Expense, Long> {

    // find the expenses for current logged in user
    //select * from expenses where profile_id = ?1 order by date desc
    List<Expense> findExpensesByProfileIdOrderByDateDesc(UUID profileId);

    // find the top 5 of records and limit it to 5
    //select * from expenses where profile_id = ?1 order by date desc limit 5
    List<Expense> findTop5ByProfileIdOrderByDateDesc(UUID profileId);

    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.profile.id = :profileId")
    BigDecimal findTotalExpenseByProfileId(@Param("profileId") UUID profileId);

    // filtering results between dates
    //select * from expense where profile_id = ?1 and date between ?2 and ?3 and name like %?4%
    List<Expense> findByProfileIdAndDateBetweenAndNameContainingIgnoreCase(
            UUID profileId,
            LocalDate startDate,
            LocalDate endDate,
            String keyword,
            Sort sort
    );

//    get all expenses a profile has
//    Within a date range
    //select * from expenses where profile_id = ?1 and date between ?2 and ?3
    List<Expense> findByProfileIdAndDateBetween(UUID profileId, LocalDate startDate, LocalDate endDate);

    //select * from expenses where profile_id = ?1 and date = ?2
    List<Expense> findByProfileIdAndDate(UUID profileId, LocalDate date);

}
