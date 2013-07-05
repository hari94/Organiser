package com.delta.delta_organiser;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class NotifyService extends Service{
NotificationManager nm;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.v("noti", "oncreate");
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		Log.v("noti", "oncreate");
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		String str=(String) intent.getExtras().get("date");
		nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Log.v("notifyservice", "on onStartCommand");
        Intent viewRemIntent = new Intent(this, ViewReminder.class);
        viewRemIntent.putExtra("key", str);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, viewRemIntent, 0);

        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("You've got a TASK to do!!!")
                .setContentText("Just bothering you from example code")
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentIntent(pIntent)
                .build();
        
        nm.notify(0, notification); 
	
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		Log.v("noti", "oncreate");
		return null;
	}
	

	

}
