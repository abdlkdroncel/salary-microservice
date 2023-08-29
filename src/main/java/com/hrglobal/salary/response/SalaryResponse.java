package com.hrglobal.salary.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class SalaryResponse {
    private Long id;
    private String staffName;
    private String staffLastname;
    private BigDecimal gross;
    private BigDecimal net;
    private BigDecimal incomeTax;
    private BigDecimal stampTax;
    private BigDecimal sgkWorker;
    private BigDecimal sgkUnemployeeWorker;
    private BigDecimal minWageIncomeEx;
    private BigDecimal minWageStampEx;
    private Long salaryCreatedDate;
    private Long salaryUpdatedDate;
}
