package controller;

import model.Data;

/**
 * 예시 클래스입니다.
 * model Package
 * 데이터 클래스의 정보를 조작하는 함수를 담습니다.
 *
 *
 * */
public class DataController {

    public static void changeIdTo(Data data, int newId){
        data.setId(newId);
    }

}
