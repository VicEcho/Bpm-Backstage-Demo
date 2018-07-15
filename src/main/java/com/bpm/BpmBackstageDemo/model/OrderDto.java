package com.bpm.BpmBackstageDemo.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class OrderDto {

    private Long id;

    private String name;

    private Integer money;

    private String customer;

    private String ticket;
}
