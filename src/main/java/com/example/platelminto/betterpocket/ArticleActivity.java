package com.example.platelminto.betterpocket;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;

import com.example.platelminto.betterpocket.databinding.ActivityArticleBinding;

public class ArticleActivity extends AppCompatActivity {

    ActivityArticleBinding activityArticleBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        Intent intent = getIntent();
        // Serializable stuff needed to pass around objects through messages
        Article article = (Article) intent.getSerializableExtra(MainActivity.EXTRA_MESSAGE);

        activityArticleBinding = ActivityArticleBinding.inflate(getLayoutInflater());
        activityArticleBinding = DataBindingUtil.setContentView(this, R.layout.activity_article);

        // Sets the article variable that will be used in the layout file to the message-sent article
        activityArticleBinding.setArticle(article);

        // Allows vertical scrolling
        activityArticleBinding.textView.setMovementMethod(new ScrollingMovementMethod());
    }
}