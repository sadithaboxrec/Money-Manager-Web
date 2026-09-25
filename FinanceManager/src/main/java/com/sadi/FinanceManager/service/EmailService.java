package com.sadi.FinanceManager.service;

import jakarta.mail.MessagingException;

public interface EmailService {

    void sendEmail(String to, String subject, String body);

    void sendEmailWithExcel(String to, String subject, String body, byte[] attachment, String filename)throws MessagingException;


}
