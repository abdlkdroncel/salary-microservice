package com.hrglobal.salary.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SalaryUpdateRequest {
    private Long id;
    private String staffName;
    private String staffLastname;
    private BigDecimal gross;
}
