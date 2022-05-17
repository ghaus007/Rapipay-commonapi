package com.rapipay.commonapi.constants;

public class DatabaseProcedures {

    public static final String MERCHANT_ON_HOLD = "[POS].[GET_MERCHANT_ONHOLD_TRANSACTIONS]";
    public static final String SOA_MID_WISE = "[STL].[GET_SOA_MID_WISE]";
    public static final String SOA_TID_WISE = "[STL].[GET_SOA_TID_WISE]";
   //public static final String DOWNLOAD_SOA_DATA = "[STL].[GET_SOA_MERCHANT]";
   public static final String DOWNLOAD_SOA_DATA = "[POS].[GET_SOA_TRANSACTION]";

    public static final String GET_NOTIFICATION_LIST = "[INF].[GET_NOTIFICATION_LIST]";
    public static final String UPDATE_NOTIFICATION_STATUS = "[INF].[UPDATE_NOTIFICATION_STATUS]";
    public static final String GET_NOTIFICATIONS_COUNT = "[INF].[GET_NOTIFICATION_COUNT]";
    public static final String GET_MERCHANT_KYC_DETAILS = "[POS].[GET_MERCHANT_KYC_DETAILS]";
    public static final String GET_MERCHANT_TID_DETAILS = "[POS].[GET_MERCHANT_TID_DETAILS]";
    public static final String Get_Transaction_History= "[POS].[Get_Transaction_History]";
    public static final String GET_MONTH_WISE_TRANSACTION_DETAILS= "[POS].[GET_MONTH_WISE_TRANSACTION_DETAILS]";
    public static final String GET_DATE_WISE_TRANSACTION_DETAILS= "[POS].[GET_DATE_WISE_TRANSACTION DETAILS]";
    public static final String CREATE_SUB_USER = "[POS].[INSERT_NEW_TERMINAL_USER_DETAILS]";
    public static final String GET_MERCHANT_MDR_DETAILS= "[POS].[GET_MERCHANT_MDR_DETAILS]";
    public static final String GET_TERMINAL_CHARGES_DETAILS= "[POS].[GET_TERMINAL_CHARGES_DETAILS]";
    public static final String GET_TRANSACTION_HISTORY_TODAY_YESTERDAY= "[POS].[GET_TRANSACTION_HISTORY_TODAY_YESTERDAY]";
    public static final String GET_REV_RKI_STATUS= "[POS].[GET_REV_RKI_STATUS]";
    public static final String GET_REV_STATUS= "[POS].[GET_REV_STATUS]";
    public static final String UPDATE_POS_TERMINAL_USER_INFO = "[POS].[UPDATE_POS_TERMINAL_USER_INFO]";
    public static final String GET_PAYOUT_DETAILS = "[STL].[GET_PAYOUT_DETAILS]";


}
