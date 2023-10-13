package com.arihant.edurite.Retrofit;

import com.arihant.edurite.models.AboutUsModel;
import com.arihant.edurite.models.CourseDetailModel;
import com.arihant.edurite.models.CourseListModel;
import com.arihant.edurite.models.FaqModel;
import com.arihant.edurite.models.LoginModel;
import com.arihant.edurite.models.MaterialDetailModel;
import com.arihant.edurite.models.MaterialListModel;
import com.arihant.edurite.models.NotificationModel;
import com.arihant.edurite.models.PrivacyPolicyModel;
import com.arihant.edurite.models.ReviewListModel;
import com.arihant.edurite.models.SignupModel;
import com.arihant.edurite.models.TermsModel;
import com.arihant.edurite.models.UserProfileModel;

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
    @POST(BaseUrl.changePassword)
    Call<LoginModel> changePassword(
            @Field("userId") String userId,
            @Field("password_old") String passwordOld,
            @Field("password_new") String passwordNew
    );

    @FormUrlEncoded
    @POST(BaseUrl.getUserProfile)
    Call<UserProfileModel> getProfile(
            @Field("user_id") String userId
    );


    @FormUrlEncoded
    @POST(BaseUrl.getUsersRatings)
    Call<ReviewListModel> getReview(
            @Field("user_id") String userId
    );

    @FormUrlEncoded
    @POST(BaseUrl.getNotificationByUserId)
    Call<NotificationModel> getNotificationList(
            @Field("user_id") String userId
    );


    @FormUrlEncoded
    @POST(BaseUrl.getCourseDetails)
    Call<CourseDetailModel> getCourseDetails(
            @Field("course_id") String courseId
    );


    @FormUrlEncoded
    @POST(BaseUrl.getMaterialDetails)
    Call<MaterialDetailModel> getMaterialDetails(
            @Field("mat_id") String matId
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

    @FormUrlEncoded
    @POST(BaseUrl.updateProfile)
    Call<SignupModel> updateProfile(
            @Field("user_id") String user_id,
            @Field("name") String name,
            @Field("image") String image
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