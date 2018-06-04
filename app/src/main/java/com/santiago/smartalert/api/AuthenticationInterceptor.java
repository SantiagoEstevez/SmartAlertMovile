package com.santiago.smartalert.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Santiago on 3/6/2018.
 */

public class AuthenticationInterceptor implements Interceptor {
    private String authToken;
    private String authTag;

    public AuthenticationInterceptor(String tag, String token) {
        this.authToken = token;
        this.authTag = tag;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request.Builder builder = original.newBuilder()
                .header(authTag, authToken);

        Request request = builder.build();
        return chain.proceed(request);
    }
}
