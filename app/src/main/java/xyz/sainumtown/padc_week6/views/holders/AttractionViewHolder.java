package xyz.sainumtown.padc_week6.views.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import xyz.sainumtown.padc_week6.R;
import xyz.sainumtown.padc_week6.datas.vos.AttractionVO;
import xyz.sainumtown.padc_week6.fragments.AttractionFragment;

/**
 * Created by User on 7/8/2016.
 */
public class AttractionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView tvAttractionTitle;
    private ImageView ivAttractionPhoto;
    private TextView tvAttractionDesc;
    private AttractionFragment.ControllerAttractionItem mAttractionItemControler;
    private AttractionVO mAttraction;

    public AttractionViewHolder(View itemView, AttractionFragment.ControllerAttractionItem mAttractionItemController) {
        super(itemView);
        itemView.setOnClickListener(this);

        mAttractionItemControler = mAttractionItemController;
        tvAttractionTitle = (TextView) itemView.findViewById(R.id.tv_attraction_title);
        tvAttractionDesc = (TextView) itemView.findViewById(R.id.tv_attraction_desc);
        ivAttractionPhoto = (ImageView) itemView.findViewById(R.id.iv_image);
    }

    public void setData(AttractionVO attraction) {
        this.mAttraction = attraction;

        tvAttractionTitle.setText(attraction.getTitle());
        tvAttractionDesc.setText(attraction.getDesc());



        Glide.with(ivAttractionPhoto.getContext())
                .load("http://www.aungpyaephyo.xyz/myanmar_attractions/"+attraction.getImages().get(0).toString())
                .centerCrop()
                .placeholder(R.drawable.stock_photo_placeholder)
                .into(ivAttractionPhoto);
    }


    @Override
    public void onClick(View v) {
        mAttractionItemControler.onTapEvent(mAttraction,ivAttractionPhoto);
    }
}