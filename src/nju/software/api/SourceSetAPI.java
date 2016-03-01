//package nju.software.api;
//
//import java.io.IOException;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.Calendar;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.util.EntityUtils;
//
//import android.R.string;
//import android.accounts.AccountManager;
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.app.PendingIntent;
//import android.bluetooth.BluetoothAdapter;
//import android.content.ContentValues;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.pm.PackageManager;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.location.Location;
//import android.location.LocationManager;
//import android.media.AudioFormat;
//import android.media.AudioRecord;
//import android.media.MediaRecorder;
//import android.net.wifi.WifiInfo;
//import android.net.wifi.WifiManager;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.preference.PreferenceManager;
//import android.provider.Browser;
//import android.provider.Contacts.People;
//import android.telephony.TelephonyManager;
//import android.telephony.gsm.GsmCellLocation;
//import android.view.Menu;
//import android.view.MenuItem;
//
//public class SourceSetAPI extends Activity {
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
////		setContentView(R.layout.activity_source_set_api);
//	}
//
//	public void locationSourceMethod() {
//		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//		Location location = locationManager
//				.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//		location.getLongitude();
//		location.getLatitude();
//	}
//
//	public void telephonyManagerSourceMethod() {
//		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//		tm.getDeviceId();
//		tm.getSubscriberId();
//		tm.getSimSerialNumber();
//		tm.getLine1Number();
//	}
//
//	public void urlConnectionSourceMethod() throws IOException {
//		URL url = new URL("");
//		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//		connection.getOutputStream();
//		connection.getInputStream();
//
//		connection.disconnect();
//	}
//
//	public void apacheSourceMethod() throws IOException {
//		HttpGet getMethodGet = new HttpGet("");
//		HttpClient httpClient = new DefaultHttpClient();
//
//		HttpResponse response = httpClient.execute(getMethodGet);
//		HttpEntity httpEntity = response.getEntity();
//		String resultString = EntityUtils.toString(httpEntity);
//		byte[] bytes = EntityUtils.toByteArray(httpEntity);
//		String charsetString = EntityUtils.getContentCharSet(httpEntity);
//	}
//
//	@SuppressLint("NewApi")
//	public void bundleSourceMethod() {
//		Bundle bundle = getIntent().getExtras();
//
//		bundle.get("object");
//		bundle.getBoolean("booleanKey");
//		bundle.getBoolean("booleanKey", false);
//		bundle.getBooleanArray("booleansKey");
//		bundle.getBundle("bundleKey");
//		bundle.getByte("byteKey");
//		bundle.getByte("byteKey", (byte) 2);
//		bundle.getByteArray("bytesKey");
//		bundle.getChar("charKey");
//		bundle.getChar("charKey", 'm');
//		bundle.getCharArray("charsKey");
//		bundle.getCharSequence("charSqKey");
//		bundle.getCharSequenceArray("charSqsKey");
//		bundle.getCharSequenceArrayList("charlistKey");
//		bundle.getClassLoader();
//		bundle.getDouble("doubleKey");
//		bundle.getDoubleArray("doublesKey");
//		bundle.getFloat("floatKey");
//		bundle.getFloatArray("floatsKey");
//		bundle.getInt("intKey");
//		bundle.getIntArray("intsKey");
//		bundle.getIntegerArrayList("intListKey");
//		bundle.getLong("longKey");
//		bundle.getLongArray("longsKey");
//		bundle.getParcelable("parcelKey");
//		bundle.getParcelableArray("parcelsKey");
//		bundle.getParcelableArrayList("parcelListKey");
//		bundle.getSerializable("serialKey");
//		bundle.getShort("shortKey");
//		bundle.getShortArray("shortsKey");
//		bundle.getSparseParcelableArray("sparseKey");
//		bundle.getString("stringKey");
//		bundle.getStringArray("stringsKey");
//		bundle.getStringArrayList("stringListKey");
//	}
//
//	public void pendingIntentSourceMethod() {
//		Intent openIntent = new Intent();
//		PendingIntent pi = PendingIntent.getActivity(this, 0, openIntent,
//				PendingIntent.FLAG_CANCEL_CURRENT);
//		pi.getBroadcast(this, 0, openIntent, PendingIntent.FLAG_CANCEL_CURRENT);
//		pi.getService(this, 0, openIntent, PendingIntent.FLAG_CANCEL_CURRENT);
//	}
//
//	public void audioSourceMethod() {
//		AudioRecord audioRecord = new AudioRecord(
//				MediaRecorder.AudioSource.MIC, 8000,
//				AudioFormat.CHANNEL_CONFIGURATION_MONO,
//				AudioFormat.ENCODING_PCM_16BIT, AudioRecord.getMinBufferSize(
//						8000, AudioFormat.CHANNEL_CONFIGURATION_MONO,
//						AudioFormat.ENCODING_PCM_16BIT));
//
//		byte[] buffer = new byte[10];
//		audioRecord.read(buffer, 0, buffer.length);
//	}
//
//	public void packageManagerSourceMethod() {
//		PackageManager pm = getPackageManager();
//		pm.getInstalledApplications(0);
//		pm.getInstalledPackages(0);
//		pm.queryIntentActivities(getIntent(), PackageManager.GET_INTENT_FILTERS);
//		pm.queryBroadcastReceivers(getIntent(),
//				PackageManager.GET_INTENT_FILTERS);
//
//	}
//
//	public void handlerSourceMethod() {
//
//		Handler handler = new Handler() {
//
//			@Override
//			public void handleMessage(Message msg) {
//				// TODO Auto-generated method stub
//				super.handleMessage(msg);
//			}
//
//		};
//
//		Message msg = handler.obtainMessage(1, 2, 3, new Object());
//
//	}
//
//	public void sharedPreferenceSourceMethod() {
//		SharedPreferences sharedPreferences = PreferenceManager
//				.getDefaultSharedPreferences(this);
//	}
//
//	public void blueToothSourceMethod() {
//		BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
//		adapter.getAddress();
//	}
//
//	public void wifiInfoSourceMethod() {
//		WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
//		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//		wifiInfo.getMacAddress();
//	}
//
//	public void gsmSourceMethod() {
//		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//		GsmCellLocation location = (GsmCellLocation) tm.getCellLocation();
//		location.getCid();
//		location.getLac();
//	}
//
//	public void accountManagerSourceMethod() {
//		AccountManager accountManager = (AccountManager) getSystemService(Context.ACCOUNT_SERVICE);
//		accountManager.getAccounts();
//	}
//
//	public void calendarSourceMethod() {
//		Calendar.getInstance().getTimeZone();
//	}
//
//	public void browserSourceMethod() {
//		Browser browser = new Browser();
//		browser.getAllBookmarks(getContentResolver());
//		browser.getAllVisitedUrls(getContentResolver());
//	}
//
//	public void contentResolverSinkMethod() {
//		Cursor cursor = getContentResolver().query(People.CONTENT_URI,
//				new String[] { "tes1", "test2" }, null, null, null);
//	}
//
//	public void activitySourceMethod() {
//		Intent intent = getIntent();
//	}
//
//	public void databaseSourceMethod() {
//		DatabaseHelper dbHelper = new DatabaseHelper(this, "sourceDB", null, 1);
//		SQLiteDatabase db = dbHelper.getWritableDatabase();
//		db.execSQL("");
//		Cursor cursor = db.rawQuery("", null);
//		while (cursor.moveToNext()) {
//			String result = cursor.getString(0);
//		}
//
//		db.query("", new String[] {}, null, null, null, null, null);
//	}
//
//}
