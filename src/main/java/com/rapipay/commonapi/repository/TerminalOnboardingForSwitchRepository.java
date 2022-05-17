package com.rapipay.commonapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.rapipay.commonapi.entity.TerminalOnBoardingForSwitch;

@Repository
@Transactional
public interface TerminalOnboardingForSwitchRepository extends JpaRepository<TerminalOnBoardingForSwitch, String> {

	@Query(
			value = "SELECT p FROM TerminalOnBoardingForSwitch p WHERE p.mid= ?1 AND p.tid= ?2"
					
    )
TerminalOnBoardingForSwitch getRkiRevStatus(String mid, String tid);

	
	
	
TerminalOnBoardingForSwitch findByMidAndTid(String mid, String tid);

}
