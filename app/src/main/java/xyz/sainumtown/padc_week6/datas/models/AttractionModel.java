package xyz.sainumtown.padc_week6.datas.models;

import android.content.Context;

import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Type;

import java.util.List;

import xyz.sainumtown.padc_week6.PADC_WEEK6_APP;
import xyz.sainumtown.padc_week6.datas.vos.AttractionVO;
import xyz.sainumtown.padc_week6.utils.CommonInstances;
import xyz.sainumtown.padc_week6.utils.JsonUtils;

/**
 * Created by User on 7/8/2016.
 */
public class AttractionModel {
    private static final String DUMMY_ATTRACTIOIN_LIST = "myanmar_attractions.json";
    private static AttractionModel objInstance;

    private List<AttractionVO> attractionList;

    public static AttractionModel getInstance() {
        if (objInstance == null) {
            objInstance = new AttractionModel();
        }
        return objInstance;
    }

    public AttractionModel() {
        this.attractionList = setUpInitialAttractions();
    }

    public AttractionVO getAttraction(int index) {
        return attractionList.get(index);
    }

    private List<AttractionVO> setUpInitialAttractions() {
        Context context = PADC_WEEK6_APP.getContext();

        try {
            String dummyAttractionList = JsonUtils.getInstance().loadDummyData(DUMMY_ATTRACTIOIN_LIST);
            Type listType = new TypeToken<List<AttractionVO>>() {
            }.getType();
            attractionList = CommonInstances.getGsonInstance().fromJson(dummyAttractionList, listType);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return attractionList;
    }

    public List<AttractionVO> getAttractionList() {
        return attractionList;
    }

    public AttractionVO getAttractionByTitle(String attractionTitle) {
        for (AttractionVO attraction : attractionList) {
            if (attraction.getTitle().equals(attractionTitle))
                return attraction;
        }
        return null;
    }
}
