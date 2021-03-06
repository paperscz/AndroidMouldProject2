package com.example.testmanager.api;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.baselibrary.api.APIConstant;
import com.example.baselibrary.okgo.OkGoRequest;
import com.example.baselibrary.okgo.utils.JsonCallback;
import com.example.baselibrary.okgo.utils.JsonConvert;
import com.example.testmanager.bean.QueryAdvertBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.convert.BitmapConvert;
import com.lzy.okgo.convert.FileConvert;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx.RxAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import rx.Observable;

/**
 * Created by conan on 2017/2/18.
 */

public class APIMethod {

    public static volatile APIMethod api;

    /**
     * @return
     */
    public static APIMethod getInstance() {
        if (api == null) {
            api = new APIMethod();
        }
        return api;
    }

    //#################################################################### 具体页面接口

    /**
     * 示例：post请求，获取json格式返回数据
     *
     * @param context
     * @return
     */
    public static Observable<QueryAdvertBean> queryAdvert(Context context) {
        //=== 参数设置
        HashMap<String, Object> params = new HashMap<>();

        //===
        return OkGoRequest.postJsonRequestDef(context, APIConstant.QUERYADVERT, params).getCall(new JsonConvert<QueryAdvertBean>() {
        }, RxAdapter.<QueryAdvertBean>create());
    }

    /**
     * 示例：获取图片
     *
     * @param context
     * @return
     */
    public static Observable<Bitmap> getBitmap(Context context) {
        //=== 参数设置
        HashMap<String, String> params = new HashMap<>();

        //===
        return OkGoRequest.postParamRequestDef(context, APIConstant.URL_IMAGE, params).getCall(BitmapConvert.create(), RxAdapter.<Bitmap>create());
    }

    /**
     * 文件上传，不带进度条
     *
     * @param context
     * @param files
     * @return
     */
    public static Observable<String> uploadFile(Context context, ArrayList<File> files) {
        //=== 参数设置
        HashMap<String, String> params = new HashMap<>();

        //===
        return OkGoRequest.formUploadRequestDef(context, APIConstant.URL_FORM_UPLOAD, params, files).getCall(StringConvert.create(), RxAdapter.<String>create());
    }

    /**
     * 文件上传，带有进度条，使用回调方式
     *
     * @param context
     * @param url
     * @param params
     * @param files
     * @param callback
     */
    public static void formUploadRequestWithCallback(Context context, String url, HashMap<String, String> params,
                                                     ArrayList<File> files, JsonCallback callback) {
        OkGo.post(url)
                .tag(context)//以对应activity或fragment作为网络请求tag，以便即时取消网络请求
                .params(params)
                .isMultipart(true)         //强制使用 multipart/form-data 表单上传（只是演示，不需要的话不要设置。默认就是false）
                .addFileParams("file", files)
                .execute(callback);
    }

    /**
     * 文件下载,不带进度条
     *
     * @param context
     * @return
     */
    public static Observable<File> downloadFile(Context context) {
        //=== 参数设置
        HashMap<String, String> params = new HashMap<>();

        //===
        return OkGoRequest.postParamRequestDef(context, APIConstant.URL_DOWNLOAD, params).getCall(new FileConvert(), RxAdapter.<File>create());
    }

    /**
     * 文件下载，进度条，使用回调方式
     *
     * @param context
     * @param url
     * @param params
     * @param callback
     */
    public static void downloadFileWithCallback(Context context, String url, HashMap<String, String> params,
                                                FileCallback callback) {
        OkGo.get(url)//
                .tag(context)//
                .params(params)
                .execute(callback);
    }

}
