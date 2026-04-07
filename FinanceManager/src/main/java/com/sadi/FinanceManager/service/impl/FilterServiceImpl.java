package com.sadi.FinanceManager.service.impl;

import com.sadi.FinanceManager.dto.ExpenseDTO;
import com.sadi.FinanceManager.dto.IncomeDTO;
import com.sadi.FinanceManager.entity.Expense;
import com.sadi.FinanceManager.entity.Income;
import com.sadi.FinanceManager.entity.Profile;
import com.sadi.FinanceManager.repo.ExpenseRepo;
import com.sadi.FinanceManager.repo.IncomeRepo;
import com.sadi.FinanceManager.service.ExpenseService;
import com.sadi.FinanceManager.service.IncomeService;
import com.sadi.FinanceManager.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilterServiceImpl {

    private final ProfileService profileService;
    private final ExpenseService expenseService;
    private final IncomeService incomeService;
    private final ExpenseRepo expenseRepo;
    private final IncomeRepo incomeRepo;

    //filter expenses
    public List<ExpenseDTO> filterExpenses(LocalDate startDate, LocalDate endDate, String keyword, Sort sort) {

        Profile profile = profileService.getCurrentProfile();

        List<Expense> expenses = expenseRepo.findByProfileIdAndDateBetweenAndNameContainingIgnoreCase(
                profile.getId(), startDate, endDate, keyword, sort);

        List<ExpenseDTO> expenseDTOList = new ArrayList<>();

        for (Expense expense : expenses) {
            expenseDTOList.add(
                    ExpenseDTO.builder()
                            .id(expense.getId())
                            .name(expense.getName())
                            .icon(expense.getIcon())
                            .categoryId(expense.getCategory() != null ? expense.getCategory().getId() : null)
                            .categoryName(expense.getCategory() != null ? expense.getCategory().getName() : "N/A")
                            .amount(expense.getAmount())
                            .date(expense.getDate())
                            .createdAt(expense.getCreatedAt())
                            .updatedAt(expense.getUpdatedAt())
                            .build()
            );
        }
        return expenseDTOList;
    }



    //filter incomes
    public List<IncomeDTO> filterIncomes(LocalDate startDate, LocalDate endDate, String keyword, Sort sort) {

        Profile profile = profileService.getCurrentProfile();

        List<Income> incomes = incomeRepo.findByProfileIdAndDateBetweenAndNameContainingIgnoreCase(profile.getId(), startDate, endDate, keyword, sort);

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

}
