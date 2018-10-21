package com.sac.watchdog;

import android.content.Context;
import android.telephony.SmsManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class Util {
    static private void sendSms(String phoneNumber, String message) {
        SmsManager.getDefault().sendTextMessage(phoneNumber, null, message, null, null);
    }

    public static void notifyEmergencyContacts(Context context, String givenMessage) {
        List<String> contactNumbers = readFromFile(context);

        for (String contactNumber : contactNumbers) {
            if (contactNumber != null) {
                sendSms(contactNumber, givenMessage);
            }
        }
    }

    private static List<String> readFromFile(Context context) {

        String ret = "";
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>() {
        }.getType();


        try {
            InputStream inputStream = context.openFileInput("config.txt");

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return gson.fromJson(ret, type);
    }

}
