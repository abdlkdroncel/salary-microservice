package com.hrglobal.salary.controller;


import com.hrglobal.salary.enums.Language;
import com.hrglobal.salary.exception.enums.FriendlyMessageCodes;
import com.hrglobal.salary.exception.utils.FriendlyMessageUtils;
import com.hrglobal.salary.repository.entity.PersonalSalary;
import com.hrglobal.salary.request.SalaryCreateRequest;
import com.hrglobal.salary.request.SalaryUpdateRequest;
import com.hrglobal.salary.response.FriendlyMessage;
import com.hrglobal.salary.response.InternalApiResponse;
import com.hrglobal.salary.response.SalaryResponse;
import com.hrglobal.salary.service.ISalaryRepositoryService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/api/1.0/salary")
@RequiredArgsConstructor
class SalaryController {
    private final ISalaryRepositoryService salaryRepositoryService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{language}/calculate")
    public InternalApiResponse<SalaryResponse> calculateSalary(@PathVariable("language") Language language,
                                                             @RequestBody SalaryCreateRequest salaryCreateRequest) {
        log.debug("[{}][calculateSalary] -> request: {}", this.getClass().getSimpleName(), salaryCreateRequest);
        PersonalSalary salary = salaryRepositoryService.calculateSalary(language, salaryCreateRequest);
        SalaryResponse salaryResponse= convertSalaryResponse(salary);
        log.debug("[{}][calculateSalary] -> response: {}", this.getClass().getSimpleName(), salaryResponse);
        return InternalApiResponse.<SalaryResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SALARY_SUCCESSFULLY_CALCULATED))
                        .build())
                .httpStatus(HttpStatus.CREATED)
                .hasError(false)
                .payload(salaryResponse)
                .build();

    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{language}/updateSalary")
    public InternalApiResponse<SalaryResponse> updateSalary(@PathVariable("language") Language language,
                                                               @RequestBody SalaryUpdateRequest salaryUpdateRequest) {
        log.debug("[{}][updateSalary] -> request: {}", this.getClass().getSimpleName(), salaryUpdateRequest);
        PersonalSalary salary = salaryRepositoryService.updateSalary(language, salaryUpdateRequest.getId(),salaryUpdateRequest);
        SalaryResponse salaryResponse= convertSalaryResponse(salary);
        log.debug("[{}][updateSalary] -> response: {}", this.getClass().getSimpleName(), salaryResponse);
        return InternalApiResponse.<SalaryResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SALARY_SUCCESSFULLY_CALCULATED))
                        .build())
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(salaryResponse)
                .build();

    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{language}/get/{id}")
    public InternalApiResponse<SalaryResponse> getSalary(@PathVariable("language") Language language,
                                                             @PathVariable("id") Long id) {
        log.debug("[{}][getSalary] -> request: {}", this.getClass().getSimpleName(), id);
        PersonalSalary salary = salaryRepositoryService.getSalary(language, id);
        SalaryResponse salaryResponse= convertSalaryResponse(salary);
        log.debug("[{}][getSalary] -> response: {}", this.getClass().getSimpleName(), salaryResponse);
        return InternalApiResponse.<SalaryResponse>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(salaryResponse)
                .build();

    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{language}/delete/{id}")
    public InternalApiResponse<SalaryResponse> deleteSalary(@PathVariable("language") Language language,
                                                         @PathVariable Long id) {
        log.debug("[{}][delete] -> request: {}", this.getClass().getSimpleName(), id);
        PersonalSalary salary = salaryRepositoryService.deleteSalary(language, id);
        SalaryResponse salaryResponse= convertSalaryResponse(salary);
        log.debug("[{}][delete] -> response: {}", this.getClass().getSimpleName(),salaryResponse);
        return InternalApiResponse.<SalaryResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SALARY_SUCCESSFULLY_CALCULATED))
                        .build())
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(salaryResponse)
                .build();

    }

    @ApiOperation("Get All Product")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{language}/salaries")
    public InternalApiResponse<List<SalaryResponse>> getSalaries(@PathVariable("language") Language language) {
        List<PersonalSalary> salary = salaryRepositoryService.getSalaries(language);
        List<SalaryResponse> collect = salary.stream().map(t -> convertSalaryResponse(t)).collect(Collectors.toList());
        log.debug("[{}][getSalaries] -> response: {}", this.getClass().getSimpleName(), collect);
        return InternalApiResponse.<List<SalaryResponse>>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(collect)
                .build();

    }

    private SalaryResponse convertSalaryResponse(PersonalSalary salary) {
        return SalaryResponse.builder()
                .id(salary.getId())
                .staffName(salary.getStaffName())
                .staffLastname(salary.getStaffLastname())
                .gross(salary.getGross())
                .incomeTax(salary.getIncomeTax())
                .stampTax(salary.getStampTax())
                .sgkUnemployeeWorker(salary.getSgkUnemployeeWorker())
                .sgkWorker(salary.getSgkWorker())
                .net(salary.getNet())
                .salaryUpdatedDate(salary.getSalaryUpdatedDate().getTime())
                .salaryCreatedDate(salary.getSalaryCreatedDate().getTime())
                .build();
    }

}
