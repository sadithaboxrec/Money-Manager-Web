package com.sadi.FinanceManager.repo;


import com.sadi.FinanceManager.entity.Income;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface IncomeRepo extends JpaRepository<Income, Long> {


    // find the expenses for current logged in user
    //select * from expenses where profile_id = ?1 order by date desc
    List<Income> findIncomesByProfileIdOrderByDateDesc(UUID profileId);

    // find the top 5 of records and limit it to 5
    //select * from expenses where profile_id = ?1 order by date desc limit 5
    List<Income> findTop5ByProfileIdOrderByDateDesc(UUID profileId);

    @Query("SELECT SUM(i.amount) FROM Income i WHERE i.profile.id = :profileId")
    BigDecimal findTotalIncomesByProfileId(@Param("profileId") UUID profileId);

    // filtering results between dates
    //select * from expense where profile_id = ?1 and date between ?2 and ?3 and name like %?4%
    List<Income> findByProfileIdAndDateBetweenAndNameContainingIgnoreCase(
            UUID profileId,
            LocalDate startDate,
            LocalDate endDate,
            String keyword,
            Sort sort
    );

    //    get all expenses a profile has
//    Within a date range
    //select * from expenses where profile_id = ?1 and date between ?2 and ?3
    List<Income> findByProfileIdAndDateBetween(UUID profileId, LocalDate startDate, LocalDate endDate);

    //select * from expenses where profile_id = ?1 and date = ?2
    List<Income> findByProfileIdAndDate(UUID profileId, LocalDate date);


}
