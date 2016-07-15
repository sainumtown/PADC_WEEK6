package xyz.sainumtown.padc_week6.datas.vos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import xyz.sainumtown.padc_week6.PADC_WEEK6_APP;
import xyz.sainumtown.padc_week6.datas.agents.retrofit.RetrofitDataAgent;
import xyz.sainumtown.padc_week6.datas.models.AttractionModel;
import xyz.sainumtown.padc_week6.datas.models.UserModel;
import xyz.sainumtown.padc_week6.datas.persistence.AttractionsContract;
import xyz.sainumtown.padc_week6.datas.responses.UserResponse;

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

    public static void saveUser(UserVO user) {
        Context context = PADC_WEEK6_APP.getContext();
        ContentValues userCV = new ContentValues();

        userCV.put(AttractionsContract.UserEntry.COLUMN_NAME, user.name);
        userCV.put(AttractionsContract.UserEntry.COLUMN_EMAIL, user.email);
        userCV.put(AttractionsContract.UserEntry.COLUMN_DOB, user.dateOfBirth);
        userCV.put(AttractionsContract.UserEntry.COLUMN_COUNTRY_ORIGNIN, user.countryOfOrigin);
        userCV.put(AttractionsContract.UserEntry.COLUMN_ACCESS_TOKEN, user.accessToken);

        Uri uriUser = context.getContentResolver().insert(AttractionsContract.UserEntry.CONTENT_URI, userCV);

        Log.d(PADC_WEEK6_APP.TAG, "Inserted into user table ( uri is ) : " + uriUser);
    }

    public static void getUserByEmail(String email) {
        Context context = PADC_WEEK6_APP.getContext();
        ContentValues userCV = new ContentValues();

        userCV.put(AttractionsContract.UserEntry.COLUMN_EMAIL, email);
        UserVO user = new UserVO();

        String selection = AttractionsContract.UserEntry.COLUMN_EMAIL + " = ?";
        String[] selectionArgs = new String[]{email};

        Cursor cursor = context.getContentResolver().query(AttractionsContract.UserEntry.CONTENT_URI, null,
                selection,
                selectionArgs,
                null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                user.name = cursor.getString(cursor.getColumnIndex(AttractionsContract.UserEntry.COLUMN_NAME));
                user.email = cursor.getString(cursor.getColumnIndex(AttractionsContract.UserEntry.COLUMN_EMAIL));
                user.countryOfOrigin = cursor.getString(cursor.getColumnIndex(AttractionsContract.UserEntry.COLUMN_COUNTRY_ORIGNIN));
                user.dateOfBirth = cursor.getString(cursor.getColumnIndex(AttractionsContract.UserEntry.COLUMN_DOB));
            } while (cursor.moveToNext());
        }

        UserModel.getInstance().setUser(user);
        Log.d(PADC_WEEK6_APP.TAG, "Selected  user is : " + user.getEmail());
    }

    public static void removeUser(String email) {
        Context context = PADC_WEEK6_APP.getContext();
        String selection = AttractionsContract.UserEntry.COLUMN_EMAIL + " = ?";
        String[] selectionArgs = new String[]{email};
        int returnId = context.getContentResolver().delete(AttractionsContract.UserEntry.CONTENT_URI, selection, selectionArgs);
        UserModel.getInstance().setUser(null);
        Log.d(PADC_WEEK6_APP.TAG, "Deleted  row(s) : " + returnId);
    }
}
