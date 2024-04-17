package service;

import util.DataLoader;
import util.MockDataLoader;

public class DataService {
    private static DataLoader dataLoader;
    private DataService(DataLoader dataLoader){
        this.dataLoader = dataLoader;
    }

    public static DataLoader getInstance(){
        if(dataLoader == null){
            dataLoader = new MockDataLoader();
        }
        return dataLoader;
    }
}
