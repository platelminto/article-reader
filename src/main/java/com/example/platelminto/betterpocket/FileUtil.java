package com.example.platelminto.betterpocket;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    // Needs to be set from the MainActivity, represents the app's article storage directory
    protected static File articleStorage;

    // Serializes & writes the Article to its own file
    public static void writeObjectToFile(Article article) {

        File articleFile = getArticleFile(article);
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(articleFile))) {

            articleFile.createNewFile();
            oos.writeObject(article);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ID is usually the article object's hashcode
    public static File getArticleFile(Article article) {

        return new File(articleStorage, Integer.toString(article.getId()) + ".html");
    }

    public static List<Article> getArticlesFromStorage() {

        final List<Article> articles = new ArrayList<>();

        for(File articleFile : FileUtil.articleStorage.listFiles()) {
            try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(articleFile))) {

                final Article article = (Article) ois.readObject();
                articles.add(article);

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return articles;
    }

    // External storage but private, removed when uninstalling the app
    public static File getPrivateStorageDir(Context context, String folderName) {

        File file = new File(context.getExternalFilesDir(
                Environment.DIRECTORY_DOWNLOADS), folderName);

        file.mkdirs();

        return file;
    }
}
