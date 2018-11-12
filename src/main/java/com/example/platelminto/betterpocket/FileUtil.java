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

    protected static File articleStorage;

    public static void writeObjectToFile(Article article) {

        File articleFile = getArticleFile(article);
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(articleFile))) {

            articleFile.createNewFile();
            oos.writeObject(article);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    public static File getPrivateStorageDir(Context context, String albumName) {

        File file = new File(context.getExternalFilesDir(
                Environment.DIRECTORY_DOWNLOADS), albumName);

        file.mkdirs();

        return file;
    }
}
