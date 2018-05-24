package com.example.silver_desk.interfactest.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.silver_desk.interfactest.HomeActivity;
import com.example.silver_desk.interfactest.R;
import com.example.silver_desk.interfactest.database.AppDatabase;
import com.example.silver_desk.interfactest.database.Evenement;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TimerService extends Service {
    // constant
    public static final long NOTIFY_INTERVAL = 30 * 1000; // 30 seconds
    private  static AppDatabase DATABASE ;
    // run on another Thread to avoid crash
    private Handler mHandler = new Handler();
    // timer handling
    private Timer mTimer = null;

    private String CHANNEL_ID="Alarme";
    private final int NOTIFICATION_ID=1;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        DATABASE= Room.databaseBuilder(this,AppDatabase.class,"AppDatabase").allowMainThreadQueries().build();
        Calendar now = Calendar.getInstance();

        // cancel if already existed
        if(mTimer != null) {
            mTimer.cancel();
        } else {
            // recreate new
            mTimer = new Timer();
        }
        // schedule task
        mTimer.scheduleAtFixedRate(new TimeDisplayTimerTask(), 0, NOTIFY_INTERVAL);
    }

    class TimeDisplayTimerTask extends TimerTask {

        @Override
        public void run() {
            // run on another thread
            mHandler.post(new Runnable() {

                @Override
                public void run() {

                    long currentTime=System.currentTimeMillis();
                    List<Evenement> Event=DATABASE.evenementDao().selectCurrentEvenement(currentTime);
                    displayNotification(Event);

                }

            });
        }





    }
    private void displayNotification(List<Evenement> event){

        for (int i=0;i <event.size();i++){
            //Son de la notification
            Uri soundURI= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
           //Create channel if Android >= 8.0
            createNotificationChannel();

            Intent homeIntent=new Intent(this, HomeActivity.class);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);

            PendingIntent homePendingIntent=PendingIntent.getActivity(this,0,homeIntent,PendingIntent.FLAG_ONE_SHOT);

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this,CHANNEL_ID);
            mBuilder.setSmallIcon(R.drawable.ic_eventnotification);
            mBuilder.setContentTitle(event.get(i).getLibele());
            mBuilder.setContentText(event.get(i).getDescription());
            mBuilder.setSound(soundURI);
            mBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
            mBuilder.setAutoCancel(true);
            mBuilder.setContentIntent(homePendingIntent);

            NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(this);
            notificationManagerCompat.notify(i,mBuilder.build());



        }
    }

    //créer channel pour les versions Oreo et ultérieure
    private void createNotificationChannel(){

        //Tester si la version de Android est supérieure ou égale à 8.0
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            //Name of the channel & description and its importance
            String channelName="Event notification";
            String channelDescription="Notification about your events";
            int importance= NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel=new NotificationChannel(CHANNEL_ID,channelName,importance);
            notificationChannel.setDescription(channelDescription);

            NotificationManager notificationManager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}
