package com.example.platelminto.betterpocket;

import android.os.AsyncTask;

import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class NetworkUtil extends AsyncTask<String, Void, String> {

    public String content = null;

    @Override
    protected String doInBackground(String...url) {
        StringBuilder content = new StringBuilder();
        URLConnection connection = null;

        try {
            connection =  new URL(url[0]).openConnection();
            Scanner scanner = new Scanner(connection.getInputStream());
            while(scanner.hasNextLine()) {
                final String s = scanner.nextLine();
                content.append(s).append("\n");
            }
        } catch (Exception ex ) {
            ex.printStackTrace();
        }

        this.content = content.toString();

        return content.toString();
    }

    @Override
    protected void onPostExecute(String html) {


    }
}
