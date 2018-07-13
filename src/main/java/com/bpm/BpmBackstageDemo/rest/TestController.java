package com.bpm.BpmBackstageDemo.rest;

import com.bpm.BpmBackstageDemo.domain.LeaveInfo;
import com.bpm.BpmBackstageDemo.domain.LeaveState;
import com.bpm.BpmBackstageDemo.enums.Events;
import com.bpm.BpmBackstageDemo.enums.States;
import com.bpm.BpmBackstageDemo.model.LeaveMessage;
import com.bpm.BpmBackstageDemo.model.ResultDto;
import com.bpm.BpmBackstageDemo.repository.LeaveStateRepository;
import com.bpm.BpmBackstageDemo.service.LeaveService;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

@RestController
public class TestController {

    @Autowired
    private StateMachine<States, Events> stateMachine;

    @Autowired
    private LeaveService leaveService;

    @Autowired
    private LeaveStateRepository leaveStateRepository;

    @GetMapping("/stateMachine")
    public ResultDto<Object> queryLeaves() {
         List<LeaveMessage> leaveMessages = leaveService.query();
        return ResultDto.builder().code(0).isSuccess(true).message("success").data(leaveMessages).build();
    }


    @PostMapping("/stateMachine")
    public ResultDto<Object> testMachine(@RequestBody LeaveInfo leave) {
        long nd = 1000 * 24 * 60 * 60;
        Long days = (leave.getEndDate().getTime() - leave.getStartDate().getTime()) / nd + 1;
        System.out.println("days" + days);
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("ksession-leaveRule");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        LeaveState leaveState = leaveStateRepository.findOneById(leave.getState());
        LeaveMessage leaveMessage = LeaveMessage.builder().id(leave.getId()).
                days(days).
                startDate(simpleDateFormat.format(leave.getStartDate())).
                endDate(simpleDateFormat.format(leave.getEndDate())).
                comments(leave.getComments())
                .build();
        kieSession.insert(leaveMessage);
        int count = kieSession.fireAllRules();
        kieSession.dispose();
        System.out.println("命中了" + count + "条规则！");
        String msg = leaveService.saveHour(leaveMessage);
        return ResultDto.builder().code(0).isSuccess(true).message(msg).data(null).build();
//        stateMachine.stop();
//        stateMachine.start();
//        System.out.println(stateMachine.getInitialState());
//        System.out.println( stateMachine.getUuid());
//        stateMachine.sendEvent(Events.SUPERVISOR_APPROVAL);
//        stateMachine.sendEvent(Events.PERSONNEL_CONFIRM);
    }

}
