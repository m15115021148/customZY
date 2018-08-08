package com.bluemobi.zhongyou.view;


public interface DateListener {
    void onReturnDate(String time, int year, int month, int day, int hour, int minute, int isShowType);

    void onReturnDate(String empty);
}
