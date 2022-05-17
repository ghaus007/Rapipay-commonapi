package com.rapipay.commonapi.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.transaction.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.nimbusds.jose.shaded.json.JSONObject;

import com.portal.common.models.Response;
import com.portal.common.models.ResponseHandlingModel;
import com.rapipay.commonapi.constants.DatabaseProcedures;
import com.rapipay.commonapi.responsedto.SOAData;
import com.rapipay.commonapi.responsedto.OnHoldTransactions;
import com.rapipay.commonapi.utils.StatusCode;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.hibernate.procedure.ProcedureOutputs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.rapipay.commonapi.requestdto.DownloadSoaDataDto;
import com.rapipay.commonapi.requestdto.GetOnHoldTransactionsDto;
import com.rapipay.commonapi.requestdto.GetSOADto;
import com.rapipay.commonapi.requestdto.GetSOASettingsDto;
import com.rapipay.commonapi.requestdto.SOASettingsDto;
import com.rapipay.commonapi.requestdto.TransactionHistoryForADateDto;

@Repository
@Transactional
public class TransactionsRepository {

    @Autowired
    EntityManager entityManager;
    @Autowired
    ObjectMapper objectMapper;

    public void getOnHoldTransactions(GetOnHoldTransactionsDto getOnHoldTransactionsDto, Response response) {
        Session session = entityManager.unwrap(Session.class);
        session.doWork(new Work() {
            public void execute(Connection connection) throws SQLException {
                CallableStatement callst = null;
                try {

                    ResultSet resultSet = null;
                    callst = connection.prepareCall("{call " + DatabaseProcedures.MERCHANT_ON_HOLD + "(?,?,?)}");
                    callst.setString(1, getOnHoldTransactionsDto.getMid());
                    if (Objects.nonNull(getOnHoldTransactionsDto.getTid()) && getOnHoldTransactionsDto.getTid().length() > 0) {
                        callst.setString(2, getOnHoldTransactionsDto.getTid());
                    } else {
                        callst.setString(2, null);
                    }
                    if (Objects.nonNull(getOnHoldTransactionsDto.getUserId()) && getOnHoldTransactionsDto.getUserId().length() > 0) {
                        callst.setString(3, getOnHoldTransactionsDto.getUserId());
                    } else {
                        callst.setString(3, null);
                    }
                    callst.execute();
                    resultSet = callst.getResultSet();
                    List<OnHoldTransactions> onHoldTransactionsList = null;

                    if (resultSet.next()) {
                         if (Objects.nonNull(resultSet.getString(1))) {
                            onHoldTransactionsList = objectMapper.readValue(resultSet.getString(1), new TypeReference<List<OnHoldTransactions>>() {
                            });
                            response.setResponseData(onHoldTransactionsList);
                            response.setResponseCode(StatusCode.SUCCESS_200.getCode());
                            response.setResponseMessage(StatusCode.SUCCESS_200.getMessage());
                        } else {
                            response.setResponseCode(StatusCode.NO_REC.getCode());
                            response.setResponseMessage(StatusCode.NO_REC.getMessage());
                        }
                    }


                } catch (SQLException e) {

                    response.setResponseCode(StatusCode.EXCEPTION_501.getCode());
                    response.setResponseMessage(StatusCode.EXCEPTION_501.getMessage());
                    response.setResponseData(e.getMessage());
                } catch (JsonMappingException e) {
                    e.printStackTrace();
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                } finally {
                    if (callst != null) {
                        callst.close();
                    }
                }

            }
        });
    }

