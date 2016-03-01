.class public Lcom/example/messagesmali/SourceSetAPI;
.super Landroid/app/Activity;
.source "SourceSetAPI.java"


# direct methods
.method public constructor <init>()V
    .locals 0

    .prologue
    .line 49
    invoke-direct {p0}, Landroid/app/Activity;-><init>()V

    return-void
.end method


# virtual methods
.method public accountManagerSourceMethod()V
    .locals 2

    .prologue
    .line 203
    const-string v1, "account"

    invoke-virtual {p0, v1}, Lcom/example/messagesmali/SourceSetAPI;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/accounts/AccountManager;

    .line 204
    .local v0, accountManager:Landroid/accounts/AccountManager;
    invoke-virtual {v0}, Landroid/accounts/AccountManager;->getAccounts()[Landroid/accounts/Account;

    .line 205
    return-void
.end method

.method public activitySourceMethod()V
    .locals 1

    .prologue
    .line 223
    invoke-virtual {p0}, Lcom/example/messagesmali/SourceSetAPI;->getIntent()Landroid/content/Intent;

    move-result-object v0

    .line 224
    .local v0, intent:Landroid/content/Intent;
    return-void
.end method

.method public apacheSourceMethod()V
    .locals 8
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .prologue
    .line 83
    new-instance v2, Lorg/apache/http/client/methods/HttpGet;

    const-string v7, ""

    invoke-direct {v2, v7}, Lorg/apache/http/client/methods/HttpGet;-><init>(Ljava/lang/String;)V

    .line 84
    .local v2, getMethodGet:Lorg/apache/http/client/methods/HttpGet;
    new-instance v3, Lorg/apache/http/impl/client/DefaultHttpClient;

    invoke-direct {v3}, Lorg/apache/http/impl/client/DefaultHttpClient;-><init>()V

    .line 86
    .local v3, httpClient:Lorg/apache/http/client/HttpClient;
    invoke-interface {v3, v2}, Lorg/apache/http/client/HttpClient;->execute(Lorg/apache/http/client/methods/HttpUriRequest;)Lorg/apache/http/HttpResponse;

    move-result-object v5

    .line 87
    .local v5, response:Lorg/apache/http/HttpResponse;
    invoke-interface {v5}, Lorg/apache/http/HttpResponse;->getEntity()Lorg/apache/http/HttpEntity;

    move-result-object v4

    .line 88
    .local v4, httpEntity:Lorg/apache/http/HttpEntity;
    invoke-static {v4}, Lorg/apache/http/util/EntityUtils;->toString(Lorg/apache/http/HttpEntity;)Ljava/lang/String;

    move-result-object v6

    .line 89
    .local v6, resultString:Ljava/lang/String;
    invoke-static {v4}, Lorg/apache/http/util/EntityUtils;->toByteArray(Lorg/apache/http/HttpEntity;)[B

    move-result-object v0

    .line 90
    .local v0, bytes:[B
    invoke-static {v4}, Lorg/apache/http/util/EntityUtils;->getContentCharSet(Lorg/apache/http/HttpEntity;)Ljava/lang/String;

    move-result-object v1

    .line 91
    .local v1, charsetString:Ljava/lang/String;
    return-void
.end method

.method public audioSourceMethod()V
    .locals 7

    .prologue
    const/16 v2, 0x1f40

    const/4 v3, 0x2

    .line 142
    new-instance v0, Landroid/media/AudioRecord;

    .line 143
    const/4 v1, 0x1

    .line 145
    invoke-static {v2, v3, v3}, Landroid/media/AudioRecord;->getMinBufferSize(III)I

    move-result v5

    move v4, v3

    .line 142
    invoke-direct/range {v0 .. v5}, Landroid/media/AudioRecord;-><init>(IIIII)V

    .line 149
    .local v0, audioRecord:Landroid/media/AudioRecord;
    const/16 v1, 0xa

    new-array v6, v1, [B

    .line 150
    .local v6, buffer:[B
    const/4 v1, 0x0

    array-length v2, v6

    invoke-virtual {v0, v6, v1, v2}, Landroid/media/AudioRecord;->read([BII)I

    .line 151
    return-void
.end method

.method public blueToothSourceMethod()V
    .locals 1

    .prologue
    .line 185
    invoke-static {}, Landroid/bluetooth/BluetoothAdapter;->getDefaultAdapter()Landroid/bluetooth/BluetoothAdapter;

    move-result-object v0

    .line 186
    .local v0, adapter:Landroid/bluetooth/BluetoothAdapter;
    invoke-virtual {v0}, Landroid/bluetooth/BluetoothAdapter;->getAddress()Ljava/lang/String;

    .line 187
    return-void
.end method

.method public browserSourceMethod()V
    .locals 2

    .prologue
    .line 212
    new-instance v0, Landroid/provider/Browser;

    invoke-direct {v0}, Landroid/provider/Browser;-><init>()V

    .line 213
    .local v0, browser:Landroid/provider/Browser;
    invoke-virtual {p0}, Lcom/example/messagesmali/SourceSetAPI;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v1

    invoke-static {v1}, Landroid/provider/Browser;->getAllBookmarks(Landroid/content/ContentResolver;)Landroid/database/Cursor;

    .line 214
    invoke-virtual {p0}, Lcom/example/messagesmali/SourceSetAPI;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v1

    invoke-static {v1}, Landroid/provider/Browser;->getAllVisitedUrls(Landroid/content/ContentResolver;)Landroid/database/Cursor;

    .line 215
    return-void
.end method

.method public bundleSourceMethod()V
    .locals 3
    .annotation build Landroid/annotation/SuppressLint;
        value = {
            "NewApi"
        }
    .end annotation

    .prologue
    .line 95
    invoke-virtual {p0}, Lcom/example/messagesmali/SourceSetAPI;->getIntent()Landroid/content/Intent;

    move-result-object v1

    invoke-virtual {v1}, Landroid/content/Intent;->getExtras()Landroid/os/Bundle;

    move-result-object v0

    .line 97
    .local v0, bundle:Landroid/os/Bundle;
    const-string v1, "object"

    invoke-virtual {v0, v1}, Landroid/os/Bundle;->get(Ljava/lang/String;)Ljava/lang/Object;

    .line 98
    const-string v1, "booleanKey"

    invoke-virtual {v0, v1}, Landroid/os/Bundle;->getBoolean(Ljava/lang/String;)Z

    .line 99
    const-string v1, "booleanKey"

    const/4 v2, 0x0

    invoke-virtual {v0, v1, v2}, Landroid/os/Bundle;->getBoolean(Ljava/lang/String;Z)Z

    .line 100
    const-string v1, "booleansKey"

    invoke-virtual {v0, v1}, Landroid/os/Bundle;->getBooleanArray(Ljava/lang/String;)[Z

    .line 101
    const-string v1, "bundleKey"

    invoke-virtual {v0, v1}, Landroid/os/Bundle;->getBundle(Ljava/lang/String;)Landroid/os/Bundle;

    .line 102
    const-string v1, "byteKey"

    invoke-virtual {v0, v1}, Landroid/os/Bundle;->getByte(Ljava/lang/String;)B

    .line 103
    const-string v1, "byteKey"

    const/4 v2, 0x2

    invoke-virtual {v0, v1, v2}, Landroid/os/Bundle;->getByte(Ljava/lang/String;B)Ljava/lang/Byte;

    .line 104
    const-string v1, "bytesKey"

    invoke-virtual {v0, v1}, Landroid/os/Bundle;->getByteArray(Ljava/lang/String;)[B

    .line 105
    const-string v1, "charKey"

    invoke-virtual {v0, v1}, Landroid/os/Bundle;->getChar(Ljava/lang/String;)C

    .line 106
    const-string v1, "charKey"

    const/16 v2, 0x6d

    invoke-virtual {v0, v1, v2}, Landroid/os/Bundle;->getChar(Ljava/lang/String;C)C

    .line 107
    const-string v1, "charsKey"

    invoke-virtual {v0, v1}, Landroid/os/Bundle;->getCharArray(Ljava/lang/String;)[C

    .line 108
    const-string v1, "charSqKey"

    invoke-virtual {v0, v1}, Landroid/os/Bundle;->getCharSequence(Ljava/lang/String;)Ljava/lang/CharSequence;

    .line 109
    const-string v1, "charSqsKey"

    invoke-virtual {v0, v1}, Landroid/os/Bundle;->getCharSequenceArray(Ljava/lang/String;)[Ljava/lang/CharSequence;

    .line 110
    const-string v1, "charlistKey"

    invoke-virtual {v0, v1}, Landroid/os/Bundle;->getCharSequenceArrayList(Ljava/lang/String;)Ljava/util/ArrayList;

    .line 111
    invoke-virtual {v0}, Landroid/os/Bundle;->getClassLoader()Ljava/lang/ClassLoader;

    .line 112
    const-string v1, "doubleKey"

    invoke-virtual {v0, v1}, Landroid/os/Bundle;->getDouble(Ljava/lang/String;)D

    .line 113
    const-string v1, "doublesKey"

    invoke-virtual {v0, v1}, Landroid/os/Bundle;->getDoubleArray(Ljava/lang/String;)[D

    .line 114
    const-string v1, "floatKey"

    invoke-virtual {v0, v1}, Landroid/os/Bundle;->getFloat(Ljava/lang/String;)F

    .line 115
    const-string v1, "floatsKey"

    invoke-virtual {v0, v1}, Landroid/os/Bundle;->getFloatArray(Ljava/lang/String;)[F

    .line 116
    const-string v1, "intKey"

    invoke-virtual {v0, v1}, Landroid/os/Bundle;->getInt(Ljava/lang/String;)I

    .line 117
    const-string v1, "intsKey"

    invoke-virtual {v0, v1}, Landroid/os/Bundle;->getIntArray(Ljava/lang/String;)[I

    .line 118
    const-string v1, "intListKey"

    invoke-virtual {v0, v1}, Landroid/os/Bundle;->getIntegerArrayList(Ljava/lang/String;)Ljava/util/ArrayList;

    .line 119
    const-string v1, "longKey"

    invoke-virtual {v0, v1}, Landroid/os/Bundle;->getLong(Ljava/lang/String;)J

    .line 120
    const-string v1, "longsKey"

    invoke-virtual {v0, v1}, Landroid/os/Bundle;->getLongArray(Ljava/lang/String;)[J

    .line 121
    const-string v1, "parcelKey"

    invoke-virtual {v0, v1}, Landroid/os/Bundle;->getParcelable(Ljava/lang/String;)Landroid/os/Parcelable;

    .line 122
    const-string v1, "parcelsKey"

    invoke-virtual {v0, v1}, Landroid/os/Bundle;->getParcelableArray(Ljava/lang/String;)[Landroid/os/Parcelable;

    .line 123
    const-string v1, "parcelListKey"

    invoke-virtual {v0, v1}, Landroid/os/Bundle;->getParcelableArrayList(Ljava/lang/String;)Ljava/util/ArrayList;

    .line 124
    const-string v1, "serialKey"

    invoke-virtual {v0, v1}, Landroid/os/Bundle;->getSerializable(Ljava/lang/String;)Ljava/io/Serializable;

    .line 125
    const-string v1, "shortKey"

    invoke-virtual {v0, v1}, Landroid/os/Bundle;->getShort(Ljava/lang/String;)S

    .line 126
    const-string v1, "shortsKey"

    invoke-virtual {v0, v1}, Landroid/os/Bundle;->getShortArray(Ljava/lang/String;)[S

    .line 127
    const-string v1, "sparseKey"

    invoke-virtual {v0, v1}, Landroid/os/Bundle;->getSparseParcelableArray(Ljava/lang/String;)Landroid/util/SparseArray;

    .line 128
    const-string v1, "stringKey"

    invoke-virtual {v0, v1}, Landroid/os/Bundle;->getString(Ljava/lang/String;)Ljava/lang/String;

    .line 129
    const-string v1, "stringsKey"

    invoke-virtual {v0, v1}, Landroid/os/Bundle;->getStringArray(Ljava/lang/String;)[Ljava/lang/String;

    .line 130
    const-string v1, "stringListKey"

    invoke-virtual {v0, v1}, Landroid/os/Bundle;->getStringArrayList(Ljava/lang/String;)Ljava/util/ArrayList;

    .line 131
    return-void
.end method

.method public calendarSourceMethod()V
    .locals 1

    .prologue
    .line 208
    invoke-static {}, Ljava/util/Calendar;->getInstance()Ljava/util/Calendar;

    move-result-object v0

    invoke-virtual {v0}, Ljava/util/Calendar;->getTimeZone()Ljava/util/TimeZone;

    .line 209
    return-void
.end method

.method public contentResolverSinkMethod()V
    .locals 7

    .prologue
    const/4 v3, 0x0

    .line 218
    invoke-virtual {p0}, Lcom/example/messagesmali/SourceSetAPI;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v0

    sget-object v1, Landroid/provider/Contacts$People;->CONTENT_URI:Landroid/net/Uri;

    .line 219
    const/4 v2, 0x2

    new-array v2, v2, [Ljava/lang/String;

    const/4 v4, 0x0

    const-string v5, "tes1"

    aput-object v5, v2, v4

    const/4 v4, 0x1

    const-string v5, "test2"

    aput-object v5, v2, v4

    move-object v4, v3

    move-object v5, v3

    .line 218
    invoke-virtual/range {v0 .. v5}, Landroid/content/ContentResolver;->query(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;

    move-result-object v6

    .line 220
    .local v6, cursor:Landroid/database/Cursor;
    return-void
.end method

.method public databaseSourceMethod()V
    .locals 10

    .prologue
    const/4 v4, 0x0

    const/4 v3, 0x0

    .line 227
    new-instance v9, Lcom/example/messagesmali/DatabaseHelper;

    const-string v1, "sourceDB"

    const/4 v2, 0x1

    invoke-direct {v9, p0, v1, v3, v2}, Lcom/example/messagesmali/DatabaseHelper;-><init>(Landroid/content/Context;Ljava/lang/String;Landroid/database/sqlite/SQLiteDatabase$CursorFactory;I)V

    .line 228
    .local v9, dbHelper:Lcom/example/messagesmali/DatabaseHelper;
    invoke-virtual {v9}, Lcom/example/messagesmali/DatabaseHelper;->getWritableDatabase()Landroid/database/sqlite/SQLiteDatabase;

    move-result-object v0

    .line 229
    .local v0, db:Landroid/database/sqlite/SQLiteDatabase;
    const-string v1, ""

    invoke-virtual {v0, v1}, Landroid/database/sqlite/SQLiteDatabase;->execSQL(Ljava/lang/String;)V

    .line 230
    const-string v1, ""

    invoke-virtual {v0, v1, v3}, Landroid/database/sqlite/SQLiteDatabase;->rawQuery(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;

    move-result-object v8

    .line 231
    .local v8, cursor:Landroid/database/Cursor;
    :goto_0
    invoke-interface {v8}, Landroid/database/Cursor;->moveToNext()Z

    move-result v1

    if-nez v1, :cond_0

    .line 235
    const-string v1, ""

    new-array v2, v4, [Ljava/lang/String;

    move-object v4, v3

    move-object v5, v3

    move-object v6, v3

    move-object v7, v3

    invoke-virtual/range {v0 .. v7}, Landroid/database/sqlite/SQLiteDatabase;->query(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;

    .line 236
    return-void

    .line 232
    :cond_0
    invoke-interface {v8, v4}, Landroid/database/Cursor;->getString(I)Ljava/lang/String;

    goto :goto_0
.end method

.method public gsmSourceMethod()V
    .locals 3

    .prologue
    .line 196
    const-string v2, "phone"

    invoke-virtual {p0, v2}, Lcom/example/messagesmali/SourceSetAPI;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Landroid/telephony/TelephonyManager;

    .line 197
    .local v1, tm:Landroid/telephony/TelephonyManager;
    invoke-virtual {v1}, Landroid/telephony/TelephonyManager;->getCellLocation()Landroid/telephony/CellLocation;

    move-result-object v0

    check-cast v0, Landroid/telephony/gsm/GsmCellLocation;

    .line 198
    .local v0, location:Landroid/telephony/gsm/GsmCellLocation;
    invoke-virtual {v0}, Landroid/telephony/gsm/GsmCellLocation;->getCid()I

    .line 199
    invoke-virtual {v0}, Landroid/telephony/gsm/GsmCellLocation;->getLac()I

    .line 200
    return-void
.end method

.method public handlerSourceMethod()V
    .locals 6

    .prologue
    .line 165
    new-instance v0, Lcom/example/messagesmali/SourceSetAPI$1;

    invoke-direct {v0, p0}, Lcom/example/messagesmali/SourceSetAPI$1;-><init>(Lcom/example/messagesmali/SourceSetAPI;)V

    .line 175
    .local v0, handler:Landroid/os/Handler;
    const/4 v2, 0x1

    const/4 v3, 0x2

    const/4 v4, 0x3

    new-instance v5, Ljava/lang/Object;

    invoke-direct {v5}, Ljava/lang/Object;-><init>()V

    invoke-virtual {v0, v2, v3, v4, v5}, Landroid/os/Handler;->obtainMessage(IIILjava/lang/Object;)Landroid/os/Message;

    move-result-object v1

    .line 177
    .local v1, msg:Landroid/os/Message;
    return-void
.end method

.method public locationSourceMethod()V
    .locals 3

    .prologue
    .line 58
    const-string v2, "location"

    invoke-virtual {p0, v2}, Lcom/example/messagesmali/SourceSetAPI;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Landroid/location/LocationManager;

    .line 60
    .local v1, locationManager:Landroid/location/LocationManager;
    const-string v2, "gps"

    invoke-virtual {v1, v2}, Landroid/location/LocationManager;->getLastKnownLocation(Ljava/lang/String;)Landroid/location/Location;

    move-result-object v0

    .line 61
    .local v0, location:Landroid/location/Location;
    invoke-virtual {v0}, Landroid/location/Location;->getLongitude()D

    .line 62
    invoke-virtual {v0}, Landroid/location/Location;->getLatitude()D

    .line 63
    return-void
.end method

.method protected onCreate(Landroid/os/Bundle;)V
    .locals 1
    .parameter "savedInstanceState"

    .prologue
    .line 53
    invoke-super {p0, p1}, Landroid/app/Activity;->onCreate(Landroid/os/Bundle;)V

    .line 54
    const v0, 0x7f03001a

    invoke-virtual {p0, v0}, Lcom/example/messagesmali/SourceSetAPI;->setContentView(I)V

    .line 55
    return-void
.end method

.method public packageManagerSourceMethod()V
    .locals 3

    .prologue
    const/16 v2, 0x20

    const/4 v1, 0x0

    .line 154
    invoke-virtual {p0}, Lcom/example/messagesmali/SourceSetAPI;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v0

    .line 155
    .local v0, pm:Landroid/content/pm/PackageManager;
    invoke-virtual {v0, v1}, Landroid/content/pm/PackageManager;->getInstalledApplications(I)Ljava/util/List;

    .line 156
    invoke-virtual {v0, v1}, Landroid/content/pm/PackageManager;->getInstalledPackages(I)Ljava/util/List;

    .line 157
    invoke-virtual {p0}, Lcom/example/messagesmali/SourceSetAPI;->getIntent()Landroid/content/Intent;

    move-result-object v1

    invoke-virtual {v0, v1, v2}, Landroid/content/pm/PackageManager;->queryIntentActivities(Landroid/content/Intent;I)Ljava/util/List;

    .line 158
    invoke-virtual {p0}, Lcom/example/messagesmali/SourceSetAPI;->getIntent()Landroid/content/Intent;

    move-result-object v1

    invoke-virtual {v0, v1, v2}, Landroid/content/pm/PackageManager;->queryBroadcastReceivers(Landroid/content/Intent;I)Ljava/util/List;

    .line 161
    return-void
.end method

.method public pendingIntentSourceMethod()V
    .locals 4

    .prologue
    const/high16 v3, 0x1000

    const/4 v2, 0x0

    .line 134
    new-instance v0, Landroid/content/Intent;

    invoke-direct {v0}, Landroid/content/Intent;-><init>()V

    .line 135
    .local v0, openIntent:Landroid/content/Intent;
    invoke-static {p0, v2, v0, v3}, Landroid/app/PendingIntent;->getActivity(Landroid/content/Context;ILandroid/content/Intent;I)Landroid/app/PendingIntent;

    move-result-object v1

    .line 137
    .local v1, pi:Landroid/app/PendingIntent;
    invoke-static {p0, v2, v0, v3}, Landroid/app/PendingIntent;->getBroadcast(Landroid/content/Context;ILandroid/content/Intent;I)Landroid/app/PendingIntent;

    .line 138
    invoke-static {p0, v2, v0, v3}, Landroid/app/PendingIntent;->getService(Landroid/content/Context;ILandroid/content/Intent;I)Landroid/app/PendingIntent;

    .line 139
    return-void
.end method

.method public sharedPreferenceSourceMethod()V
    .locals 1

    .prologue
    .line 181
    invoke-static {p0}, Landroid/preference/PreferenceManager;->getDefaultSharedPreferences(Landroid/content/Context;)Landroid/content/SharedPreferences;

    move-result-object v0

    .line 182
    .local v0, sharedPreferences:Landroid/content/SharedPreferences;
    return-void
.end method

.method public telephonyManagerSourceMethod()V
    .locals 2

    .prologue
    .line 66
    const-string v1, "phone"

    invoke-virtual {p0, v1}, Lcom/example/messagesmali/SourceSetAPI;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/telephony/TelephonyManager;

    .line 67
    .local v0, tm:Landroid/telephony/TelephonyManager;
    invoke-virtual {v0}, Landroid/telephony/TelephonyManager;->getDeviceId()Ljava/lang/String;

    .line 68
    invoke-virtual {v0}, Landroid/telephony/TelephonyManager;->getSubscriberId()Ljava/lang/String;

    .line 69
    invoke-virtual {v0}, Landroid/telephony/TelephonyManager;->getSimSerialNumber()Ljava/lang/String;

    .line 70
    invoke-virtual {v0}, Landroid/telephony/TelephonyManager;->getLine1Number()Ljava/lang/String;

    .line 71
    return-void
.end method

.method public urlConnectionSourceMethod()V
    .locals 3
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .prologue
    .line 74
    new-instance v1, Ljava/net/URL;

    const-string v2, ""

    invoke-direct {v1, v2}, Ljava/net/URL;-><init>(Ljava/lang/String;)V

    .line 75
    .local v1, url:Ljava/net/URL;
    invoke-virtual {v1}, Ljava/net/URL;->openConnection()Ljava/net/URLConnection;

    move-result-object v0

    check-cast v0, Ljava/net/HttpURLConnection;

    .line 76
    .local v0, connection:Ljava/net/HttpURLConnection;
    invoke-virtual {v0}, Ljava/net/HttpURLConnection;->getOutputStream()Ljava/io/OutputStream;

    .line 77
    invoke-virtual {v0}, Ljava/net/HttpURLConnection;->getInputStream()Ljava/io/InputStream;

    .line 79
    invoke-virtual {v0}, Ljava/net/HttpURLConnection;->disconnect()V

    .line 80
    return-void
.end method

.method public wifiInfoSourceMethod()V
    .locals 3

    .prologue
    .line 190
    const-string v2, "wifi"

    invoke-virtual {p0, v2}, Lcom/example/messagesmali/SourceSetAPI;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Landroid/net/wifi/WifiManager;

    .line 191
    .local v1, wifiManager:Landroid/net/wifi/WifiManager;
    invoke-virtual {v1}, Landroid/net/wifi/WifiManager;->getConnectionInfo()Landroid/net/wifi/WifiInfo;

    move-result-object v0

    .line 192
    .local v0, wifiInfo:Landroid/net/wifi/WifiInfo;
    invoke-virtual {v0}, Landroid/net/wifi/WifiInfo;->getMacAddress()Ljava/lang/String;

    .line 193
    return-void
.end method
