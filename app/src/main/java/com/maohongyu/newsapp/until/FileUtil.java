package com.maohongyu.newsapp.until;

import android.app.Activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by hongyu on 2017/2/3 上午1:12.
 * 作用：
 */

public class FileUtil {



    public interface Callback{

        void getStringFinish(String result);
    }

    public static void getStringFromFile(final Activity activity, final String fileName, final Callback callback){
        new Thread(new Runnable() {
            @Override
            public void run() {
                StringBuffer sb = new StringBuffer();
                try {
                    InputStream is = activity.getAssets().open(fileName);
                    if (is != null) {
                        String line = null;
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
                        while ((line = bufferedReader.readLine()) != null) {
                            sb.append(line);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                callback.getStringFinish(sb.toString());
            }
        }).start();
    }

    public static String getStringFromFile(final Activity activity, final String fileName){
        StringBuffer sb = new StringBuffer();
        try {
            InputStream is = activity.getAssets().open(fileName);
            if (is != null) {
                String line = null;
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
