package com.bpm.BpmBackstageDemo.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Builder
public class LeaveMessage {

    private Long id;

    private String startDate;

    private String endDate;

    private String comments;

    private Long state;

    private String stateName;

    private Long days;

    private String result;
}
