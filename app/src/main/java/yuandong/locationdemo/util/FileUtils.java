package yuandong.locationdemo.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by yuandong on 2017/10/24.
 */

public class FileUtils {
    /**
     * 从assets中读取文件
     * @param context
     * @param txt 文件名称
     * @return
     */
    public static String readFromAssets(Context context, String txt) {
        String text = "";
        try {
            InputStream is = context.getAssets().open(txt);
            text = readTextFromFile(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }

    /**
     * 读取文件
     * @param is
     * @return
     * @throws Exception
     */
    public static String readTextFromFile(InputStream is) throws Exception {
        InputStreamReader reader = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuffer buffer = new StringBuffer("");
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            buffer.append(str);
            buffer.append("\n");
        }
        return buffer.toString();
    }
}
