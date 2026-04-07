package com.sadi.FinanceManager.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FilterDTO {

    private String type;  // income or expense

    private LocalDate startDate;
    private LocalDate endDate;
    private String keyword;

    private String sortField;  // date amount
    private String sortOrder;

}
