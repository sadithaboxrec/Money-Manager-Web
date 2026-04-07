package com.sadi.FinanceManager.controller;

import com.sadi.FinanceManager.dto.ExpenseDTO;
import com.sadi.FinanceManager.dto.FilterDTO;
import com.sadi.FinanceManager.dto.IncomeDTO;
import com.sadi.FinanceManager.service.impl.FilterServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/filter")
public class FilterController {

    private final FilterServiceImpl filterService;


    @PostMapping
    public ResponseEntity<?> filterTransactions(@RequestBody FilterDTO filterDTO) {

        // if user don't send a date send the created date
        LocalDate startDate = filterDTO.getStartDate() != null ? filterDTO.getStartDate() : LocalDate.MIN;
        LocalDate endDate = filterDTO.getEndDate() != null ? filterDTO.getEndDate() : LocalDate.now();

        String keyword = filterDTO.getKeyword() != null ? filterDTO.getKeyword() : "";
//        If no keyword:  use empty string  matches everything

        String sortField = filterDTO.getSortField() != null ? filterDTO.getSortField() : "date";
// default sort field

        Sort.Direction direction = "desc".equalsIgnoreCase(filterDTO.getSortOrder()) ? Sort.Direction.DESC : Sort.Direction.ASC;
        // default sort data

        Sort sort = Sort.by(direction, sortField);

        if ("income".equals(filterDTO.getType())) {
            List<IncomeDTO> incomes = filterService.filterIncomes(startDate, endDate, keyword, sort);
            return ResponseEntity.ok(incomes);
        } else if ("expense".equalsIgnoreCase(filterDTO.getType())) {
            List<ExpenseDTO> expenses = filterService.filterExpenses(startDate, endDate, keyword, sort);
            return ResponseEntity.ok(expenses);
        } else {
            return ResponseEntity.badRequest().body("Invalid type. Must be 'income' or 'expense'");
        }
    }

}
