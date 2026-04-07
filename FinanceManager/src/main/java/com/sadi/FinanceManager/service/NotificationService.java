package com.sadi.FinanceManager.service;

import com.sadi.FinanceManager.dto.ExpenseDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface NotificationService {

    List<ExpenseDTO> getExpensesForUserOnDate(UUID profileId, LocalDate date);
}
