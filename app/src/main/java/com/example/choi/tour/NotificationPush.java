package com.example.choi.tour;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by choi on 2017-05-09.
 */

public class NotificationPush extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_push);

        Button button = (Button) findViewById(R.id.alarmBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                NotificationManager notificationManager = (NotificationManager) NotificationPush.this.getSystemService(NotificationPush.this.NOTIFICATION_SERVICE);
                Intent intent1 = new Intent(NotificationPush.this.getApplicationContext(), MainActivity.class); //인텐트 생성.


                Notification.Builder builder = new Notification.Builder(getApplicationContext());
                intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);//현재 액티비티를 최상으로 올리고, 최상의 액티비티를 제외한 모든 액티비티를


                PendingIntent pendingNotificationIntent = PendingIntent.getActivity(NotificationPush.this, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
                //PendingIntent는 일회용 인텐트 같은 개념입니다.

                builder.setSmallIcon(R.drawable.on).setTicker("HETT").setWhen(System.currentTimeMillis())
                        .setNumber(1).setContentTitle("Travel Planner").setContentText("최저가 ")
                        .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE).setContentIntent(pendingNotificationIntent).setAutoCancel(true).setOngoing(true);

                notificationManager.notify(1, builder.build()); // Notification send
            }
        });
    }
}