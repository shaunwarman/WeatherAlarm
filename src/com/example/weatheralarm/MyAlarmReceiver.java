package com.example.weatheralarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


/**
 * Created by shaunw on 9/25/13.
 */
public class MyAlarmReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            Intent scheduledIntent = new Intent(context, AlarmAlertActivity.class);
            scheduledIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(scheduledIntent);
        }

    }
