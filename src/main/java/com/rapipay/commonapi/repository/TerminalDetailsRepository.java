package com.rapipay.commonapi.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rapipay.commonapi.entity.TerminalUser;

@Repository
@Transactional
public interface TerminalDetailsRepository extends JpaRepository<TerminalUser, String> {

	
	
	  @Query( value =
	  "SELECT USER_ID, USER_NAME,USER_MOBILE_NO,DESCRIPTION,USER_LOGIN_ID,CREATED_ON,CREATED_BY,UPDATED_ON,UPDATED_BY FROM MST.TERMINAL_USER_DETAILS WHERE user_login_id= ?"
	  , nativeQuery = true )
	 
	TerminalUser findByUserId(String user_login_id);

	 @Modifying
	    @Query(value = "UPDATE MST.TERMINAL_USER_DETAILS SET USER_MOBILE_NO=?,USER_NAME=? WHERE USER_LOGIN_ID = ?", nativeQuery = true)
	    int updateUserInformation(String mobileNo,String userName ,String userId);
	    

}
