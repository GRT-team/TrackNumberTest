package com.test1.utils;

import com.test1.api.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    private static Retrofit retrofit;

    private static RetrofitHelper ourInstance = new RetrofitHelper();

    public static RetrofitHelper getInstance() {
        return ourInstance;
    }

    private RetrofitHelper() {
        retrofit = new Retrofit.Builder().
                baseUrl(Api.API_PATH).
                addConverterFactory(GsonConverterFactory.create()).
                client(OkHttpHelper.getInstance().getOkHttpClient()).
                build();
    }

    public Retrofit getRetrofit(){
        return retrofit;
    }
}
