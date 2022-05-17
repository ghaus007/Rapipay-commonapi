package com.rapipay.commonapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.rapipay.commonapi.entity.TerminalMaster;

@Repository
@Transactional
public interface TerminalMasterRepository  extends JpaRepository<TerminalMaster, String>{
	TerminalMaster findByTerminalSerialNo(String deviceSerialNo);

	
	/*
	 * @Query(
	 * value="SELECT TM_ID FROM MST.TERMINAL_MASTER  WHERE TERMINAL_SERIAL_NO= ?1"
	 * ,nativeQuery = true)
	 * 
	 * @Query( value =
	 * "SELECT p FROM MST.TERMINAL_MASTER p WHERE p.terminalSerialNo= ?1"
	 * 
	 * )
	 */
	

}
