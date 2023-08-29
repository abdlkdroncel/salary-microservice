package com.hrglobal.salary.service.impl;

import com.hrglobal.salary.enums.Language;
import com.hrglobal.salary.exception.enums.FriendlyMessageCodes;
import com.hrglobal.salary.exception.exceptions.SalaryAlreadyDeletedException;
import com.hrglobal.salary.exception.exceptions.SalaryNotCalculatedException;
import com.hrglobal.salary.exception.exceptions.SalaryNotFoundException;
import com.hrglobal.salary.repository.SalaryRepository;
import com.hrglobal.salary.repository.entity.PersonalSalary;
import com.hrglobal.salary.request.SalaryCreateRequest;
import com.hrglobal.salary.request.SalaryUpdateRequest;
import com.hrglobal.salary.service.ISalaryRepositoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class SalaryRepositoryServiceImpl implements ISalaryRepositoryService {
    private final SalaryRepository salaryRepository;

    @Override
    public PersonalSalary calculateSalary(Language language, SalaryCreateRequest salaryCreateRequest) {
        log.debug("[{}][createProduct] -> request: {}", this.getClass().getSimpleName(), salaryCreateRequest);
        try {
            BigDecimal gross=salaryCreateRequest.getGross();
            BigDecimal sgkWorker=gross.multiply(BigDecimal.valueOf(0.14));
            BigDecimal sgkUnemployeeWorker=gross.multiply(BigDecimal.valueOf(0.01));
            BigDecimal taxAssesment=gross.subtract(sgkUnemployeeWorker).subtract(sgkWorker);
            BigDecimal incomeTax=taxAssesment.multiply(BigDecimal.valueOf(0.15));
            BigDecimal stamTax=gross.multiply(BigDecimal.valueOf(0.00759));
            BigDecimal minWageIncomeEx=BigDecimal.valueOf(1276.02);
            BigDecimal minWageStampEx=BigDecimal.valueOf(75.96);
            BigDecimal net=gross.subtract(sgkUnemployeeWorker).subtract(sgkWorker).subtract(incomeTax).subtract(stamTax).add(minWageIncomeEx).add(minWageStampEx);
            PersonalSalary salary = PersonalSalary.builder()
                    .staffName(salaryCreateRequest.getStaffName())
                    .staffLastname(salaryCreateRequest.getStaffLastname())
                    .gross(salaryCreateRequest.getGross())
                    .sgkWorker(sgkWorker)
                    .sgkUnemployeeWorker(sgkUnemployeeWorker)
                    .incomeTax(incomeTax)
                    .stampTax(stamTax)
                    .net(net)
                    .minWageIncomeEx(minWageIncomeEx).minWageStampEx(minWageStampEx)
                    .deleted(false)
                    .build();
            PersonalSalary response = salaryRepository.save(salary);
            log.debug("[{}][createProduct] -> response: {}", this.getClass().getSimpleName(), response);
            return response;
        } catch (Exception exception) {
            throw new SalaryNotCalculatedException(language, FriendlyMessageCodes.SALARY_NOT_CALCULATED_EXCEPTION,"salary request"+salaryCreateRequest.toString());
        }
    }

    @Override
    public PersonalSalary getSalary(Language language, Long id) {
        log.debug("[{}][getSalary] -> request: {}", this.getClass().getSimpleName(), id);
        PersonalSalary salary = salaryRepository.getByIdAndDeletedFalse(id);
        if (Objects.isNull(salary)) {
            throw new SalaryNotFoundException(language,FriendlyMessageCodes.SALARY_NOT_FOUND_EXCEPTION,"Salary Not Found");
        }
        log.debug("[{}][getSalary] -> request: {}", this.getClass().getSimpleName(), salary);
        return salary;
    }

    @Override
    public List<PersonalSalary> getSalaries(Language language) {
        log.debug("[{}][getAllSalaries] -> request: {}", this.getClass().getSimpleName());
        return salaryRepository.getAllByDeletedFalse();
    }

    @Override
    public PersonalSalary updateSalary(Language language, Long id, SalaryUpdateRequest salaryUpdateRequest) {
        log.debug("[{}][updateSalary] -> request: {}", this.getClass().getSimpleName(), salaryUpdateRequest);
        PersonalSalary salary=getSalary(language,id);
        if(Objects.isNull(salary)){
            throw new SalaryNotFoundException(language,FriendlyMessageCodes.SALARY_NOT_FOUND_EXCEPTION,"Salary Not Found");
        }
        salary.setGross(salaryUpdateRequest.getGross());
        BigDecimal gross = salary.getGross();
        BigDecimal sgkWorker=gross.multiply(BigDecimal.valueOf(0.14));
        BigDecimal sgkUnemployeeWorker=gross.multiply(BigDecimal.valueOf(0.01));
        BigDecimal taxAssesment=gross.subtract(sgkUnemployeeWorker).subtract(sgkWorker);
        BigDecimal incomeTax=taxAssesment.multiply(BigDecimal.valueOf(0.15));
        BigDecimal stamTax=gross.multiply(BigDecimal.valueOf(0.00759));
        BigDecimal minWageIncomeEx=BigDecimal.valueOf(1276.02);
        BigDecimal minWageStampEx=BigDecimal.valueOf(75.96);
        BigDecimal net=gross.subtract(sgkUnemployeeWorker).subtract(sgkWorker).subtract(incomeTax).subtract(stamTax).add(minWageIncomeEx).add(minWageStampEx);

        salary = PersonalSalary.builder()
                .staffName(salaryUpdateRequest.getStaffName())
                .staffLastname(salaryUpdateRequest.getStaffLastname())
                .gross(gross)
                .sgkWorker(sgkWorker)
                .sgkUnemployeeWorker(sgkUnemployeeWorker)
                .incomeTax(incomeTax)
                .stampTax(stamTax)
                .net(net)
                .minWageIncomeEx(minWageIncomeEx).minWageStampEx(minWageStampEx)
                .deleted(false)
                .build();
        PersonalSalary response=salaryRepository.save(salary);
        log.debug("[{}][updateSalary] -> request: {}", this.getClass().getSimpleName(), response);
        return response;
    }

    @Override
    public PersonalSalary deleteSalary(Language language, Long id) {
        log.debug("[{}][deleteSalary] -> request: {}", this.getClass().getSimpleName(), id);
        try {
            PersonalSalary salary = salaryRepository.getByIdAndDeletedFalse(id);
            salary.setDeleted(true);
            salary.setSalaryUpdatedDate(new Date());
            PersonalSalary savedSalary = salaryRepository.save(salary);
            log.debug("[{}][deleteSalary] -> request: {}", this.getClass().getSimpleName(), savedSalary);

            return savedSalary;

        }catch (SalaryAlreadyDeletedException salaryAlreadyDeletedException){
            throw new SalaryAlreadyDeletedException(language,FriendlyMessageCodes.SALARY_ALREADY_DELETED,"Salary Already Deleted");
        }

    }
}
