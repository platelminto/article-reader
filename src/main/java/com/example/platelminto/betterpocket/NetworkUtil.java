package com.example.platelminto.betterpocket;

import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class NetworkUtil {

    public static String fetchHtmlFromURL(String url) {

        StringBuilder content = new StringBuilder();
        URLConnection connection = null;

        try {
            connection =  new URL("http://www.google.com").openConnection();
            Scanner scanner = new Scanner(connection.getInputStream());
            while(scanner.hasNextLine()) {
                content.append(scanner.nextLine()).append("\n");
            }
        } catch (Exception ex ) {
            ex.printStackTrace();
        }

        return content.toString();
    }
}
