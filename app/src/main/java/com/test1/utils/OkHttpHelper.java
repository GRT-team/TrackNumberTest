package com.test1.utils;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpHelper {

    private static OkHttpHelper mInstance = null;
    private OkHttpClient client;

    private OkHttpHelper() {

        client = new okhttp3.OkHttpClient.Builder().
                addInterceptor(new AddCookiesInterceptor()). //      for custom interceptors
                addInterceptor(new ReceivedResponseInterceptor()). //      for custom interceptors
                connectTimeout(2, TimeUnit.MINUTES). //set timeout
                readTimeout(2, TimeUnit.MINUTES).//set timeout
                writeTimeout(2, TimeUnit.MINUTES).//set timeout
                build(); //create OkHttpClient

    }

    public static OkHttpHelper getInstance() {
        if (mInstance == null) {
            mInstance = new OkHttpHelper();
        }
        return mInstance;
    }

    public OkHttpClient getOkHttpClient() {
        return client;
    }

    /**
     * This interceptor put all the Cookies from Preferences to the Request.
     */
    class AddCookiesInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder();

            return chain.proceed(builder.build());
        }
    }

    /**
     * This interceptor handles the Response.
     */
    class ReceivedResponseInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            return newResponse(originalResponse);
        }
        //There should be correct response format to parse it accordingly.
        private Response newResponse(Response originalResponse) {
            JsonObject object = null;
//            String body = bodyToString(originalResponse);

            return originalResponse;
        }

        public String bodyToString(final Response response) {
            try {
                final Response copy = response.newBuilder().build();
                return copy.body().string();
            } catch (final IOException e) {
                return "{}";
            }
        }

    }
}
