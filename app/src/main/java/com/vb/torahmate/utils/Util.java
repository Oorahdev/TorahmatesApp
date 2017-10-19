package com.vb.torahmate.utils;

import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.vb.torahmate.R;
import com.vb.torahmate.main.MainActivity;
import com.vb.torahmate.settings.ToastSubmitting;

import org.apache.http.conn.util.InetAddressUtils;

import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * Created by Najia on 7/22/2015.
 */
public class Util {

    public static ArrayAdapter<String> adapter;
    public static String contactNumber = "";
    public static String contactName = "";
    public static String checkAppName = "false";
    public static boolean checkCallEnd = false;
    public static Toast toast;
    public static ToastSubmitting toastSubmitting;
    public static boolean checkMilesUpdates = true;
    public static boolean checkAlarmDisabled = false;
    public static int statusVal = 10;


    public static String regexEmail = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(" +
            "([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";

    public static String regexPassword = "[a-zA-Z0-9_-]+";

    public static boolean isLollipopOrGreater() {
        return Build.VERSION.SDK_INT >= 21 ? true : false;
    }

    public static boolean isJellyBeanOrGreater() {
        return Build.VERSION.SDK_INT >= 16 ? true : false;
    }

    public static void showToast(Context context, String msg) {
//        LinearLayout layout = new LinearLayout(context);
//        TextView tv = new TextView(context);
//        layout.setBackgroundResource(R.color.colorPrimaryAqua);
//        layout.addView(tv);
//        layout.setMinimumHeight(70);
//        tv.setMinimumWidth(355);
//        tv.setGravity(Gravity.CENTER);
//        tv.setPadding(20, 20, 20, 20);
////        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
//        layout.setGravity(Gravity.CENTER);
//        tv.setText(msg);
//        toast = Toast.makeText(AppManager.getAppContext(), msg, Toast.LENGTH_LONG);
//        toast.setView(layout);
//        toast.show();
        try {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
            toast.show();
        }
        catch (Exception e) {}
    }

    public static void showToastSubmitting() {
        toastSubmitting = new ToastSubmitting();
    }

    public static void cancelToastSubmitting() {
        if(toastSubmitting != null) {
            toastSubmitting.cancel();
        }
    }

    public static void showLoginToast(Context context, String msg) {
        LinearLayout layout = new LinearLayout(context);
        TextView tv = new TextView(context);
        layout.setBackgroundResource(R.color.colorPrimaryAqua);
        layout.addView(tv);
        layout.setMinimumHeight(60);
        tv.setMinimumWidth(395);
        tv.setGravity(Gravity.CENTER);
        layout.setGravity(Gravity.CENTER);
        tv.setPadding(20, 20, 20, 20);
        tv.setText(msg);
        toast = Toast.makeText(AppManager.getAppContext(), msg, Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.setGravity(Gravity.CENTER | Gravity.CENTER, 10, 100);
        toast.show();
    }

    public static void cancelToast() {
        if (toast != null) {
            toast.cancel();
        }
    }

    public static boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (NetworkInfo anInfo : info)
                    if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public static final String getDeviceIMEINumber(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return manager.getDeviceId();
    }

    public static final String getFromDialerNumber(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return manager.getLine1Number();
    }

    public static String getIPAddress() {
        try {
            String ipv4;
            List<NetworkInterface> networkInterfaceList = Collections.list(NetworkInterface.getNetworkInterfaces());
            if (networkInterfaceList.size() > 0) {
                for (NetworkInterface networkInterface : networkInterfaceList) {
                    List<InetAddress> inetAddressList = Collections.list(networkInterface.getInetAddresses());
                    if (inetAddressList.size() > 0) {
                        for (InetAddress address : inetAddressList) {
                            if (!address.isLoopbackAddress() && InetAddressUtils.isIPv4Address(ipv4 = address.getHostAddress())) {
                                return ipv4;
                            }
                        }
                    }
                }
            }

        } catch (SocketException ex) {

        }
        return "";
    }

    public static void sendEmail(Context context, String mailId) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{mailId});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");
        try {
            context.startActivity(Intent.createChooser(emailIntent, context.getResources().getString(R.string.send_mail)));
        } catch (android.content.ActivityNotFoundException ex) {
            Util.showToast(context, context.getResources().getString(R.string.missing_email_client));
        }
    }

