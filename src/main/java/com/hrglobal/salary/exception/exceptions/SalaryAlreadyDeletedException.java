package com.hrglobal.salary.exception.exceptions;

import com.hrglobal.salary.enums.Language;
import com.hrglobal.salary.exception.enums.IFriendlyMessageCode;
import com.hrglobal.salary.exception.utils.FriendlyMessageUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class SalaryAlreadyDeletedException extends RuntimeException{

    private final Language language;
    private final IFriendlyMessageCode friendlyMessageCode;

    public SalaryAlreadyDeletedException(Language language, IFriendlyMessageCode friendlyMessageCode,String message) {
        super(FriendlyMessageUtils.getFriendlyMessage(language,friendlyMessageCode));
        this.language = language;
        this.friendlyMessageCode = friendlyMessageCode;
        log.error("[SalaryAlreadyDeletedException] -> message: {} developer message: {}", FriendlyMessageUtils.getFriendlyMessage(language, friendlyMessageCode), message);

    }
}
