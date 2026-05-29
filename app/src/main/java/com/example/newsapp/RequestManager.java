package com.example.newsapp;

import android.content.Context;

import com.example.newsapp.Models.NewsApiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RequestManager {

    Context context;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManager(Context context) {

        this.context = context;
    }

    // Main Function
    public void getNewsHeadlines(
            OnFaceDataListener listener,
            String category,
            String query) {

        CallNewApi callNewApi =
                retrofit.create(CallNewApi.class);

        Call<NewsApiResponse> call;

        // If user searches something
        if (query != null && !query.isEmpty()) {

            call = callNewApi.callNews(
                    query,
                    context.getString(R.string.api_key)
            );
        }

        // If user clicks category button
        else {

            call = callNewApi.callNews(
                    category,
                    context.getString(R.string.api_key)
            );
        }

        try {

            call.enqueue(new Callback<NewsApiResponse>() {

                @Override
                public void onResponse(
                        Call<NewsApiResponse> call,
                        Response<NewsApiResponse> response) {

                    if (!response.isSuccessful()) {

                        listener.onError(
                                "Code: "
                                        + response.code()
                                        + "\nMessage: "
                                        + response.message()
                        );

                        return;
                    }

                    if (response.body() == null) {

                        listener.onError(
                                "Response body is NULL"
                        );

                        return;
                    }

                    if (response.body().getArticles() == null) {

                        listener.onError(
                                "No Articles Found"
                        );

                        return;
                    }

                    listener.onFetchdata(
                            response.body().getArticles(),
                            response.message()
                    );
                }

                @Override
                public void onFailure(
                        Call<NewsApiResponse> call,
                        Throwable throwable) {

                    listener.onError(
                            throwable.getMessage()
                    );
                }
            });

        }

        catch (Exception e) {

            e.printStackTrace();
        }
    }

    // API Interface
    public interface CallNewApi {

        @GET("everything")
        Call<NewsApiResponse> callNews(

                @Query("q") String query,
                @Query("apiKey") String api_key
        );
    }
}
