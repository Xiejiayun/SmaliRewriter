package nju.software.rules.sink;

import java.util.ArrayList;

import android.app.Activity;
import android.widget.Toast;

public class CheckMessageRule {

	public static void checkSMSMessage(String phoneNumber, String message,
			Activity act) {
		Toast.makeText(act.getApplicationContext(), "hello",
				Toast.LENGTH_LONG).show();
		if (phoneNumber.equals("") || message.equals("")) {
			Toast.makeText(act.getApplicationContext(), "",
					Toast.LENGTH_LONG).show();
		}
	}

	public static void checkSMSmessageWithList(String phoneNumber,
			ArrayList<String> messageList, Activity act) {
		Toast.makeText(act.getApplicationContext(), "",
				Toast.LENGTH_LONG).show();
	}

}