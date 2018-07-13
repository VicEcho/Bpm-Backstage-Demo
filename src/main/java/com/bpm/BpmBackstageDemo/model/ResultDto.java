package com.bpm.BpmBackstageDemo.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class ResultDto<T> {

    private Integer code;

    private String message;

    private Boolean isSuccess;

    private T data;
}
