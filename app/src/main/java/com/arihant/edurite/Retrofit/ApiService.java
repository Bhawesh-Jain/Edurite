package com.arihant.edurite.Retrofit;

import com.arihant.edurite.models.AboutUsModel;
import com.arihant.edurite.models.CourseListModel;
import com.arihant.edurite.models.FaqModel;
import com.arihant.edurite.models.LoginModel;
import com.arihant.edurite.models.MaterialListModel;
import com.arihant.edurite.models.PrivacyPolicyModel;
import com.arihant.edurite.models.SignupModel;
import com.arihant.edurite.models.TermsModel;

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

    @FormUrlEncoded
    @POST(BaseUrl.signup)
    Call<SignupModel> signup(
            @Field("name") String name,
            @Field("username") String username,
            @Field("password") String password,
            @Field("image") String image,
            @Field("fcm") String fcm
    );

    @POST(BaseUrl.getCourseList)
    Call<CourseListModel> getCourseList();

    @POST(BaseUrl.getMaterialList)
    Call<MaterialListModel> getMaterialList();

    @POST(BaseUrl.getAboutUs)
    Call<AboutUsModel> getAboutUs();
    @POST(BaseUrl.getPrivacyPolicy)
    Call<PrivacyPolicyModel> getPrivacyPolicy();
    @POST(BaseUrl.getTerms)
    Call<TermsModel> getTerms();
    @POST(BaseUrl.get_faq)
    Call<FaqModel> getFaq();
}