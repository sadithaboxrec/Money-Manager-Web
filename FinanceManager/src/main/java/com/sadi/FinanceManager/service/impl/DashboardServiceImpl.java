package com.sadi.FinanceManager.service.impl;

import com.sadi.FinanceManager.dto.ExpenseDTO;
import com.sadi.FinanceManager.dto.IncomeDTO;
import com.sadi.FinanceManager.dto.LatestTransactionDTO;
import com.sadi.FinanceManager.entity.Expense;
import com.sadi.FinanceManager.entity.Income;
import com.sadi.FinanceManager.entity.Profile;
import com.sadi.FinanceManager.repo.CategoryRepo;
import com.sadi.FinanceManager.repo.ExpenseRepo;
import com.sadi.FinanceManager.repo.IncomeRepo;
import com.sadi.FinanceManager.service.DashboardService;
import com.sadi.FinanceManager.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Stream.concat;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final CategoryRepo categoryRepo;
    private final ExpenseRepo expenseRepo;
    private final IncomeRepo incomeRepo;
    private final ProfileService profileService;



    // Get latest 5 expenses for current user
    public List<ExpenseDTO> getLatest5ExpensesForCurrentUser() {

        Profile profile = profileService.getCurrentProfile();

        List<Expense> expenses = expenseRepo.findTop5ByProfileIdOrderByDateDesc(profile.getId());

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


    // Get total expenses of  logged user
    public BigDecimal getTotalExpenseForCurrentUser() {

        Profile profile = profileService.getCurrentProfile();
        BigDecimal total = expenseRepo.findTotalExpenseByProfileId(profile.getId());

        return total != null ? total: BigDecimal.ZERO;
    }



    // Get latest 5 expenses for current user
    public List<IncomeDTO> getLatest5IncomesForCurrentUser() {

        Profile profile = profileService.getCurrentProfile();

        List<Income> incomes = incomeRepo.findTop5ByProfileIdOrderByDateDesc(profile.getId());

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


    // Get total expenses of  logged user
    public BigDecimal getTotalIncomesForCurrentUser() {

        Profile profile = profileService.getCurrentProfile();
        BigDecimal total = expenseRepo.findTotalExpenseByProfileId(profile.getId());

        return total != null ? total: BigDecimal.ZERO;
    }


    public Map<String, Object> getDashboardData() {

        Profile profile = profileService.getCurrentProfile();

        Map<String, Object> returnValue = new LinkedHashMap<>();

        List<IncomeDTO> latestIncomes = getLatest5IncomesForCurrentUser();
        List<ExpenseDTO> latestExpenses = getLatest5ExpensesForCurrentUser();

        BigDecimal totalIncome = getTotalIncomesForCurrentUser();
        BigDecimal totalExpense = getTotalExpenseForCurrentUser();

        List<LatestTransactionDTO> recentTransactions = concat(latestIncomes.stream().map(income ->

                        LatestTransactionDTO.builder()
                                .id(income.getId())
                                .profileId(profile.getId())
                                .icon(income.getIcon())
                                .name(income.getName())
                                .amount(income.getAmount())
                                .date(income.getDate())
                                .createdAt(income.getCreatedAt())
                                .updatedAt(income.getUpdatedAt())
                                .type("income")
                                .build()),

                latestExpenses.stream().map(expense ->
                        LatestTransactionDTO.builder()
                                .id(expense.getId())
                                .profileId(profile.getId())
                                .icon(expense.getIcon())
                                .name(expense.getName())
                                .amount(expense.getAmount())
                                .date(expense.getDate())
                                .createdAt(expense.getCreatedAt())
                                .updatedAt(expense.getUpdatedAt())
                                .type("expense")
                                .build()))


                .sorted((a, b) -> {

                    int compare = b.getDate().compareTo(a.getDate());

                    if (compare == 0 && a.getCreatedAt() != null && b.getCreatedAt() != null) {
                        return b.getCreatedAt().compareTo(a.getCreatedAt());
                    }
                    return compare;
                }).collect(Collectors.toList());


        returnValue.put("totalBalance", getTotalIncomesForCurrentUser()
                        .subtract(getTotalExpenseForCurrentUser()));
        returnValue.put("totalIncome", getTotalIncomesForCurrentUser());
        returnValue.put("totalExpense", getTotalExpenseForCurrentUser());
        returnValue.put("recent5Expenses", latestExpenses);
        returnValue.put("recent5Incomes", latestIncomes);
        returnValue.put("recentTransactions", recentTransactions);
        return returnValue;
    }



}
