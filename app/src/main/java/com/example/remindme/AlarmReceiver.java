package com.example.remindme;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Calendar;

import static android.media.RingtoneManager.setActualDefaultRingtoneUri;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context arg0, Intent arg1) {
        String message = arg1.getStringExtra("Message");
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(arg0, "alarms")
                .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                .setContentTitle(message)
                .setSound(alarmSound, AudioManager.STREAM_RING);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(arg0);
        notificationManagerCompat.notify(200, builder.build());

        Calendar c = Calendar.getInstance();
        Ringtone r = RingtoneManager.getRingtone(arg0, alarmSound);
        r.play();

    }

}
