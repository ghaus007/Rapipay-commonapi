package com.rapipay.commonapi.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rapipay.commonapi.entity.UsersList;

@Repository
@Transactional
public interface ManageUsersRepository extends JpaRepository<UsersList, String> {

	@Query(
			value = "SELECT LN_ID,MID,TID,USER_ID,STATUS,DEVICE_SERIAL_NO,Access_Type,PASSWORD,UPDATED_ON,UPDATED_BY FROM POS.USER_LOGIN_INFO WHERE MID = ? AND TID = ?"
					,		nativeQuery = true
    )
    List<UsersList> findByMidAndTids(String mid, String tid);
    
	
	@Query(
			value = "SELECT LN_ID,MID,TID,USER_ID,STATUS,DEVICE_SERIAL_NO,Access_Type,PASSWORD,UPDATED_ON,UPDATED_BY FROM POS.USER_LOGIN_INFO WHERE MID = ? "
					,		nativeQuery = true
    )
    List<UsersList> findByMids(String mid);
	
	/*
	 * @Query( value =
	 * "SELECT LN_ID,USER_ID,PASSWORD,STATUS,Access_Type from pos.user_login_info where user_id= ?"
	 * , nativeQuery = true )
	 */
    UsersList findByUserId(String userId);
    
    
   List< UsersList> findByTid(String tid);

    @Modifying
    @Query(value = "UPDATE POS.USER_LOGIN_INFO SET STATUS = ? WHERE USER_ID = ?", nativeQuery = true)
    int updateUserData(String userStatus, String userId);
    
  
    
	



}
