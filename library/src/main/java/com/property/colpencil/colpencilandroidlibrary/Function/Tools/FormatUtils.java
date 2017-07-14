package com.property.colpencil.colpencilandroidlibrary.Function.Tools;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class FormatUtils {

    public static String formatDouble(float price) {
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        return decimalFormat.format(price);//format 返回的是字符串
    }

}
