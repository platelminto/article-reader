package com.example.platelminto.betterpocket;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

// Is called to download a url's html in the background
public class DownloadTask extends IntentService {

    String html;
    public static String url; // Needs to be set before this intent is executed
    public static final String EXTRA_MESSENGER="com.example.platelminto.betterpocket.EXTRA_MESSENGER";

    public DownloadTask() {
        super("DownloadTask");
    }

    @Override
    public void onCreate() {

        super.onCreate();
    }

    // Called when an intent is called with this task as its target
    @Override
    protected void onHandleIntent(Intent intent) {

        html = fetchHtml();

        sendMessage(intent);
    }

    // Fetches the url's html
    private String fetchHtml() {

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

        return content.toString();
    }

    // Send message with this object (and the html) back to the original context defined by EXTRA_MESSENGER
    private void sendMessage(Intent intent) {

        Bundle extras = intent.getExtras();

        // Add this object to the message
        if(extras != null) {
            Messenger messenger = (Messenger) extras.get(EXTRA_MESSENGER);
            Message message = Message.obtain();

            message.obj = this;

            try {
                messenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