    public void getSOA(GetSOADto getSOADto, Response response) {
        Session session = entityManager.unwrap(Session.class);

        session.doWork(new Work() {
            public void execute(Connection connection) throws SQLException {
                CallableStatement callst = null;
                try {

                    ResultSet resultSet = null;
                    String procedureName = null;
                    if (getSOADto.getDataType().equals("mid")) {
                        procedureName = DatabaseProcedures.SOA_MID_WISE;
                    } else if (getSOADto.getDataType().equals("tid")) {
                        procedureName = DatabaseProcedures.SOA_TID_WISE;
                    }

                    callst = connection.prepareCall("{call " + procedureName + "(?,?,?)}");
                    callst.setString(1, getSOADto.getSelectionType());
                    callst.setString(2, getSOADto.getFromDate());
                    callst.setString(3, getSOADto.getToDate());
                    callst.execute();
                    resultSet = callst.getResultSet();
                    List<SOAData> soaDataList = null;
                    if (resultSet.next()) {
                        if (resultSet.getString(1) == null) {
                            response.setResponseCode(StatusCode.NO_REC.getCode());
                            response.setResponseMessage(StatusCode.NO_REC.getMessage());
                        } else {
                            soaDataList = objectMapper.readValue(resultSet.getString(1), new TypeReference<List<SOAData>>() {

                            });
                            response.setResponseData(soaDataList);
                            response.setResponseCode(StatusCode.SUCCESS_200.getCode());
                            response.setResponseMessage(StatusCode.SUCCESS_200.getMessage());
                        }
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

    public void updateSOASettings(SOASettingsDto updateSoaSettingDto, Response response) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("[POS].[UPDATE_MERCHANT_SOA_SETTING]")
                .registerStoredProcedureParameter("MID", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("Email", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("Doc_type", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("Frequency_type", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("ResponseCode", String.class, ParameterMode.OUT)
                .registerStoredProcedureParameter("ResponseMsg", String.class, ParameterMode.OUT)
                .setParameter("MID", updateSoaSettingDto.getMid())
                .setParameter("Email", updateSoaSettingDto.getSecondaryEmail())
                .setParameter("Doc_type", updateSoaSettingDto.getFormat())
                .setParameter("Frequency_type", updateSoaSettingDto.getFrequency());

        try {
            query.execute();

            String responseCode = (String) query.getOutputParameterValue("ResponseCode");
            String responseMessage = (String) query.getOutputParameterValue("ResponseMsg");
            JSONObject jsonData = new JSONObject();
            jsonData.put("responseCode", responseCode);
            jsonData.put("responseMessage", responseMessage);
            response.setResponseCode(StatusCode.SUCCESS_200.getCode());
            response.setResponseMessage(StatusCode.SUCCESS_200.getMessage());
            response.setResponseData(jsonData);

        } catch (Exception e) {

            response.setResponseCode(StatusCode.EXCEPTION_501.getCode());
            response.setResponseMessage(StatusCode.EXCEPTION_501.getMessage());
            response.setResponseData(e.getMessage());

        } finally {

            query.unwrap(ProcedureOutputs.class).release();

        }

    }

    public void getSOASettings(GetSOASettingsDto getSoaSettingDto, Response response) {

        Session session = entityManager.unwrap(Session.class);
        session.doWork(new Work() {

            public void execute(Connection connection) throws SQLException {
                PreparedStatement statement = null;
                try {
                    statement = connection.prepareStatement(
                            "select ALTERNATE_EMAIL,MERCHANT_EMAIL,DOC_TYPE,FREQUENCY_TYPE,MID FROM [MST].[MERCHANT_MASTER] MM\n"
                                    + "			INNER JOIN [POS].[MERCHANT_ONBORDING_FOR_SWITCH] MOFS ON\n"
                                    + "				MOFS.MM_ID = MM.MM_ID where MOFS.MID = ?");

                    statement.setString(1, getSoaSettingDto.getMid());
                    ResultSet resultSet = statement.executeQuery();
                    JsonObject responseObject = new JsonObject();
                    while (resultSet.next()) {

                        responseObject.addProperty("ALTERNATE_EMAIL", resultSet.getString("ALTERNATE_EMAIL"));
                        responseObject.addProperty("MERCHANT_EMAIL", resultSet.getString("MERCHANT_EMAIL"));
                        responseObject.addProperty("DOC_TYPE", resultSet.getString("DOC_TYPE"));
                        responseObject.addProperty("FREQUENCY_TYPE", resultSet.getString("FREQUENCY_TYPE"));
                        responseObject.addProperty("MID", resultSet.getString("MID"));

                    }
                    JsonNode soaSettingsData = objectMapper.readTree(responseObject.toString());
                    response.setResponseCode(StatusCode.SUCCESS_200.getCode());
                    response.setResponseMessage(StatusCode.SUCCESS_200.getMessage());
                    response.setResponseData(soaSettingsData);

                } catch (Exception e) {
                    response.setResponseCode(StatusCode.EXCEPTION_501.getCode());
                    response.setResponseMessage(StatusCode.EXCEPTION_501.getMessage());
                    response.setResponseData(e.getMessage());

                } finally {
                    if (statement != null) {
                        statement.close();
                    }
                }

            }

        });

    }

    public void downloadSOAData(DownloadSoaDataDto downloadSoaDataDto, ResponseHandlingModel response) {

        Session session = entityManager.unwrap(Session.class);
        session.doWork(new Work() {

            public void execute(Connection connection) throws SQLException {
                CallableStatement callst = null;
                try {

                    callst = connection.prepareCall("{call " + DatabaseProcedures.DOWNLOAD_SOA_DATA + "(?,?,?)}");
                    callst.setString(1, downloadSoaDataDto.getMid());
                    callst.setString(2, downloadSoaDataDto.getFromDate());
                    callst.setString(3, downloadSoaDataDto.getToDate());
                    callst.execute();
                    ResultSet resultSet = callst.getResultSet();
                    List<JSONObject> downloadSOAData = null;
                    if (resultSet.next()) {

                        downloadSOAData = objectMapper.readValue(resultSet.getString(1), new TypeReference<List<JSONObject>>() {
                        });

                    }
                    response.setResponseData(downloadSOAData);
                    response.setResponseCode(StatusCode.SUCCESS_200.getCode());
                    response.setResponseMessage(StatusCode.SUCCESS_200.getMessage());
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

    public void getTransactionHistoryForADate(TransactionHistoryForADateDto transactionHistoryForADateDto, Response response, String procedureName, List<String> list, String procParameters) {

        Session session = entityManager.unwrap(Session.class);

        session.doWork(new Work() {
            public void execute(Connection connection) throws SQLException {
                CallableStatement callst = null;
                try {

                    ResultSet resultSet = null;
                    callst = connection.prepareCall("{call " + procedureName + procParameters);
                    int index = 1;
                    for (String s : list)
                        callst.setString(index++, s);
                    callst.execute();
                    resultSet = callst.getResultSet();

                    ResultSetMetaData md = resultSet.getMetaData();
                    int columns = md.getColumnCount();
                    List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
                    while (resultSet.next()) {
                        Map<String, Object> row = new HashMap<String, Object>(columns);
                        for (int i = 1; i <= columns; ++i) {
                            row.put(md.getColumnName(i), resultSet.getObject(i).toString());
                        }
                        rows.add(row);
                    }
                    if (rows.isEmpty()) {
                        response.setResponseCode(StatusCode.NO_REC.getCode());
                        response.setResponseMessage(StatusCode.NO_REC.getMessage());

                    } else {
                        response.setResponseCode(StatusCode.SUCCESS_200.getCode());
                        response.setResponseMessage(StatusCode.SUCCESS_200.getMessage());
                        response.setResponseData(rows);
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
}
