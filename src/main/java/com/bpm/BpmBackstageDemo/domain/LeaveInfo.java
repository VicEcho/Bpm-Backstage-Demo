package com.bpm.BpmBackstageDemo.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table
public class LeaveInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comments;

    private Date startDate;

    private Date endDate;

    /**
     * 0 提交待组长审核  1 待部门负责人审核 2 待人力资源审核 3 归档
     * */
    @ManyToOne
    private LeaveState state;

}
