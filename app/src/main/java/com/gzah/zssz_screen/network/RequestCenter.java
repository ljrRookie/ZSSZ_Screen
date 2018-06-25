package com.gzah.zssz_screen.network;

import com.gzah.zssz_screen.Common;
import com.gzah.zssz_screen.bean.BannerBean;
import com.gzah.zssz_screen.bean.GetCodeBean;
import com.gzah.zssz_screen.bean.MachineGoodBean;
import com.ljr.delegate_sdk.okhttp.CommonOkHttpClient;
import com.ljr.delegate_sdk.okhttp.listener.DisposeDataHandle;
import com.ljr.delegate_sdk.okhttp.listener.DisposeDataListener;
import com.ljr.delegate_sdk.okhttp.request.CommonRequest;
import com.ljr.delegate_sdk.okhttp.request.RequestParams;

/**
 * Created by 林佳荣 on 2018/5/31.
 * Github：https://github.com/ljrRookie
 * Function ：
 */

public class RequestCenter {

    /**
     * 根据参数发送所有post请求
     */

    public static void getRequest(String url, RequestParams params,
                                  DisposeDataListener listener, Class<?> clazz) {
        CommonOkHttpClient.get(CommonRequest.createGetRequest(url, params), new DisposeDataHandle(listener, clazz));
    }

    public static void postRequest(String url, RequestParams params,
                                   DisposeDataListener listener, Class<?> clazz) {

        CommonOkHttpClient.post(CommonRequest.createPostRequest(url, params), new DisposeDataHandle(listener, clazz));

    }

    public static void BigScreenAdvertisement( DisposeDataListener listener){
        RequestCenter.postRequest(Common.Banner, null, listener, BannerBean.class);
    }

    public static void GetMachineCommodityList(RequestParams requestParams,  DisposeDataListener listener) {
        RequestCenter.postRequest(Common.GetMachineCommodityList, requestParams, listener, MachineGoodBean.class);
    }
    public static void GetMachineInfo1(RequestParams requestParams, DisposeDataListener listener) {
        RequestCenter.postRequest(Common.GetMachineInfo1, requestParams, listener, GetCodeBean.class);

    }
}
