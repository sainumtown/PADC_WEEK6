package xyz.sainumtown.padc_week6.datas.agents.retrofit;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import xyz.sainumtown.padc_week6.datas.responses.AttractionListResponse;
import xyz.sainumtown.padc_week6.utils.MyanmarAttractionsConstants;


/**
 * Created by aung on 7/9/16.
 */
public interface AttractionApi {

    @FormUrlEncoded
    @POST(MyanmarAttractionsConstants.API_GET_ATTRACTION_LIST)
    Call<AttractionListResponse> loadAttractions(
            @Field(MyanmarAttractionsConstants.PARAM_ACCESS_TOKEN) String param);
}
