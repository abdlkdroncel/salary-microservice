package com.hrglobal.salary.repository;

import com.hrglobal.salary.repository.entity.PersonalSalary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalaryRepository extends JpaRepository<PersonalSalary, Long> {

    PersonalSalary getByIdAndDeletedFalse(Long id);
    List<PersonalSalary> getAllByDeletedFalse();
}
