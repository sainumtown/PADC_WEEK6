package xyz.sainumtown.padc_week6.datas.agents.retrofit;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;
import xyz.sainumtown.padc_week6.datas.responses.AttractionListResponse;
import xyz.sainumtown.padc_week6.datas.responses.UserResponse;
import xyz.sainumtown.padc_week6.utils.MyanmarAttractionsConstants;


/**
 * Created by aung on 7/9/16.
 */
public interface AttractionApi {

    @FormUrlEncoded
    @POST(MyanmarAttractionsConstants.API_GET_ATTRACTION_LIST)
    Call<AttractionListResponse> loadAttractions(
            @Field(MyanmarAttractionsConstants.PARAM_ACCESS_TOKEN) String param);

    @FormUrlEncoded
    @POST(MyanmarAttractionsConstants.API_USER_LOGIN)
    Call<UserResponse> userLogin(
            @Field(MyanmarAttractionsConstants.PARAM_EMAIL) String email,
            @Field(MyanmarAttractionsConstants.PARAM_PASSWORD) String password);

    @FormUrlEncoded
    @POST(MyanmarAttractionsConstants.API_USER_REGISTER)
    Call<UserResponse> userRegister(
            @Field(MyanmarAttractionsConstants.PARAM_EMAIL) String email,
            @Field(MyanmarAttractionsConstants.PARAM_PASSWORD) String password,
            @Field(MyanmarAttractionsConstants.PARAM_DOB) String dob,
            @Field(MyanmarAttractionsConstants.PARAM_REGION) String region,
            @Field(MyanmarAttractionsConstants.PARAM_NAME)String name);
}
