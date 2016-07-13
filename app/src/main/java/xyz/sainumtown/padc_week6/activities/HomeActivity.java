package xyz.sainumtown.padc_week6.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import xyz.sainumtown.padc_week6.R;
import xyz.sainumtown.padc_week6.datas.vos.AttractionVO;
import xyz.sainumtown.padc_week6.fragments.AttractionFragment;

public class HomeActivity extends AppCompatActivity implements AttractionFragment.ControllerAttractionItem {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AttractionFragment fragment = AttractionFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_container, fragment)
                .commit();


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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
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
}
