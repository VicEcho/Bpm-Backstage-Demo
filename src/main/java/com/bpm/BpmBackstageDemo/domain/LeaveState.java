package com.bpm.BpmBackstageDemo.domain;

import lombok.*;

import javax.persistence.*;

@Builder
@Entity
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table
public class LeaveState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
