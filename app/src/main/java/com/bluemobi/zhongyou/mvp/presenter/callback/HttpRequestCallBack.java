package com.bluemobi.zhongyou.mvp.presenter.callback;

/**
 * Created by ${chenM} on ${2017}.
 */
public interface HttpRequestCallBack {
    void onSuccess(String result);
    void onFailure(String error);
    void onCancel();
}
