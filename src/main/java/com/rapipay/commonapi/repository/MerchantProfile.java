
package com.rapipay.commonapi.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantProfile /*
									 * extends JpaRepository<MerchantMaster, Long>
									 */									  {

	@Transactional

	@Modifying

	@Query(value = "UPDATE [MST].[MERCHANT_MASTER] SET MERCHANT_EMAIL = :emailId , ADDRESS = :address "
			+ "WHERE MERCHANT_MOBILE = :mobile", nativeQuery = true)
	int updateMerchantInformation(@Param("emailId") String emailId, @Param("address") String address,
			@Param(("mobile")) String merchantMobileNo);

}
