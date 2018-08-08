package com.bluemobi.zhongyou.mvp.view;

/**
 * Created by ${chenM} on ${2017}.
 */
public interface BaseView {
    void showLoading();
    void showLoading(String title);
    void hideLoading();
    void showError(String errorMessage);
}
