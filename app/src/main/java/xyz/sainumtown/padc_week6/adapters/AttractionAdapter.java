package xyz.sainumtown.padc_week6.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import xyz.sainumtown.padc_week6.PADC_WEEK6_APP;
import xyz.sainumtown.padc_week6.R;
import xyz.sainumtown.padc_week6.datas.vos.AttractionVO;
import xyz.sainumtown.padc_week6.views.holders.AttractionViewHolder;

/**
 * Created by User on 7/8/2016.
 */
public class AttractionAdapter extends RecyclerView.Adapter<AttractionViewHolder> {

    private LayoutInflater mInflater;
    private List<AttractionVO> mAttractionList;
    private AttractionViewHolder.ControllerAttractionItem mControllerAttractionItem;

    public AttractionAdapter(List<AttractionVO> attractionList, AttractionViewHolder.ControllerAttractionItem controllerAttractionItem) {
        mInflater = LayoutInflater.from(PADC_WEEK6_APP.getContext());
        mAttractionList = attractionList;
        mControllerAttractionItem = controllerAttractionItem;
    }

    @Override
    public AttractionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.view_item_attractions, parent, false);
        return new AttractionViewHolder(itemView, mControllerAttractionItem);
    }

    @Override
    public void onBindViewHolder(AttractionViewHolder holder, int position) {
        holder.bindData(mAttractionList.get(position));
    }

    @Override
    public int getItemCount() {
        return mAttractionList.size();
    }

    public void setNewData(List<AttractionVO> newAttractionList) {
        mAttractionList = newAttractionList;
        notifyDataSetChanged();
    }
}

