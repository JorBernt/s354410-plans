package apputvikling.jorber.s354410_plans.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class PeriodicService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent i = new Intent(getApplicationContext(), SendService.class);
        PendingIntent pintent = PendingIntent.getService(getApplicationContext(), 0, i, 0);
        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, getSharedPreferences("PREFERENCE", MODE_PRIVATE).getLong("NotificationTime", 0), AlarmManager.INTERVAL_DAY, pintent);
        return super.onStartCommand(intent, flags, startId);
    }
}
