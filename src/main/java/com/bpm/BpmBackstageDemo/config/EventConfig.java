package com.bpm.BpmBackstageDemo.config;

import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;

@WithStateMachine
public class EventConfig {

    @OnTransition(target = "WAITING_SUPERVISOR_APPROVAL")
    public void create() {
        System.out.println("提交请假申请，待主管审核");
    }
    @OnTransition(source = "WAITING_SUPERVISOR_APPROVAL", target = "WAITING_PERSONNEL_CONFIRM")
    public void check() {
        System.out.println("主管审核完成，待人事审核");
    }
    @OnTransition(source = "WAITING_PERSONNEL_CONFIRM", target = "DONE")
    public void done() {
        System.out.println("人事审核完成，归档");
    }

}
