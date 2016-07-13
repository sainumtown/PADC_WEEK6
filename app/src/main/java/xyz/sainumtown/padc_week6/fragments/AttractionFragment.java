package xyz.sainumtown.padc_week6.fragments;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import xyz.sainumtown.padc_week6.R;
import xyz.sainumtown.padc_week6.adapters.AttractionAdapter;
import xyz.sainumtown.padc_week6.datas.models.AttractionModel;
import xyz.sainumtown.padc_week6.datas.vos.AttractionVO;

/**
 * A placeholder fragment containing a simple view.
 */
public class AttractionFragment extends Fragment {

    private AttractionAdapter mAttractionAdapter;
    private ControllerAttractionItem mControllerAttractionItem;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mControllerAttractionItem = (ControllerAttractionItem) context;
    }

    public AttractionFragment() {
    }

    public static AttractionFragment newInstance() {

        Bundle args = new Bundle();

        AttractionFragment fragment = new AttractionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAttractionAdapter = new AttractionAdapter(AttractionModel.getInstance().getAttractionList(), mControllerAttractionItem);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attractions, container, false);

        RecyclerView rvAttraction = (RecyclerView) view.findViewById(R.id.rv_attractions);
        rvAttraction.setAdapter(mAttractionAdapter);
        rvAttraction.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });

        return view;
    }

    public interface ControllerAttractionItem {
        void onTapEvent(AttractionVO attraction, ImageView ivAttractionPhoto);
    }

}
