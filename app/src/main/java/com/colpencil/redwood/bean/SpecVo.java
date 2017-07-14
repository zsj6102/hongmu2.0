package com.colpencil.redwood.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 商品的规格(尺寸,颜色)
 * @author 曾 凤
 * @Email  20000263@qq.com
 * @date 2016/8/5
 */
public class SpecVo implements Serializable {
    /**
     * 规格id
     */
    private int spec_id;
    /**
     * 规格名字
     */
    private String spec_name;
    /**
     * 具体的规格信息
     */
    private List<Standard> valueList;
}
