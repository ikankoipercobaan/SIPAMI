package com.uas.sipami.auth;
import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {
    private String authToken;

    public TokenInterceptor(String authToken) {
        this.authToken = authToken;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        // Menambahkan header Authorization dengan token JWT
        Request modifiedRequest = originalRequest.newBuilder()
                .header("Authorization", "Bearer " + authToken)
                .build();

        return chain.proceed(modifiedRequest);
    }
}
