package com.sadi.FinanceManager.service;

import com.sadi.FinanceManager.dto.ExpenseDTO;
import com.sadi.FinanceManager.dto.IncomeDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface DashboardService {

     List<ExpenseDTO> getLatest5ExpensesForCurrentUser();

     BigDecimal getTotalExpenseForCurrentUser();

     List<IncomeDTO> getLatest5IncomesForCurrentUser();

     BigDecimal getTotalIncomesForCurrentUser();

     Map<String, Object> getDashboardData();
}
