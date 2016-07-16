package xyz.sainumtown.padc_week6.fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.sainumtown.padc_week6.R;
import xyz.sainumtown.padc_week6.activities.HomeActivity;
import xyz.sainumtown.padc_week6.datas.agents.retrofit.RetrofitDataAgent;
import xyz.sainumtown.padc_week6.datas.responses.UserResponse;
import xyz.sainumtown.padc_week6.datas.vos.UserVO;

/**
 * A placeholder fragment containing a simple view.
 */
public class RegisterFragment extends Fragment {

    @BindView(R.id.et_user_mail_register)
    EditText etMail;

    @BindView(R.id.et_password_register)
    EditText etPassword;

    @BindView(R.id.et_dob)
    EditText etDob;

    @BindView(R.id.et_region)
    EditText etRegion;

    @BindView(R.id.et_name)
    EditText etName;

    @BindView(R.id.btn_register_register)
    Button btnRegister;

    public static RegisterFragment newInstance() {

        Bundle args = new Bundle();

        RegisterFragment fragment = new RegisterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public RegisterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, view);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<UserResponse> userCall = RetrofitDataAgent.getInstance().getTheApi().userRegister(
                        etMail.getText().toString(),
                        etPassword.getText().toString(),
                        etDob.getText().toString(),
                        etRegion.getText().toString(),
                        etName.getText().toString());
                userCall.enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        UserResponse userResponse = response.body();
                        if (userResponse == null) {
                            Toast.makeText(getContext(), "There is no user with that email", Toast.LENGTH_SHORT).show();
                        } else {
                            UserVO user = userResponse.getUser();

                            if (String.valueOf(userResponse.getCode()).equals("401")) {
                                Toast.makeText(getContext(),userResponse.getMessage(),Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), userResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                // keep the data in persistent layer;
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
