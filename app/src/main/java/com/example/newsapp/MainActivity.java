package com.example.newsapp;

import androidx.core.content.ContextCompat;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.Models.NewsApiResponse;
import com.example.newsapp.Models.NewsHeadlines;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements SelectListener, View.OnClickListener {

    RecyclerView recyclerView;
    CustomAdapter adapter;
    RequestManager manager;
    ProgressDialog dialog;

    Button b1, b2, b3, b4, b5, b6, b7;

    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ImageView back = findViewById(R.id.btn_back);

        back.setOnClickListener(v -> finish());

        // Progress Dialog
        dialog = new ProgressDialog(this);
        dialog.setTitle("Fetching News Articles...");

        // Request Manager
        manager = new RequestManager(this);

        // SearchView
        searchView = findViewById(R.id.serach_view);

        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {

                    @Override
                    public boolean onQueryTextSubmit(String query) {

                        dialog.setTitle(query + " News");

                        dialog.show();
                        searchView.clearFocus();

                        manager.getNewsHeadlines(
                                listener,
                                "general",
                                query
                        );

                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {

                        return false;
                    }
                });

        // Buttons
        b1 = findViewById(R.id.btn_1);
        b2 = findViewById(R.id.btn_2);
        b3 = findViewById(R.id.btn_3);
        b4 = findViewById(R.id.btn_4);
        b5 = findViewById(R.id.btn_5);
        b6 = findViewById(R.id.btn_6);
        b7 = findViewById(R.id.btn_7);

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        b6.setOnClickListener(this);
        b7.setOnClickListener(this);

        // RecyclerView
        recyclerView = findViewById(R.id.recyclear_main);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(
                new GridLayoutManager(this, 1));

        recyclerView.setNestedScrollingEnabled(true);

        // Default News
        getNews();
    }

    // Default News
    private void getNews() {

        dialog.show();

        manager.getNewsHeadlines(
                listener,
                "technology",
                null
        );
    }

    // Listener
    private final OnFaceDataListener<NewsApiResponse> listener =
            new OnFaceDataListener<NewsApiResponse>() {

                @Override
                public void onFetchdata(
                        List<NewsHeadlines> list,
                        String message) {

                    dialog.dismiss();

                    if (list == null || list.isEmpty()) {

                        Toast.makeText(
                                MainActivity.this,
                                message,
                                Toast.LENGTH_LONG
                        ).show();

                        return;
                    }

                    showNews(list);
                }

                @Override
                public void onError(String message) {

                    dialog.dismiss();

                    Toast.makeText(
                            MainActivity.this,
                            "Error: " + message,
                            Toast.LENGTH_LONG
                    ).show();

                    Log.e("NEWS_ERROR", message);
                }
            };

    // Show News
    private void showNews(List<NewsHeadlines> list) {

        adapter = new CustomAdapter(
                this,
                list,
                this
        );

        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    private void resetButtons() {

        b1.setBackgroundTintList(
                ContextCompat.getColorStateList(this, R.color.light_gray));

        b2.setBackgroundTintList(
                ContextCompat.getColorStateList(this, R.color.light_gray));

        b3.setBackgroundTintList(
                ContextCompat.getColorStateList(this, R.color.light_gray));

        b4.setBackgroundTintList(
                ContextCompat.getColorStateList(this, R.color.light_gray));

        b5.setBackgroundTintList(
                ContextCompat.getColorStateList(this, R.color.light_gray));

        b6.setBackgroundTintList(
                ContextCompat.getColorStateList(this, R.color.light_gray));

        b7.setBackgroundTintList(
                ContextCompat.getColorStateList(this, R.color.light_gray));

        b1.setTextColor(
                ContextCompat.getColor(this, R.color.gray_dark));

        b2.setTextColor(
                ContextCompat.getColor(this, R.color.gray_dark));

        b3.setTextColor(
                ContextCompat.getColor(this, R.color.gray_dark));

        b4.setTextColor(
                ContextCompat.getColor(this, R.color.gray_dark));

        b5.setTextColor(
                ContextCompat.getColor(this, R.color.gray_dark));

        b6.setTextColor(
                ContextCompat.getColor(this, R.color.gray_dark));

        b7.setTextColor(
                ContextCompat.getColor(this, R.color.gray_dark));
    }


    // Open Details Page
    @Override
    public void OnNewsClicked(NewsHeadlines headlines) {

        startActivity(
                new Intent(
                        MainActivity.this,
                        DetailsActivity.class
                ).putExtra("data", headlines)
        );
    }

    // Category Buttons
    @Override
    public void onClick(View view) {

        // Reset all buttons
        resetButtons();

        // Selected button
        Button button = (Button) view;

        // Make selected button blue
        button.setBackgroundTintList(
                ContextCompat.getColorStateList(this, R.color.blue));

        button.setTextColor(
                ContextCompat.getColor(this, R.color.white));

        // Get category
        String category = button.getText().toString();

        // Dialog
        dialog.setTitle(category + " News");

        dialog.show();

        // Fetch news
        manager.getNewsHeadlines(
                listener,
                category,
                null
        );
    }
}