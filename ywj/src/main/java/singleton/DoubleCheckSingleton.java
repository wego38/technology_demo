package singleton;

/**
 * Created by yuan on 2018/1/22.
 */
public class DoubleCheckSingleton {

    private String a="1";

    private volatile static DoubleCheckSingleton singleton;
    private DoubleCheckSingleton (){
        System.out.println("DoubleCheckSingleton build");
        a="2";
    }
    public static DoubleCheckSingleton getSingleton() {
        if (singleton == null) {
            synchronized (DoubleCheckSingleton.class) {
                if (singleton == null) {
                    singleton = new DoubleCheckSingleton();
                }
            }
        }
        return singleton;
    }
}
