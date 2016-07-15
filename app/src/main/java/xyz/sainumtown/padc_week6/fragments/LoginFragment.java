package xyz.sainumtown.padc_week6.fragments;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import xyz.sainumtown.padc_week6.R;
import xyz.sainumtown.padc_week6.datas.agents.retrofit.RetrofitDataAgent;
import xyz.sainumtown.padc_week6.datas.models.UserModel;
import xyz.sainumtown.padc_week6.datas.vos.UserVO;

/**
 * A placeholder fragment containing a simple view.
 */

public class LoginFragment extends Fragment {

    Button btn_login;

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

        btn_login = (Button) view.findViewById(R.id.btn_login2);

        final Handler hand = new Handler();
        final Runnable run = new Runnable() {
            @Override
            public void run() {
            }

        };
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserVO user = new UserVO();
                RetrofitDataAgent.getInstance().userLogin("user@gmail.com", "12345");
                hand.postDelayed(run, 3000);
                RetrofitDataAgent.getInstance().userLogin("user@gmail.com", "12345");
                if (TextUtils.isEmpty(UserModel.getInstance().getUser().toString() )) {
                    Toast.makeText(getContext(), UserModel.getInstance().getUser().getName(), Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getContext(), "Connection is not good or no connection", Toast.LENGTH_LONG).show();
                }

            }
        });



        return view;
    }
    void  CheckLogin(){

    }
}
