package com.sac.speechdemo;

import android.telephony.SmsManager;

public class Util {
    static private void sendSms(String phoneNumber, String message) {
        SmsManager.getDefault().sendTextMessage(phoneNumber, null, message, null, null);
    }

    public static void notifyEmergencyContacts(String givenMessage){
        String contactNumbers[] = {
                "5156610722",
                "5154600087",
                "6127495598"
        };

        for(String contactNumber:contactNumbers){
            sendSms(contactNumber, givenMessage);
        }
    }

}
