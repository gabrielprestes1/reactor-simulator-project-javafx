package model.service;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WriterJson {

    private final Map<String, List<String>> reactorDataMap = new HashMap<>();

    public String WriterDataToJson() {
        Gson gson = new Gson();
        return gson.toJson(reactorDataMap);
    }

    public void saveData(String reactorType, List<String> data){
        reactorDataMap.put(reactorType, data);
    }

    public void removeData(String reactorType) {
        reactorDataMap.remove(reactorType);
    }

    public Map<String, List<String>> getReactorDataMap() {
        return reactorDataMap;
    }
}
