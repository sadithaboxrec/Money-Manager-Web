package com.sadi.FinanceManager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDTO {

    private Long id;
    private UUID profileId;
    private String name;
    private String icon;
    private String categoryType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
