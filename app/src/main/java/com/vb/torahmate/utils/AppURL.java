package com.vb.torahmate.utils;

/**
 * Created by Najia on 7/22/2015.
 */
public class AppURL {
    //    Base url
    public static final String API_KEY = "92bRf8915cT7adFe4b25e45493b298801";
    public static final String SECRET_KEY = "9W97Ac9b8dE0a7njd2c52bkW9Ou7T";
    //public static final String BASE_URL = "http://69.16.236.89/NativeApp/";
    public static final String BASE_URL = "http://api.torahmates.org/";
    //Sub url
    public static final String subUrlForgetPassword = "forget_password?";
    public static final String subUrlLogin = "MileageLoginMobile";
    public static final String subUrlContactList = "MileageContacts?";
    public static final String subUrlMyTorahmatesList = "MileageGetPartners?";
    public static final String subUrlMileageLog = "MileageLog?";
    public static final String subUrlMileageCallLog = "MileageCallLog";
    public static final String subUrlMileageGetSessions = "MileageGetSessions";
    public static final String subUrlMileageGetReminderAlarms = "MileageGetReminderAlarms";
    public static final String subUrlMileageGetDayOptions = "MileageGetDayOptions";
    public static final String subUrlMileageEnterSessionMobile = "MileageEnterSessionMobile?";
    public static final String subUrlMileageReminderAlarms = "MileageReminderAlarms?";
    public static final String subUrlSendLogs = "MileageLoginMobile";
    public static final String subUrlMileagePlaceCall = "MileagePlaceCall";
    public static final String subUrlMileageLoginInfo = "MileageLoginInfo";
    public static final String subUrlMileageDashboard = "MileageDashboard";
    //    service url
    public static final String urlLogin = BASE_URL + subUrlLogin;
    public static final String urlContactList = BASE_URL + subUrlContactList;
    public static final String urlMyTorahmates = BASE_URL + subUrlMyTorahmatesList;
    public static final String urlMileageLog = BASE_URL + subUrlMileageLog;
    public static final String urlMileageCallLog = BASE_URL + subUrlMileageCallLog;
    public static final String urlForgetPassword = BASE_URL + subUrlForgetPassword;
    public static final String urlMileageGetSessions = BASE_URL + subUrlMileageGetSessions;
    public static final String urlMileageGetReminderAlarms = BASE_URL + subUrlMileageGetReminderAlarms;
    public static final String urlSetSchedule = BASE_URL + subUrlMileageGetDayOptions;
    public static final String urlMileageEnterSessionMobile = BASE_URL + subUrlMileageEnterSessionMobile;
    public static final String MileageReminderAlarms = BASE_URL + subUrlMileageReminderAlarms;
    public static final String SendLogs = BASE_URL + subUrlSendLogs;
    public static final String urlMileagePlaceCall = BASE_URL + subUrlMileagePlaceCall;
    public static final String urlMileageLoginInfo = BASE_URL + subUrlMileageLoginInfo;
    public static final String urlMileageDashboard = BASE_URL + subUrlMileageDashboard;
}
