package apputvikling.jorber.s354410_plans.services;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import apputvikling.jorber.s354410_plans.R;
import apputvikling.jorber.s354410_plans.Repository;
import apputvikling.jorber.s354410_plans.fragments.AppointmentViewFragment;
import apputvikling.jorber.s354410_plans.models.Appointment;
import apputvikling.jorber.s354410_plans.models.Contact;

public class SendService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        class GetAppointments extends AsyncTask<Void, Void, List<Appointment>> {
            @Override
            protected List<Appointment> doInBackground(Void... voids) {
                return Repository.getInstance(getApplicationContext()).getAllAppointments();
            }

            @Override
            protected void onPostExecute(List<Appointment> appointments) {
                super.onPostExecute(appointments);
                appointments.sort(Comparator.comparing(Appointment::getLocalDateTime));
                LocalDate dateNow = LocalDate.now();
                Intent i = new Intent(getApplicationContext(), AppointmentViewFragment.class);
                PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), 0, i, 0);
                boolean sendSMS = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("SEND_SMS", false);
                System.out.println(sendSMS);
                for (Appointment a : appointments) {
                    if (!a.getLocalDateTime().toLocalDate().isEqual(dateNow))
                        continue;
                    if (sendSMS)
                        sendSms(a);
                    sendNotification(a, pIntent);
                }
            }
        }
        GetAppointments get = new GetAppointments();
        get.execute();
        return super.onStartCommand(intent, flags, startId);
    }

    private void sendNotification(Appointment appointment, PendingIntent pIntent) {
        Notification notification = new NotificationCompat.Builder(this, "NotificationChannel")
                .setContentTitle(appointment.getTitle())
                .setContentText(appointment.getMessage())
                .setSmallIcon(R.drawable.appointment_icon)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pIntent).build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).notify(88, notification);
    }

    private void sendSms(Appointment appointment) {
        int MY_PERMISSIONS_REQUEST_SEND_SMS = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS);
        int MY_PHONE_STATE_PERMISSION = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE);
        if (MY_PERMISSIONS_REQUEST_SEND_SMS == PackageManager.PERMISSION_GRANTED &&
                MY_PHONE_STATE_PERMISSION ==
                        PackageManager.PERMISSION_GRANTED) {
            SmsManager smsMan = SmsManager.getDefault();
            String message = appointment.getMessage().isEmpty() ?
                    getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("SMS_default_text" + appointment.getTime(), "")
                    :
                    appointment.getMessage();
            for (Contact c : appointment.getContacts().getContacts()) {
                smsMan.sendTextMessage(c.getPhoneNumber(), null, message, null, null);
            }
            Toast.makeText(this, getString(R.string.sent_sms), Toast.LENGTH_SHORT).show();
        }
    }
}
