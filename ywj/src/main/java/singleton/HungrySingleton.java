package singleton;

/**
 * Created by yuan on 2018/1/22.
 */
public class HungrySingleton {
    private static HungrySingleton instance = new HungrySingleton();
    private HungrySingleton (){}
    public static HungrySingleton getInstance() {
        return instance;
    }
}
