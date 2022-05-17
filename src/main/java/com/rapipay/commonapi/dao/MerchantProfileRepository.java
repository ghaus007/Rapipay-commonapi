package com.rapipay.commonapi.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nimbusds.jose.shaded.json.JSONObject;
import com.portal.common.models.Response;
import com.rapipay.commonapi.constants.DatabaseProcedures;
import com.rapipay.commonapi.utils.StatusCode;
@Repository
public class MerchantProfileRepository {
	@Autowired
	EntityManager entityManager;

	Logger log = LoggerFactory.getLogger(NotificationsDao.class);

	public JSONObject  getMerchantProfileDetails(String mid, Response response) {
		Session session = entityManager.unwrap(Session.class);
		JSONObject jsonObject = new JSONObject();

		session.doWork(new Work() {
			public void execute(Connection connection) throws SQLException {
				CallableStatement callst = null;
				try {
					callst = connection.prepareCall("{call" + DatabaseProcedures.GET_MERCHANT_KYC_DETAILS + "(?)}");
					callst.setString(1, mid);
					callst.execute();
					ResultSet resultSet = callst.getResultSet();

					if (resultSet.next()) {

						jsonObject.put("ACCOUNT_HOLDER_NAME", resultSet.getString("ACCOUNT_HOLDER_NAME"));
						jsonObject.put("ADDRESS", resultSet.getString("ADDRESS"));
						jsonObject.put("ADD_BACK", resultSet.getString("ADD_BACK"));
						jsonObject.put("ADD_FRONT", resultSet.getString("ADD_FRONT"));
						jsonObject.put("BANK_ACCOUNT_NUMBER", resultSet.getString("BANK_ACCOUNT_NUMBER"));
						jsonObject.put("BANK_IFSC", resultSet.getString("BANK_IFSC"));
						jsonObject.put("BANK_NAME", resultSet.getString("BANK_NAME"));
						jsonObject.put("BRAND_NAME", resultSet.getString("BRAND_NAME"));
						jsonObject.put("BusinessCity", resultSet.getString("BusinessCity"));
						jsonObject.put("CITY", resultSet.getString("CITY"));
						jsonObject.put("DISTRICT", resultSet.getString("DISTRICT"));
						jsonObject.put("DOBonDoc", resultSet.getString("DOBonDoc"));
						jsonObject.put("DOC_NO", resultSet.getString("DOC_NO"));
						jsonObject.put("DocAddress", resultSet.getString("DocAddress"));
						jsonObject.put("KYC_BUSINESS_NAME", resultSet.getString("KYC_BUSINESS_NAME"));
						jsonObject.put("KYC_Doc_Format", resultSet.getString("KYC_Doc_Format"));
						jsonObject.put("KYC_Doc_Format_PAN", resultSet.getString("KYC_Doc_Format_PAN"));
						jsonObject.put("KYC_Doc_Name_PAN", resultSet.getString("KYC_Doc_Name_PAN"));
						jsonObject.put("KYC_Doc_Type", resultSet.getString("KYC_Doc_Type"));
						jsonObject.put("KYC_Doc_Type_PAN", resultSet.getString("KYC_Doc_Type_PAN"));
						jsonObject.put("MCC", resultSet.getString("MCC"));
						jsonObject.put("MERCHANT_EMAIL", resultSet.getString("MERCHANT_EMAIL"));
						jsonObject.put("MERCHANT_MOBILE", resultSet.getString("MERCHANT_MOBILE"));
						jsonObject.put("MERCHANT_REF_ID", resultSet.getString("MERCHANT_REF_ID"));
						jsonObject.put("MERCHANT_TYPE", resultSet.getString("MERCHANT_TYPE"));
						jsonObject.put("MId", resultSet.getString("MId"));
						jsonObject.put("PAN_Image", resultSet.getString("PAN_Image"));
						jsonObject.put("PAN_NO", resultSet.getString("PAN_NO"));
						jsonObject.put("PinCode", resultSet.getString("PinCode"));
						jsonObject.put("STATE", resultSet.getString("STATE"));
						jsonObject.put("STATE_NAME", resultSet.getString("STATE_NAME"));
						jsonObject.put("STORE_NAME", resultSet.getString("STORE_NAME"));
						jsonObject.put("ZIP_CODE", resultSet.getString("ZIP_CODE"));
						

					
					
					}
					
					
				

					response.setResponseCode(StatusCode.SUCCESS_200.getCode());
					response.setResponseMessage(StatusCode.SUCCESS_200.getMessage());
					response.setResponseData(jsonObject);

				} catch (SQLException e) {

					response.setResponseCode(StatusCode.EXCEPTION_501.getCode());
					response.setResponseMessage(StatusCode.EXCEPTION_501.getMessage());
					response.setResponseData(e.getMessage());
				} finally {
					if (callst != null) {
						callst.close();
					}
				}
			}

		});
		return jsonObject;

	}
}
