package xyz.sainumtown.padc_week6.datas.models;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import java.util.ArrayList;
import java.util.List;


import de.greenrobot.event.EventBus;
import xyz.sainumtown.padc_week6.PADC_WEEK6_APP;
import xyz.sainumtown.padc_week6.datas.agents.AttractionDataAgent;
import xyz.sainumtown.padc_week6.datas.agents.retrofit.RetrofitDataAgent;
import xyz.sainumtown.padc_week6.datas.vos.AttractionVO;
import xyz.sainumtown.padc_week6.events.DataEvent;

/**
 * Created by aung on 7/6/16.
 */
public class AttractionModel {

    public static final String BROADCAST_DATA_LOADED = "BROADCAST_DATA_LOADED";

    private static final int INIT_DATA_AGENT_OFFLINE = 1;
    private static final int INIT_DATA_AGENT_HTTP_URL_CONNECTION = 2;
    private static final int INIT_DATA_AGENT_OK_HTTP = 3;
    private static final int INIT_DATA_AGENT_RETROFIT = 4;

    private static AttractionModel objInstance;

    private List<AttractionVO> mAttractionList;

    private AttractionDataAgent dataAgent;

    private AttractionModel() {
        mAttractionList = new ArrayList<>();
        initDataAgent(INIT_DATA_AGENT_RETROFIT);
        dataAgent.loadAttractions();
    }

    public static AttractionModel getInstance() {
        if (objInstance == null) {
            objInstance = new AttractionModel();
        }
        return objInstance;
    }

    private void initDataAgent(int initType) {
        switch (initType) {
            case INIT_DATA_AGENT_OFFLINE:
              /*  dataAgent = OfflineDataAgent.getInstance();*/
                break;
            case INIT_DATA_AGENT_HTTP_URL_CONNECTION:
              /*  dataAgent = HttpUrlConnectionDataAgent.getInstance();*/
                break;
            case INIT_DATA_AGENT_OK_HTTP:
              /*  dataAgent = OkHttpDataAgent.getInstance();*/
                break;
            case INIT_DATA_AGENT_RETROFIT:
                dataAgent = RetrofitDataAgent.getInstance();
                break;
        }
    }

    public List<AttractionVO> getAttractionList() {
        return mAttractionList;
    }

    public AttractionVO getAttractionByName(String attractionName) {
        for (AttractionVO attraction : mAttractionList) {
            if (attraction.getTitle().equals(attractionName))
                return attraction;
        }

        return null;
    }

    public void notifyAttractionsLoaded(List<AttractionVO> attractionList) {
        //Notify that the data is ready - using LocalBroadcast
        mAttractionList = attractionList;

        // keep the data in persistent layer;
        AttractionVO.saveAttractions(mAttractionList);

        broadcastAttractionLoadedWithEventBus();
        //broadcastAttractionLoadedWithLocalBroadcastManager();
    }

    public void notifyErrorInLoadingAttractions(String message) {

    }

    private void broadcastAttractionLoadedWithLocalBroadcastManager() {
        Intent intent = new Intent(BROADCAST_DATA_LOADED);
        intent.putExtra("key-for-extra", "extra-in-broadcast");
        LocalBroadcastManager.getInstance(PADC_WEEK6_APP.getContext()).sendBroadcast(intent);
    }

    private void broadcastAttractionLoadedWithEventBus() {
        EventBus.getDefault().post(new DataEvent.AttractionDataLoadedEvent("extra-in-broadcast", mAttractionList));
    }
}
