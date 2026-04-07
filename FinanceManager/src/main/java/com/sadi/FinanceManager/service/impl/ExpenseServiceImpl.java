package com.sadi.FinanceManager.service.impl;

import com.sadi.FinanceManager.dto.ExpenseDTO;
import com.sadi.FinanceManager.entity.Category;
import com.sadi.FinanceManager.entity.Expense;
import com.sadi.FinanceManager.entity.Profile;
import com.sadi.FinanceManager.exception.ResourceNotFoundException;
import com.sadi.FinanceManager.exception.UnauthorizedActionException;
import com.sadi.FinanceManager.repo.CategoryRepo;
import com.sadi.FinanceManager.repo.ExpenseRepo;
import com.sadi.FinanceManager.service.ExpenseService;
import com.sadi.FinanceManager.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final CategoryRepo categoryRepo;
    private final ExpenseRepo expenseRepo;
    private final ProfileService profileService;


    // Adds a new expense
    public ExpenseDTO addExpense(ExpenseDTO expenseDTO) {

        Profile profile = profileService.getCurrentProfile();

        // return optional so need exception handling
        Category category = categoryRepo.findById(expenseDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Expense newExpense =  Expense.builder()
//        newExpense.builder()
                .name(expenseDTO.getName())
                .icon(expenseDTO.getIcon())
                .amount(expenseDTO.getAmount())
                .date(expenseDTO.getDate())
                .profile(profile)
                .category(category)
                .build();

        Expense savedExpense = expenseRepo.save(newExpense);

        return ExpenseDTO.builder()
                .id(savedExpense.getId())
                .name(savedExpense.getName())
                .icon(savedExpense.getIcon())
                .categoryId(savedExpense.getCategory() != null ? savedExpense.getCategory().getId(): null)
                .categoryName(savedExpense.getCategory() != null ? savedExpense.getCategory().getName(): "N/A")
                .amount(savedExpense.getAmount())
                .date(savedExpense.getDate())
                .createdAt(savedExpense.getCreatedAt())
                .updatedAt(savedExpense.getUpdatedAt())
                .build();
    }


    // Retrieves all expenses for current month/based on the start date and end date
    public List<ExpenseDTO> getCurrentMonthExpensesForLoggedUser() {
        Profile profile = profileService.getCurrentProfile();

        LocalDate now = LocalDate.now();
        LocalDate startDate = now.withDayOfMonth(1);
        LocalDate endDate = now.withDayOfMonth(now.lengthOfMonth());

        List<Expense> expenses = expenseRepo.findByProfileIdAndDateBetween(profile.getId(), startDate, endDate);

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




    //delete expense by id for logged in  user
    public void deleteExpense(Long expenseId) {

        Profile profile = profileService.getCurrentProfile();

        Expense expense = expenseRepo.findById(expenseId)
                .orElseThrow(() -> new ResourceNotFoundException("Expense with id " + expenseId + " not found"));

        // checkd if it with logged user
        if (!expense.getProfile().getId().equals(profile.getId())) {
//            throw new RuntimeException("Can not delete this expense");
            throw new UnauthorizedActionException("You cannot delete this income");

        }

        expenseRepo.delete(expense);
    }




}
