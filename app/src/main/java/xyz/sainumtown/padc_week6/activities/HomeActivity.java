package xyz.sainumtown.padc_week6.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.util.Pair;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import xyz.sainumtown.padc_week6.PADC_WEEK6_APP;
import xyz.sainumtown.padc_week6.R;
import xyz.sainumtown.padc_week6.adapters.AttractionAdapter;
import xyz.sainumtown.padc_week6.datas.models.AttractionModel;
import xyz.sainumtown.padc_week6.datas.persistence.AttractionsContract;
import xyz.sainumtown.padc_week6.datas.vos.AttractionVO;
import xyz.sainumtown.padc_week6.events.DataEvent;
import xyz.sainumtown.padc_week6.fragments.LoginFragment;
import xyz.sainumtown.padc_week6.fragments.RegisterFragment;
import xyz.sainumtown.padc_week6.utils.MyanmarAttractionsConstants;
import xyz.sainumtown.padc_week6.views.holders.AttractionViewHolder;

public class HomeActivity extends AppCompatActivity implements AttractionViewHolder.ControllerAttractionItem, View.OnClickListener, NavigationView.OnNavigationItemSelectedListener, LoaderManager.LoaderCallbacks<Cursor> {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FrameLayout fl;
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
        ButterKnife.bind(this, this);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        }

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(this);


        // btn regsiter click
        Button btnRegister = (Button) findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);


        // btn login click
        Button btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);

        // control two panels hide process
        fl = (FrameLayout) findViewById(R.id.fl_container);
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

        getSupportLoaderManager().initLoader(MyanmarAttractionsConstants.ATTRACTION_LIST_LOADER, null, this);
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

   /* @Override
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
*/
    @Override
    public boolean onNavigationItemSelected(final MenuItem menuItem) {
        menuItem.setChecked(true);
        drawerLayout.closeDrawers();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (menuItem.getItemId()) {
                    case R.id.left_menu_attractions:
                        rvAttractions.setVisibility(View.VISIBLE);

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
        rvAttractions.setVisibility(View.GONE);
        drawerLayout.closeDrawers();
        fl.setVisibility(View.VISIBLE);
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
        Toast.makeText(getApplicationContext(), "Extra bus: " + extra, Toast.LENGTH_SHORT).show();

        //List<AttractionVO> newAttractionList = AttractionModel.getInstance().getAttractionList();
        List<AttractionVO> newAttractionList = event.getAttractionList();
        mAttractionAdapter.setNewData(newAttractionList);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,
                AttractionsContract.AttractionEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        List<AttractionVO> attractionList = new ArrayList<>();
        if (data != null && data.moveToFirst()) {
            do {
                AttractionVO attraction = AttractionVO.parseFromCursor(data);
                attraction.setImages(AttractionVO.loadAttractionImagesByTitle(attraction.getTitle()));
                attractionList.add(attraction);
            } while (data.moveToNext());
        }

        Log.d(PADC_WEEK6_APP.TAG, "Retrieved attractions : " + attractionList.size());
        mAttractionAdapter.setNewData(attractionList);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onTapAttraction(AttractionVO attraction, ImageView ivAttraction) {
        Intent intent = AttractionDetailActivity.newIntent(attraction.getTitle());
        ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                new Pair(ivAttraction, getString(R.string.share_element_transistion_attractioin)));
        ActivityCompat.startActivity(this, intent, activityOptions.toBundle());
    }
}
