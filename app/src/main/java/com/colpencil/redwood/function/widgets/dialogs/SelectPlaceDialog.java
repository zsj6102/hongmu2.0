package com.colpencil.redwood.function.widgets.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.api.RedWoodApi;
import com.colpencil.redwood.base.App;
import com.colpencil.redwood.bean.AddresBean;
import com.colpencil.redwood.bean.CityModel;
import com.colpencil.redwood.bean.CountryBean;
import com.colpencil.redwood.bean.DistrictModel;
import com.colpencil.redwood.bean.ProvinceModel;
import com.colpencil.redwood.bean.result.CityBean;
import com.colpencil.redwood.bean.result.ResultRegion;
import com.colpencil.redwood.function.config.UrlConfig;
import com.colpencil.redwood.function.utils.XmlParserHandler;
import com.colpencil.redwood.function.widgets.wheel.Adapters.ArrayWheelAdapter;
import com.colpencil.redwood.function.widgets.wheel.OnWheelChangedListener;
import com.colpencil.redwood.function.widgets.wheel.WheelView;
import com.property.colpencil.colpencilandroidlibrary.Function.MianCore.RetrofitManager;
import com.property.colpencil.colpencilandroidlibrary.Function.Tools.SharedPreferencesUtil;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.unionpay.mobile.android.global.a.C;
import static de.greenrobot.daogenerator.PropertyType.Int;

/**
 * 日期选择器
 */

public class SelectPlaceDialog extends Dialog implements OnWheelChangedListener {

    private Context context;
    private TextView okBtn, cancelBtn;//按钮
    private View view;
    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;
    private TextView textView;
    /**
     * 所有省
     */
    protected String[] mProvinceDatas;
    /**
     * key - 省 value - 市
     */
    protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    /**
     * key - 市 values - 区
     */
    protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();

    protected Map<String,Integer[]>  mDistId = new HashMap<>();

    /**
     * key - 区 values - 邮编
     */
    protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();

    /**
     * 当前省的名称
     */
    protected String mCurrentProviceName;
    /**
     * 当前市的名称
     */
    protected String mCurrentCityName;
    /**
     * 当前区的名称
     */
    protected String mCurrentDistrictName = "";
    /**
     * 当前区的id
     */
    protected int mCurrentDisId = 0;

    /**
     * 当前区的邮政编码
     */
    protected String mCurrentZipCode = "";

    private TextView select_splace_title;

    AddresBean bean;
    int type = 0;
    public SelectPlaceDialog(Context context, TextView textView, AddresBean addresBean,int type) {
        super(context, R.style.selectorDialog);
        this.context = context;
        this.textView = textView;
        this.bean = addresBean;
        this.type = type;
        initalize();
        this.setCanceledOnTouchOutside(true);
    }

