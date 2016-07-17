package xyz.sainumtown.padc_week6.fragments;

import android.content.Context;
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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.sainumtown.padc_week6.R;
import xyz.sainumtown.padc_week6.activities.HomeActivity;
import xyz.sainumtown.padc_week6.datas.agents.retrofit.RetrofitDataAgent;
import xyz.sainumtown.padc_week6.datas.models.UserModel;
import xyz.sainumtown.padc_week6.datas.responses.UserResponse;
import xyz.sainumtown.padc_week6.datas.vos.AttractionVO;
import xyz.sainumtown.padc_week6.datas.vos.UserVO;
import xyz.sainumtown.padc_week6.events.DataEvent;

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

    UserController mUserController;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mUserController = (UserController) context;
    }

    public static LoginFragment newInstance() {

        Bundle args = new Bundle();

        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public LoginFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus eventBus = EventBus.getDefault();
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus eventBus = EventBus.getDefault();
        eventBus.unregister(this);
    }

    public void onEventMainThread(DataEvent.UserDataLoadedEvent event) {
        String extra = event.getExtraMessage();
        Toast.makeText(getContext(), "Extra bus: " + extra, Toast.LENGTH_SHORT).show();

        if (UserModel.getInstance().getUser() != null) {
            Intent intent = HomeActivity.newIntent(UserModel.getInstance().getUser().getEmail());
            startActivity(intent);
        } else {
            Toast.makeText(getContext(), "Your mail is not registered", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_login, container, false);

        ButterKnife.bind(this, view);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserController.onTapLogin(etMail.getText().toString(), etPassword.getText().toString());

            }
        });

        return view;
    }

    public interface UserController {
        void onTapLogin(String email, String password);
    }
}
