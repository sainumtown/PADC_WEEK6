package xyz.sainumtown.padc_week6.fragments;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.sainumtown.padc_week6.R;
import xyz.sainumtown.padc_week6.activities.HomeActivity;
import xyz.sainumtown.padc_week6.datas.agents.retrofit.RetrofitDataAgent;
import xyz.sainumtown.padc_week6.datas.models.UserModel;
import xyz.sainumtown.padc_week6.datas.responses.UserResponse;
import xyz.sainumtown.padc_week6.datas.vos.UserVO;

/**
 * A placeholder fragment containing a simple view.
 */

public class LoginFragment extends Fragment {

    @BindView(R.id.btn_login2)
    Button btn_login;

    @BindView(R.id.et_user_mail)
    EditText etMail;

    @BindView(R.id.et_password)
    EditText etPassword;

    public static LoginFragment newInstance() {

        Bundle args = new Bundle();

        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_login, container, false);

        ButterKnife.bind(this, view);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Call<UserResponse> userCall = RetrofitDataAgent.getInstance().getTheApi().userLogin(etMail.getText().toString(), etPassword.getText().toString());
                userCall.enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        UserResponse userResponse = response.body();
                        if (userResponse == null) {
                            Toast.makeText(getContext(), "There is no user with that email", Toast.LENGTH_SHORT).show();
                        } else {
                            UserVO user = userResponse.getUser();
                            if (String.valueOf(userResponse.getCode()).equals("401")) {
                                Toast.makeText(getContext(), userResponse.getMessage(), Toast.LENGTH_SHORT).show();

                            } else {
                                // keep the data in persistent layer;
                                Toast.makeText(getContext(), userResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                UserVO.saveUser(user);
                                Intent intent = HomeActivity.newIntent(user.getEmail());
                                startActivity(intent);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        Toast.makeText(getContext(), "fail", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        return view;
    }

}
