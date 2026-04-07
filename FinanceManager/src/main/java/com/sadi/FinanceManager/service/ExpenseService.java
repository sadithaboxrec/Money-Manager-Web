package com.sadi.FinanceManager.service;

import com.sadi.FinanceManager.dto.ExpenseDTO;

import java.util.List;

public interface ExpenseService {

    ExpenseDTO addExpense(ExpenseDTO expenseDTO);

    List<ExpenseDTO> getCurrentMonthExpensesForLoggedUser();

    void deleteExpense(Long expenseId);

}
