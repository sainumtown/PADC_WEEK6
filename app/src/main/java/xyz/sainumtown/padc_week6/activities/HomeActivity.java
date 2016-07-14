package xyz.sainumtown.padc_week6.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.util.Pair;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import de.greenrobot.event.EventBus;
import xyz.sainumtown.padc_week6.R;
import xyz.sainumtown.padc_week6.adapters.AttractionAdapter;
import xyz.sainumtown.padc_week6.datas.models.AttractionModel;
import xyz.sainumtown.padc_week6.datas.vos.AttractionVO;
import xyz.sainumtown.padc_week6.events.DataEvent;
import xyz.sainumtown.padc_week6.fragments.AttractionFragment;
import xyz.sainumtown.padc_week6.fragments.LoginFragment;
import xyz.sainumtown.padc_week6.fragments.RegisterFragment;

public class HomeActivity extends AppCompatActivity implements AttractionFragment.ControllerAttractionItem, View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FrameLayout fl2;
    private AttractionAdapter mAttractionAdapter;
    @BindView(R.id.rv_attractions)
    RecyclerView rvAttractions;


    private BroadcastReceiver mDataLoadedBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //TODO instructions when the new data is ready.
            String extra = intent.getStringExtra("key-for-extra");
            Toast.makeText(getApplicationContext(), "Extra : " + extra, Toast.LENGTH_SHORT).show();

            List<AttractionVO> newAttractionList = AttractionModel.getInstance().getAttractionList();
            mAttractionAdapter.setNewData(newAttractionList);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_left_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        }

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(this);

        AttractionFragment fragment = AttractionFragment.newInstance();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_container, fragment)
                .commit();


        // btn regsiter click
        Button btnRegister = (Button) findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);


        // btn login click
        Button btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);

        // control two panels hide process
        fl2 = (FrameLayout) findViewById(R.id.fl_container_2);
        if (fl2 != null) {
            btnRegister.setText(R.string.lbl_login_register);
            btnLogin.setVisibility(View.GONE);
        }

        List<AttractionVO> attractionList = AttractionModel.getInstance().getAttractionList();
        mAttractionAdapter = new AttractionAdapter(attractionList, this);
        rvAttractions.setAdapter(mAttractionAdapter);

        int gridColumnSpanCount = 1;
        rvAttractions.setLayoutManager(new GridLayoutManager(getApplicationContext(), gridColumnSpanCount));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTapEvent(AttractionVO attraction, ImageView ivAttractionPhoto) {
        Intent intent = AttractionDetailActivity.newIntent(attraction.getTitle());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            String tsName = getResources().getString(R.string.share_element_transistion_attractioin);
            ActivityOptionsCompat activityOption = ActivityOptionsCompat.makeSceneTransitionAnimation(this, new Pair(ivAttractionPhoto, tsName));
            ActivityCompat.startActivity(this, intent, activityOption.toBundle());
        } else {
            startActivity(intent);
        }
    }

    @Override
    public boolean onNavigationItemSelected(final MenuItem menuItem) {
        menuItem.setChecked(true);
        drawerLayout.closeDrawers();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (menuItem.getItemId()) {
                    case R.id.left_menu_attractions:
                        AttractionFragment fragment = AttractionFragment.newInstance();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fl_container, fragment)
                                .commit();
                        if (fl2 != null) {
                            fl2.setVisibility(View.GONE);
                        }
                        break;

                }
            }
        }, 100); //to close drawer smoothly.

        return true;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        drawerLayout.closeDrawers();
        if (fl2 != null) {
            fl2.setVisibility(View.VISIBLE);
            LoginFragment loginFragment = LoginFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container_2, loginFragment)
                    .commit();
            RegisterFragment fragment = RegisterFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container, fragment)
                    .commit();

        } else {
            int id = v.getId();
            switch (id) {
                case R.id.btn_register:
                    RegisterFragment fragment = RegisterFragment.newInstance();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fl_container, fragment)
                            .commit();
                    break;

                case R.id.btn_login:
                    LoginFragment loginFragment = LoginFragment.newInstance();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fl_container, loginFragment)
                            .commit();
                    break;
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver(mDataLoadedBroadcastReceiver, new IntentFilter(AttractionModel.BROADCAST_DATA_LOADED));

        EventBus eventBus = EventBus.getDefault();
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mDataLoadedBroadcastReceiver);

        EventBus eventBus = EventBus.getDefault();
        eventBus.unregister(this);
    }



    public void onEventMainThread(DataEvent.AttractionDataLoadedEvent event) {
        String extra = event.getExtraMessage();
        Toast.makeText(getApplicationContext(), "Extra : "+extra, Toast.LENGTH_SHORT).show();

        //List<AttractionVO> newAttractionList = AttractionModel.getInstance().getAttractionList();
        List<AttractionVO> newAttractionList = event.getAttractionList();
        mAttractionAdapter.setNewData(newAttractionList);
    }
}
