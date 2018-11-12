package com.example.platelminto.betterpocket;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;

import com.example.platelminto.betterpocket.databinding.ActivityArticleBinding;

public class ArticleActivity extends AppCompatActivity {

    ActivityArticleBinding activityArticleBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        Intent intent = getIntent();
        Article article = (Article) intent.getSerializableExtra(MainActivity.EXTRA_MESSAGE);

        activityArticleBinding = ActivityArticleBinding.inflate(getLayoutInflater());
        activityArticleBinding = DataBindingUtil.setContentView(this, R.layout.activity_article);

        activityArticleBinding.setArticle(article);
        activityArticleBinding.textView.setMovementMethod(new ScrollingMovementMethod());
    }
}
