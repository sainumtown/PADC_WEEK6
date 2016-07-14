package xyz.sainumtown.padc_week6.datas.responses;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import xyz.sainumtown.padc_week6.datas.vos.AttractionVO;


/**
 * Created by aung on 7/9/16.
 */
public class AttractionListResponse {

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("attraction")
    private ArrayList<AttractionVO> attractionList;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<AttractionVO> getAttractionList() {
        return attractionList;
    }
}
