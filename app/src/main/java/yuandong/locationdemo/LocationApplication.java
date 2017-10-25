package yuandong.locationdemo;

import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * Created by yuandong on 2017/10/23.
 */

public class LocationApplication extends Application {
    public static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext=this.getApplicationContext();
        initLogger();

    }

    private void initLogger(){
//        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
//                .showThreadInfo(false)  //是否选择显示线程信息，默认为true
//                .methodCount(0)         //方法数显示多少行，默认2行
//                .methodOffset(7)        //隐藏方法内部调用到偏移量，默认5
//                .logStrategy(customLog) //打印日志的策略，默认LogCat
//                .tag("My custom tag")   //自定义TAG全部标签，默认PRETTY_LOGGER
//                .build();
        Logger.addLogAdapter(new AndroidLogAdapter());
    }
}
