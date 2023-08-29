package com.hrglobal.salary.exception.enums;

public enum FriendlyMessageCodes implements IFriendlyMessageCode {
    OK(1000),
    ERROR(1001),
    SUCCESS(1002),

    SALARY_NOT_CALCULATED_EXCEPTION(1500),
    SALARY_SUCCESSFULLY_CALCULATED(1501),
    SALARY_NOT_FOUND_EXCEPTION(1502),
    SALARY_SUCCESSFULLY_UPDATED(1503),
    SALARY_ALREADY_DELETED(1504);

    private final int value;

    FriendlyMessageCodes(int value) {
        this.value = value;
    }

    @Override
    public int getFriendlyMessageCode() {
        return value;
    }
}
