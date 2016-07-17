package xyz.sainumtown.padc_week6.datas.models;

import android.content.Context;

import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Type;

import de.greenrobot.event.EventBus;
import xyz.sainumtown.padc_week6.PADC_WEEK6_APP;
import xyz.sainumtown.padc_week6.datas.agents.AttractionDataAgent;
import xyz.sainumtown.padc_week6.datas.agents.retrofit.RetrofitDataAgent;
import xyz.sainumtown.padc_week6.datas.vos.AttractionVO;
import xyz.sainumtown.padc_week6.datas.vos.UserVO;
import xyz.sainumtown.padc_week6.events.DataEvent;
import xyz.sainumtown.padc_week6.utils.CommonInstances;
import xyz.sainumtown.padc_week6.utils.JsonUtils;

/**
 * Created by User on 7/14/2016.
 */
public class UserModel {
    private static final String DUMMY_USER_DENIED = "user_denied.json";
    private static UserModel objInstance;

    private UserVO user;

    public UserVO getUser() {
        return user;
    }

    private AttractionDataAgent dataAgent;

    public static UserModel getInstance() {
        if (objInstance == null) {
            objInstance = new UserModel();
        }
        return objInstance;
    }

    public void setUser(UserVO user) {
        this.user = user;
    }

    public UserModel() {
        user = new UserVO();
        dataAgent = RetrofitDataAgent.getInstance();
    }



   /* private UserVO setUpInitial() {
        Context context = PADC_WEEK6_APP.getContext();
        try {
            String dummyUser = JsonUtils.getInstance().loadDummyData(DUMMY_USER_DENIED);

            user = CommonInstances.getGsonInstance().fromJson(dummyUser, UserVO.class);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }*/

    public void notifyErrorInUserLogin(String message) {
        //coment
    }

    public void notifyUserLoginLoaded(UserVO user) {
        this.user = user;
        if (user != null) {
            // persistence layer
            UserVO.saveUser(user);
        }
        broadcastUserLoadedWithEventBus();
    }

    private void broadcastUserLoadedWithEventBus() {
        EventBus.getDefault().post(new DataEvent.UserDataLoadedEvent("extra-in-broadcast", user));
    }

    public static void Register(String email, String password, String dob, String region, String name) {
        RetrofitDataAgent.getInstance().register(email, password, dob, region, name);
    }

    public static void login(String email, String password) {
        RetrofitDataAgent.getInstance().login(email, password);
    }
}
