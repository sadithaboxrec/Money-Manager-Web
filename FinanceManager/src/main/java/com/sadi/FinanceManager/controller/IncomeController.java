package com.sadi.FinanceManager.controller;

import com.sadi.FinanceManager.dto.ExpenseDTO;
import com.sadi.FinanceManager.dto.IncomeDTO;
import com.sadi.FinanceManager.service.IncomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/incomes")
public class IncomeController {

    private final IncomeService incomeService;

    @PostMapping
    public ResponseEntity<IncomeDTO> addIncome(@RequestBody IncomeDTO incomeDTO) {

        IncomeDTO income = incomeService.addIncome(incomeDTO);

        return  ResponseEntity.status(HttpStatus.CREATED).body(income);
    }


    @GetMapping("/{id}")
    public ResponseEntity<List<IncomeDTO>> getAllExpenses() {

        List<IncomeDTO> incomes=incomeService.getCurrentMonthIncomesForLoggedUser();
        return  ResponseEntity.noContent().build();
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {

        incomeService.deleteIncome(id);
        return  ResponseEntity.noContent().build();
    }

}
