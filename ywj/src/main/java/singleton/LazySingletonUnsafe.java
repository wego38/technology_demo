package singleton;

/**
 * Created by yuan on 2018/1/22.
 */
public class LazySingletonUnsafe {

    private static LazySingletonUnsafe instance;
    private LazySingletonUnsafe (){}

    public static LazySingletonUnsafe getInstance() {
        if (instance == null) {
            instance = new LazySingletonUnsafe();
        }
        return instance;
    }
}
