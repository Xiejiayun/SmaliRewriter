D:
cd D:\androidAPK\SendSMSAutoDapk
apktool b D:\androidAPK\SendSMSAutoDapk -o newSms.apk
keytool -genkey -v -keystore debug.keystore -alias android -keyalg RSA -keysize 2048 -validity 20000
jarsigner -verbose -sigalg SHA1withRSA -digestalg SHA1 -keystore debug.keystore newSms.apk android
Pause
EXIT