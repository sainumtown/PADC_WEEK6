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

    @SerializedName("date_of_birth")
    private String dateOfBirth;

    @SerializedName("country_of_origin")
    private String countryOfOrigin;

    public UserVO(String name, String email, String dateOfBirth, String countryOfOrigin) {
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.countryOfOrigin = countryOfOrigin;
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
}
