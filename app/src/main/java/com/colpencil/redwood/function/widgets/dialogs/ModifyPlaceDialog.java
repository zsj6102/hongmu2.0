package com.colpencil.redwood.function.widgets.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.colpencil.redwood.R;
import com.colpencil.redwood.bean.AddresBean;
import com.colpencil.redwood.bean.CountryBean;
import com.colpencil.redwood.bean.result.CityBean;
import com.colpencil.redwood.function.widgets.wheel.Adapters.ArrayWheelAdapter;
import com.colpencil.redwood.function.widgets.wheel.OnWheelChangedListener;
import com.colpencil.redwood.function.widgets.wheel.WheelView;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.attr.id;


/**
 * 日期选择器
 */

public class ModifyPlaceDialog extends Dialog implements OnWheelChangedListener {

    private Context context;
    private TextView okBtn, cancelBtn;//按钮
    private View view;
    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;

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

    protected Map<String, Integer[]> mDistId = new HashMap<>();


    private btnClick listener;

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
    private TextView select_splace_title;
    AddresBean bean;

    public ModifyPlaceDialog(Context context, AddresBean addresBean) {
        super(context, R.style.selectorDialog);
        this.context = context;

        this.bean = addresBean;

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
                String s = mCurrentProviceName + "," + mCurrentCityName + "," + mCurrentDistrictName;
                listener.sureClick(s, mCurrentDisId);
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                listener.cancelClick();
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

    public void setListener(btnClick listener) {
        this.listener = listener;
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
                mDistId.put(cityNames[j], disId);
            }
            mCitisDatasMap.put(bean.getData().get(i).getProvince_name(), cityNames);
        }
    }

    public interface btnClick {
        void sureClick(String s, int id);

        void cancelClick();
    }
}
