package com.sadi.FinanceManager.controller;

import com.sadi.FinanceManager.service.ExpenseService;
import com.sadi.FinanceManager.service.IncomeService;
import com.sadi.FinanceManager.service.impl.ExcelService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/excel")
@RequiredArgsConstructor
public class ExcelController {


    private final ExcelService excelService;
    private final IncomeService incomeService;
    private final ExpenseService expenseService;

    @GetMapping("/download/income")
    public void downloadIncomeExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=yourincome.xlsx");
        excelService.writeIncomesToExcel(response.getOutputStream(), incomeService.getCurrentMonthIncomesForLoggedUser());
    }

    @GetMapping("/download/expense")
    public void downloadExpenseExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=yourexpense.xlsx");
        excelService.writeExpensesToExcel(response.getOutputStream(), expenseService.getCurrentMonthExpensesForLoggedUser());
    }
}
