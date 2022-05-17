package com.rapipay.commonapi.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;


import com.portal.common.models.Response;

import com.rapipay.commonapi.constants.DatabaseProcedures;
import com.rapipay.commonapi.utils.StatusCode;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nimbusds.jose.shaded.json.JSONObject;
import com.rapipay.commonapi.requestdto.CountNotificationsDto;
import com.rapipay.commonapi.requestdto.GetNotificationsDto;
import com.rapipay.commonapi.requestdto.UpdateNotificationsViewDto;

@Repository
@Transactional
public class NotificationsDao {
    @Autowired
    EntityManager entityManager;

    Logger log = LoggerFactory.getLogger(NotificationsDao.class);

    public void getNotificationsList(GetNotificationsDto getNotificationsDto, Response response) {
        Session session = entityManager.unwrap(Session.class);
        session.doWork(new Work() {
            public void execute(Connection connection) throws SQLException {
                CallableStatement callst = null;
                try {

                    ResultSet resultSet = null;
                    callst = connection.prepareCall("{call " + DatabaseProcedures.GET_NOTIFICATION_LIST + "(?)}");
                    callst.setString(1, getNotificationsDto.getUserId());
                    callst.execute();
                    resultSet = callst.getResultSet();
                    ArrayList<Object> arrList = new ArrayList<Object>();

                    while (resultSet.next()) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("N_id", resultSet.getString(1));
                        jsonObject.put("Name", resultSet.getString(2));
                        jsonObject.put("Description", resultSet.getString(3));
                        jsonObject.put("Creation_Date", resultSet.getString(4));
                        if (resultSet.getString(5).equals("1")) {
                            jsonObject.put("View_Status", true);
                        } else {
                            jsonObject.put("View_Status", false);
                        }

                        arrList.add(jsonObject);

                    }
                    Collections.reverse(arrList);
                    response.setResponseData(arrList);

                    response.setResponseCode(StatusCode.SUCCESS_200.getCode());
                    response.setResponseMessage(StatusCode.SUCCESS_200.getMessage());
                } catch (SQLException e) {

                    response.setResponseCode(StatusCode.EXCEPTION_501.getCode());
                    response.setResponseMessage(StatusCode.EXCEPTION_501.getMessage());
                    response.setResponseData(e.getMessage());
                } finally {

                    log.info("response from database {}", response);
                    if (callst != null) {
                        callst.close();

                    }
                }

            }
        });
    }

    public void updateNotificationViewStatus(UpdateNotificationsViewDto updateViewNotificationsDto, Response response) {
        Session session = entityManager.unwrap(Session.class);


        session.doWork(new Work() {
            public void execute(Connection connection) throws SQLException {
                CallableStatement callst = null;
                try {
                    callst = connection.prepareCall("{call" + DatabaseProcedures.UPDATE_NOTIFICATION_STATUS + "(?,?)}");
                    callst.setString(1, updateViewNotificationsDto.getNotificationId());
                    callst.setString(2, updateViewNotificationsDto.getViewStatus());
                    callst.execute();
                    response.setResponseCode(StatusCode.SUCCESS_200.getCode());
                    response.setResponseMessage(StatusCode.SUCCESS_200.getMessage());

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

    }

    public void getNotificationsCount(CountNotificationsDto countNotificationsDto, Response response) {
        Session session = entityManager.unwrap(Session.class);


        session.doWork(new Work() {
            public void execute(Connection connection) throws SQLException {
                CallableStatement callst = null;
                try {
                    callst = connection.prepareCall("{call" + DatabaseProcedures.GET_NOTIFICATIONS_COUNT + "(?)}");
                    callst.setString(1, countNotificationsDto.getUserId());
                    callst.execute();
                    ResultSet resultSet = callst.getResultSet();
                    JSONObject jsonObject = new JSONObject();

                    if (resultSet.next()) {

                        jsonObject.put("NOTIFICATION_COUNT", resultSet.getString(1));
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

    }
}
