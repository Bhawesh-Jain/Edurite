package com.arihant.edurite.Retrofit;

import com.arihant.edurite.models.LoginModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST(BaseUrl.login)
    Call<LoginModel> login(
            @Field("username") String username,
            @Field("password") String password,
            @Field("fcm") String fcm
    );


}