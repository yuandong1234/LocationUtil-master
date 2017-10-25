package yuandong.locationdemo.entity;

/**
 * Created by yuandong on 2017/10/24.
 */

public class WeatherArea {
    public  String ID;
    public String cityName;//北京
    public String cityEN;//Beijing
    public String townID;//CHBJ000200
    public String townName;//朝阳
    public String townEN;//Chaoyang

    @Override
    public String toString() {
        return "WeatherArea{" +
                "ID='" + ID + '\'' +
                ", cityName='" + cityName + '\'' +
                ", cityEN='" + cityEN + '\'' +
                ", townID='" + townID + '\'' +
                ", townName='" + townName + '\'' +
                ", townEN='" + townEN + '\'' +
                '}';
    }
}
