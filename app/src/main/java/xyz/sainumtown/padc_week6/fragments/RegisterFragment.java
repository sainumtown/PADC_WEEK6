package xyz.sainumtown.padc_week6.fragments;

import android.content.Context;
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
import de.greenrobot.event.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.sainumtown.padc_week6.R;
import xyz.sainumtown.padc_week6.activities.HomeActivity;
import xyz.sainumtown.padc_week6.datas.agents.retrofit.RetrofitDataAgent;
import xyz.sainumtown.padc_week6.datas.models.UserModel;
import xyz.sainumtown.padc_week6.datas.responses.UserResponse;
import xyz.sainumtown.padc_week6.datas.vos.UserVO;
import xyz.sainumtown.padc_week6.events.DataEvent;

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

    private UserController mUserController;

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
                mUserController.onTapRegister(
                        etMail.getText().toString()
                        , etPassword.getText().toString()
                        , etDob.getText().toString()
                        , etRegion.getText().toString()
                        , etName.getText().toString());
            }
        });
        return view;
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mUserController = (UserController) context;

    }

    public void onEventMainThread(DataEvent.UserDataLoadedEvent event) {
        String extra = event.getExtraMessage();
        /*Toast.makeText(getContext(), "Extra bus: " + extra, Toast.LENGTH_SHORT).show();*/

        if (UserModel.getInstance().getUser() != null) {
            Intent intent = HomeActivity.newIntent(UserModel.getInstance().getUser().getEmail());
            startActivity(intent);
        } else {
            Toast.makeText(getContext(), "Your mail is already registered", Toast.LENGTH_SHORT).show();
        }
    }

    public interface UserController {
        void onTapRegister(String email, String password, String dob, String region, String name);
    }
}
