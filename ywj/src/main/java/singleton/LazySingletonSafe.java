package singleton;

/**
 * Created by yuan on 2018/1/22.
 */
public class LazySingletonSafe {
    private static LazySingletonSafe instance;
    private LazySingletonSafe (){}
    public static synchronized LazySingletonSafe getInstance() {
        if (instance == null) {
            instance = new LazySingletonSafe();
        }
        return instance;
    }
}
