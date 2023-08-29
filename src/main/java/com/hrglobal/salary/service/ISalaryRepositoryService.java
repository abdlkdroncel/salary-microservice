package com.hrglobal.salary.service;

import com.hrglobal.salary.enums.Language;
import com.hrglobal.salary.repository.entity.PersonalSalary;
import com.hrglobal.salary.request.SalaryCreateRequest;
import com.hrglobal.salary.request.SalaryUpdateRequest;

import java.util.List;

public interface ISalaryRepositoryService {

    PersonalSalary calculateSalary(Language language, SalaryCreateRequest salaryCreateRequest);

    PersonalSalary getSalary(Language language, Long id);

    List<PersonalSalary> getSalaries(Language language);

    PersonalSalary updateSalary(Language language, Long id, SalaryUpdateRequest salaryUpdateRequest);

    PersonalSalary deleteSalary(Language language, Long id);

}
