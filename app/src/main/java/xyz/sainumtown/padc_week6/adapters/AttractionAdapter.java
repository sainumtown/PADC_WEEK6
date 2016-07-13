package xyz.sainumtown.padc_week6.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import xyz.sainumtown.padc_week6.PADC_WEEK5_APP;
import xyz.sainumtown.padc_week6.R;
import xyz.sainumtown.padc_week6.datas.vos.AttractionVO;
import xyz.sainumtown.padc_week6.fragments.AttractionFragment;
import xyz.sainumtown.padc_week6.views.holders.AttractionViewHolder;

/**
 * Created by User on 7/8/2016.
 */
public class AttractionAdapter extends RecyclerView.Adapter<AttractionViewHolder> {

    private LayoutInflater inflater;
    private List<AttractionVO> attractionList;
    private AttractionFragment.ControllerAttractionItem mAttractionItemController;

    public AttractionAdapter(List<AttractionVO> attractionList, AttractionFragment.ControllerAttractionItem controllerAttractionItem) {
        inflater = LayoutInflater.from(PADC_WEEK5_APP.getContext());
        this.attractionList = attractionList;
        mAttractionItemController = controllerAttractionItem;
    }

    @Override
    public AttractionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.view_item_attractions, parent, false);
        AttractionViewHolder attractionVH = new AttractionViewHolder(view,mAttractionItemController);

        return attractionVH;
    }

    @Override
    public void onBindViewHolder(AttractionViewHolder holder, int position) {
        holder.setData(attractionList.get(position));
    }

    @Override
    public int getItemCount() {
        return attractionList.size();
    }
}
