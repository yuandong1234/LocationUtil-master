package yuandong.locationdemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import java.util.List;

import yuandong.locationdemo.entity.WeatherArea;
import yuandong.locationdemo.util.FileUtils;
import yuandong.locationdemo.util.LocationUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = MainActivity.class.getSimpleName();
    private static int PERMISSION_ACCESS_LOCATION = 1;
    private Button bt_location, bt_gps;
    private Handler handler;
    private String locality="北京市";
    private String subLocality="怀柔区";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }


    private void initView() {
        bt_location = (Button) findViewById(R.id.bt_location);
        bt_gps = (Button) findViewById(R.id.bt_gps);
        bt_gps.setOnClickListener(this);
        bt_location.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_location:
                getLocation();
                break;
            case R.id.bt_gps:
                readFileFromAssets();
                break;
        }

    }


    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(LocationApplication.mContext,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(LocationApplication.mContext,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //申请权限
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ACCESS_LOCATION);
        } else {
            if (LocationUtils.getInstance().isOpenGPS()) {
                Log.e(TAG, "已打开GPS");
                LocationUtils.getInstance().getLocation();
            } else {
                Log.e(TAG, "未打开GPS");
                LocationUtils.getInstance().openGPS(this);
            }
        }
    }

    private void readFileFromAssets() {
        String townId="";
        Log.e(TAG," start read time :"+System.currentTimeMillis());
        String json = FileUtils.readFromAssets(this, "CenterWeatherCityCode.json");
        //Logger.json(json);
        Gson gson = new Gson();
        List<WeatherArea> areas = gson.fromJson(json, new TypeToken<List<WeatherArea>>() {}.getType());
        for(WeatherArea temp: areas ){
            if(temp.cityName.contains(locality)||locality.contains(temp.cityName)){
                if(TextUtils.isEmpty(townId)){
                    townId=temp.townID;
                }
                if(temp.townName.contains(subLocality)||subLocality.contains(temp.townName)){
                    townId=temp.townID;
                    break;
                }
            }
        }
        Log.e(TAG,townId);
        Log.e(TAG," start end time :"+System.currentTimeMillis());
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_ACCESS_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (LocationUtils.getInstance().isOpenGPS()) {
                    Log.e(TAG, "已打开GPS");
                    LocationUtils.getInstance().getLocation();
                } else {
                    Log.e(TAG, "未打开GPS");
                    LocationUtils.getInstance().openGPS(this);
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //在这里可以在此检查GSP是否开启
        if (requestCode == LocationUtils.GPS_LOCATION_REQUEST_CODE) {
            if (handler == null) {
                handler = new Handler();
            }
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    LocationUtils.getInstance().getLocation();
                }
            }, 2000);
        }

    }
}