    public static void call(Context context, String number, String name, String... ext) {
        contactName = name;
        contactNumber = number;
        Util.checkAppName = "true";
        try {
            String uri = "tel:" + contactNumber;
            uri = ext.length > 0 ? uri + ";" + ext[0] : uri;
            Uri numbercall = Uri.parse(uri);
            Intent dial = new Intent(Intent.ACTION_CALL, numbercall);
            context.startActivity(dial);
        } catch (ActivityNotFoundException activityException) {
            Util.showToast(context, context.getResources().getString(R.string.call_failed));
        }
    }

    public static String callLength(int s) {
        int q = 0;
        if (s > 60) {
            q = 710 / 60;
            s = 710 % 60;
        }
        return q + "." + s;
    }

    public static void launchActiveAndroid(Context context) {
        ActiveAndroid.initialize(context);
        ActiveAndroid.setLoggingEnabled(false);
    }

    public static int getDPI(int size, Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context
                .getSystemService(context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        return (size * metrics.densityDpi) / DisplayMetrics.DENSITY_DEFAULT;
    }

    public static void spinnerHeight(Spinner spinner, Context context) {
        try {
            if (Build.VERSION.SDK_INT > 19) {
                Field popup = Spinner.class.getDeclaredField("mPopup");
                popup.setAccessible(true);
                android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spinner);
                popupWindow.setHeight(getDPI(200, context));
                popupWindow.setVerticalOffset(getDPI(56, context));
            } else {
                Field popup = Spinner.class.getDeclaredField("mPopup");
                popup.setAccessible(true);
                android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spinner);
                popupWindow.setHeight(getDPI(200, context));
                popupWindow.setVerticalOffset(getDPI(-26, context));
            }

        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
        }
    }

    public static String initSpinner(final Context mContext, List items, final MaterialSpinner mSpinnerDays, final String mStrTorahmates) {
        adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item,
                items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerDays.setAdapter(adapter);
        return mStrTorahmates;
    }

    public static List initNameSpinner(final Context mContext, List<String> names, final MaterialSpinner mSpinnerDays, final List
            mList) {
        adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, names);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerDays.setAdapter(adapter);
        return names;
    }

    public static String getContactPhoto(String number) {
        Uri uri = Uri.withAppendedPath(
                ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(number));
        String photo = "123";
        try {
            ContentResolver contentResolver = AppManager.getAppContext().getContentResolver();
            Cursor contactLookup = contentResolver.query(uri, new String[]{
                            BaseColumns._ID, ContactsContract.PhoneLookup.PHOTO_URI},
                    null, null, null);

            if (contactLookup != null && contactLookup.getCount() > 0) {
                contactLookup.moveToNext();
                photo = contactLookup.getString(contactLookup
                        .getColumnIndex(ContactsContract.Data.PHOTO_URI));
                if (photo == null) {
                    photo = "123";
                }
            }
            contactLookup.close();
        } catch (IllegalArgumentException e) {
        }
        return photo;
    }

    public static BroadcastReceiver mConnReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            NetworkInfo currentNetworkInfo = (NetworkInfo) intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
            if (currentNetworkInfo.isConnected()) {
                MainActivity.messageBar.setVisibility(View.GONE);
                if (ConnectionPreferences.getCheckConnected((AppManager.getAppContext())) == true) {
                    ConnectionPreferences.setCheckConnected(AppManager.getAppContext(), false);
                }
            } else {
                if (ConnectionPreferences.getCheckConnected((AppManager.getAppContext())) == false) {
                    MainActivity.messageBar.setVisibility(View.VISIBLE);
                }
            }
        }
    };

    public static String getCurrentDayName() {
        String weekDay;
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
        Calendar calendar = Calendar.getInstance();
        weekDay = dayFormat.format(calendar.getTime());
        return weekDay;
    }

    public static void googleTracking(String cat, String name) {
        Tracker mTracker = AppManager.getDefaultTracker();
        mTracker.setScreenName(cat + "  " + name);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public static String stringTimeFormate(String inRegx, String outRegx, String input) {
        SimpleDateFormat parser = new SimpleDateFormat(inRegx);
        String timeString="";
        try {
            timeString = new SimpleDateFormat(outRegx).format(parser.parse(input));
        } catch (ParseException e) {
            System.out.print("error");
        }
        return timeString;
    }
}