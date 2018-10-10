package com.wallet.track.dev.melvin9.trackme;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.wallet.track.dev.melvin9.trackme.Database.DatabaseHelper;
import com.wallet.track.dev.melvin9.trackme.Database.SqlData;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.app.Notification.DEFAULT_SOUND;
import static android.app.Notification.DEFAULT_VIBRATE;
import static android.content.Context.NOTIFICATION_SERVICE;

public class AlarmReceiver extends BroadcastReceiver {
static int i;
public static String B_ID;
    @Override
    public void onReceive(Context context, Intent intent) {
        DatabaseHelper db1 = new DatabaseHelper(context);
        SqlData data1 = db1.getData();
        // TODO Auto-generated method stub
        Uri uriSms = Uri.parse("content://sms/");
        @SuppressLint("Recycle") final Cursor cursor = context.getContentResolver().query(uriSms,
                new String[]{"_id", "address","person", "date", "body","type"}, "address like '%"+data1.getAddress()+"%' and lower(body) like '%debited%'",null, "date desc");
        Log.e("Main",data1.getAddress());
        assert cursor != null;
        if (cursor.moveToNext()) {
            String address = cursor.getString(1);
            String amt = null;
            String msg = cursor.getString(4);
            String date = cursor.getString(3);
            DatabaseHelper db = new DatabaseHelper(context);
            SqlData data = db.getData();
            if (address != null) {
                Date date1 = new Date(Long.parseLong(date));
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formatted = format.format(date1);
                if (formatted.compareTo(data.getTimestamp()) > 0) {
                    Pattern regEx
                            = Pattern.compile("(?:(?:Rs|RS|inr|INR)\\.?\\s?)(\\d+(:?\\,\\d+)?(\\,\\d+)?(\\.\\d{1,2})?)");
                    // Find instance of pattern matches
                    Matcher m = regEx.matcher(msg);
                    if (m.find()) {
                        try {
                            Log.e("amount_value= ", "" + m.group(0));
                            amt = (m.group(0).replaceAll("inr", ""));
                            amt = amt.replaceAll("rs", "");
                            amt = amt.replaceAll("Rs.", "");
                            amt = amt.replaceAll("rs.", "");
                            amt = amt.replaceAll("Rs", "");
                            amt = amt.replaceAll("INR", "");
                            amt = amt.replaceAll("INR.", "");
                            amt = amt.replaceAll("inr", "");
                            amt = amt.replaceAll(" ", "");
                            amt = amt.replaceAll(",", "");

                        } catch (Exception ignored) {
                        }
                    } else {
                        Log.e("No_matchedValue ", "No_matchedValue ");
                    }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        String name = "123";
                        String description = "123";
                        int importance = NotificationManager.IMPORTANCE_HIGH; //Important for heads-up notification
                        NotificationChannel channel = new NotificationChannel("1", name, importance);
                        channel.setDescription(description);
                        channel.setShowBadge(true);
                        channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
                        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
                        notificationManager.createNotificationChannel(channel);
                    }

                    RemoteViews notificationLayoutExpanded = new RemoteViews(context.getPackageName(), R.layout.expanded_custom_push_green);
                    notificationLayoutExpanded.setTextViewText(R.id.title1,"TRACK ME");
                    Intent notificationIntent = new Intent(context, MainActivity.class);

                    notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                            | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                    PendingIntent intent1 = PendingIntent.getActivity(context, 0,
                            notificationIntent, 0);
                    notificationLayoutExpanded.setTextViewText(R.id.text1,"Amount of RS "+amt+" was debited.\nUse Track me for further details");
                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "1")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContent(notificationLayoutExpanded)
                             .setColor(context.getResources().getColor(R.color.colorGreen))
                            .setAutoCancel(true)
                            .setContentIntent(intent1)
                            .setDefaults(DEFAULT_SOUND | DEFAULT_VIBRATE) //Important for heads-up notification
                            .setPriority(Notification.PRIORITY_MAX);

                    Notification buildNotification = mBuilder.build();
                    NotificationManager mNotifyMgr = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
                    mNotifyMgr.notify(001, buildNotification);
                }

            }
        }

    }

}