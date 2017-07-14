package com.property.colpencil.colpencilandroidlibrary.Function.Tools;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class OkhttpUtils {
    public static  RequestBody toRequestBody(String value){
        RequestBody requestBody  = RequestBody.create(MediaType.parse("text/plain"),value);
        return requestBody;
    }

}
