package apputvikling.jorber.s354410_plans.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BroadCastReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, PeriodicService.class);
        context.startService(i);
    }
}
