package com.rapipay.commonapi.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.json.JSONArray;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.portal.common.models.Response;
import com.rapipay.commonapi.constants.DatabaseProcedures;
import com.rapipay.commonapi.entity.TerminalUser;
import com.rapipay.commonapi.entity.UsersList;
import com.rapipay.commonapi.repository.ManageUsersRepository;
import com.rapipay.commonapi.repository.TerminalDetailsRepository;
import com.rapipay.commonapi.requestdto.CreateSubUserDto;
import com.rapipay.commonapi.requestdto.UpdateUserDataDto;
import com.rapipay.commonapi.utils.StatusCode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class ManageUsersRepo {

	@Autowired
	ObjectMapper mapper;
	
	@Autowired
	ManageUsersRepository manageUsersRepository;

	@Autowired
	EntityManager entityManager;
	

	
	@Autowired
	EntityManagerFactory entityManagerFactory;
	
	@Autowired
	TerminalDetailsRepository terminalDetailsRepository;

	private static final Logger log = LoggerFactory.getLogger(ManageUsersRepo.class);

	public void createSubUserviaProc(CreateSubUserDto createSubUserDto, Response response) {

		Session session = entityManager.unwrap(Session.class);

		session.doWork(new Work() {
			public void execute(Connection connection) throws SQLException {
				String procedure = DatabaseProcedures.CREATE_SUB_USER;

				log.info("inside of CREATE_SUB_USER");
				CallableStatement callst = null;
				ResultSet resultSet = null;
				try {
					callst = connection.prepareCall("{call " + procedure + "(?,?,?,?,?,?,?,?,?,?)}");
					callst.setString(1, createSubUserDto.getMid());
					callst.setString(2, createSubUserDto.getTid());
					callst.setString(3, createSubUserDto.getTofsId());
					callst.setString(4, createSubUserDto.getDeviceSerialNo());
					callst.setString(5, createSubUserDto.getPassword());
					callst.setString(6, createSubUserDto.getUserId());
					callst.setString(7, createSubUserDto.getUserName());
					callst.setString(8, createSubUserDto.getMobileNo());
					callst.setString(9, createSubUserDto.getUserAccess().equals("merchantUser") ? "8" : "9");
					callst.setString(10, createSubUserDto.getCreatedBy());

					callst.execute();
					resultSet = callst.getResultSet();
					log.info("Success in call {}", procedure);

					JSONObject responseData = new JSONObject();
					if (resultSet.next()) {

						responseData.put("responseCode", resultSet.getString("ResponseCode"));
						responseData.put("responseMessage", resultSet.getString("ResponseMsg"));
						response.setResponseCode(StatusCode.SUCCESS_200.getCode());
						response.setResponseMessage(StatusCode.SUCCESS_200.getMessage());
						response.setResponseData(responseData);

					}

				} catch (Exception e) {
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

	}

	public void getRevRkiStatus(CreateSubUserDto createSubUserDto, Response response, JSONObject responseData) {

		Session session = entityManager.unwrap(Session.class);

		session.doWork(new Work() {
			public void execute(Connection connection) throws SQLException {
				String procedure = DatabaseProcedures.GET_REV_RKI_STATUS;

				log.info("inside of CREATE_SUB_USER");
				CallableStatement callst = null;
				ResultSet resultSet = null;
				try {
					callst = connection.prepareCall("{call " + procedure + "(?,?,?)}");
					callst.setString(2, createSubUserDto.getMid());
					callst.setString(3, createSubUserDto.getTid());
					callst.setString(1, createSubUserDto.getUserId());

					callst.execute();
					resultSet = callst.getResultSet();
					log.info("Success in call {}", procedure);

					if (resultSet.next()) {

						responseData.put("isRki", resultSet.getString("RKI"));
						responseData.put("isRev", resultSet.getString("REV"));

					}

				} catch (Exception e) {
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

	}

	public void getRevStatusFlag(CreateSubUserDto createSubUserDto, Response response, JSONObject responseData) {

		Session session = entityManager.unwrap(Session.class);

		session.doWork(new Work() {
			public void execute(Connection connection) throws SQLException {
				String procedure = DatabaseProcedures.GET_REV_STATUS;

				log.info("inside of CREATE_SUB_USER");
				CallableStatement callst = null;
				ResultSet resultSet = null;
				try {
					callst = connection.prepareCall("{call " + procedure + "(?)}");
					callst.setString(1, createSubUserDto.getDeviceSerialNo());

					callst.execute();
					resultSet = callst.getResultSet();
					log.info("Success in call {}", procedure);

					if (resultSet.next()) {

						responseData.put("revStatus", resultSet.getString(1));

					}

				} catch (Exception e) {
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

	}

	/*
	 * public void updateUserInformation(UpdateUserDataDto updateUserDataDto,
	 * Response response) { Session session = entityManager.unwrap(Session.class);
	 * 
	 * 
	 * session.doWork(new Work() { public void execute(Connection connection) throws
	 * SQLException { String procedure =
	 * DatabaseProcedures.UPDATE_POS_TERMINAL_USER_INFO;
	 * 
	 * log.info("inside of CREATE_SUB_USER"); CallableStatement callst = null;
	 * ResultSet resultSet = null; try { callst = connection.prepareCall("{call " +
	 * procedure + "(?,?,?,?,?,?,?,?,?)}");
	 * 
	 * callst.setString(1, updateUserDataDto.getMerchantUserId());
	 * callst.setString(2, updateUserDataDto.getTerminalUserId());
	 * callst.setString(3, updateUserDataDto.getTerminalUserId());
	 * callst.setString(4, updateUserDataDto.getMid()); callst.setString(5,
	 * updateUserDataDto.getTid()); callst.setString(6,
	 * updateUserDataDto.getUserAccess().equals("merchantUser")?"8":"9");
	 * callst.setString(7, updateUserDataDto.getAuthPin());
	 * 
	 * 
	 * callst.setString(8, updateUserDataDto.getUserName()); callst.setString(9,
	 * updateUserDataDto.getMobileNo()); callst.execute(); resultSet =
	 * callst.getResultSet(); log.info("Success in call {}", procedure);
	 * 
	 * JSONObject responseData = new JSONObject(); if (resultSet.next()) {
	 * 
	 * responseData.put("responseCode", resultSet.getString("ResponseCode"));
	 * responseData.put("responseMessage", resultSet.getString("ResponseMsg"));
	 * response.setResponseCode(StatusCode.SUCCESS_200.getCode());
	 * response.setResponseMessage(StatusCode.SUCCESS_200.getMessage());
	 * response.setResponseData(responseData);
	 * 
	 * }
	 * 
	 * 
	 * } catch (Exception e) {
	 * response.setResponseCode(StatusCode.EXCEPTION_501.getCode());
	 * response.setResponseMessage(StatusCode.EXCEPTION_501.getMessage());
	 * response.setResponseData(e.getMessage()); } finally { if (callst != null) {
	 * callst.close(); } } } });
	 * 
	 * 
	 * }
	 */

	public void updateUserInformation(UpdateUserDataDto updateUserDataDto, Response response) {
		

		UsersList usersList = null;
			usersList = manageUsersRepository.findByUserId(updateUserDataDto.getUserId());
            usersList.setPassword(updateUserDataDto.getAuthPin());
            usersList.setAccessType(updateUserDataDto.getUserAccess().equals("terminalUser")?"9":"8");
           TerminalUser terminalUser=terminalDetailsRepository.findByUserId(String.valueOf(usersList.getId()));
			
			  terminalUser.setUserName(updateUserDataDto.getUserName());
			  terminalUser.setUserMobileNo(updateUserDataDto.getMobileNo());
			  try {
				EntityManager entityManager = entityManagerFactory.createEntityManager();
					entityManager.getTransaction().begin();
				    entityManager.merge(usersList);
				    entityManager.merge(terminalUser);
				   entityManager.getTransaction().commit();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		// Save Account
	

		
		response.setResponseCode(StatusCode.SUCCESS_200.getCode());
	}
	
public void createSubUser(CreateSubUserDto createSubUserDto, Response response) {
	UsersList usersList =new UsersList();
	TerminalUser terminalUser =new TerminalUser();

                  usersList.setAccessType(createSubUserDto.getUserAccess().equals("terminalUser")?"9":"8");
                  usersList.setDeviceSerialNo(createSubUserDto.getDeviceSerialNo());
                  usersList.setMid(createSubUserDto.getMid());
                  usersList.setPassword(createSubUserDto.getPassword());
                  usersList.setStatus("1");
                  usersList.setUserId(createSubUserDto.getUserId());
                  usersList.setTid(createSubUserDto.getTid());

                  usersList.setUpdatedBy("adminUser");
					
					  terminalUser.setUserName(createSubUserDto.getUserName());
					  terminalUser.setUserMobileNo(createSubUserDto.getMobileNo());
					 

					  try {
				EntityManager entityManager = entityManagerFactory.createEntityManager();
					entityManager.getTransaction().begin();
				    entityManager.persist(usersList);
					
					  terminalUser.setUserLoginId(String.valueOf(usersList.getId()));
					    entityManager.persist(terminalUser);
				    

				   entityManager.getTransaction().commit();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				response.setResponseCode(StatusCode.ERROR_501.getCode());

			}

		// Save Account
	

		
		response.setResponseCode(StatusCode.SUCCESS_200.getCode());
	}

	public int updateTerminalUserInfo(String userAccess, String authPin, String mobileNo, String userName,
			String userId, String mid, String tid) {
		
		SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
		Session session = sessionFactory.openSession();
		

		// TODO Auto-generated method stub
		return 0;
	}

}
