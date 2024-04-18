package util;

/**
 * ServiceLocator는 프로젝트 내에서 의존성을 관리합니다.
 * 객체가 문어다발처럼 참조되지 않도록 사용하는 DI 컨테이너입니다.
 */
public class ServiceLocator {
    private static volatile ServiceLocator instance;


    public static ServiceLocator getInstance() {
        if (instance == null) { // First check (no locking)
            synchronized (ServiceLocator.class) {
                if (instance == null) { // Second check (with locking)
                    // 다른 쓰레드에서 if문을 통과할수있기떄문에 double Locking을 함.
                    instance = new ServiceLocator();
                }
            }
        }
        return instance;
    }
}
