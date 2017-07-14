package com.colpencil.redwood.function.utils;

import java.util.Collection;

/**
 * @author 陈宝
 * @Description:List的工具类
 * @Email DramaScript@outlook.com
 * @date 2016/7/29
 */
public class ListUtils {

    /**
     * 判断list是否为空
     *
     * @param list
     * @return
     */
    public static boolean listIsNullOrEmpty(Collection<?> list) {
        return list == null || list.isEmpty();
    }

}
