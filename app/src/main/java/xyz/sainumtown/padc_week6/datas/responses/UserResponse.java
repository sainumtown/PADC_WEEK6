package xyz.sainumtown.padc_week6.datas.responses;

import com.google.gson.annotations.SerializedName;

import xyz.sainumtown.padc_week6.datas.vos.UserVO;

/**
 * Created by User on 7/14/2016.
 */
public class UserResponse {

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("login_user")
    private UserVO user;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public UserVO getUser() {
        return user;
    }
}
