package xyz.sainumtown.padc_week6.events;

import java.util.List;

import xyz.sainumtown.padc_week6.datas.vos.AttractionVO;
import xyz.sainumtown.padc_week6.datas.vos.UserVO;


/**
 * Created by aung on 7/9/16.
 */
public class DataEvent {

    public static class AttractionDataLoadedEvent {
        private String extraMessage;
        private List<AttractionVO> attractionList;

        public AttractionDataLoadedEvent(String extraMessage, List<AttractionVO> attractionList) {
            this.extraMessage = extraMessage;
            this.attractionList = attractionList;
        }

        public String getExtraMessage() {
            return extraMessage;
        }

        public List<AttractionVO> getAttractionList() {
            return attractionList;
        }
    }


    public static class UserDataLoadedEvent {
        private String extraMessage;
        private UserVO user;

        public UserDataLoadedEvent(String extraMessage, UserVO user) {
            this.extraMessage = extraMessage;
            this.user = user;
        }

        public String getExtraMessage() {
            return extraMessage;
        }

        public UserVO getUser() {
            return user;
        }
    }
}
