package com.sadi.FinanceManager.controller;

import com.sadi.FinanceManager.entity.Profile;
import com.sadi.FinanceManager.service.EmailService;
import com.sadi.FinanceManager.service.ExpenseService;
import com.sadi.FinanceManager.service.IncomeService;
import com.sadi.FinanceManager.service.ProfileService;
import com.sadi.FinanceManager.service.impl.ExcelService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {

    private final ExcelService excelService;
    private final EmailService emailService;
    private final ProfileService profileService;
    private final ExpenseService expenseService;
    private final IncomeService incomeService;

    @GetMapping("/income-excel")
    public ResponseEntity<Void> emailIncomeExcel() throws IOException, MessagingException {

        Profile profile = profileService.getCurrentProfile();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        excelService.writeIncomesToExcel(baos, incomeService.getCurrentMonthIncomesForLoggedUser());

        emailService.sendEmailWithExcel(profile.getEmail(),
                "Your Income Excel Report",
                "Here is your Finance Manager Incomes",
                baos.toByteArray(),
                "income.xlsx");
        return ResponseEntity.ok(null);
    }

    @GetMapping("/expense-excel")
    public ResponseEntity<Void> emailExpenseExcel() throws IOException, MessagingException {
        Profile profile = profileService.getCurrentProfile();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        excelService.writeExpensesToExcel(baos, expenseService.getCurrentMonthExpensesForLoggedUser());
        emailService.sendEmailWithExcel(
                profile.getEmail(),
                "Your Expense Excel Report",
                "Here is your Finance Manager expenses.",
                baos.toByteArray(),
                "expenses.xlsx");
        return ResponseEntity.ok(null);
    }
}
