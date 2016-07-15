package xyz.sainumtown.padc_week6.datas.vos;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by User on 7/14/2016.
 */
public class UserVO {

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("date_of_birth")
    private String dateOfBirth;

    @SerializedName("country_of_origin")
    private String countryOfOrigin;

    public UserVO(String name, String email, String dateOfBirth, String countryOfOrigin, String accessToken) {
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.countryOfOrigin = countryOfOrigin;
        this.accessToken = accessToken;
    }

    public UserVO() {
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
