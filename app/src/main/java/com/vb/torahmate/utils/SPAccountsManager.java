package com.vb.torahmate.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Najia on 7/22/2015.
 */
public class SPAccountsManager {

    private static final String PREF_NAME = "prefName";
    private static final String USER_ID = "user_id";
    private static final String TOKEN = "token";
    private static final String LOG_IN_TOKEN = "log_in_token";
    private static final String EMAIL = "email";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String PASSWORD = "password";
    private static final String IMEI_NUMBER = "imeiNumber";
    private static final String LOGIN_EMAIL = "loginemail";
    private static final String LOGIN_PASSWORD = "loginPassword";
    private static final String CHECKED_PASSWORD = "checkedPassword";
    private static final String LOGIN_IMEI_NUMBER = "loginImeiNumber";
    private static final String CHECK_LOGIN = "check_login";
    private static final String RELATED_TO_VAL = "RelatedTo";
    private static final String RELATED_ID_VAL = "RelatedId";
    public static final String MinutesUnverified = "MinutesUnverified";
    public static final String MinutesVerified = "MinutesVerified";
    public static final String MinutesTotal = "MinutesTotal";
    public static final String MileageAvailable = "MileageAvailable";
    public static final String MilesUsed = "MilesUsed";
    public static final String MilesTotal = "MilesTotal";
    public static final String MILES_UPDATE_TIME = "time";
    public static final String MILES_UPDATE_MINUTES = "minutes";
    public static final String MILES = "miles";
    public static final String CELL_NUMBER = "cellNumber";


    public static void setUserID(Context context, String id) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USER_ID, id);
        editor.apply();
    }

    public static String getUserID(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(USER_ID, "");
    }

    public static void setToken(Context context, String token) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(TOKEN, token);
        editor.commit();
    }

    public static String getToken(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(TOKEN, "");
    }

    public static void setAuthLogInToken(Context context, String token) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LOG_IN_TOKEN, token);
        editor.commit();
    }

    public static String getAuthLogInToken(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(LOG_IN_TOKEN, "");
    }

    public static void setEmail(Context context, String email) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(EMAIL, email);
        editor.commit();
    }

    public static String getEmail(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(EMAIL, "");
    }

    public static void setFirstName(Context context, String firstName) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(FIRST_NAME, firstName);
        editor.commit();
    }

    public static String getFirstName(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(FIRST_NAME, "");
    }

    public static void setLastName(Context context, String lastName) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LAST_NAME, lastName);
        editor.commit();
    }

    public static String getLastName(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(LAST_NAME, "");
    }

    public static void setPassword(Context context, String password) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PASSWORD, password);
        editor.commit();
    }

    public static String getPassword(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(PASSWORD, "");
    }

    public static void setImeiNumber(Context context, String imeiNumber) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(IMEI_NUMBER, imeiNumber);
        editor.commit();
    }

    public static String getImeiNumber(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(IMEI_NUMBER, "");
    }

    public static void setLoginEmail(Context context, String loginEmail) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LOGIN_EMAIL, loginEmail);
        editor.commit();
    }

    public static String getLoginEmail(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(LOGIN_EMAIL, "");
    }


    public static void setLoginPassword(Context context, String loginPassword) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LOGIN_PASSWORD, loginPassword);
        editor.commit();
    }

    public static String getLoginPassword(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(LOGIN_PASSWORD, "");
    }

    public static void setCheckedPassword(Context context, String checkedPassword) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(CHECKED_PASSWORD, checkedPassword);
        editor.commit();
    }

    public static String getCheckedPassword(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(CHECKED_PASSWORD, "");
    }

    public static void setLoginImeiNumber(Context context, String loginImeiNumber) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LOGIN_IMEI_NUMBER, loginImeiNumber);
        editor.commit();
    }

    public static String getLoginImeiNumber(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(LOGIN_IMEI_NUMBER, "");
    }


    public static void setCheckLogin(Context context, boolean checkLogin) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(CHECK_LOGIN, checkLogin);
        editor.commit();
    }

    public static boolean getCheckLogin(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getBoolean(CHECK_LOGIN, false);
    }

    public static void setRelatedToVal(Context context, String relatedTo) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(RELATED_TO_VAL, relatedTo);
        editor.commit();
    }

    public static String getRelatedToVal(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(RELATED_TO_VAL, "");
    }

    public static void setRelatedIdVal(Context context, String relatedId) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(RELATED_ID_VAL, relatedId);
        editor.commit();
    }

    public static String getRelatedIdVal(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(RELATED_ID_VAL, "");
    }

    public static String getMinutesUnverified(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(MinutesUnverified, "");
    }

    public static void setMinutesUnverified(Context context, String minutesUnverified) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(MinutesUnverified, minutesUnverified);
        editor.commit();
    }

    public static String getMinutesVerified(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(MinutesVerified, "");
    }

    public static void setMinutesVerified(Context context, String minutesVerified) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(MinutesVerified, minutesVerified);
        editor.commit();
    }

    public static String getMinutesTotal(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(MinutesTotal, "");
    }

    public static void setMinutesTotal(Context context, String minutesTotal) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(MinutesTotal, minutesTotal);
        editor.commit();
    }

    public static String getMileageAvailable(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(MileageAvailable, "");
    }

    public static void setMileageAvailable(Context context, String mileageAvailable) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(MileageAvailable, mileageAvailable);
        editor.commit();
    }

    public static String getMilesUsed(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(MilesUsed, "");
    }

    public static void setMilesUsed(Context context, String milesUsed) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(MilesUsed, milesUsed);
        editor.commit();
    }

    public static String getMilesTotal(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(MilesTotal, "");
    }

    public static void setMilesTotal(Context context, String totalMiles) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(MilesTotal, totalMiles);
        editor.commit();
    }

    public static String getMilesUpdateTime(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(MILES_UPDATE_TIME, "");
    }

    public static void setMilesUpdateTime(Context context, String time) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(MILES_UPDATE_TIME, time);
        editor.commit();
    }

    public static String getMilesUpdateMinutes(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(MILES_UPDATE_MINUTES, "");
    }

    public static void setMilesUpdateMinutes(Context context, String minutes) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(MILES_UPDATE_MINUTES, minutes);
        editor.commit();
    }

    public static String getMiles(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(MILES, "");
    }

    public static void setMiles(Context context, String miles) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(MILES, miles);
        editor.commit();
    }


    public static String getCellNumber(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(CELL_NUMBER, "");
    }

    public static void setCellNumber(Context context, String cellNumber) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(CELL_NUMBER, cellNumber);
        editor.commit();
    }
}
