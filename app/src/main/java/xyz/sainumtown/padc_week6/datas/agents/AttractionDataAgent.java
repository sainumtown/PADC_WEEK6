package xyz.sainumtown.padc_week6.datas.agents;

/**
 * Created by aung on 7/9/16.
 */
public interface AttractionDataAgent {
    void loadAttractions();
    void login(String email,String passsword);
    void register(String email, String password, String dob, String region, String name);
}

