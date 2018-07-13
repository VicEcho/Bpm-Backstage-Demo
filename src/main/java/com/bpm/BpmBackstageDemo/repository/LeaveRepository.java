package com.bpm.BpmBackstageDemo.repository;

import com.bpm.BpmBackstageDemo.domain.LeaveInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRepository extends JpaRepository<LeaveInfo, Long>, JpaSpecificationExecutor<LeaveInfo> {
//    Leave findOne(Long id);
    List<LeaveInfo> findAllByOrderByIdDesc();

}
