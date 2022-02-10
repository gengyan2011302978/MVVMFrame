package com.phjt.module_base.http;

import android.content.Context;

/**
 * @author: gengyan
 * date:    2021/9/8
 * company: GY
 * description: 描述
 */
public interface ResponseErrorListener {

    void handleResponseError(Throwable t);

    ResponseErrorListener EMPTY = new ResponseErrorListener() {
        @Override
        public void handleResponseError(Throwable t) {

        }
    };
}
