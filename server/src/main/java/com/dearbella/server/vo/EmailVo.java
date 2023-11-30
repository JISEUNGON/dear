package com.dearbella.server.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class EmailVo {
    private String to;
    private String subject;
    private String message;
}
