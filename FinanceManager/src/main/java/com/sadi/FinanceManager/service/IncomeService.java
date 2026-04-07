package com.sadi.FinanceManager.service;

import com.sadi.FinanceManager.dto.IncomeDTO;

import java.util.List;

public interface IncomeService {

    public IncomeDTO addIncome(IncomeDTO incomeDTO);

    List<IncomeDTO> getCurrentMonthIncomesForLoggedUser();

    void deleteIncome(Long incomeId);
}
