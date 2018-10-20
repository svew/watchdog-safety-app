package com.sac.watchdog;

import android.telephony.SmsManager;

public class Util {
    static public void sendSms(String phoneNumber, String message) {
        SmsManager.getDefault().sendTextMessage(phoneNumber, null, message, null, null);
    }
}
