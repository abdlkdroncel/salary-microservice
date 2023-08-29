package com.hrglobal.salary.repository.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PersonalSalary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String staffName;

    private String staffLastname;

    private BigDecimal gross;

    private BigDecimal sgkWorker;

    private BigDecimal sgkUnemployeeWorker;

    private BigDecimal incomeTax;

    private BigDecimal stampTax;

    private BigDecimal minWageIncomeEx;

    private BigDecimal minWageStampEx;

    private BigDecimal net;

    @Builder.Default
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date salaryUpdatedDate = new Date();

    @Builder.Default
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date salaryCreatedDate = new Date();

    private boolean deleted;


}
