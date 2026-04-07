package com.sadi.FinanceManager.service.impl;


import com.sadi.FinanceManager.dto.IncomeDTO;
import com.sadi.FinanceManager.entity.Category;
import com.sadi.FinanceManager.entity.Expense;
import com.sadi.FinanceManager.entity.Income;
import com.sadi.FinanceManager.entity.Profile;
import com.sadi.FinanceManager.exception.ResourceNotFoundException;
import com.sadi.FinanceManager.exception.UnauthorizedActionException;
import com.sadi.FinanceManager.repo.CategoryRepo;
import com.sadi.FinanceManager.repo.IncomeRepo;
import com.sadi.FinanceManager.service.IncomeService;
import com.sadi.FinanceManager.service.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class IncomeServiceImpl implements IncomeService {

    private final CategoryRepo categoryRepo;
    private final IncomeRepo incomeRepo;
    private final ProfileService profileService;

    // Adds a new expense to the database
    public IncomeDTO addIncome(IncomeDTO incomeDTO) {


        Profile profile = profileService.getCurrentProfile();

        Category category = categoryRepo.findById(incomeDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Income newExpense =  Income.builder()
                .name(incomeDTO.getName())
                .icon(incomeDTO.getIcon())
                .amount(incomeDTO.getAmount())
                .date(incomeDTO.getDate())
                .profile(profile)
                .category(category)
                .build();

        Income savedIncome = incomeRepo.save(newExpense);

        return IncomeDTO.builder()
                .id(savedIncome.getId())
                .name(savedIncome.getName())
                .icon(savedIncome.getIcon())
                .categoryId(savedIncome.getCategory() != null ? savedIncome.getCategory().getId(): null)
                .categoryName(savedIncome.getCategory() != null ? savedIncome.getCategory().getName(): "N/A")
                .amount(savedIncome.getAmount())
                .date(savedIncome.getDate())
                .createdAt(savedIncome.getCreatedAt())
                .updatedAt(savedIncome.getUpdatedAt())
                .build();


    }




    public List<IncomeDTO> getCurrentMonthIncomesForLoggedUser() {

        Profile profile = profileService.getCurrentProfile();

        LocalDate now = LocalDate.now();
        LocalDate startDate = now.withDayOfMonth(1);
        LocalDate endDate = now.withDayOfMonth(now.lengthOfMonth());

        List<Income> incomes = incomeRepo.findByProfileIdAndDateBetween(profile.getId(), startDate, endDate);

        List<IncomeDTO> incomeDTOList = new ArrayList<>();

        for (Income income : incomes) {
            incomeDTOList.add(
                    IncomeDTO.builder()
                            .id(income.getId())
                            .name(income.getName())
                            .icon(income.getIcon())
                            .categoryId(income.getCategory() != null ? income.getCategory().getId() : null)
                            .categoryName(income.getCategory() != null ? income.getCategory().getName() : "N/A")
                            .amount(income.getAmount())
                            .date(income.getDate())
                            .createdAt(income.getCreatedAt())
                            .updatedAt(income.getUpdatedAt())
                            .build()
            );

        }

        return incomeDTOList;
    }


    //delete expense by id for logged in  user
    public void deleteIncome(Long incomeId) {

        Profile profile = profileService.getCurrentProfile();

        Income income = incomeRepo.findById(incomeId)
//                .orElseThrow(() -> new RuntimeException("Income not found"));
                .orElseThrow(() -> new ResourceNotFoundException("Income with id " + incomeId + " not found"));

        // checkd if it with logged user
        if (!income.getProfile().getId().equals(profile.getId())) {
            throw new UnauthorizedActionException("You cannot delete this income");
        }

        incomeRepo.delete(income);
    }

}
