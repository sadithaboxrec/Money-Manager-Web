package com.sadi.FinanceManager.service.impl;

import com.sadi.FinanceManager.dto.ExpenseDTO;
import com.sadi.FinanceManager.entity.Expense;
import com.sadi.FinanceManager.entity.Profile;
import com.sadi.FinanceManager.repo.ExpenseRepo;
import com.sadi.FinanceManager.repo.ProfileRepo;
import com.sadi.FinanceManager.service.EmailService;
import com.sadi.FinanceManager.service.ExpenseService;
import com.sadi.FinanceManager.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j // to add logs
public class NotificationServiceImpl implements NotificationService {

    private final ExpenseRepo expenseRepo;

    private final EmailService emailService;
    private final ExpenseService expenseService;
    private final ProfileRepo profileRepo;

    @Value("${web.url}")
    private String frontend;

    public List<ExpenseDTO> getExpensesForUserOnDate(UUID profileId, LocalDate date) {

        List<Expense> expenses = expenseRepo.findByProfileIdAndDate(profileId, date);

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



  @Scheduled(cron = "0 0 23 * * *", zone = "IST")    // send email in 11 pm
//    @Scheduled(cron = "0 * * * * *", zone = "IST")
    public void sendDailyIncomeExpenseReminder() {

        log.info("Notifcications started: sendDailyIncomeExpenseReminder()");

        List<Profile> profiles = profileRepo.findAll();

        for(Profile profile : profiles) {

            String body = "<div style='font-family: Arial, sans-serif; line-height:1.6; color:#333;'>"
                    + "<h2 style='color:#4CAF50;'>Hi " + profile.getFullName() + ",</h2>"

                    + "<p>This is a friendly reminder to update your <b>income</b> and <b>expenses</b> for today in your Money Manager.</p>"

                    + "<div style='margin:20px 0;'>"
                    + "<a href='" + frontend + "' "
                    + "style='background-color:#4CAF50; color:white; padding:12px 24px; text-decoration:none; "
                    + "border-radius:6px; font-size:14px; font-weight:bold; display:inline-block;'>"
                    + "Open Money Manager"
                    + "</a>"
                    + "</div>"

                    + "<p style='font-size:13px; color:#777;'>Stay consistent and keep tracking your finances 💸</p>"

                    + "<br>"
                    + "<p>Best regards,<br><b>Money Manager Team</b></p>"
                    + "</div>";

            emailService.sendEmail(profile.getEmail(), "Reminder: Add your financial reports", body);

        }
        log.info("Job completed: sendDailyIncomeExpenseReminder()");
    }







}
