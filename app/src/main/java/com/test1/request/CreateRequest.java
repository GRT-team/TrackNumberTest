package com.test1.request;

import android.content.Context;

import com.google.gson.JsonObject;
import com.test1.api.Api;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by paul on 2016/07/06.
 */
public class CreateRequest extends BaseRequest {

    Call<JsonObject> request;

    public CreateRequest(Context context, BaseRequest.CallbackListener<JsonObject> callback, String phone, String internationalCode) {

        super(context, CreateInterface.class, callback);
//        ProgressId progressId = new ProgressId();
//        progressId.setId(result.getScore() == null ? result.getId() : result.getScore().getId());
//        String json = GsonHelper.getGsonNewInstance(context).toJson(progressId);
//        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
        request = ((CreateInterface) getService()).makeRequest(phone, internationalCode);

    }

    @Override
    public void executeAsync() {
        request.enqueue(this);
    }

    @Override
    public Response<JsonObject> executeSync() {
        try {
            return request.execute();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private interface CreateInterface {
        @FormUrlEncoded
        @POST(Api.CREATE)
        Call<JsonObject> makeRequest(@Field("phone") String phone, @Field("internationalCode") String internationalCode);

    }

}
