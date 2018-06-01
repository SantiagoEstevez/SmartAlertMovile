package com.santiago.smartalert.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Santiago on 22/5/2018.
 */

public class ServiceGenerator {
    //private static final String BASE_URL = "http://172.16.104.83:8080/Proyecto2018/rest/seguridad/";
    private static final String BASE_URL = "http://10.0.2.2:52087/api/values/";

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    private static OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder();

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
