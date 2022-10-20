package com.nisumpractice.reportgeneration.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExpiryDateDto {
    private Integer month;
    private Integer year;
}
