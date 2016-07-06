package com.test1.request;

import android.content.Context;

import com.test1.utils.RetrofitHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public abstract class BaseRequest<T> implements Callback<T> {

    private Retrofit retrofit;
    private Context mContext;
    private T service;
    private CallbackListener<T> callbackListener;

    public interface CallbackListener<T> {

        void success(Response<T> response);

        void failure(Throwable t);

        void error(Response<T> error);

    }

    public Context getContext() {
        return mContext;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public BaseRequest(Context context, Class<T> request, CallbackListener<T> callbackListener) {
        retrofit = RetrofitHelper.getInstance().getRetrofit();

        this.mContext = context;
        this.callbackListener = callbackListener;

        service = retrofit.create(request);
    }

    public T getService() {
        return service;
    }

    public abstract void executeAsync();

    public abstract Response<T> executeSync();

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            callbackListener.success(response);
        } else {
            callbackListener.error(response);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        callbackListener.failure(t);
    }
}
