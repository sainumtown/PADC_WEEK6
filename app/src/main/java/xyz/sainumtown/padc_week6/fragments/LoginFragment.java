package xyz.sainumtown.padc_week6.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import xyz.sainumtown.padc_week6.R;
import xyz.sainumtown.padc_week6.datas.models.UserModel;

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
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        btn_login = (Button) view.findViewById(R.id.btn_login2);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserModel.getInstance().getUser();
                Toast.makeText(getContext(), UserModel.getInstance().getUser().getName(), Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }
}
