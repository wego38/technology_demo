package singleton;

/**
 * Created by yuan on 2018/1/22.
 */
public class StaticInnerClassSingleton {
    private static class SingletonHolder {
        private static final StaticInnerClassSingleton INSTANCE = new StaticInnerClassSingleton();
    }
    private StaticInnerClassSingleton (){}
    public static final StaticInnerClassSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
