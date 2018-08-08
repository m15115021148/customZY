package com.bluemobi.zhongyou.bean;

import java.util.List;

/**
 * Created by ${chenM} on ${2017}.
 */
public class PrinterBean {
    private String mTitle ;//标题
    private String mOrderNumber ;//订单号
    private String mTime ;//时间
    private String mType;//oil ro goods
    private String mName;//名称
    private String mNumber;//数量
    private String mPrice;//单价
    private String mTotalPrice ;//总价
    private String mOffer ;//优惠金额
    private String mActualAmount ;//实际金额
    private String mPayment;

    private int orderNumber;
    private String time;
    private double totalPrice;
    private double offer;
    private double actualAmount;
    private String payment;

    private List<Printers> data;

    private String qrTitle ;
    private String qrContent = "123456789";

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmOrderNumber() {
        return mOrderNumber;
    }

    public void setmOrderNumber(String mOrderNumber) {
        this.mOrderNumber = mOrderNumber;
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmNumber() {
        return mNumber;
    }

    public void setmNumber(String mNumber) {
        this.mNumber = mNumber;
    }

    public String getmPrice() {
        return mPrice;
    }

    public void setmPrice(String mPrice) {
        this.mPrice = mPrice;
    }

    public String getmTotalPrice() {
        return mTotalPrice;
    }

    public void setmTotalPrice(String mTotalPrice) {
        this.mTotalPrice = mTotalPrice;
    }

    public String getmOffer() {
        return mOffer;
    }

    public void setmOffer(String mOffer) {
        this.mOffer = mOffer;
    }

    public String getmActualAmount() {
        return mActualAmount;
    }

    public void setmActualAmount(String mActualAmount) {
        this.mActualAmount = mActualAmount;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getOffer() {
        return offer;
    }

    public void setOffer(double offer) {
        this.offer = offer;
    }

    public double getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(double actualAmount) {
        this.actualAmount = actualAmount;
    }

    public List<Printers> getData() {
        return data;
    }

    public void setData(List<Printers> data) {
        this.data = data;
    }

    public String getQrTitle() {
        return qrTitle;
    }

    public void setQrTitle(String qrTitle) {
        this.qrTitle = qrTitle;
    }

    public String getQrContent() {
        return qrContent;
    }

    public void setQrContent(String qrContent) {
        this.qrContent = qrContent;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    public String getmPayment() {
        return mPayment;
    }

    public void setmPayment(String mPayment) {
        this.mPayment = mPayment;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public static class Printers {
        private String type;
        private String name;
        private String number;
        private double price;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }
    }

}
