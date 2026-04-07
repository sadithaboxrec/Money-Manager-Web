package com.sadi.FinanceManager.controller;

import com.sadi.FinanceManager.dto.ExpenseDTO;
import com.sadi.FinanceManager.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<ExpenseDTO> addExpense(@RequestBody ExpenseDTO expenseDTO) {

        ExpenseDTO expense = expenseService.addExpense(expenseDTO);

        return  ResponseEntity.status(HttpStatus.CREATED).body(expense);

    }

    @GetMapping
    public ResponseEntity<List<ExpenseDTO>> getAllExpenses() {

        List<ExpenseDTO> expenses=expenseService.getCurrentMonthExpensesForLoggedUser();
        return  ResponseEntity.status(HttpStatus.OK).body(expenses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {

        expenseService.deleteExpense(id);
        return  ResponseEntity.noContent().build();
    }

}
