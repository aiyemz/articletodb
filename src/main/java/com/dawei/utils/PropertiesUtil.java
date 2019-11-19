package com.dawei.utils;

import java.io.*;
import java.util.Properties;

public class PropertiesUtil {

    private PropertiesUtil() {

    }

    public static Properties loadPropertyFile(String fullFile) {
        String webRootPath = null;
        if (null == fullFile || "".equals(fullFile))
            throw new IllegalArgumentException(
                    "Properties file path can not be null : " + fullFile);
        webRootPath = PropertiesUtil.class.getClassLoader().getResource("\\").getPath();
        webRootPath = new File(webRootPath).getParent();
        InputStream inputStream = null;
        Properties p = null;
        try {
            String profilepath = webRootPath + File.separator + fullFile;
            inputStream = new FileInputStream(new File(profilepath));
            p = new Properties();
            p.load(inputStream);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Properties file not found: "
                    + fullFile);
        } catch (IOException e) {
            throw new IllegalArgumentException(
                    "Properties file can not be loading: " + fullFile);
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return p;
    }

}
