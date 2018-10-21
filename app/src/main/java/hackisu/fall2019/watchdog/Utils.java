package hackisu.fall2019.watchdog;

import android.telephony.SmsManager;

public class Utils {


    static public void sendSms(String phoneNumber , String message)
    {
        SmsManager.getDefault().sendTextMessage(phoneNumber, null, message, null, null);

    }
}
