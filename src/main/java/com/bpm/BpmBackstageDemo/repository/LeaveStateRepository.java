package com.bpm.BpmBackstageDemo.repository;

import com.bpm.BpmBackstageDemo.domain.LeaveState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LeaveStateRepository extends JpaRepository<LeaveState, Long>, JpaSpecificationExecutor<LeaveState> {
    LeaveState findOneById( Long id );

}
