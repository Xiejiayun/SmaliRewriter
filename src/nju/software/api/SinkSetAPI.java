//package nju.software.api;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.InetSocketAddress;
//import java.net.MalformedURLException;
//import java.net.Socket;
//import java.net.URL;
//import java.util.ArrayList;
//
//import org.apache.http.HttpResponse;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.impl.conn.DefaultClientConnection;
//
//import android.app.Activity;
//import android.content.BroadcastReceiver;
//import android.content.ComponentName;
//import android.content.ContentValues;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.content.SharedPreferences;
//import android.content.SharedPreferences.Editor;
//import android.database.Cursor;
//import android.media.MediaRecorder;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.IBinder;
//import android.provider.Contacts.People;
//import android.telephony.SmsManager;
//import android.util.Log;
//import android.util.SparseArray;
//import android.view.SurfaceHolder;
//import android.view.SurfaceView;
//
//public class SinkSetAPI extends Activity {
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
////		setContentView(R.layout.activity_bundle_sink_test);
//	}
//
//	public void bundleSinkMethod() {
//		Bundle bundle = new Bundle();
//		bundle.putBoolean("isSink", true);
//		bundle.putBooleanArray("isSinks", new boolean[] { true, false, false });
//		bundle.putByte("sinkByte", Byte.MAX_VALUE);
//		bundle.putByteArray("sinkBytes", "6".getBytes());
//		bundle.putChar("sinkChar", 'm');
//		bundle.putCharArray("sinkCharArray", new char[] { 'm', 'm', 'j' });
//		bundle.putCharSequence("sinkCharQ", "charq");
//		bundle.putCharSequenceArray("sinkCharQs", new String[] { "charQ1",
//				"charQ2" });
//		bundle.putCharSequenceArrayList("sinkCharQst",
//				new ArrayList<CharSequence>());
//		bundle.putDouble("sinkDouble", 6.66);
//		bundle.putDoubleArray("sinkDoubles", new double[] { 6.66, 7.77, 8.88 });
//		bundle.putFloat("sinkFloat", (float) 6.66);
//		bundle.putFloatArray("sinkFloats", new float[] { (float) 6.66,
//				(float) 8.88 });
//		bundle.putInt("sinkInt", 8);
//		bundle.putIntArray("sinkInts", new int[] { 1, 2, 3 });
//		bundle.putIntegerArrayList("sinkIntegers", new ArrayList<Integer>());
//		bundle.putLong("sinkLong", 10);
//		bundle.putLongArray("sinkLongs", new long[] { 12, 13, 14 });
//		bundle.putParcelable("sinkParcel", new PersonParcelable());
//		bundle.putParcelableArray("sinkParcels", new PersonParcelable[] {});
//		bundle.putParcelableArrayList("sinkParcelList",
//				new ArrayList<PersonParcelable>());
//		bundle.putSerializable("sinkSerial", new PersonSerilization());
//		bundle.putShort("sinkShort", (short) 2);
//		bundle.putShortArray("sinkShorts", new short[] { 1, 2, 3 });
//		bundle.putSparseParcelableArray("sinkSparse",
//				new SparseArray<PersonParcelable>());
//		bundle.putString("sinkString", "sinkStringTest");
//		bundle.putStringArray("sinkStrings", new String[] { "string1",
//				"string2" });
//		bundle.putStringArrayList("sinkStringList", new ArrayList<String>());
//		bundle.putBundle("sinkBundle", new Bundle());
//		bundle.putAll(new Bundle());
//	}
//
//	public void logSinkMethod() {
//		Log.d("logSinkD", "testLogD");
//		Log.d("logSinkD2", "testLogD", new Throwable());
//		Log.e("logSinkE", "testLog.e");
//		Log.e("logSinkE", "testLog.e", new Throwable());
//		Log.i("logSinkI", "testLog.i");
//		Log.i("logSinkI", "testLog.i", new Throwable());
//		Log.v("logSinkV", "testLog.v");
//		Log.v("logSinkV", "testLog.v", new Throwable());
//		Log.w("logSinkW", "testLog.w");
//		Log.w("logSinkW", new Throwable());
//		Log.w("logSinkW", "testLog.w", new Throwable());
//		Log.wtf("logSinWTF", "testLog.wtf");
//		Log.wtf("logSinkWTF", new Throwable());
//		Log.wtf("logSinkWTF", "testLog.wtf", new Throwable());
//	}
//
//	public void ioSinkMethod() throws IOException {
//		OutputStream outputStream = new FileOutputStream(new File(""));
//		outputStream.write("test".getBytes());
//		outputStream.close();
//
//	}
//
//	public void intentSinkMethod() {
//		Intent intent = new Intent();
//		intent.setAction("testAction");
//		intent.setClassName(this, "test.activity");
//		intent.setClassName("test1", "test2");
//		intent.setComponent(new ComponentName("", ""));
//	}
//
//	public void contextSinkMethod() {
//		Intent intent = new Intent();
//		sendBroadcast(intent);
//		sendBroadcast(intent, "permission");
//
//		BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver() {
//
//			@Override
//			public void onReceive(Context arg0, Intent arg1) {
//				// TODO Auto-generated method stub
//
//			}
//		};
//		IntentFilter intentFilter = new IntentFilter();
//		intentFilter.addAction("action");
//
//		registerReceiver(myBroadcastReceiver, intentFilter);
//		registerReceiver(myBroadcastReceiver, intentFilter, "permission",
//				new Handler());
//
//		startActivity(intent);
//		startService(intent);
//		setResult(1, intent);
//
//	}
//
//	public void mediaSinkMethod() {
//		MediaRecorder mRecorder = new MediaRecorder();
//		mRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
//		mRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
//		mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
//		mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
//		mRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H263);
//		mRecorder.start();
//	}
//
//	public void messageSinkMethod() {
//		SmsManager smsManager = SmsManager.getDefault();
//		smsManager.sendTextMessage("123456789", null, "test", null, null);
//
//		short port = 1000;
//		smsManager.sendDataMessage("123456789", null, port, "test".getBytes(),
//				null, null);
//
//		ArrayList<String> parts = smsManager.divideMessage("test");
//		smsManager.sendMultipartTextMessage("123456789", null, parts, null,
//				null);
//	}
//
//	public void sharedPreferenceSinkMethod() {
//		SharedPreferences sp = getSharedPreferences("preferenceSink",
//				Activity.MODE_PRIVATE);
//		Editor editor = sp.edit();
//
//		editor.putBoolean("sharedBool", false);
//		editor.putFloat("sharedFloat", (float) 6.6);
//		editor.putInt("sharedInt", 8);
//		editor.putLong("sharedLong", 888888);
//		editor.putString("sharedString", "testSink");
//
//		editor.commit();
//	}
//
//	public void netUrlSinkMethod() throws IOException {
//		URL url = new URL("");
//
//		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//		connection.setRequestMethod("POST");
//		connection.setRequestProperty("Connection", "Keep-Alive");
//	}
//
//	public void socketSinkMethod() throws IOException {
//		Socket socket = new Socket();
//		socket.connect(new InetSocketAddress(8080));
//
//		socket.close();
//	}
//
//	public void apacheSinkMethod() throws IOException, IOException {
//		HttpGet getMethodGet = new HttpGet("");
//		HttpClient httpClient = new DefaultHttpClient();
//
//		HttpResponse response = httpClient.execute(getMethodGet);
//
//	}
//
//	@SuppressWarnings("deprecation")
//	public void contentResolverSinkMethod() {
//		ContentValues ct = new ContentValues();
//		ct.put("sink1", "content");
//		getContentResolver().insert(People.CONTENT_URI, ct);
//
//		getContentResolver().delete(People.CONTENT_URI, "test", null);
//
//		ContentValues ct2 = new ContentValues();
//		ct2.put("sink2", "content2");
//		getContentResolver().update(People.CONTENT_URI, ct2, "test", null);
//
//		Cursor cursor = getContentResolver().query(People.CONTENT_URI,
//				new String[] { "tes1", "test2" }, null, null, null);
//	}
//
//	public void processBuilderSinkMethod() throws IOException {
//		ProcessBuilder processBuilder = new ProcessBuilder("");
//		processBuilder.start();
//	}
//}
