package nju.software.rules.sink;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

public class CheckMessageRule {

    public static void checkSMSMessage(String phoneNumber, String message,
                                       Context act) {
        Toast.makeText(act.getApplicationContext(), "hello",
                Toast.LENGTH_LONG).show();
        String sms = "正在向号码"+phoneNumber+"发送短信"+message;
        if (phoneNumber != null && phoneNumber.equals("1234567890") || message.equals("000000000000000")) {
            Toast.makeText(act.getApplicationContext(),sms,
                    Toast.LENGTH_LONG).show();
        }
    }

    public static void checkSMSmessageWithList(String phoneNumber,
                                               ArrayList<String> messageList, Activity act) {
        Toast.makeText(act.getApplicationContext(), "",
                Toast.LENGTH_LONG).show();
    }

}
