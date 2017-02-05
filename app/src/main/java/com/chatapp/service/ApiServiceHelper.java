package com.chatapp.service;

import com.chatapp.Config;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by thanhnguyen on 2/5/17.
 */

public class ApiServiceHelper {

    private static volatile ApiService service;

    public static ApiService getInstance() {
        if (service == null) {
            synchronized (ApiService.class) {
                if (service == null) {
                    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

                    OkHttpClient.Builder client = new OkHttpClient.Builder();

                    client.addInterceptor(loggingInterceptor);
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(Config.BASE_URL)
                            .client(client.build())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    service = retrofit.create(ApiService.class);
                }
            }
        }

        return service;

    }
}
