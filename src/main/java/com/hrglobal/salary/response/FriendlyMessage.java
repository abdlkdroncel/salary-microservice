package com.hrglobal.salary.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FriendlyMessage {

    /**
     * Controller ve diger mesajlar için kullanılacak
     */
    private String title;
    private String description;
    private String buttonPositive;
}
