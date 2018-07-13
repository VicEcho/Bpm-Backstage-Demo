package com.bpm.BpmBackstageDemo.config;

import com.bpm.BpmBackstageDemo.enums.Events;
import com.bpm.BpmBackstageDemo.enums.States;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.transition.Transition;

import java.util.EnumSet;

@Configuration
@EnableStateMachine
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
        states
                .withStates()
                .initial(States.WAITING_SUPERVISOR_APPROVAL)
                .states(EnumSet.allOf(States.class));

    }

    @Override
    public void  configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
        transitions
                .withExternal()
                .source(States.WAITING_SUPERVISOR_APPROVAL).target(States.WAITING_PERSONNEL_CONFIRM)// 指定状态来源和目标
                .event(Events.SUPERVISOR_APPROVAL)    // 指定触发事件
                .and()
                .withExternal()
                .source(States.WAITING_PERSONNEL_CONFIRM).target(States.DONE)
                .event(Events.PERSONNEL_CONFIRM);
    }

//    @Override
//    public void configure(StateMachineConfigurationConfigurer<States, Events> config)
//            throws Exception {
//        config
//                .withConfiguration()
//                .listener(listener());  // 指定状态机的处理监听器
//    }

//    @Bean
//    public StateMachineListener<States, Events> listener() {
//        return new StateMachineListenerAdapter<States, Events>() {
//
//            @Override
//            public void transition(Transition<States, Events> transition) {
//                if (transition.getTarget().getId() == States.WAITING_SUPERVISOR_APPROVAL) {
//                    System.out.println("提交请假申请，待主管审核");
//                    return;
//                }
//                if (transition.getSource().getId() == States.WAITING_SUPERVISOR_APPROVAL
//                        && transition.getTarget().getId() == States.WAITING_PERSONNEL_CONFIRM) {
//                    System.out.println("主管审核完成，待人事审核");
//                    return;
//                }
//
//                if (transition.getSource().getId() == States.WAITING_PERSONNEL_CONFIRM
//                        && transition.getTarget().getId() == States.DONE) {
//                    System.out.println("人事审核完成，归档");
//                    return;
//                }
//            }
//
//        };
//      }
    }