    private void initalize() {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.dialog_selectplace, null);
        setContentView(view);
        initWindow();
        select_splace_title = (TextView) view.findViewById(R.id.select_splace_title);
        mViewProvince = (WheelView) view.findViewById(R.id.id_province);
        mViewCity = (WheelView) view.findViewById(R.id.id_city);
        mViewDistrict = (WheelView) view.findViewById(R.id.id_district);
        // 添加change事件
        mViewProvince.addChangingListener(this);
        // 添加change事件
        mViewCity.addChangingListener(this);
        // 添加change事件
        mViewDistrict.addChangingListener(this);
        //        initPovinceData();
        initProvinceDatas();
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(context, mProvinceDatas));
        // 设置可见条目数量
        mViewProvince.setVisibleItems(7);
        mViewCity.setVisibleItems(7);
        mViewDistrict.setVisibleItems(7);
        updateCities();
        updateAreas();
        // 添加onclick事件
        okBtn = (TextView) view.findViewById(R.id.btn_place_ok);
        cancelBtn = (TextView) view.findViewById(R.id.btn_place_cancel);
        okBtn.setOnClickListener(new View.OnClickListener() {
            //按钮事件
            @Override
            public void onClick(View v) {
                SharedPreferencesUtil.getInstance(context).setString("province", mCurrentProviceName);
                SharedPreferencesUtil.getInstance(context).setString("city", mCurrentCityName);
                SharedPreferencesUtil.getInstance(context).setString("region", mCurrentDistrictName);
                if(type == 0){
                    SharedPreferencesUtil.getInstance(context).setInt("region_id0",mCurrentDisId);
                }
                if(type == 1){
                    SharedPreferencesUtil.getInstance(context).setInt("region_id1",mCurrentDisId);
                }
                if(type == 2){
                    SharedPreferencesUtil.getInstance(context).setInt("region_id2",mCurrentDisId);
                }
                textView.setText(mCurrentProviceName + "," + mCurrentCityName + "," + mCurrentDistrictName);
                dismiss();

            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dismiss();
            }
        });

    }

    /**
     * 添加黑色半透明背景
     */
    private void initWindow() {
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics();// 获取屏幕尺寸
        lp.width = (int) (d.widthPixels * 0.8); // 宽度为屏幕80%
        lp.gravity = Gravity.CENTER; // 中央居中
        dialogWindow.setAttributes(lp);
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        // TODO Auto-generated method stub
        if (wheel == mViewProvince) {
            updateCities();
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[0];
            mCurrentDisId = mDistId.get(mCurrentCityName)[0];

        } else if (wheel == mViewCity) {
            updateAreas();
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[0];
            mCurrentDisId = mDistId.get(mCurrentCityName)[0];
        } else if (wheel == mViewDistrict) {
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
//            mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
            mCurrentDisId = mDistId.get(mCurrentCityName)[newValue];
        }
        select_splace_title.setText(mCurrentProviceName + "," + mCurrentCityName + "," + mCurrentDistrictName);
    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateCities() {
        int pCurrent = mViewProvince.getCurrentItem();
        mCurrentProviceName = mProvinceDatas[pCurrent];
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        if (cities == null) {
            cities = new String[]{""};
        }
        mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(context, cities));
        mViewCity.setCurrentItem(0);
        updateAreas();
    }

    /**
     * 根据当前的市，更新区WheelView的信息
     */
    private void updateAreas() {
        int pCurrent = mViewCity.getCurrentItem();
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
        String[] areas = mDistrictDatasMap.get(mCurrentCityName);

        if (areas == null) {
            areas = new String[]{""};
        }
        mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(context, areas));
        mViewDistrict.setCurrentItem(0);
    }



    /**
     * 解析省市区的XML数据
     */

    protected void initProvinceDatas() {
        List<ProvinceModel> provinceList = null;
        //        AssetManager asset = context.getAssets();
        //        try {
        //            InputStream input = asset.open("province_data.xml");
        //            // 创建一个解析xml的工厂对象
        //            SAXParserFactory spf = SAXParserFactory.newInstance();
        //            // 解析xml
        //            SAXParser parser = spf.newSAXParser();
        //            XmlParserHandler handler = new XmlParserHandler();
        //            parser.parse(input, handler);
        //            input.close();
        //            // 获取解析出来的数据
        //            provinceList = handler.getDataList();
        //            //*/ 初始化默认选中的省、市、区
        //            if (provinceList != null && !provinceList.isEmpty()) {
        //                mCurrentProviceName = provinceList.get(0).getName();
        //                List<CityModel> cityList = provinceList.get(0).getCityList();
        //                if (cityList != null && !cityList.isEmpty()) {
        //                    mCurrentCityName = cityList.get(0).getName();
        //                    List<DistrictModel> districtList = cityList.get(0).getDistrictList();
        //                    mCurrentDistrictName = districtList.get(0).getName();
        //                    mCurrentZipCode = districtList.get(0).getZipcode();
        //                }
        //            }
        //            //*/
        //            mProvinceDatas = new String[provinceList.size()];
        //            for (int i = 0; i < provinceList.size(); i++) {
        //                // 遍历所有省的数据
        //                mProvinceDatas[i] = provinceList.get(i).getName();
        //                List<CityModel> cityList = provinceList.get(i).getCityList();
        //                String[] cityNames = new String[cityList.size()];
        //                for (int j = 0; j < cityList.size(); j++) {
        //                    // 遍历省下面的所有市的数据
        //                    cityNames[j] = cityList.get(j).getName();
        //                    List<DistrictModel> districtList = cityList.get(j).getDistrictList();
        //                    String[] distrinctNameArray = new String[districtList.size()];
        //                    DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];
        //                    for (int k = 0; k < districtList.size(); k++) {
        //                        // 遍历市下面所有区/县的数据
        //                        DistrictModel districtModel = new DistrictModel(districtList.get(k).getName(), districtList.get(k).getZipcode());
        //                        // 区/县对于的邮编，保存到mZipcodeDatasMap
        //                        mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
        //                        distrinctArray[k] = districtModel;
        //                        distrinctNameArray[k] = districtModel.getName();
        //                    }
        //                    // 市-区/县的数据，保存到mDistrictDatasMap
        //                    mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
        //                }
        //                // 省-市的数据，保存到mCitisDatasMap
        //                mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
        //            }
        //        } catch (Throwable e) {
        //            e.printStackTrace();
        //        } finally {
        //
        //        }
        if (bean.getData().size() > 0) {

                mCurrentProviceName = bean.getData().get(0).getProvince_name();
                List<CityBean> cityList = bean.getData().get(0).getProvince_next_city();
                if (cityList != null && !cityList.isEmpty()) {
                    mCurrentCityName = cityList.get(0).getCity_name();
                    List<CountryBean> districtList = cityList.get(0).getList_country();
                    mCurrentDistrictName = districtList.get(0).getLocal_name();
                    mCurrentDisId = districtList.get(0).getRegion_id();
                }
        }
        mProvinceDatas = new String[bean.getData().size()];
        for (int i = 0; i < bean.getData().size(); i++) {
            mProvinceDatas[i] = bean.getData().get(i).getProvince_name();
            List<CityBean> cityList = bean.getData().get(i).getProvince_next_city();
            String[] cityNames = new String[cityList.size()];
            for (int j = 0; j < cityList.size(); j++) {
                cityNames[j] = cityList.get(j).getCity_name();
                List<CountryBean> districtList = cityList.get(j).getList_country();
                String[] distrinctNameArray = new String[districtList.size()];
                Integer[] disId = new Integer[districtList.size()];
                for (int k = 0; k < districtList.size(); k++) {
                    distrinctNameArray[k] = districtList.get(k).getLocal_name();
                    disId[k] = districtList.get(k).getRegion_id();
                }
                mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
                mDistId.put(cityNames[j],disId);
            }
            mCitisDatasMap.put(bean.getData().get(i).getProvince_name(), cityNames);
        }
    }
}
