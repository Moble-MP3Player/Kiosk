package model;

/**
 * 예시 클래스입니다.
 * model Package
 * 모델 패키지는 데이터만으로 구성된 클래스만 담습니다.
 * 데이터를 조작하는 함수는 Controller로 이동하여 생성하여 관리해주세요.
 * ex) Data, DataController
 *
 * */
public class Data {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
