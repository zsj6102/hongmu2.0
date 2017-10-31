package com.colpencil.redwood.model.imples;

import com.colpencil.redwood.bean.ImageSpan;
import com.colpencil.redwood.bean.ResultInfo;
import com.property.colpencil.colpencilandroidlibrary.ControlerBase.MVP.ColpencilModel;

import java.util.HashMap;


import okhttp3.RequestBody;
import rx.Observer;

public interface IGoodNoteModel extends ColpencilModel{
    void loadGoodUrl(HashMap<String, RequestBody> map);

    void subUrl(Observer<ResultInfo<ImageSpan>> observer);
}
