package com.rapipay.commonapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rapipay.commonapi.entity.NotificationEntity;
@Repository
public interface NotificationsRepository extends JpaRepository<NotificationEntity, String> {
	

    @Query(
			value = " SELECT TOP 15 N_id,Name,Description,FORMAT(CAST(Creation_Date as datetimeoffset), 'yyyy-MM-dd hh:mm:ss') AS Creation_Date,View_Status \n"
					+ "FROM INF.Notification WHERE User_id=?1",		nativeQuery = true
    )
    List<NotificationEntity> getMerchantNotification(String userId);
}
