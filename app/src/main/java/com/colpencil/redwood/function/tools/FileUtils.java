package com.colpencil.redwood.function.tools;

import java.io.File;

public class FileUtils {

    //删除文件夹下面的文件
    public static void deleteAllFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        if (!file.isDirectory()) {
            file.mkdir();
        }
        String[] tempList = file.list();
        File temp;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
        }
    }

}
