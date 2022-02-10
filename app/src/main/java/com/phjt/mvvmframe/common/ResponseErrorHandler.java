package com.phjt.mvvmframe.common;

import android.content.Context;
import android.net.ParseException;
import android.widget.Toast;

import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.phjt.module_base.http.ResponseErrorListener;
import com.phjt.module_base.utils.AppContextUtil;
import com.phjt.module_base.utils.log.LogUtils;

import org.json.JSONException;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;
import timber.log.Timber;

/**
 * @author: gengyan
 * date:    2021/9/18
 * company: GY
 * description: 网络请求错误处理
 */
public class ResponseErrorHandler {

    public static void handleResponseError(Throwable t) {
        String msg = "抱歉,访问出了点问题哦!";
        if (t instanceof UnknownHostException) {
            msg = "网络不可用";
        } else if (t instanceof SocketTimeoutException) {
            msg = "请求网络超时";
        } else if (t instanceof HttpException) {
            HttpException httpException = (HttpException) t;
            msg = convertStatusCode(httpException);
        } else if (t instanceof JsonParseException || t instanceof ParseException || t instanceof JSONException || t instanceof JsonIOException) {
            msg = "数据解析错误";
        }
        //TODO 替换为ToastUtils
        Toast.makeText(AppContextUtil.getInstance(), msg, Toast.LENGTH_SHORT).show();
    }

    private static String convertStatusCode(HttpException httpException) {
        String msg;
        if (httpException.code() == 500) {
            msg = "服务器发生错误";
        } else if (httpException.code() == 404) {
            msg = "请求地址不存在";
        } else if (httpException.code() == 403) {
            msg = "请求被服务器拒绝";
        } else if (httpException.code() == 307) {
            msg = "请求被重定向到其他页面";
        } else {
            msg = httpException.message();
        }
        LogUtils.e("httpException:" + httpException);
        return "数据加载失败";
    }
}
