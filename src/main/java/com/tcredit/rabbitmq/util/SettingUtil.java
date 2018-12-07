package com.tcredit.rabbitmq.util;

import java.io.FileInputStream;
import java.net.URLDecoder;
import java.util.Properties;

/**
 * 读取settings配置文件信息
 */
public class SettingUtil {
    private static Properties pro;
    static {
        pro = new Properties();
        try {
            FileInputStream in = new FileInputStream(URLDecoder.decode(
                    SettingUtil.class.getClassLoader()
                            .getResource("settings.properties").getFile(),
                    "UTF-8"));
            pro.load(in);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getValue(String key) {
        return pro.getProperty(key);
    }

    public static String getValue(String key, String defaultValue) {
        return pro.getProperty(key, defaultValue);
    }
    
}
