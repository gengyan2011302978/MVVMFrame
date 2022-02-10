package com.phjt.mvvmframe.app;

import com.phjt.module_base.http.GlobalHttpHandler;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author: gengyan
 * date:    2021/9/8 15:50
 * company: GY
 * description: 请求拦截 请求发出前 与 请求响应后 提前得到 {@link Request} 和  {@link Response}
 */
public class GlobalHttpHandlerImpl implements GlobalHttpHandler {


    /**
     * 这里可以先客户端一步拿到每一次 Http 请求的结果, 可以先解析成 Json, 再做一些操作, 如检测到 token 过期后
     * 重新请求 token, 并重新执行请求
     *
     * @param httpResult 服务器返回的结果 (已被框架自动转换为字符串)
     * @param chain      {@link Interceptor.Chain}
     * @param response   {@link Response}
     * @return {@link Response}
     */
    @NonNull
    @NotNull
    @Override
    public Response onHttpResultResponse(@Nullable @org.jetbrains.annotations.Nullable String httpResult, @NonNull @NotNull Interceptor.Chain chain, @NonNull @NotNull Response response) {
        return response;
    }

    /**
     * 这里可以在请求服务器之前拿到 {@link Request}, 做一些操作比如给 {@link Request} 统一添加 token 或者 header 以及参数加密等操作
     *
     * @param chain   {@link Interceptor.Chain}
     * @param request {@link Request}
     * @return {@link Request}
     */
    @NonNull
    @NotNull
    @Override
    public Request onHttpRequestBefore(@NonNull @NotNull Interceptor.Chain chain, @NonNull @NotNull Request request) {
        return request;
    }
}
