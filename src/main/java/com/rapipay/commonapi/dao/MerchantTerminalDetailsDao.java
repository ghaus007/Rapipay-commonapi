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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.json.JSONArray;
import com.portal.common.models.Response;
import com.rapipay.commonapi.constants.DatabaseProcedures;
import com.rapipay.commonapi.utils.StatusCode;

@Repository
public class MerchantTerminalDetailsDao {
    @Autowired
    ObjectMapper mapper;
    
	@Autowired
	EntityManager entityManager;
	/*
	 * public JSONObject getMerchantProfileDetails(String string, Response response)
	 * { // TODO Auto-generated method stub return null; }
	 */
    private static final Logger log = LoggerFactory.getLogger(MerchantTerminalDetailsDao.class);

	public void getMerchantTerminalDetails(String mid,Response response) {
		
		
	    Session session = entityManager.unwrap(Session.class);
	    

	    
	    session.doWork(new Work() {
	        public void execute(Connection connection) throws SQLException {
	        	String procedure=
	    		DatabaseProcedures.GET_MERCHANT_TID_DETAILS;
	    		
	            log.info("inside of GET TID DETAILS");
	            CallableStatement callst = null;
	            ResultSet resultSet = null;
	            try {
	                callst = connection.prepareCall("{call " + procedure + "(?)}");
	                callst.setString(1, mid);
	                
	                callst.execute();
	                resultSet = callst.getResultSet();
	                log.info("Success in call {}",procedure);


	                if (resultSet.next()) {

	                	JSONArray terminalDetails=mapper.readValue(resultSet.getString(1), JSONArray.class);
	                	
	                	response.setResponseCode(StatusCode.SUCCESS_200.getCode());
	                	response.setResponseMessage(StatusCode.SUCCESS_200.getMessage());
	                	response.setResponseData(terminalDetails);
	                   
	                } 


	            } catch (Exception e) {
	            	response.setResponseCode(StatusCode.EXCEPTION_501.getCode());
	            	response.setResponseMessage(StatusCode.EXCEPTION_501.getMessage());
	            	response.setResponseData(e.getMessage());
				}  finally {
	                if (callst != null) {
	                    callst.close();
	                }
	            }
	        }
	    });


	}

}
