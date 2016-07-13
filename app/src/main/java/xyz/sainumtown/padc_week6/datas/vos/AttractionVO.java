package xyz.sainumtown.padc_week6.datas.vos;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by User on 7/8/2016.
 */
public class AttractionVO {

    @SerializedName("title")
    private String title;

    @SerializedName("desc")
    private String desc;

    @SerializedName("images")
    private List<String> images;

    public AttractionVO(String title, String desc, List<String> images) {
        this.title = title;
        this.desc = desc;
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public List<String> getImages() {
        return images;
    }
}
