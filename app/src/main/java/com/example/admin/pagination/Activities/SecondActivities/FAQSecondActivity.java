package com.example.admin.pagination.Activities.SecondActivities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.admin.pagination.R;

public class FAQSecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqsecond);
        TextView tvNews=(TextView) findViewById(R.id.tv_news_second);
        TextView tvAnswer=(TextView) findViewById(R.id.tv_answer);
        String text=getIntent().getStringExtra("text");
        String title=getIntent().getStringExtra("title");
        tvAnswer.setText(title);
        tvNews.setText(text);
        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle(R.string.ac_faq);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);

    }

}
