package xyz.sainumtown.padc_week6.activities;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import xyz.sainumtown.padc_week6.PADC_WEEK5_APP;
import xyz.sainumtown.padc_week6.R;
import xyz.sainumtown.padc_week6.datas.models.AttractionModel;
import xyz.sainumtown.padc_week6.datas.vos.AttractionVO;

public class AttractionDetailActivity extends AppCompatActivity {

    private static final String IE_ATTRACTION_TITLE = "attration_title";
    private CollapsingToolbarLayout collapsingToolbar;
    private ShareActionProvider mShareActionProvider;
    private AttractionVO attraction;

    public static Intent newIntent(String title) {
        Intent intent = new Intent(PADC_WEEK5_APP.getContext(), AttractionDetailActivity.class);
        intent.putExtra(IE_ATTRACTION_TITLE, title);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attraction_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_assert_24dp);
        }

        TextView tvDesc = (TextView) findViewById(R.id.tv_attraction_detail_desc);
        ImageView ivImage = (ImageView) findViewById(R.id.iv_image);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_share);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "http://www.aungpyaephyo.xyz/myanmar_attractions/" + attraction.getImages().get(0).toString();
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Share Image");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share hhvia"));

            }
        });

        String attractionTitle = getIntent().getStringExtra(IE_ATTRACTION_TITLE);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            String tsName = getResources().getString(R.string.share_element_transistion_attractioin);
            ivImage.setTransitionName(tsName);
        }
        attraction = AttractionModel.getInstance().getAttractionByTitle(attractionTitle);
        if (attraction == null) {
            throw new RuntimeException("Can't find Attraction obj with the title : " + attractionTitle);
        } else {
            collapsingToolbar.setTitle(attraction.getTitle());
            tvDesc.setText(attraction.getDesc() + "\n\n" +
                    attraction.getDesc() + "\n\n" +
                    attraction.getDesc() + "\n\n" +
                    attraction.getDesc() + "\n\n" +
                    attraction.getDesc()
            );

            Glide.with(ivImage.getContext())
                    .load("http://www.aungpyaephyo.xyz/myanmar_attractions/" + attraction.getImages().get(0).toString())
                    .centerCrop()
                    .placeholder(R.drawable.stock_photo_placeholder)
                    .into(ivImage);
        }
    }
}
