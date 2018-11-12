package com.example.platelminto.betterpocket;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class DownloadTask extends IntentService {

    String html;
    public static String url;
    public static final String EXTRA_MESSENGER="com.example.platelminto.betterpocket.EXTRA_MESSENGER";

    public DownloadTask() {
        super("DownloadTask");
    }

    @Override
    public void onCreate() {

        super.onCreate();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        int result = Activity.RESULT_CANCELED;

        StringBuilder content = new StringBuilder();
        URLConnection connection = null;

        try {
            connection =  new URL(url).openConnection();
            Scanner scanner = new Scanner(connection.getInputStream());
            while(scanner.hasNextLine()) {
                final String s = scanner.nextLine();
                content.append(s).append("\n");
            }
        } catch (Exception ex ) {
            ex.printStackTrace();
        }

        html = content.toString();

        Bundle extras = intent.getExtras();

        if(extras != null) {
            Messenger messenger = (Messenger) extras.get(EXTRA_MESSENGER);
            Message message = Message.obtain();

            message.arg1 = result;
            message.obj = this;

            try {
                messenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
