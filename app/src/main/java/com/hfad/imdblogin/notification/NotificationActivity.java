package com.hfad.imdblogin.notification;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hfad.imdblogin.R;


/**
 * Created by Boibi on 3/28/2018.
 */

public class NotificationActivity extends AppCompatActivity {

    private EditText etTextTitle;
    private EditText etTextMessage;
    private Button btnChannel;

    private NotificationHelper notificationHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        etTextTitle = findViewById(R.id.et_title);
        etTextMessage =  findViewById(R.id.et_message);
        btnChannel = findViewById(R.id.btn_channel);

        notificationHelper = new NotificationHelper(this);

        btnChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToChannel(etTextTitle.getText().toString(), etTextMessage.getText().toString());
            }
        });

    }

    public void sendToChannel(String title, String message)
    {
        NotificationCompat.Builder notificationBuilder = notificationHelper.getChannelNotification(title, message);
        notificationHelper.getNotificationManager().notify(1, notificationBuilder.build());
    }


}
