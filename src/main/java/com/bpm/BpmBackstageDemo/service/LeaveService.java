package com.bpm.BpmBackstageDemo.service;

import com.bpm.BpmBackstageDemo.domain.LeaveInfo;
import com.bpm.BpmBackstageDemo.domain.LeaveState;
import com.bpm.BpmBackstageDemo.model.LeaveMessage;
import com.bpm.BpmBackstageDemo.repository.LeaveRepository;
import com.bpm.BpmBackstageDemo.repository.LeaveStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class LeaveService {

    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private LeaveStateRepository leaveStateRepository;

    public List<LeaveMessage> query() {
        List<LeaveInfo> leaveInfos = leaveRepository.findAllByOrderByIdDesc();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return leaveInfos.stream().map(item -> {
            int nd = 1000 * 24 * 60 * 60;
            Long days = (item.getEndDate().getTime() - item.getStartDate().getTime()) / nd;
            return LeaveMessage.builder().id(item.getId()).
                days(days).
                startDate(simpleDateFormat.format(item.getStartDate())).
                endDate(simpleDateFormat.format(item.getEndDate())).
                state(item.getState().getId()).
                stateName(item.getState().getName()).
                comments(item.getComments())
                .build();
        }).collect(Collectors.toList());
    }

    public String saveHour(LeaveMessage lm) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
//            LeaveState leaveState = LeaveState.builder().id(lm.getState()).build();
            LeaveState leaveState = leaveStateRepository.findOneById(lm.getState());
            LeaveInfo leave = LeaveInfo.builder().
                    comments(lm.getComments()).
                    startDate(simpleDateFormat.parse(lm.getStartDate())).
                    endDate(simpleDateFormat.parse(lm.getEndDate())).
                    state(leaveState).build();
            System.out.println("word" + " " + lm.getResult());
            leaveRepository.save(leave);
            return lm.getResult();
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

}
